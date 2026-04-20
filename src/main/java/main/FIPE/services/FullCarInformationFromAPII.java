package main.FIPE.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.FIPE.models.GenericItem;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class FullCarInformationFromAPII implements IModelGetFullInfo {

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

    private static HttpResponse<String> ApiCallToGetFullInfo(int brandCode, String modelCode, String carYear)
            throws InterruptedException, IOException {
        Thread.sleep(1000);
        String url ="https://fipe.parallelum.com.br/api/v2/cars/brands/" + brandCode
                + "/models/" + modelCode + "/years/" + carYear.trim();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    public String checkWhichMonth (){
        String date = String.valueOf(LocalDate.now());
        String month = date.trim().substring(5,7);

        return month;
    }

}
