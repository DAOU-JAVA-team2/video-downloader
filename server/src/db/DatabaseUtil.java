package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseUtil {
    // settings
    public Connection conn;
    public Statement stmt;
    public ResultSet rs;

    // constructor
    public DatabaseUtil() {
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            connect();
            statement();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    // db basic functions
    public void connect() throws Exception {
        String url = System.getenv("DB_URL");
        String id = System.getenv("DB_USER");
        String pass = System.getenv("DB_PASS");
        conn = DriverManager.getConnection(url,id,pass);
    }
    public void statement() throws Exception{
        stmt = conn.createStatement();
    }
    public void close() throws Exception {
        stmt.close();
        conn.close();
    }
}
