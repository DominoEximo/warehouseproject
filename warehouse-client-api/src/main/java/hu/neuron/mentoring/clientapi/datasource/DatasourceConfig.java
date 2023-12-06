package hu.neuron.mentoring.clientapi.datasource;



import hu.neuron.mentoring.clientapi.entity.Category;
import hu.neuron.mentoring.clientapi.utility.ScriptRunnerUtil;
import hu.neuron.mentoring.clientapi.entity.Product;
import hu.neuron.mentoring.clientapi.entity.Unit;

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

    String path = "C:\\Users\\PappD\\IdeaProjects\\warehouseproject\\warehouse-client-api\\src\\main\\resources\\data.sql";

    static final String ADDPRODUCT = "INSERT INTO WAREHOUSE.products (name,category,amount,unit,purchaseprice,sellprice,description) VALUES(?,?,?,?,?,?,?)";
    static final String GETALLPRODUCTSQUERY = "SELECT * FROM WAREHOUSE.products";


    public DatasourceConfig() {}


    public static synchronized DatasourceConfig getInstance() {
        if (instance == null){
            instance = new DatasourceConfig();
            instance.setUpDB();
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

            while (rs.next()){
                mockedData.add(new Product(rs.getString("name"),rs.getObject("category", Category.class),rs.getInt("amount"),rs.getObject("unit", Unit.class),rs.getBigDecimal("purchaseprice"),rs.getBigDecimal("sellprice"),rs.getString("description")));
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        return mockedData;

    }

    public void setUpDB(){
        openConnection();
        try {
            ScriptRunnerUtil.runScript(path, conn);
        }catch (Exception e){
            System.out.println(e);
        }

    }

    public void addProduct(Product product){
        try {

            stmt = conn.prepareStatement(ADDPRODUCT);

            stmt.setString(1,product.getName());
            stmt.setObject(2,product.getCategory());
            stmt.setInt(3,product.getAmount());
            stmt.setObject(4,product.getUnit());
            stmt.setBigDecimal(5,product.getPurchasePrice());
            stmt.setBigDecimal(6,product.getSellPrice());
            stmt.setString(7,product.getDescription());

            stmt.executeUpdate();


        }catch (SQLException e){
            System.out.println(e);
        }
    }

}
