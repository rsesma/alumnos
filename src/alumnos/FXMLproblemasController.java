/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import alumnos.model.Problema;
import alumnos.model.getAlumnosData;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author R
 */
public class FXMLproblemasController implements Initializable {

    @FXML
    TableView<Problema> table;
    @FXML
    private TableColumn grupoCol;
    @FXML
    private TableColumn dniCol;
    @FXML
    private TableColumn nombreCol;
    @FXML
    private TableColumn mdbCol;
    @FXML
    private TableColumn pdfCol;
    @FXML
    private TableColumn honorCol;
    @FXML
    private TableColumn emailCol;
    @FXML
    TextField search;
    @FXML
    Button btSearch;
    @FXML
    Button btClean;

    final ObservableList<Problema> data = FXCollections.observableArrayList();
    
    getAlumnosData d;
    boolean pec1;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
	ImageView imgSearch = new ImageView(new Image(getClass().getResourceAsStream("search.png")));
	imgSearch.setFitWidth(15);
	imgSearch.setFitHeight(15);
        this.btSearch.setGraphic(imgSearch);
        
	ImageView imgClean = new ImageView(new Image(getClass().getResourceAsStream("no_filter.png")));
	imgClean.setFitWidth(15);
	imgClean.setFitHeight(15);
        this.btClean.setGraphic(imgClean);

        this.table.setItems(this.data);
        this.table.setEditable(true);
        
        // Set up the alumnos table
        this.grupoCol.setCellValueFactory(new PropertyValueFactory<>("Grupo"));
        this.nombreCol.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        this.dniCol.setCellValueFactory(new PropertyValueFactory<>("DNI"));
        this.emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));

        // checkbox MDB
        this.mdbCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Problema, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Problema, Boolean> param) {
                Problema p = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(p.getMDB());
                // when column change
                booleanProp.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                            Boolean newValue) { 
                        p.setMDB(newValue);
                        p.setChanged(true);
                    }
                });
                return booleanProp;
            }
        });
        this.mdbCol.setCellFactory(new Callback<TableColumn<Problema, Boolean>, TableCell<Problema, Boolean>>() {
            @Override
            public TableCell<Problema, Boolean> call(TableColumn<Problema, Boolean> p) {
                CheckBoxTableCell<Problema, Boolean> cell = new CheckBoxTableCell<Problema, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
        
        // checkbox PDF
        this.pdfCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Problema, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Problema, Boolean> param) {
                Problema p = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(p.getPDF());
                // when column change
                booleanProp.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                            Boolean newValue) { 
                        p.setPDF(newValue);
                        p.setChanged(true);
                    }
                });
                return booleanProp;
            }
        });
        this.pdfCol.setCellFactory(new Callback<TableColumn<Problema, Boolean>, TableCell<Problema, Boolean>>() {
            @Override
            public TableCell<Problema, Boolean> call(TableColumn<Problema, Boolean> p) {
                CheckBoxTableCell<Problema, Boolean> cell = new CheckBoxTableCell<Problema, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });

        // checkbox MDB
        this.honorCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Problema, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Problema, Boolean> param) {
                Problema p = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(p.getHonor());
                // when column change
                booleanProp.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                            Boolean newValue) { 
                        p.setHonor(newValue);
                        p.setChanged(true);
                    }
                });
                return booleanProp;
            }
        });
        this.honorCol.setCellFactory(new Callback<TableColumn<Problema, Boolean>, TableCell<Problema, Boolean>>() {
            @Override
            public TableCell<Problema, Boolean> call(TableColumn<Problema, Boolean> p) {
                CheckBoxTableCell<Problema, Boolean> cell = new CheckBoxTableCell<Problema, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
    }
    
    @FXML
    public void pbSearch(ActionEvent event) {
        String filter = "";
        if (!this.search.getText().trim().isEmpty()) {
            filter = "Periodo = '".concat(this.search.getText()).concat("'");
            filter = filter.concat("OR Grupo = '").concat(this.search.getText()).concat("'");
            filter = filter.concat("OR DNI LIKE '%").concat(this.search.getText()).concat("%'");
            filter = filter.concat("OR nom LIKE '%").concat(this.search.getText()).concat("%'");
            this.data.removeAll(this.data);
            LoadProblemasTable(filter);
        }
    }

    @FXML
    public void pbClean(ActionEvent event) {
        this.data.removeAll(this.data);
        this.search.setText("");
        LoadProblemasTable("");
    }

    @FXML
    public void pbCopiarMail(ActionEvent event) {
        StringBuilder mails = new StringBuilder("");
        this.data.forEach((p) -> { 
            mails.append(p.getEmail().concat(", "));
        });
        
        Toolkit.getDefaultToolkit()
            .getSystemClipboard()
            .setContents(new StringSelection(mails.toString()),null);
    }
    
    @FXML
    public void pbGrabar(ActionEvent event) {
        this.data.forEach((p) -> { 
            if (p.getChanged()) this.d.updateEntregaPEC(p,this.pec1);
        });
        this.pbSearch(null);
    }
    
    public void SetData(getAlumnosData d, boolean pec1) {
        this.d = d;
        this.pec1 = pec1;
        if (!this.pec1) {
            this.mdbCol.setVisible(false);
            this.pdfCol.setVisible(false);
        }
        LoadProblemasTable("");
    }
    
    public void LoadProblemasTable(String filter) {
        try{
            ResultSet rs;
            if (this.pec1) rs = this.d.getProblemasPEC1(filter);
            else rs = this.d.getProblemasPEC(filter);
            while(rs.next()){
                Problema p = new Problema();
                p.setGrupo(rs.getString("Grupo"));
                p.setDNI(rs.getString("DNI"));
                p.setNombre(rs.getString("nom"));
                if (this.pec1) {
                    p.setMDB(rs.getBoolean("mdb"));
                    p.setPDF(rs.getBoolean("pdf"));
                } else {
                    p.setMDB(false);
                    p.setPDF(false);
                }
                p.setHonor(rs.getBoolean("honor"));
                p.setEmail(rs.getString("email"));
                
                this.data.add(p);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
}
