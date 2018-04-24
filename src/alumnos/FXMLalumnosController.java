/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import alumnos.model.AlumnosRow;
import alumnos.model.getAlumnosData;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author r
 */
public class FXMLalumnosController implements Initializable {
    
    @FXML
    TableView<AlumnosRow> table;
    @FXML
    TableColumn itemPeriodoCol;
    @FXML
    TableColumn itemCursoCol;
    @FXML
    TableColumn itemGrupoCol;
    @FXML
    TableColumn itemDNICol;
    @FXML
    TableColumn itemPCCol;
    @FXML
    TableColumn itemNameCol;
    @FXML
    TableColumn itemFijoCol;
    @FXML
    TableColumn itemCLASECol;
    @FXML
    TableColumn itemPEC1Col;
    @FXML
    TableColumn itemPECCol;
    @FXML
    TableColumn itemNOTACol;
    
    final ObservableList<AlumnosRow> alumnosData = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        // Set up the alumnos table
        itemPeriodoCol.setCellValueFactory(new PropertyValueFactory<>("itemPeriodo"));
        itemCursoCol.setCellValueFactory(new PropertyValueFactory<>("itemCurso"));
        itemGrupoCol.setCellValueFactory(new PropertyValueFactory<>("itemGrupo"));
        itemDNICol.setCellValueFactory(new PropertyValueFactory<>("itemDNI"));
        itemPCCol.setCellValueFactory(new PropertyValueFactory<>("itemPC"));
        itemFijoCol.setCellValueFactory(new PropertyValueFactory<>("itemFijo"));
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemCLASECol.setCellValueFactory(new PropertyValueFactory<>("itemCLASE"));
        itemPEC1Col.setCellValueFactory(new PropertyValueFactory<>("itemPEC1"));
        itemPECCol.setCellValueFactory(new PropertyValueFactory<>("itemPEC"));
        itemNOTACol.setCellValueFactory(new PropertyValueFactory<>("itemNOTA"));
        
        table.setItems(alumnosData);
        
        LoadAlumnosTable();
    }

    public void LoadAlumnosTable() {
        try{
            getAlumnosData d = new getAlumnosData();
            ResultSet rs = d.getAlumnosRs();
            while(rs.next()){
                AlumnosRow row = new AlumnosRow();
                row.itemPeriodo.set(rs.getString("Periodo"));
                row.itemCurso.set(rs.getString("Curso"));
                row.itemGrupo.set(rs.getString("Grupo"));
                row.itemDNI.set(rs.getString("DNI"));
                row.itemPC.set(rs.getInt("PC"));
                row.itemFijo.set(rs.getString("fix"));
                row.itemName.set(rs.getString("nom"));
                row.itemCLASE.set(rs.getString("CLASE"));
                row.itemPEC1.set(rs.getString("PEC1"));
                row.itemPEC.set(rs.getString("PEC"));
                row.itemNOTA.set(rs.getFloat("NOTA"));

                alumnosData.add(row);
            }   
        }
        catch(SQLException e){
        }
    }
}
