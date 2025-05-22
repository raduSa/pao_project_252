package Utile;

public class Constants {
    public static final String NUME_FISIER = "src/Resources/Produse.txt";
    public static final String EXCEPT_VARSTA = "Varsta nu se incadreaza intre 14 si 120!";
    public static final String EXCEPT_DBPROPERTIES = "Nu s-au gasit proprietatile!";
    public static final String FISIER_DB = "D:\\Lab Java\\Test\\src\\Resources\\proprietatiDB.txt";
    public static final String QUERY_INSERT_CLIENT = "insert into client (nume, varsta, email, adresa, numar_comenzi)"
            + "values (?, ?, ?, ?, ?)";
    public static final String QUERY_INSERT_COMANDA = "INSERT INTO comanda (data, status, id_client) " +
            "VALUES (?, ?, ?)";
    public static final String QUERY_INSERT_CONTINE = "INSERT INTO contine (id_comanda, id_produs) " +
            "VALUES (?, ?)";
    public static final String QUERY_INSERT_PRODUS = "INSERT INTO produs (data, status) " +
            "VALUES (?, ?)";
    public static final String QUERY_GET_CLIENTS = "SELECT * FROM client;";
    public static final String QUERY_DELETE_CLIENT = "DELETE FROM client WHERE id = ?";
    public static final String QUERY_GET_PRODUSE = "SELECT * FROM produs";
    public static final String QUERY_GET_COMENZI_JOIN_PRODUSE = "SELECT * " +
                                                                "FROM comanda c" +
                                                                "   JOIN contine con ON(c.id = con.id_comanda)" +
                                                                "   JOIN produs p ON(p.id = con.id_produs)";
    public static final String QUERY_GET_CLIENTI_CU_MAI_MULT_DE_O_COMANDA = "SELECT * " +
                                                                            "FROM client c " +
                                                                            "JOIN comanda com ON(c.id = com.id_client) " +
                                                                            "JOIN contine con ON(com.id = con.id_comanda) " +
                                                                            "JOIN produs p ON(con.id_produs = p.id) " +
                                                                            "WHERE numar_comenzi > 1";
    public static final String QUERY_GET_PRODUS_BY_NAME = "SELECT * " +
                                                          "FROM produs " +
                                                          "WHERE nume = ?";



}
