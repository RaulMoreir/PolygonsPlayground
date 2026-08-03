package main.FIPE.services.SqlServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.FIPE.models.GenericItem;
import main.FIPE.services.database.ConnectionFactory;
import main.FIPE.services.interfaces.IModelYearCache;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static main.FIPE.services.ExternalFipeApiConsumer.apiCallGetYearsByBrandAndModel;

public class SqlModelYear implements IModelYearCache {

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public String cacheSearchYearsByBrandAndModel(int brandCode, int modelCode, String ano)
            throws IOException, InterruptedException {
        String years = searchYearsInDatabase(modelCode, ano);
        List<GenericItem> items;

        if (years != null && years.contains(ano)) {
            return years;
            // "2014-x"
        }else{
            HttpResponse<String> response = apiCallGetYearsByBrandAndModel(brandCode,modelCode);
            if (response.statusCode() != 200) {
                return null;
            }

            items = Arrays.asList(mapper.readValue(response.body(), GenericItem[].class));
            items.forEach(item -> {
                saveYears(brandCode, modelCode, item.getCode(), item.getName());
            });
        }

        return items.stream()
                .map(GenericItem::getCode)
                .filter(code -> code.contains(ano))
                .findFirst()
                .orElse(null);
    }

    private String searchYearsInDatabase(int modelCode, String ano) {
        String sql = "select year_code from model_years where model_code = ? and year_code LIKE ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, modelCode);
            statement.setString(2, "%"+ano.substring(0, 4)+"%");

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


    private void saveYears(int brandCode, int modelCode, String yearCode, String yearName) {
        String sql = "insert or ignore into model_years (brand_code, model_code, year_code, name) values (?, ?, ?, ?)";

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
