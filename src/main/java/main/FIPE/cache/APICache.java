package main.FIPE.cache;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.FIPE.pojo.CarData;
import main.FIPE.pojo.GenericItem;

import java.io.File;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class APICache {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String cacheSearchYearsByBrandAndModel(int brandCode, int modelCode, String ano)
            throws IOException, InterruptedException {
        String cacheFileName = "src/main/resources/cache/years/"+ brandCode + "_" + modelCode + ".json";
        Path path = Paths.get(cacheFileName);
        List<GenericItem> items;

        if (Files.exists(path)) {
            try (FileReader reader = new FileReader(path.toString())) {
                items = Arrays.asList(mapper.readValue(reader, GenericItem[].class));
            }
        } else {
            HttpResponse<String> response = apiCallGetYearsByBrandAndModel(brandCode,modelCode);
            if (response.statusCode() != 200) {
                return null;
            }

            try (FileWriter newFile = new FileWriter(cacheFileName)) {
                newFile.write(response.body());
            }
            items = Arrays.asList(mapper.readValue(response.body(), GenericItem[].class));
        }

        items.forEach(it -> it.setName(String.valueOf(modelCode)));

        return items.stream()
                .map(GenericItem::getCode)
                .filter(code -> ano != null && code.contains(ano))
                .findFirst()
                .orElse(null);

    }

    private static HttpResponse<String> apiCallGetYearsByBrandAndModel(int brandCode, int modelCode) throws IOException, InterruptedException {
        Thread.sleep(1000);
        String url ="https://fipe.parallelum.com.br/api/v2/cars/brands/" + brandCode
                + "/models/" + modelCode + "/years";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

}
