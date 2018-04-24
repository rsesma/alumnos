
package alumnos.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class getAlumnosData {
    private static String C_DRIVER = "jdbc:mariadb";
    private static String C_SERVER = "127.0.0.1";
    private static String C_USER = "rsesma";
    private static String C_PSWD = "amsesr";
    private static Connection conn;
    
    public getAlumnosData() throws SQLException {
        getConnection();
    }
    
    public Connection getConnection() throws SQLException {
        conn = null;
        conn = DriverManager.getConnection(
                C_DRIVER + "://" + C_SERVER + ":3306/alumnos",
                C_USER,C_PSWD);
        return conn;
    }
    
    public ResultSet getAlumnosRs() throws SQLException {
        PreparedStatement q = conn.prepareStatement("SELECT Periodo, Curso, Grupo, DNI, PC, nombre FROM alumnos ORDER BY Periodo, Grupo, PC");
        ResultSet rs = q.executeQuery();
        return rs;
    }
}