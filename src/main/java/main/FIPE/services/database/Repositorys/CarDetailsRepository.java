package main.FIPE.services.database.Repositorys;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.FIPE.models.FipeResponse;
import main.FIPE.services.database.ConnectionFactory;
import main.FIPE.services.database.DatabaseSetup;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Stream;

public class CarDetailsRepository {

    public static void insertCarDetails(FipeResponse car){
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


//            stmt.setInt(1, car.getVehicleType());
            stmt.setString(2, car.getPrice());
            stmt.setString(3, car.getBrand());

            stmt.setString(4, car.getModel());
//            stmt.setInt(5, car.getModelYear());
            stmt.setString(6, car.getFuel());

            stmt.setString(7, car.getCodeFipe());
            stmt.setString(8, car.getReferenceMonth());
            stmt.setString(9, car.getFuelAcronym());

//            stmt.setInt(10, car.getBrandCodeFK());
//            stmt.setInt(11, car.getModelCodeFK());
//            stmt.setString(12, car.getModelYearCodeFK());


            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void loadCarDetailInRepo(){
        DatabaseSetup.setup();
        ObjectMapper mapper = new ObjectMapper();

        Path path = Paths.get("src/main/resources/cache/FullCarInfo");
        try  (Stream<Path> arquivos = Files.list(path)){
            arquivos.forEach(arquivo -> {

                try{
                    String nomeArquivo = arquivo.getFileName()
                            .toString()
                            .replace(".json", "");
                    String[] partes = nomeArquivo.split("_");

                    FipeResponse response = mapper.readValue(Files.readString(arquivo),
                            FipeResponse.class);


                        //response.setBrandCodeFK(Integer.parseInt(partes[0]));
                        //response.setModelCodeFK(Integer.parseInt(partes[1]));
                        //response.setModelYearCodeFK(partes[2]);

                        CarDetailsRepository.insertCarDetails(response);


                } catch (Exception e) {
                    throw new RuntimeException("Erro ao processar arquivo: " + arquivo,
                            e);
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
