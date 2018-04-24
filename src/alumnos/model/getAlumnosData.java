
package alumnos.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;


public class getAlumnosData {
    private static final String C_DRIVER = "jdbc:mariadb";
    private static final String C_SERVER = "192.168.1.69";
    private static final String C_USER = "rsesma";
    private static final String C_PSWD = "amsesr1977";
    private static Connection conn;
    
    public getAlumnosData() throws SQLException {
        getConnection();
    }
    
    private Connection getConnection() throws SQLException {
        conn = null;
        conn = DriverManager.getConnection(
                C_DRIVER + "://" + C_SERVER + ":3306/alumnos",
                C_USER,C_PSWD);
        return conn;
    }
    
    public ResultSet getAlumnosRs() throws SQLException {
        return conn.prepareStatement("SELECT * FROM alumnos_clase").executeQuery();
    }
}