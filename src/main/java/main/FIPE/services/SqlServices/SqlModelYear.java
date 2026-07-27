package main.FIPE.services.SqlServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.FIPE.models.GenericItem;
import main.FIPE.services.database.ConnectionFactory;
import main.FIPE.services.interfaces.IModelYearCache;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SqlModelYear implements IModelYearCache {
    @Override
    public String cacheSearchYearsByBrandAndModel(int brandCode, int modelCode, String ano)
            throws IOException, InterruptedException {
        GenericItem yearFromApi;
        String years = searchYearsInDatabase(modelCode, ano);

       if (years != null && years.contains(ano)) {
            return years;
            // "2014-x"
        }else{
            yearFromApi = searchYearsInApi(brandCode, modelCode, ano);
            if (yearFromApi != null) {
                saveYears(brandCode, modelCode, yearFromApi.getCode(), yearFromApi.getName());
                return yearFromApi.getCode();
            }
        }

        return null;
    }

    private String searchYearsInDatabase(int modelCode, String ano) {
        String sql = "select year_code from model_years where model_code = ? and year_code LIKE ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, modelCode);
            statement.setString(2, "%"+ano.substring(4)+"%");

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("year_code");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private GenericItem searchYearsInApi(int brandCode, int modelCode, String ano)
            throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        String url = "https://fipe.parallelum.com.br/api/v2/cars/brands/" + brandCode
                + "/models/" + modelCode
                + "/years";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro na API: " + response.statusCode());
        }

        GenericItem yearAndCodeFromApi = parseYearsResponse(response.body(), ano);

        return yearAndCodeFromApi;
    }

    private GenericItem parseYearsResponse(String json, String ano) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<GenericItem> yearsFromApi = Arrays.asList(mapper.readValue(json, GenericItem[].class));

        return yearsFromApi.stream()
                .filter(item -> item.getName() != null && item.getName().contains(ano))
                .findFirst()
                .orElse(null);
    }

    private void saveYears(int brandCode, int modelCode, String yearCode, String yearName) {
        String sql = "insert into model_years (brand_code, model_code, year_code, name) values (?, ?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, brandCode);
                statement.setInt(2, modelCode);
                statement.setString(3, yearCode);
                statement.setString(4,yearName);
                // 2006 flex
                statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
