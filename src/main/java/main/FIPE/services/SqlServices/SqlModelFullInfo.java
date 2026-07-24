package main.FIPE.services.SqlServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.FIPE.models.FipeResponse;
import main.FIPE.models.FipeResponseSQLFormated;
import main.FIPE.models.GenericItem;
import main.FIPE.services.database.ConnectionFactory;
import main.FIPE.services.interfaces.IModelGetFullInfo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlModelFullInfo implements IModelGetFullInfo {
    @Override
public String getFullCarInformation(int BrandCode, String ModelCode, String carYear) throws IOException, InterruptedException {
        List<FipeResponseSQLFormated> fullCarInfoList = searchInDatabase(ModelCode, carYear);

        if (!fullCarInfoList.isEmpty()) {
            return fullCarInfoList.get(0).toString();
        }

        FipeResponseSQLFormated fullCarInfo = searchInApi(BrandCode, ModelCode, carYear);
        insertCarDetails(fullCarInfo);

        return fullCarInfo.toString();
    }

    private List<FipeResponseSQLFormated> searchInDatabase (String ModelCode, String carYear){
        String sql = "select * from car_details where model_code = ? and model_year = ?";
        carYear = carYear.substring(4);
        List<FipeResponseSQLFormated> carslist = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, Integer.parseInt(ModelCode));
            statement.setInt(2, Integer.parseInt(carYear));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    FipeResponseSQLFormated item = new FipeResponseSQLFormated();


                    item.setVehicleType(resultSet.getInt("vehicle_type"));
                    item.setPrice(resultSet.getString("price"));
                    item.setBrand(resultSet.getString("brand"));

                    item.setModel (resultSet.getString("model"));
                    item.setModelYear(resultSet.getInt("model_year"));
                    item.setFuel(resultSet.getString("fuel"));

                    item.setCodeFipe(resultSet.getString("code_fipe"));
                    item.setReferenceMonth(resultSet.getString("reference_month"));
                    item.setFuelAcronym(resultSet.getString("fuel_acronym"));

                    item.setBrandCodeFK(resultSet.getInt("brand_code"));
                    item.setModelCodeFK(resultSet.getInt("model_code"));
                    item.setModelYearCodeFK(resultSet.getString("year_code"));


                    carslist.add(item);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return carslist;

    }


    private FipeResponseSQLFormated searchInApi (int brandCode, String modelCode, String ano) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String url = "https://fipe.parallelum.com.br/api/v2/cars/brands/" + brandCode
                + "/models/" + modelCode
                + "/years/" + ano;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro na API: " + response.statusCode());
        }

        ObjectMapper mapper = new ObjectMapper();
        FipeResponse apiResponse = mapper.readValue(response.body(), FipeResponse.class);

        FipeResponseSQLFormated car = new FipeResponseSQLFormated();

        car.setVehicleType(Integer.parseInt(apiResponse.getVehicleType()));
        car.setPrice(apiResponse.getPrice());
        car.setBrand(apiResponse.getBrand());

        car.setModel(apiResponse.getModel());
        car.setModelYear(Integer.parseInt(apiResponse.getModelYear()));
        car.setFuel(apiResponse.getFuel());

        car.setCodeFipe(apiResponse.getCodeFipe());
        car.setReferenceMonth(apiResponse.getReferenceMonth());
        car.setFuelAcronym(apiResponse.getFuelAcronym());

        car.setBrandCodeFK(brandCode);
        car.setModelCodeFK(Integer.parseInt(modelCode));
        car.setModelYearCodeFK(ano);

        return car;
    }

    public static void insertCarDetails(FipeResponseSQLFormated car){
        String sql = "INSERT OR IGNORE INTO car_details (" +
                "vehicle_type," +
                "price, " +
                "brand, " +
                "model, " +
                "model_year, " +
                "fuel, " +
                "code_fipe, " +
                "reference_month, " +
                "fuel_acronym, " +
                "brand_code, " +
                "model_code, " +
                "year_code) " +
                "VALUES (?,?,?," + "?,?,?," +  "?,?,?," + "?,?,?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, car.getVehicleType());
            stmt.setString(2, car.getPrice());
            stmt.setString(3, car.getBrand());

            stmt.setString(4, car.getModel());
            stmt.setInt(5, car.getModelYear());
            stmt.setString(6, car.getFuel());

            stmt.setString(7, car.getCodeFipe());
            stmt.setString(8, car.getReferenceMonth());
            stmt.setString(9, car.getFuelAcronym());

            stmt.setInt(10, car.getBrandCodeFK());
            stmt.setInt(11, car.getModelCodeFK());
            stmt.setString(12, car.getModelYearCodeFK());


            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
