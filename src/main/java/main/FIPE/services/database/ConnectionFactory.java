package main.FIPE.services.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionFactory {
    //Unica responsa é abrir conexão
    private static final String URL = "jdbc:sqlite:carDatabase2.db";

    public static Connection getConnection() throws SQLException {
        try{
            Connection conn = DriverManager.getConnection(URL);
            try(PreparedStatement stmt = conn.prepareStatement("pragma foreign_keys = on ")){
                stmt.execute();
            }
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
