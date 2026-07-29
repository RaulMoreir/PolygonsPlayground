package main.FIPE.services.SqlServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.FIPE.models.FipeResponse;
import main.FIPE.services.ExternalFipeApiConsumer;
import main.FIPE.services.database.ConnectionFactory;
import main.FIPE.services.interfaces.IModelGetFullInfo;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlModelFullInfo implements IModelGetFullInfo {

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public FipeResponse getFullCarInformation(int BrandCode, String ModelCode, String carYear) throws IOException, InterruptedException {
        List<FipeResponse> fullCarInfoList = searchInDatabase(ModelCode, carYear);

        if (!fullCarInfoList.isEmpty()) {
            return fullCarInfoList.get(0);
        }

        HttpResponse<String> fullCarInfo = ExternalFipeApiConsumer.ApiCallToGetFullInfo(BrandCode, ModelCode, carYear);
        if (fullCarInfo.statusCode() != 200) {
            return null;
        }
        FipeResponse car = mapper.readValue(fullCarInfo.body(), FipeResponse.class);

        insertCarDetails(car,BrandCode ,ModelCode ,carYear);
        return car;
    }


    private List<FipeResponse> searchInDatabase (String ModelCode, String carYear){
        String sql =
        "SELECT " +
        "c.*, " +
        "b.name AS brand, " +
        "m.name AS model " +
        "FROM car_details c " +
        "INNER JOIN brands b " +
        "ON c.brand_code = b.brand_code " +
        "INNER JOIN models m " +
        "ON c.brand_code = m.brand_code " +
        "AND c.model_code = m.model_code " +
        "WHERE c.model_code = ? " +
        "AND c.model_year = ?";

        carYear = carYear.substring(4);
        List<FipeResponse> carslist = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, Integer.parseInt(ModelCode));
            statement.setInt(2, Integer.parseInt(carYear));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    FipeResponse item = new FipeResponse();


                    item.setVehicleType(resultSet.getString("vehicle_type"));//tem q fazer o sql retornar
                    item.setPrice(resultSet.getString("price"));
                    item.setBrand(resultSet.getString("brand"));

                    item.setModel (resultSet.getString("model"));
                    item.setModelYear(resultSet.getString("model_year"));
                    item.setFuel(resultSet.getString("fuel"));

                    item.setCodeFipe(resultSet.getString("code_fipe"));
                    item.setReferenceMonth(resultSet.getString("reference_month"));
                    item.setFuelAcronym(resultSet.getString("fuel_acronym"));

//                    item.setBrandCodeFK(resultSet.getInt("brand_code"));
//                    item.setModelCodeFK(resultSet.getInt("model_code"));
//                    item.setModelYearCodeFK(resultSet.getString("year_code"));


                    carslist.add(item);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return carslist;

    }

    public static void insertCarDetails(FipeResponse car,int brandCode, String modelCode, String yearCode){
        String sql = """
        INSERT OR IGNORE INTO car_details (
            vehicle_type,
            price,
            model_year,
            fuel,
            code_fipe,
            reference_month,
            fuel_acronym,
            brand_code,
            model_code,
            year_code
        )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";

        try (Connection conn = ConnectionFactory.getConnection();


             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, Integer.parseInt(car.getVehicleType()));
            stmt.setString(2, car.getPrice());

            stmt.setInt(3, Integer.parseInt(car.getModelYear()));
            stmt.setString(4, car.getFuel());

            stmt.setString(5, car.getCodeFipe());
            stmt.setString(6, car.getReferenceMonth());
            stmt.setString(7, car.getFuelAcronym());

            stmt.setInt(8, brandCode);
            stmt.setInt(9, Integer.parseInt(modelCode));
            stmt.setString(10, yearCode);


            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
