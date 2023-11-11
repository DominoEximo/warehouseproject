package hu.neuron.mentoring.web.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ConnectDatabase {
    public static void main(String[] args) {

        Connection con = null;

        Logger logger = Logger.getLogger(ConnectDatabase.class.getName());

        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/testdb","SA","");
            if(con != null){
                System.out.println("Connection created successfully");
            }else {
                System.out.println("Problem with creating connection");
            }

        }catch (Exception e){
            logger.setLevel(Level.SEVERE);
            logger.log(logger.getLevel(),"Exception during db creation");
        }
    }
}
