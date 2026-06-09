package main.FIPE.services.database.Repositorys;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.FIPE.models.GenericItem;
import main.FIPE.models.ModelData;
import main.FIPE.services.database.ConnectionFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModelRepository {
    public static void insertModel(int brandId, String modelCode, String modelName){
        String sql = "INSERT or ignore INTO models (brand_code, fipe_code, name) VALUES (?,?,?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, brandId);
            stmt.setString(2, modelCode);
            stmt.setString(3, modelName);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadIntoDatabase() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Path pasta = Paths.get("src/main/resources/JsonModels");
        try {
            Files.list(pasta).forEach(arquive -> {
                try{
                    ModelData data = mapper.readValue(arquive.toFile(), ModelData.class);

                    for (GenericItem model : data.getModels()) {
                        ModelRepository.insertModel(
                                data.getFipeModelCode(),
                                model.getCode(),
                                model.getName() );

                        System.out.println(
                                "Brand: " + data.getFipeModelCode() +
                                        " | Model: " + model.getCode() +
                                        " | " + model.getName()
                        );
                        System.out.println("salvou no .db" );}

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
