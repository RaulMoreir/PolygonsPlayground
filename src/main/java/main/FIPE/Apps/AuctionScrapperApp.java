package main.FIPE.Apps;

import main.FIPE.services.APICache;
import main.FIPE.models.CarData;
import main.FIPE.models.CarMatchReport;
import main.FIPE.models.FipeResponse;
import main.FIPE.services.*;

import java.io.IOException;
import java.util.*;

public class AuctionScrapperApp {

    private final AuctionScraperService scrapper = new AuctionScraperService();
    private final GetIModelFromBrandID fetcher = new GetIModelFromBrandID();
    private final FilterIModelByName filter = new FilterIModelByName();
    private final FullCarInformationFromAPII info = new FullCarInformationFromAPII();
    private final APICache cache = new APICache();

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

