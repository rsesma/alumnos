
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

    public ResultSet getProblemasPEC1(String filter) throws SQLException {
        if (filter.length()>0) {
            return conn.prepareStatement("SELECT * FROM problemasPEC1 WHERE " + filter).executeQuery();
        }
        else {
            return conn.prepareStatement("SELECT * FROM problemasPEC1").executeQuery();            
        }
    }
    
    public void importExcelRow(org.apache.poi.ss.usermodel.Row row, String periodo) {
        try {
            String grupo = row.getCell(0).getStringCellValue() + row.getCell(1).getStringCellValue() + row.getCell(2).getStringCellValue();
            
            PreparedStatement q;
            q = this.conn.prepareStatement("INSERT INTO alumnos (Periodo,Curso,DNI,Grupo,nombre,ape1,ape2,email,provincia,poblacion) " +
                                            "VALUES(?,?,?,?,?,?,?,?,?,?)");
            q.setString(1,periodo);
            q.setString(2,row.getCell(0).getStringCellValue());
            q.setString(3,row.getCell(3).getStringCellValue());
            q.setString(4,grupo);
            q.setString(5,row.getCell(4).getStringCellValue());
            q.setString(6,row.getCell(5).getStringCellValue());
            q.setString(7,row.getCell(6).getStringCellValue());
            q.setString(8,row.getCell(8).getStringCellValue());
            q.setString(9,row.getCell(10).getStringCellValue());
            q.setString(10,row.getCell(9).getStringCellValue());
            q.executeUpdate();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage());
            alert.showAndWait();
        }
    }

    public void entregaPEC1(String dni, Boolean mdb, Boolean pdf, Boolean honor) {
        try {
            PreparedStatement q;
            q = this.conn.prepareStatement("INSERT INTO entregahonorpec1 (DNI,entregada,mdb,pdf,honor) VALUES(?,?,?,?,?)");
            q.setString(1, dni);
            q.setBoolean(2, true);
            q.setBoolean(3, mdb);
            q.setBoolean(4, pdf);
            q.setBoolean(5, honor);
            q.executeUpdate();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage());
            alert.showAndWait();
        }
    }
    
    public void updateAlumno(Alumno a) {
        try {
            PreparedStatement q;
            q = conn.prepareStatement("UPDATE alumnos SET PC = ?, Fijo = ?, CLASE = ?, Comentario = ? WHERE DNI = ? AND GRUPO = ?");
            q.setString(1,a.getPC());
            q.setBoolean(2,a.getFijo());
            q.setString(3,a.getClase());
            q.setString(4,a.getComent());
            q.setString(5,a.getDNI());
            q.setString(6,a.getGrupo());
            q.executeUpdate();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage());
            alert.showAndWait();
        }
    }

    public void updateEntregaPEC1(Problema p) {
        try {
            PreparedStatement q;
            q = conn.prepareStatement("UPDATE entregahonorpec1 SET mdb = ?, pdf = ?, honor = ? WHERE DNI = ?");
            q.setBoolean(1,p.getMDB());
            q.setBoolean(2,p.getPDF());
            q.setBoolean(3,p.getHonor());
            q.setString(4,p.getDNI());
            q.executeUpdate();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage());
            alert.showAndWait();
        }
    }
}