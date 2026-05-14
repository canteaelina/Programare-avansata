package org.example.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "elina";
    private static final String PASSWORD = "elina";

    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(10);
        dataSource = new HikariDataSource(config);
    }

    private Database() {}

    public static Connection getConnection() throws SQLException
    {
        return dataSource.getConnection();
    }

    public static void closeDataSource()
    {
        if (dataSource != null) dataSource.close();
    }

    /*
    private static Connection connection = null;

    private Database() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexiunea la Oracle a fost realizata cu succes!");
            } catch (SQLException e) {
                System.err.println("Nu s-a putut realiza conexiunea: " + e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexiunea a fost inchisa.");
            } catch (SQLException e) {
                System.err.println("Eroare la inchiderea conexiunii: " + e.getMessage());
            }
        }
    }
  */
}
