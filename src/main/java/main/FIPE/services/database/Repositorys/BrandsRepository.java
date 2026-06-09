package main.FIPE.services.database.Repositorys;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.FIPE.models.GenericItem;
import main.FIPE.services.database.ConnectionFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BrandsRepository {

    public void insertBrand(String code, String name){

        String sql = "INSERT OR IGNORE INTO brands (fipe_code, name) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, code);
            stmt.setString(2, name);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadBrandsIndoRepo() throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        Path path = Paths.get("src/main/resources/brands.json");
        try {
            List<GenericItem> brands = mapper.readValue(Files.readString(path), new TypeReference<List<GenericItem>>() {
            });
            BrandsRepository repository = new BrandsRepository();

            for (GenericItem brand : brands) {
                repository.insertBrand(
                        brand.getCode(),
                        brand.getName()
                );

                System.out.println(
                        "salvou: " +
                                brand.getCode() +
                                " - " +
                                brand.getName()
                );

            }
        }catch (Exception e) {
            throw new RuntimeException(e);

    }
    }

    private static HttpResponse<String> apiCallGetModelsFromBrand() throws IOException, InterruptedException {
        Thread.sleep(1000);
        String url ="https://fipe.parallelum.com.br/api/v2/cars/brands/";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }
}
