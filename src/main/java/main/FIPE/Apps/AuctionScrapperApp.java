package main.FIPE.Apps;

import main.FIPE.services.JsonServices.YearSearch;
import main.FIPE.models.CarData;
import main.FIPE.models.CarMatchReport;
import main.FIPE.models.FipeResponse;
import main.FIPE.services.*;
import main.FIPE.services.FilterIModelByName;
import main.FIPE.services.FipeModelMatcherService;
import main.FIPE.services.JsonServices.FullCarInformationFromAPI;
import main.FIPE.services.JsonServices.JsonModelFetcher;

import java.io.IOException;
import java.util.*;

public class AuctionScrapperApp {

    private final AuctionScraperService scrapper = new AuctionScraperService();
    private final JsonModelFetcher fetcher = new JsonModelFetcher();
    private final FilterIModelByName filter = new FilterIModelByName();
    private final FullCarInformationFromAPI info = new FullCarInformationFromAPI();
    private final YearSearch cache = new YearSearch();

    private final FipeModelMatcherService matcher = new FipeModelMatcherService(fetcher, filter, info, cache);

    public void runJsonDatabase() throws IOException{
        List<CarData> carsExtracted = scrapper.scrapeAuctionCars();
        CsvWriter csv = new CsvWriter(System.currentTimeMillis()+"Report.csv");
        // montagem e escrita do writer
        try{
            //para cada UNIDADE CarData contido em carsExtracted..
            for (CarData carData : carsExtracted){
                System.out.println("pegamos UM carro hehe");
                try {
                    //chama o bloco de possiveis modelos
                    List<FipeResponse> response = matcher.carregarPossiveisModelos(carData);
                    //se carregou entao escreve no relatorio (adiciona ao final)
                    CarMatchReport report = new CarMatchReport(carData , response);
                    csv.writeCarReport(report);

                }catch (InterruptedException e ){
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupção ao consultar FIPE", e);}
            }

        }finally {
            csv.close();
        }

    }

    public void runSqliteDatabase() throws IOException {
        List<CarData> carsExtracted = scrapper.scrapeAuctionCars();
        CsvWriter csv = new CsvWriter(System.currentTimeMillis()+"Report.csv");


    }

}

