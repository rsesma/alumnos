
package alumnos.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javafx.scene.control.Alert;


public class getAlumnosData {
    private static final String C_DRIVER = "jdbc:mariadb";
    private static Connection conn;
    
    public getAlumnosData(Boolean load) {
        this.conn = null;
        
        if (load) getConnection("roberto","amsesr","192.168.1.69");
    }
    
    public Boolean getConnection(String user, String pswd, String server) {
        try {
            this.conn = DriverManager.getConnection(
                    C_DRIVER + "://" + server + ":3306/alumnos",
                    user, pswd);
            return true;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage());
            alert.showAndWait();
            return false;
        }
    }
    
    public ResultSet getAlumnosRs(String filter) throws SQLException {
        if (filter.length()>0) {
            return conn.prepareStatement("SELECT * FROM alumnos_clase WHERE " + filter).executeQuery();
        }
        else {
            return conn.prepareStatement("SELECT * FROM alumnos_clase").executeQuery();            
        }
    }

    public void importExcelFile(org.apache.poi.ss.usermodel.Row row) {
        try {
            String grupo = row.getCell(0).getStringCellValue() + row.getCell(1).getStringCellValue() + row.getCell(2).getStringCellValue();
            String dni = row.getCell(3).getStringCellValue();
            String nombre = row.getCell(4).getStringCellValue();
            
            System.out.println(grupo + ";" + dni + ";" + nombre);

/*            PreparedStatement q;
            q = conn.prepareStatement("UPDATE alumnos SET PC = ?, Fijo = ?, Comentario = ? WHERE DNI = ? AND GRUPO = ?");
            q.setString(1,a.getPC());
            q.setBoolean(2,a.getFijo());
            q.setString(3,a.getComent());
            q.setString(4,a.getDNI());
            q.setString(5,a.getGrupo());
            q.executeUpdate();*/
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage());
            alert.showAndWait();
        }
    }
    
    public void updateAlumno(Alumno a) {
        try {
            PreparedStatement q;
            q = conn.prepareStatement("UPDATE alumnos SET PC = ?, Fijo = ?, Comentario = ? WHERE DNI = ? AND GRUPO = ?");
            q.setString(1,a.getPC());
            q.setBoolean(2,a.getFijo());
            q.setString(3,a.getComent());
            q.setString(4,a.getDNI());
            q.setString(5,a.getGrupo());
            q.executeUpdate();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage());
            alert.showAndWait();
        }
    }
}