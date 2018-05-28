/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import alumnos.model.Alumno;
import alumnos.model.Notas;
import alumnos.model.getAlumnosData;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

/**
 *
 * @author r
 */
public class FXMLalumnosController implements Initializable {
    
    @FXML
    TableView<Alumno> table;
    @FXML
    TableColumn periodoCol;
    @FXML
    TableColumn cursoCol;
    @FXML
    TableColumn grupoCol;
    @FXML
    TableColumn DNICol;
    @FXML
    TableColumn PCCol;
    @FXML
    TableColumn fijoCol;
    @FXML
    TableColumn nameCol;
    @FXML
    TableColumn claseCol;
    @FXML
    TableColumn pec1Col;
    @FXML
    TableColumn pecCol;
    @FXML
    TableColumn notaCol;
    @FXML
    TextField fPeriodo;
    @FXML
    TextField fCurso;
    @FXML
    TextField fGrupo;
    @FXML
    TextField fDNI;
    @FXML
    TextField fNombre;
    @FXML
    Label lbTotal;
    
    final ObservableList<Alumno> data = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        this.table.setEditable(true);
        
        // Set up the alumnos table
        this.periodoCol.setCellValueFactory(new PropertyValueFactory<>("Periodo"));
        this.cursoCol.setCellValueFactory(new PropertyValueFactory<>("Curso"));
        this.grupoCol.setCellValueFactory(new PropertyValueFactory<>("Grupo"));
        this.DNICol.setCellValueFactory(new PropertyValueFactory<>("DNI"));
        this.nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        this.pec1Col.setCellValueFactory(new PropertyValueFactory<>("PEC1"));
        this.pecCol.setCellValueFactory(new PropertyValueFactory<>("PEC"));
        this.notaCol.setCellValueFactory(new PropertyValueFactory<>("NOTA"));
        
        // pc is editable
        this.PCCol.setCellValueFactory(new PropertyValueFactory<>("PC"));
        this.PCCol.setCellFactory(TextFieldTableCell.<Alumno> forTableColumn());
        
        // checkbox fijo
        this.fijoCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Alumno, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Alumno, Boolean> param) {
                Alumno a = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(a.getFijo());
 
                // Note: singleCol.setOnEditCommit(): Not work for
                // CheckBoxTableCell.
 
                // When "Fijo" column change.
                booleanProp.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                            Boolean newValue) { a.setFijo(newValue); }
                });
                
                return booleanProp;
            }
        });
        this.fijoCol.setCellFactory(new Callback<TableColumn<Alumno, Boolean>, TableCell<Alumno, Boolean>>() {
            @Override
            public TableCell<Alumno, Boolean> call(TableColumn<Alumno, Boolean> p) {
                CheckBoxTableCell<Alumno, Boolean> cell = new CheckBoxTableCell<Alumno, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
        
        // combo notas
        ObservableList<Notas> notasList = FXCollections.observableArrayList(Notas.values());
        this.claseCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Alumno, Notas>, ObservableValue<Notas>>() {
            @Override
            public ObservableValue<Notas> call(TableColumn.CellDataFeatures<Alumno, Notas> param) {
                Alumno a = param.getValue();
                String notasCode = a.getClase();
                Notas nota = Notas.getByCode(notasCode);
                return new SimpleObjectProperty<Notas>(nota);
            }
        });
        
        this.claseCol.setCellFactory(ComboBoxTableCell.forTableColumn(notasList));
        this.claseCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Alumno, Notas>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Alumno, Notas> event) {
                TablePosition<Alumno, Notas> pos = event.getTablePosition();
                
                Notas newNota = event.getNewValue();
                
                int row = pos.getRow();
                Alumno a = event.getTableView().getItems().get(row);
                
                a.setClase(newNota.getCode());
            }
        });
        
        table.setItems(this.data);
        
        LoadAlumnosTable("");
    }

    public void LoadAlumnosTable(String filter) {
        int count = 0;        
        try{
            getAlumnosData d = new getAlumnosData();
            ResultSet rs = d.getAlumnosRs(filter);
            while(rs.next()){
                Alumno a = new Alumno();
                a.setPeriodo(rs.getString("Periodo"));
                a.setCurso(rs.getString("Curso"));
                a.setGrupo(rs.getString("Grupo"));
                a.setDNI(rs.getString("DNI"));
                a.setPC(rs.getString("PC"));
                a.setFijo(rs.getBoolean("Fijo"));
                a.setName(rs.getString("nom"));
                a.setClase(rs.getString("CLASE"));
                a.setPEC1(rs.getString("PEC1"));
                a.setPEC(rs.getString("PEC"));
                a.setNOTA(rs.getString("NOTA"));

                this.data.add(a);
                
                count++;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        lbTotal.setText(count + " registros");
    }
    
    public void FilterTable(ActionEvent event) {
        String filter = "";
        if (fPeriodo.getText().length()>0) {
            filter = "Periodo = '" + fPeriodo.getText() + "'";
        }
        if (fCurso.getText().length()>0) {
            if (filter.length()>0) filter = filter + " AND ";
            filter = filter + "Curso = '" + fCurso.getText() + "'";
        }
        if (fGrupo.getText().length()>0) {
            if (filter.length()>0) filter = filter + " AND ";
            filter = filter + "Grupo = '" + fGrupo.getText() + "'";
        }
        if (fDNI.getText().length()>0) {
            if (filter.length()>0) filter = filter + " AND ";
            filter = filter + "DNI LIKE '%" + fDNI.getText() + "%'";
        }
        if (fNombre.getText().length()>0) {
            if (filter.length()>0) filter = filter + " AND ";
            filter = filter + "nom LIKE '%" + fNombre.getText() + "%'";
        }
        
        if (filter.length()>0) {
            this.data.removeAll(this.data);
            LoadAlumnosTable(filter);
        }
    }

    public void CleanFilter(ActionEvent event) {
        this.data.removeAll(this.data);
        LoadAlumnosTable("");
    }
}
