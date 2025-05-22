package Repositories;

import Config.DatabaseConnection;
import Entitati.Comanda;
import Entitati.Produs;
import Utile.Constants;
import Utile.Status;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static Repositories.ProdusRepo.getProduse;
import static Repositories.ProdusRepo.produse;

public class ComandaRepo {
    public static List<Comanda> getComenzi() throws IOException, SQLException {
        List<Comanda> comenzi = new ArrayList<>();
        Comanda comanda = new Comanda();
        List<Produs> produseRepo = getProduse();
        comanda.addProdus(produseRepo.get(0));
        comanda.addProdus(produseRepo.get(1));
        comanda.addProdus(produseRepo.get(2));
        comanda.addProdus(produseRepo.get(3));

        Comanda comanda2 = new Comanda();
        comanda2.addProdus(produseRepo.get(0));
        comanda2.addProdus(produseRepo.get(4));
        comanda2.addProdus(produseRepo.get(4));

        Comanda comanda3 = new Comanda();
        comanda3.addProdus(produseRepo.get(0));
        comanda3.addProdus(produseRepo.get(3));
        comanda3.addProdus(produseRepo.get(4));

        Comanda comanda4 = new Comanda();
        comanda4.addProdus(produseRepo.get(0));
        comanda4.addProdus(produseRepo.get(2));
        comanda4.addProdus(produseRepo.get(5));

        Comanda comanda5 = new Comanda();
        comanda5.addProdus(produseRepo.get(0));
        comanda5.addProdus(produseRepo.get(2));
        comanda5.addProdus(produseRepo.get(4));

        Comanda comanda6 = new Comanda();
        comanda6.addProdus(produseRepo.get(0));
        comanda6.addProdus(produseRepo.get(6));
        comanda6.addProdus(produseRepo.get(4));

        Comanda comanda7 = new Comanda();
        comanda7.addProdus(produseRepo.get(0));
        comanda7.addProdus(produseRepo.get(4));
        comanda7.addProdus(produseRepo.get(4));

        comenzi.add(comanda);
        comenzi.add(comanda2);
        comenzi.add(comanda3);
        comenzi.add(comanda4);
        comenzi.add(comanda5);
        comenzi.add(comanda6);
        comenzi.add(comanda7);

        return comenzi;

    }

    public static List<Comanda> getComenziJoinProduse() throws SQLException {
        String sql = Constants.QUERY_GET_COMENZI_JOIN_PRODUSE;

        Connection conn = DatabaseConnection.getDatabaseConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        ResultSet rs = preparedStatement.executeQuery();
        HashMap<Integer, Comanda> comenzi = new HashMap<>();
        HashMap<Integer, Produs> produse = new HashMap<>();

        while (rs.next()) {
            Integer id_comanda = rs.getInt("id_comanda");
            Integer id_produs = rs.getInt("id_produs");
            if (!comenzi.containsKey(id_comanda)) {
                Comanda comanda = new Comanda(id_comanda,
                                              rs.getTimestamp("data").toLocalDateTime(),
                                              Status.valueOf(rs.getString("status")));
                comenzi.put(id_comanda, comanda);
            }
            if (!produse.containsKey(id_produs)) {
                Produs produs = new Produs(rs.getString("nume"), rs.getDouble("pret"));
                produse.put(id_produs, produs);
            }
            Comanda currComanda = comenzi.get(id_comanda);
            Produs currProdus = produse.get(id_produs);
            currComanda.addProdus(currProdus);
        }

        return new ArrayList<Comanda>(comenzi.values());
    }

    public static void addComanda(Comanda comanda, Integer id_client) throws SQLException {
        // inseram produsele care nu exista
        for (Produs p : comanda.getProduse()) {
            if (!ProdusRepo.getProdusByName(p.getNume()).next()) {
                int id_prouds = ProdusRepo.addProdus(p);
                System.out.println("Am inserat produs cu id: " + id_prouds);
            }
        }

        String sql = Constants.QUERY_INSERT_COMANDA;

        Connection conn = DatabaseConnection.getDatabaseConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setTimestamp(1, Timestamp.valueOf(comanda.getData()));
        preparedStatement.setString(2, String.valueOf(comanda.getStatus()));
        preparedStatement.setInt(3, id_client);

        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        int id_comanda;
        if (generatedKeys.next()) {
            id_comanda = generatedKeys.getInt(1);
        }
        else {
            throw new SQLException();
        }

        // inseram in tabela contine
        for (Produs p : comanda.getProduse()) {
            ResultSet rs = ProdusRepo.getProdusByName(p.getNume());
            rs.next();
            Integer id_produs = rs.getInt("id");
            ContineRepo.addEntry(id_comanda, id_produs);
        }
    }
}