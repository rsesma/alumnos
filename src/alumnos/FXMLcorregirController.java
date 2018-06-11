/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import alumnos.model.Alumno;
import alumnos.model.Notas;
import alumnos.model.getAlumnosData;
import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private TableColumn abreCol;
    @FXML
    private TableColumn notaCol;
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

        this.abreCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        Callback<TableColumn<Alumno, String>, TableCell<Alumno, String>> cellAbre = (final TableColumn<Alumno, String> param) -> {
            final TableCell<Alumno, String> cell = new TableCell<Alumno, String>() {
                final Button btn = new Button("Abrir");
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btn.setOnAction(event -> {
                            Alumno a = getTableView().getItems().get(getIndex());
                            AbrePEC(a.getDNI(), a.getCurso());
                        });
                        setGraphic(btn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        this.abreCol.setCellFactory(cellAbre);
        
        this.notaCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY2"));
        Callback<TableColumn<Alumno, String>, TableCell<Alumno, String>> cellNota = (final TableColumn<Alumno, String> param) -> {
            final TableCell<Alumno, String> cell = new TableCell<Alumno, String>() {
                final Button btn = new Button("Nota");
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btn.setOnAction(event -> {
                            Alumno a = getTableView().getItems().get(getIndex());
                            System.out.println(a.getDNI());
                        });
                        setGraphic(btn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        this.notaCol.setCellFactory(cellNota);
        
        this.table.setEditable(true);
        this.table.setItems(this.data);
    }

    public void AbrePEC(String dni, String curso) {
        File pdfSource = null;
        File pdfDest = null;
        File doFile = null;
        if (Desktop.isDesktopSupported()) {
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
    
    public void SetData(getAlumnosData d) {
        this.d = d;
        LoadTable("");
    }
    
    public void LoadTable(String filter) {
        int count = 0;        
        try{
            ResultSet rs = this.d.getCorrigeRs(filter);
            while(rs.next()){
                Alumno a = new Alumno();
                a.setPeriodo(rs.getString("Periodo"));
                a.setCurso(rs.getString("Curso"));
                a.setGrupo(rs.getString("Grupo"));
                a.setDNI(rs.getString("DNI"));
                a.setPC(rs.getString("PC"));
                a.setName(rs.getString("nom"));
                a.setClase(Notas.getByCode(rs.getString("CLASE")).toString());
                a.setPEC(rs.getString("Nota_PEC"));
                
                count++;
                a.setN(Integer.toString(count));
                
                this.data.add(a);
                
                
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        this.ntotal.setText(count + " registros");
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
