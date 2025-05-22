package Repositories;

import Config.DatabaseConnection;
import Entitati.Client;
import Entitati.Comanda;
import Entitati.Produs;
import Utile.ClientUtils;
import Utile.Constants;
import Utile.Status;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static Config.DatabaseConnection.getDatabaseConnection;

public class ClientRepo {
    public static Integer addClient(Client client) throws SQLException {
        try {
            String sql = Constants.QUERY_INSERT_CLIENT;
            Connection conn = DatabaseConnection.getDatabaseConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, client.getNume());
            preparedStatement.setInt(2, client.getVarsta());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setString(4, client.getAdresa());
            preparedStatement.setInt(5, client.getComenzi().size());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int id_client;
            if (generatedKeys.next()) {
                id_client = generatedKeys.getInt(1);
            }
            else {
                throw new SQLException();
            }

            // inseram comenzile
            for (Comanda comanda : client.getComenzi()) {
                ComandaRepo.addComanda(comanda, id_client);
            }

            return id_client;
        }
        catch (SQLException e) {
            System.out.println("\nExista deja");
            return null;
        }
    }

    public static ResultSet getClients() throws SQLException {
        String sql = Constants.QUERY_GET_CLIENTS;

        Connection conn = DatabaseConnection.getDatabaseConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        return preparedStatement.executeQuery();
    }

    public static void deleteClientById(int id) throws SQLException {
        String sql = Constants.QUERY_DELETE_CLIENT;

        Connection conn = DatabaseConnection.getDatabaseConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, id);
        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Client with ID " + id + " deleted successfully.");
        } else {
            System.out.println("No client found with ID " + id + ".");
        }
    }

    public static List<Client> getClientsWithMultipleOrders() throws SQLException {
//        String sql = Constants.QUERY_GET_CLIENTI_CU_MAI_MULT_DE_O_COMANDA;
//
//        Connection conn = DatabaseConnection.getDatabaseConnection();
//        PreparedStatement preparedStatement = conn.prepareStatement(sql);
//
//        ResultSet rs = preparedStatement.executeQuery();
//        List<Client> clienti = new ArrayList<>();
//
//        while (rs.next()) {
//            Client client = new Client(rs.getString("nume"),
//                                       rs.getString("email"),
//                                       rs.getInt("varsta"),
//                                       rs.getString("adresa"));
//            clienti.add(client);
//        }
//
//        return clienti;
        String sql = Constants.QUERY_GET_CLIENTI_CU_MAI_MULT_DE_O_COMANDA;

        Connection conn = DatabaseConnection.getDatabaseConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        ResultSet rs = preparedStatement.executeQuery();
        HashMap<Integer, Client> clienti = new HashMap<>();
        HashMap<Integer, Comanda> comenzi = new HashMap<>();
        HashMap<Integer, Produs> produse = new HashMap<>();

        while (rs.next()) {
            Integer id_client = rs.getInt(1);
            Integer id_comanda = rs.getInt("id_comanda");
            Integer id_produs = rs.getInt("id_produs");
            if (!clienti.containsKey(id_client)) {
                Client client = new Client(rs.getString("nume"),
                                       rs.getString("email"),
                                       rs.getInt("varsta"),
                                       rs.getString("adresa"));
                clienti.put(id_client, client);
            }
            if (!comenzi.containsKey(id_comanda)) {
                Comanda comanda = new Comanda(id_comanda,
                        rs.getTimestamp("data").toLocalDateTime(),
                        Status.valueOf(rs.getString("status")));
                comenzi.put(id_comanda, comanda);
                // adaug comanda clientului
                clienti.get(id_client).adaugaComanda(comanda);
            }
            if (!produse.containsKey(id_produs)) {
                Produs produs = new Produs(rs.getString(15), rs.getDouble("pret"));
                produse.put(id_produs, produs);
            }
            Comanda currComanda = comenzi.get(id_comanda);
            Produs currProdus = produse.get(id_produs);
            currComanda.addProdus(currProdus);
        }

        return new ArrayList<Client>(clienti.values());
    }
}
