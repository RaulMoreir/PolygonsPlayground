package main.FIPE.services;

import main.FIPE.cache.APICache;
import main.FIPE.models.CarData;
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

    public void run() throws IOException {
        List<CarData> carsExtracted = scrapper.scrapeAuctionCars();
        CsvWriter csv = new CsvWriter(System.currentTimeMillis()+"Report.csv");
        carsExtracted.forEach(carData -> {
            System.out.println("Verificando dados da FIPE...");
            try {
                List<FipeResponse> response = matcher.carregarPossiveisModelos(carData);

                csv.writeAll( response );
                csv.blankLine();

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
        csv.close();
    }


}

