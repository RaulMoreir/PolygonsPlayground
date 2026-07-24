package main.FIPE.Apps;

import main.FIPE.models.*;
import main.FIPE.services.*;
import main.FIPE.services.JsonServices.*;
import main.FIPE.services.SqlServices.SqlModelFetcher;
import main.FIPE.services.SqlServices.SqlModelFullInfo;
import main.FIPE.services.SqlServices.SqlModelYear;
import main.FIPE.services.interfaces.IModelFetcher;
import main.FIPE.services.interfaces.IModelGetFullInfo;
import main.FIPE.services.interfaces.IModelYearCache;

import java.io.IOException;
import java.util.List;

public class SearchService {


    private final FipeModelMatcherService matcher ;

    public SearchService(SearchModeEnum mode) {

        IModelFetcher fetcher;
        IModelGetFullInfo info;
        IModelYearCache yearSearch;

        switch (mode) {
            case JSON -> {
                fetcher = new JsonModelFetcher();
                info = new FullCarInformationFromAPII();
                yearSearch = new YearSearch();

            }
            case SQLITE_DATABASE -> {
                fetcher = new SqlModelFetcher();
                info = new SqlModelFullInfo();
                yearSearch = new SqlModelYear();
            }
            default -> throw new IllegalArgumentException("Modo inválido: " + mode);
        }

        FilterIModelByName filter = new FilterIModelByName();

        matcher = new FipeModelMatcherService(fetcher, filter, info, yearSearch);
    }

    public void SearchCar(String marca, String modelo, String ano) throws IOException {

        CarData carData = new CarData();
        carData.setBrand(marca);
        carData.setBrandEnum(BrandsEnum.fromString(marca.toUpperCase()));
        carData.setModel(modelo);
        carData.setModelYear(ano);

        CsvWriter csv = new CsvWriter("Pesquisa_"+ marca + "_" + modelo + "_" + ano +"_Report.csv");

        try {
            // montagem e escrita do writer
            System.out.println("pegamos UM carro hehe");

            //chama o bloco de possiveis modelos
            List<FipeResponse> response = matcher.carregarPossiveisModelos(carData);
            //se carregou entao escreve no relatorio (adiciona ao final)
            CarMatchReport report = new CarMatchReport(carData , response);
            csv.writeCarReport(report);

        }catch (InterruptedException e ){
            e.printStackTrace();
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupção ao consultar FIPE", e);

        }finally {
            csv.close();
        }


    }
}
