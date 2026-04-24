package main.FIPE.services;

import main.FIPE.cache.APICache;
import main.FIPE.models.CarData;
import main.FIPE.models.CarMatchReport;
import main.FIPE.models.FipeResponse;

import java.io.IOException;
import java.util.*;

public class NewFIPE {

    private final AuctionScraperService scrapper = new AuctionScraperService();
    private final GetIModelFromBrandID fetcher = new GetIModelFromBrandID();
    private final FilterIModelNamesProgressivelyByName filter = new FilterIModelNamesProgressivelyByName();
    private final FullCarInformationFromAPII info = new FullCarInformationFromAPII();
    private final APICache cache = new APICache();

    private final FipeModelMatcherService matcher = new FipeModelMatcherService(fetcher, filter, info, cache);

    public void run2() throws IOException{
        List<CarData> carsExtracted = scrapper.scrapeAuctionCars();
        CsvWriter csv = new CsvWriter(System.currentTimeMillis()+"Report.csv");

        // montagem e escrita do writer
        try{
            //para cada UNIDADE CarData contido em carsExtracted..
            for (CarData carData : carsExtracted){
                System.out.println("pegano os carro hehe");
                try {
                    //chama o bloco de possiveis modelos
                    List<FipeResponse> response = matcher.carregarPossiveisModelos(carData);

                    CarMatchReport report = new CarMatchReport(carData , response);
                    csv.writeCarReport(report);

                }catch (InterruptedException e ){
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupção ao consultar FIPE", e);}
            }

        }finally {
            csv.close();
        }

    }

}

