package main.FIPE.services.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseSetup {

    public static void setup() {
        String brandsTable = """
                CREATE TABLE IF NOT EXISTS brands (
                        fipe_code INTEGER PRIMARY KEY UNIQUE ,
                    name TEXT NOT NULL
                );
                """;

        String modelsTable = """
                CREATE TABLE IF NOT EXISTS models (
                    brand_code INTEGER NOT NULL,
                    fipe_code INTEGER NOT NULL,
                    name TEXT NOT NULL,
                    PRIMARY KEY (brand_code, fipe_code),
                    FOREIGN KEY (brand_code) REFERENCES brands(fipe_code)
                );
                """;

        String modelYearsTable = """
                CREATE TABLE IF NOT EXISTS model_years (
        brand_code INTEGER NOT NULL,
        model_code INTEGER NOT NULL,
        year_code TEXT NOT NULL,
        name TEXT NOT NULL,

        PRIMARY KEY (brand_code, model_code, year_code),

        FOREIGN KEY (brand_code, model_code)
            REFERENCES models(brand_code, fipe_code)
        );
        """;


        String carDetailsTable = """
                CREATE TABLE IF NOT EXISTS car_details (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,

                    vehicle_type INTEGER NOT NULL,
                    price TEXT NOT NULL,
                    brand TEXT NOT NULL,
                    model TEXT NOT NULL,
                    model_year INTEGER NOT NULL,
                    fuel TEXT NOT NULL,
                    code_fipe TEXT NOT NULL,
                    reference_month TEXT NOT NULL,
                    fuel_acronym TEXT NOT NULL,
                
                
                    brand_code INTEGER NOT NULL,
                    model_code INTEGER NOT NULL,
                    year_code TEXT NOT NULL,
                
                    FOREIGN KEY (brand_code, model_code, year_code)
                        REFERENCES model_years(brand_code, model_code, year_code)
              
                );
                """;

        try (Connection conn = ConnectionFactory.getConnection()) {
            execute(conn, brandsTable);
            execute(conn, modelsTable);
            execute(conn, modelYearsTable);
            execute(conn, carDetailsTable);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inicializar o banco", e);
        }
    }

    private static void execute(Connection conn, String sql) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
        }
    }
}