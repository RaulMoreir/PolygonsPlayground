package main.FIPE.services.JsonServices;

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

    public String getFullCarInformation(int brandCode, String modelCode, String carYear) throws IOException, InterruptedException {
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

        String item;
        if (Files.exists(path)) {
            item = Files.readString(path);

        } else {
            HttpResponse<String> response = ApiCallToGetFullInfo(brandCode,modelCode,carYear);
            if (response.statusCode() != 200) {
                return null;
            }

            try (FileWriter newFile = new FileWriter(cacheFileName)) {
                newFile.write(response.body());
            }
            item = response.body();
        }
        return item;
    }

    public String checkWhichMonth (){
        String date = String.valueOf(LocalDate.now());
        String month = date.trim().substring(5,7);

        return month;
    }

}
