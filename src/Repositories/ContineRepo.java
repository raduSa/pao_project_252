package Repositories;

import Config.DatabaseConnection;
import Utile.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ContineRepo {
    public static void addEntry(Integer id_comanda, Integer id_produs) throws SQLException {
        String sql = Constants.QUERY_INSERT_CONTINE;

        Connection conn = DatabaseConnection.getDatabaseConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setInt(1, id_comanda);
        preparedStatement.setInt(2, id_produs);

        preparedStatement.executeUpdate();
    }
}
