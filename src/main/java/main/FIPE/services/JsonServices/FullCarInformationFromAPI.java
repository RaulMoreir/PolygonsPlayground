package main.FIPE.services.JsonServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.FIPE.models.FipeResponse;
import main.FIPE.services.interfaces.IModelGetFullInfo;

import java.io.FileWriter;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static main.FIPE.services.ExternalFipeApiConsumer.ApiCallToGetFullInfo;

public class FullCarInformationFromAPI implements IModelGetFullInfo {
    private final ObjectMapper mapper = new ObjectMapper();

    public FipeResponse getFullCarInformation(int brandCode, String modelCode, String carYear) throws IOException, InterruptedException {
        if (carYear == null || carYear.isEmpty()){
            return null;
        }

        String cacheFileName = "src/main/resources/cache/FullCarInfo/"+
                brandCode + "_" +
                modelCode + "_" +
                carYear + "_" +
                checkWhichMonth() +
                ".json";
        Path path = Paths.get(cacheFileName);

        //String item;

        FipeResponse fipeResponse = new FipeResponse();
        if (Files.exists(path)) {
            //item = Files.readString(path);
            fipeResponse = mapper.readValue(Files.readString(path),FipeResponse.class);

        } else {
            HttpResponse<String> response = ApiCallToGetFullInfo(brandCode,modelCode,carYear);
            if (response.statusCode() != 200) {
                return null;
            }

            try (FileWriter newFile = new FileWriter(cacheFileName)) {
                newFile.write(response.body());
            }
            //item = response.body();
            fipeResponse = mapper.readValue(response.body(), FipeResponse.class);
        }
        return fipeResponse;
    }

    public String checkWhichMonth (){
        String date = String.valueOf(LocalDate.now());
        String month = date.trim().substring(5,7);

        return month;
    }

}
