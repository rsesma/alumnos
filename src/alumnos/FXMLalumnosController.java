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
    
    final ObservableList<AlumnosRow> alumnosData = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        // Set up the alumnos table
        itemPeriodoCol.setCellValueFactory(new PropertyValueFactory<AlumnosRow,String>("itemPeriodo"));
        itemCursoCol.setCellValueFactory(new PropertyValueFactory<AlumnosRow,String>("itemCurso"));
        itemGrupoCol.setCellValueFactory(new PropertyValueFactory<AlumnosRow,String>("itemGrupo"));
        itemDNICol.setCellValueFactory(new PropertyValueFactory<AlumnosRow,String>("itemDNI"));
        itemPCCol.setCellValueFactory(new PropertyValueFactory<AlumnosRow,String>("itemPC"));
        itemNameCol.setCellValueFactory(new PropertyValueFactory<AlumnosRow,String>("itemName"));
        
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
                row.itemPC.set(rs.getString("PC"));
                row.itemName.set(rs.getString("nombre"));

                alumnosData.add(row);
            }   
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
