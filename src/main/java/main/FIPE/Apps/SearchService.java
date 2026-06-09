package main.FIPE.Apps;

import main.FIPE.models.BrandsEnum;
import main.FIPE.models.CarData;
import main.FIPE.models.CarMatchReport;
import main.FIPE.models.FipeResponse;
import main.FIPE.services.*;

import java.io.IOException;
import java.util.List;

public class SearchService {
    private final GetIModelFromBrandID fetcher = new GetIModelFromBrandID();
    private final FilterIModelByName filter = new FilterIModelByName();
    private final FullCarInformationFromAPII info = new FullCarInformationFromAPII();
    private final APICache cache = new APICache();

    private final FipeModelMatcherService matcher = new FipeModelMatcherService(fetcher, filter, info, cache);

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
