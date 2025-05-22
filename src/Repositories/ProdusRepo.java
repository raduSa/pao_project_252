package Repositories;

import Config.DatabaseConnection;
import Entitati.Produs;
import Utile.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProdusRepo {
    public static List<Produs> produse;

    public static List<Produs> getProduse() throws SQLException {
        String sql = Constants.QUERY_GET_PRODUSE;

        Connection conn = DatabaseConnection.getDatabaseConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        ResultSet rs = preparedStatement.executeQuery();

        List<Produs> produse = new ArrayList<>();

        while (rs.next()) {
            Produs produs = new Produs(rs.getString("nume"), rs.getDouble("pret"));
            produse.add(produs);
        }

        return produse;
    }

    public static HashMap<Integer, Produs> getProduseMap() throws SQLException {
        String sql = Constants.QUERY_GET_PRODUSE;

        Connection conn = DatabaseConnection.getDatabaseConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        ResultSet rs = preparedStatement.executeQuery();

        HashMap<Integer, Produs> produse = new HashMap<>();

        while (rs.next()) {
            Produs produs = new Produs(rs.getString("nume"), rs.getDouble("pret"));
            produse.put(rs.getInt("id"), produs);
        }

        return produse;
    }

    public static ResultSet getProdusByName(String nume) throws SQLException {
        String sql = Constants.QUERY_GET_PRODUS_BY_NAME;
        Connection conn = DatabaseConnection.getDatabaseConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setString(1, nume);

        return preparedStatement.executeQuery();
    }

    public static Integer addProdus(Produs produs) throws SQLException {
        String sql = Constants.QUERY_INSERT_PRODUS;

        Connection conn = DatabaseConnection.getDatabaseConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, produs.getNume());
        preparedStatement.setDouble(2, produs.getPret());

        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        int id_produs;
        if (generatedKeys.next()) {
            id_produs = generatedKeys.getInt(1);
        }
        else {
            throw new SQLException();
        }

        return id_produs;
    }

}