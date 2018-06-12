/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import alumnos.model.Alumno;
import alumnos.model.Notas;
import alumnos.model.getAlumnosData;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author r
 */
public class FXMLcorregirController implements Initializable {

    @FXML
    private TableView<Alumno> table;
    @FXML
    private TableColumn nCol;
    @FXML
    private TableColumn periodoCol;
    @FXML
    private TableColumn cursoCol;
    @FXML
    private TableColumn grupoCol;
    @FXML
    private TableColumn DNICol;
    @FXML
    private TableColumn PCCol;
    @FXML
    private TableColumn nameCol;
    @FXML
    private TableColumn claseCol;
    @FXML
    private TableColumn pecCol;    
    @FXML
    private TableColumn copiaCol;
    @FXML
    private TableColumn idCopiaCol;
    @FXML
    private Label ntotal;
    @FXML
    private TextField search;
    @FXML
    private Button btSearch;
    @FXML
    private Button btClean;

    final ObservableList<Alumno> data = FXCollections.observableArrayList();
    
    getAlumnosData d;
    
    private static final String D_CorregirPECs = "/CorregirPECs";
    private static final String ST1_originales = "/ST1/PEC2/originales/PEC2_ST1_";
    private static final String ST1_corregidas = "/ST1/PEC2/corregidas/PEC2_ST1_";
    private static final String ST1_sintaxis = "/ST1/PEC2/sintaxis/";
    private static final String ST2_originales = "/ST2/originales/PEC1_ST2_";
    private static final String ST2_corregidas = "/ST2/corregidas/PEC1_ST2_";
    private static final String ST2_sintaxis = "/ST2/sintaxis/";

    private final File def = new File(new File(System.getProperty("user.home")), D_CorregirPECs);
    
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
                
        // Set up the alumnos table
        this.nCol.setCellValueFactory(new PropertyValueFactory<>("N"));
        this.periodoCol.setCellValueFactory(new PropertyValueFactory<>("Periodo"));
        this.cursoCol.setCellValueFactory(new PropertyValueFactory<>("Curso"));
        this.grupoCol.setCellValueFactory(new PropertyValueFactory<>("Grupo"));
        this.DNICol.setCellValueFactory(new PropertyValueFactory<>("DNI"));
        this.PCCol.setCellValueFactory(new PropertyValueFactory<>("PC"));
        this.nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        this.claseCol.setCellValueFactory(new PropertyValueFactory<>("Clase"));
        this.pecCol.setCellValueFactory(new PropertyValueFactory<>("PEC"));

