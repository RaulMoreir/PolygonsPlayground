package main.FIPE.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExternalFipeApiConsumer {

    public static HttpResponse<String> ApiCallToGetFullInfo(int brandCode, String modelCode, String carYear)
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


    public static HttpResponse<String> apiCallGetYearsByBrandAndModel(int brandCode, int modelCode) throws IOException, InterruptedException {
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
