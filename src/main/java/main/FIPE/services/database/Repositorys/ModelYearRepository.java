package main.FIPE.services.database.Repositorys;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.FIPE.models.GenericItem;
import main.FIPE.services.database.ConnectionFactory;
import main.FIPE.services.database.DatabaseSetup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ModelYearRepository {

    public static void insertModelYear(int brandCode,int modelCode, String yearCode, String nameCode){
        String sql = "INSERT INTO model_years (brand_code,model_code,year_code,name) VALUES (?,?,?,?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, brandCode);
            stmt.setInt(2, modelCode);
            stmt.setString(3, yearCode);
            stmt.setString(4, nameCode);

            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void loadYearsIntoDatabase() {
        DatabaseSetup.setup();
        ObjectMapper mapper = new ObjectMapper();

        Path pasta = Paths.get("src/main/resources/cache/years");

        try {
            Files.list(pasta).forEach(arquivo -> {
                try {

                    String nomeArquivo = arquivo.getFileName()
                            .toString()
                            .replace(".json", "");
                    String[] partes = nomeArquivo.split("_");
                    System.out.println(nomeArquivo + " -> " + Arrays.toString(partes));

                    int brandCode = Integer.parseInt(partes[0]);
                    int modelCode = Integer.parseInt(partes[1]);

                    List<GenericItem> years = mapper.readValue(
                            arquivo.toFile(),
                            new TypeReference<List<GenericItem>>() {}
                    );

                    for (GenericItem year : years) {

                        ModelYearRepository.insertModelYear(
                                brandCode,
                                modelCode,
                                year.getCode(),
                                year.getName()
                        );

                        System.out.println(
                                "Model: " + modelCode +
                                        " | Year: " + year.getCode() +
                                        " | " + year.getName()
                        );
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
