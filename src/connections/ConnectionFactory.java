package connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {


    private static final String URL =
            "jdbc:postgresql://localhost:5432/restaurant";


    private static final String USER =
            "postgres";


    private static final String PASSWORD =
            "Ro8284ed";

    public static Connection getConnection() {

        try {

            Connection conn =
                    DriverManager.getConnection(
                            URL,
                            USER,
                            PASSWORD
                    );

            System.out.println("Conexão realizada!");

            return conn;

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erro ao conectar ao banco: "
                            + e.getMessage()
            );
        }
    }


}
