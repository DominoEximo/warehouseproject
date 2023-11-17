package hu.neuron.mentoring.client_api.datasource;


import hu.neuron.mentoring.client_api.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatasourceConfig {


    private static DatasourceConfig instance = null;

    private Connection conn = null;

    private String url;

    private String username;

    private String pass;

    private PreparedStatement stmt;

    static final String GETALLPRODUCTSQUERY = "SELECT NAME FROM PUBLIC.PRODUCTS";


    public DatasourceConfig() {}


    public static synchronized DatasourceConfig getInstance() {
        if (instance == null){
            instance = new DatasourceConfig();
        }
        return  instance;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public PreparedStatement getStmt() {
        return stmt;
    }

    public void setStmt(PreparedStatement stmt) {
        this.stmt = stmt;
    }

    public void openConnection()  {
        try {
            url = "jdbc:hsqldb:mem:testdb";
            username = "SA";
            pass = "";

            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            conn = DriverManager.getConnection(url,username,pass);
        }catch (SQLException e ){
            throw new RuntimeException("SQL Exception");
        }catch (ClassNotFoundException e){
            throw new RuntimeException("Driver class not found");
        }

    }


    public void closeConnection() {
        try {
            conn.close();
        }catch (SQLException e){
            throw new RuntimeException("SQL Exception");
        }

    }


    public List<Product>  getProductsFromDatabase()  {
        List<Product> mockedData = new ArrayList<>();
        try {
            stmt = conn.prepareStatement(GETALLPRODUCTSQUERY);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("NAME"));
                //mockedData.add(new Product(rs.getString(0), rs.getString(1), rs.getInt(2), rs.getString(3), rs.getBigDecimal(4), rs.getBigDecimal(5), rs.getString(6)));
            }


        }catch (SQLException e){
            System.out.println(e);
        }
        return mockedData;
    }

}
