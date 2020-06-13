package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCutils {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void createDBConnection(String url, String dbUsername, String dbpassword) {
        try {
            connection = DriverManager.getConnection(url, dbUsername, dbpassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeDBConnection() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /**
     *
     * @param query
     * @return returns a single cell value. If the results in multiple rows and/or
     *         columns of data, only first column of the first row will be returned.
     *         The rest of the data will be ignored
     */
    public static Object getFirstCellValue(String query) {
        return getQueryResultList(query).get(0).get(0);
    }
    /**
     *
     * @param query
     * @return returns a list of Strings which represent a row of data. If the query
     *         results in multiple rows and/or columns of data, only first row will
     *         be returned. The rest of the data will be ignored
     */
    public static List<Object> getFirstRowList(String query) {
        return getQueryResultList(query).get(0);
    }
    /**
     *
     * @param query
     * @return returns a map which represent a row of data where key is the column
     *         name. If the query results in multiple rows and/or columns of data,
     *         only first row will be returned. The rest of the data will be ignored
     */
    public static Map<String, Object> getFirstRowMap(String query) {
        return getQueryResultMap(query).get(0);
    }
    /**
     *
     * @param query
     * @return returns query result in a list of lists where outer list represents
     *         collection of rows and inner lists represent a single row
     */
    public static List<List<Object>> getQueryResultList(String query) {
        executeQuery(query);
        List<List<Object>> rowList = new ArrayList<List<Object>>();
        ResultSetMetaData rsmd;

        try {
            rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                List<Object> row = new ArrayList<Object>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    row.add(resultSet.getObject(i));
                }

                rowList.add(row);

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return rowList;

    }
    /**
     *
     * @param query
     * @param column
     * @return list of values of a single column from the result set
     */
    public static List<Object> getColumnData(String query, String column) {
        executeQuery(query);
        List<Object> rowList = new ArrayList<Object>();

        try {
            while (resultSet.next()) {
                rowList.add(resultSet.getObject(column));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return rowList;

    }
    /**
     *
     * @param query
     * @return returns query result in a list of maps where the list represents
     *         collection of rows and a map represents represent a single row with
     *         key being the column name
     */
    public static List<Map<String, Object>> getQueryResultMap(String query) {
        executeQuery(query);
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        ResultSetMetaData rsmd;

        try {
            rsmd = resultSet.getMetaData();

            while (resultSet.next()) {

                Map<String, Object> colNameValueMap = new HashMap<String, Object>();

                for (int i = 1; i <= rsmd.getColumnCount(); i++) {

                    colNameValueMap.put(rsmd.getColumnName(i), resultSet.getObject(i));
                }

                rowList.add(colNameValueMap);

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return rowList;

    }
    /**
     *
     * @param query
     * @return List of columns returned in result set
     */
    public static List<String> getColumnNames(String query) {
        executeQuery(query);
        List<String> columns = new ArrayList<String>();
        ResultSetMetaData rsmd;

        try {
            rsmd = resultSet.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                columns.add(rsmd.getColumnName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columns;

    }

    private static void executeQuery(String query) {
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getRowCount() {

        try {
            resultSet.last();
            int rowCount = resultSet.getRow();
            return rowCount;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
