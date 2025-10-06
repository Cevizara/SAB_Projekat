package student;

import java.sql.*;


public class DB {
    private static final String username="sa1";
    private static final String password = "123";
    private static final String database = "FilmoviKorisnici";
    private static final int port = 1433;
    private static final String server = "localhost\\SQLEXPRESS";

    private static final String connectionUrl =
            "jdbc:sqlserver://"+server+ ":"+port+";databaseName="+database+";encrypt=false;trustServerCertificate=true" ;
    private Connection connection;

    private DB() {
        try {
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(connectionUrl, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static DB db = null;

    public static DB getInstance() {
        if (db == null) {
            db = new DB();
        }
        return db;
    }
    public Connection getConnection() {
        return connection;
    }
}

