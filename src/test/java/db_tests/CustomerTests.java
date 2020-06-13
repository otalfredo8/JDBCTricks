package db_tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.JDBCutils;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class CustomerTests{
    private static String url = "jdbc:postgresql://localhost:5432/dvdrental";
    private static String username = "postgres";
    private static String password =  "abc123";

    @BeforeMethod
    public void creatingConnection(){
        JDBCutils.createDBConnection(url, username, password);
    }
    @AfterMethod
    public void closingConnection(){
        JDBCutils.closeDBConnection();
    }
    @Test
    public void firstCellValue(){
        System.out.println(JDBCutils.getFirstCellValue("SELECT * FROM actor;"));
    }
    @Test
    public void firstRowValue(){
        List<Object> objectList = JDBCutils.getFirstRowList("SELECT * FROM actor;");
        for (int i = 0; i<objectList.size(); i++){
            System.out.println(objectList.get(i));
        }
    }
    @Test
    public void firstRowMap (){
        Map<String, Object> stringObjectMap = JDBCutils.getFirstRowMap("SELECT * FROM actor;");
        for (Map.Entry<String, Object> entry:stringObjectMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue().toString());
        }
    }
    @Test
    public void columnData (){
        List<Object> objectList = JDBCutils.getColumnData("SELECT * FROM actor;", "first_name");
        for (Object object:objectList) {
            System.out.println(object);
        }
    }
    @Test
    public void columnNames(){
        List<String> stringList = JDBCutils.getColumnNames("SELECT * FROM actor;");
        for (String str:stringList) {
            System.out.println(str);
        }
        System.out.println(JDBCutils.getRowCount());
    }

//    public static void main(String[] args) {
//        try{
//            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dvdrental", "postgres", "abc123");
//            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            resultSet = statement.executeQuery("SELECT * FROM actor;");
//
//            //four columns. int and Strings
//            while (resultSet.next()) {
//                System.out.println(resultSet.getInt(1));
//                System.out.println(resultSet.getString(4));
//                System.out.println(resultSet.getString("last_name"));
//            }
//            resultSet.last();
//            System.out.println(resultSet.getRow());
//
//
//
//        }catch(SQLException e){
//            e.printStackTrace();
//        }
//    }
}