        // checkbox copia
        this.copiaCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Alumno, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Alumno, Boolean> param) {
                Alumno a = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(a.getCopia());
                // When "Copia" column change.
                booleanProp.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                            Boolean newValue) { 
                        a.setCopia(newValue);
                        a.setChanged(true);
                    }
                });                
                return booleanProp;
            }
        });
        this.copiaCol.setCellFactory(new Callback<TableColumn<Alumno, Boolean>, TableCell<Alumno, Boolean>>() {
            @Override
            public TableCell<Alumno, Boolean> call(TableColumn<Alumno, Boolean> p) {
                CheckBoxTableCell<Alumno, Boolean> cell = new CheckBoxTableCell<Alumno, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });

        // IDcopia is editable
        this.idCopiaCol.setCellValueFactory(new PropertyValueFactory<>("IDCopia"));
        this.idCopiaCol.setCellFactory(TextFieldTableCell.<Alumno> forTableColumn());
        this.idCopiaCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Alumno, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Alumno, String> event) {
                TablePosition<Alumno, String> pos = event.getTablePosition();                
                Alumno a = event.getTableView().getItems().get(pos.getRow());
                a.setIDCopia(event.getNewValue());
                a.setChanged(true);
            }
        });
        
        this.table.setEditable(true);
        this.table.setItems(this.data);
    }

    public void mnuAbrePEC(ActionEvent event) {
        File pdfSource = null;
        File pdfDest = null;
        File doFile = null;
        if (Desktop.isDesktopSupported()) {
            Alumno a = this.table.getItems().get(this.table.getSelectionModel().getSelectedIndex());
            if (a != null) {
                String dni = a.getDNI();
                String curso = a.getCurso();
                try {
                    if (curso.equalsIgnoreCase("ST1")) {
                        pdfSource = new File(def, ST1_originales.concat(dni).concat(".pdf"));
                        pdfDest = new File(def, ST1_corregidas.concat(dni).concat(".pdf"));
                        doFile = new File(def, ST1_sintaxis.concat(dni).concat(".do"));
                    }
                    if (curso.equalsIgnoreCase("ST2")) {
                        pdfSource = new File(def, ST2_originales.concat(dni).concat(".pdf"));
                        pdfDest = new File(def, ST2_corregidas.concat(dni).concat(".pdf"));
                        doFile = new File(def, ST2_sintaxis.concat(dni).concat(".do"));
                    }

                    Files.move(pdfSource.toPath(), pdfDest.toPath());
                    Desktop.getDesktop().open(pdfDest);
                    Desktop.getDesktop().open(doFile);
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                    alert.showAndWait();
                }
            }
        }
    }

    public void mnuNotaPEC(ActionEvent event) {
        File pdf = null;
        Alumno a = this.table.getItems().get(this.table.getSelectionModel().getSelectedIndex());
        if (a != null) {
            String dni = a.getDNI();
            String curso = a.getCurso();
            try {
                if (curso.equalsIgnoreCase("ST1")) pdf = new File(def, ST1_corregidas.concat(dni).concat(".pdf"));
                if (curso.equalsIgnoreCase("ST2")) pdf = new File(def, ST2_corregidas.concat(dni).concat(".pdf"));

                PdfReader reader = new PdfReader(pdf.getAbsolutePath());
                AcroFields form = reader.getAcroFields();

                ResultSet rs = this.d.getPreguntasRs(a.getPeriodo(),curso);
                while(rs.next()){
                    String p = rs.getString("pregunta");
                    String r = form.getField("P"+p).replace(".", ",");
                    
                    this.d.insertRespuesta(a.getPeriodo(), curso, dni, p, r);
                }
                reader.close();
                rs.close();
           
                // update tableview to show new NOTAPEC
                this.pbSearch(null);
                
                // select item again
                this.data.forEach((i) -> { 
                    if (i.getN().equals(a.getN())) {
                        table.getSelectionModel().select(i);
                        table.scrollTo(i);
                    }
                });
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        }
    }
    
    public void mnuAbrirCorr(ActionEvent event) {
        File pdf = null;
        if (Desktop.isDesktopSupported()) {
            Alumno a = this.table.getItems().get(this.table.getSelectionModel().getSelectedIndex());
            if (a != null) {
                String dni = a.getDNI();
                String curso = a.getCurso();
                try {
                    if (curso.equalsIgnoreCase("ST1")) pdf = new File(def, ST1_corregidas.concat(dni).concat(".pdf"));
                    if (curso.equalsIgnoreCase("ST2")) pdf = new File(def, ST2_corregidas.concat(dni).concat(".pdf"));

                    Desktop.getDesktop().open(pdf);
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                    alert.showAndWait();
                }
            }
        }
    }
    
    @FXML
    public void mnuSave(ActionEvent event) {
        this.data.forEach((a) -> { 
            if (a.getChanged()) {
                this.d.updateCopia(a);
            }
        });
    }

    @FXML
    public void mnuClose(ActionEvent event) {
        Stage stage = (Stage) this.search.getScene().getWindow();
        stage.close();
    }
    
    public void SetData(getAlumnosData d) {
        this.d = d;
        LoadTable("");
    }
    
    public void LoadTable(String filter) {
        int count = 0;        
        try{
            ResultSet rs = this.d.getCorrigeRs(filter);
            while(rs.next()){
                count++;
                this.data.add(LoadAlumno(rs,count));
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        this.ntotal.setText(count + " registros");
    }
    
    public Alumno LoadAlumno(ResultSet rs, Integer count) {
        Alumno a = new Alumno();
        try {
            a.setPeriodo(rs.getString("Periodo"));
            a.setCurso(rs.getString("Curso"));
            a.setGrupo(rs.getString("Grupo"));
            a.setDNI(rs.getString("DNI"));
            a.setPC(rs.getString("PC"));
            a.setName(rs.getString("nom"));
            a.setClase(Notas.getByCode(rs.getString("CLASE")).toString());
            a.setPEC(rs.getString("Nota_PEC"));
            a.setCopia(rs.getBoolean("Copia"));
            a.setIDCopia(rs.getString("IDcopia"));
            a.setN(Integer.toString(count));
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return a;
    }
    
    @FXML
    void pbClean(ActionEvent event) {
        this.data.removeAll(this.data);
        this.search.setText("");
        LoadTable("");
    }

    @FXML
    void pbSearch(ActionEvent event) {
        if (!this.search.getText().trim().isEmpty()) {
            String c = this.search.getText().trim();
            String filter = "Periodo = '".concat(c.split(" ")[0]).concat("'");
            if (c.contains(" ")) filter = filter.concat("AND Curso = '").concat(c.split(" ")[1]).concat("'");
            this.data.removeAll(this.data);
            LoadTable(filter);
        }
    }
}
