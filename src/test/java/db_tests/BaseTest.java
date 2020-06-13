package db_tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.*;

public class BaseTest {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private String sqlQuery;

    @Test
    public void dvdrental_connection() {
        String connectionString = "jdbc:postgresql://localhost:5432/dvdrental";
        String dbUsername = "postgres";
        String dbPassword = "abc123";
        try {
            connection = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
            preparedStatement = connection.prepareStatement("SELECT * FROM actor;");
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
