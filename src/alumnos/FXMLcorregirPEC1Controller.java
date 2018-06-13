/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import alumnos.model.Alumno;
import alumnos.model.getAlumnosData;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import java.awt.Desktop;
import java.io.File;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

/**
 * FXML Controller class
 *
 * @author r
 */
public class FXMLcorregirPEC1Controller implements Initializable {

    @FXML
    private TableView<Alumno> table;
    @FXML
    private TableColumn nCol;
    @FXML
    private TableColumn periodoCol;
    @FXML
    private TableColumn grupoCol;
    @FXML
    private TableColumn DNICol;
    @FXML
    private TableColumn PCCol;
    @FXML
    private TableColumn nameCol;
    @FXML
    private TableColumn pecCol;
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
    private static final String DESCOMPRIMIDAS = "/ST1/PEC1/descomprimidas/";
    private static final String CORREGIDAS = "/ST1/PEC1/corregidas/";

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
        this.grupoCol.setCellValueFactory(new PropertyValueFactory<>("Grupo"));
        this.DNICol.setCellValueFactory(new PropertyValueFactory<>("DNI"));
        this.PCCol.setCellValueFactory(new PropertyValueFactory<>("PC"));
        this.nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        this.pecCol.setCellValueFactory(new PropertyValueFactory<>("PEC1"));
        
        this.table.setEditable(true);
        this.table.setItems(this.data);
    }

    public void mnuAbrePEC(ActionEvent event) {
        File source = null;
        File dest = null;
        if (Desktop.isDesktopSupported()) {
            Alumno a = this.table.getItems().get(this.table.getSelectionModel().getSelectedIndex());
            if (a != null) {
                String dni = a.getDNI();
                source = new File(def, DESCOMPRIMIDAS.concat(dni));
                dest = new File(def, CORREGIDAS.concat(dni));

                try {
                    // move files to corregidas path
                    FileUtils.moveDirectory(source, dest);

                    // open pdf and database file
                    File[] list = dest.listFiles();
                    for (File f : list) {
                        String ext = f.getName().substring(f.getName().lastIndexOf(".")+1);
                        if (ext.equalsIgnoreCase("pdf") || ext.equalsIgnoreCase("mdb") || 
                                ext.equalsIgnoreCase("accdb") || ext.equalsIgnoreCase("odb")) {
                            Desktop.getDesktop().open(f);
                        }
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                    alert.showAndWait();
                }
            }
        }
    }

    public void mnuNotaPEC(ActionEvent event) {
        Alumno a = this.table.getItems().get(this.table.getSelectionModel().getSelectedIndex());
        if (a != null) {
            String dni = a.getDNI();
            File folder = new File(def, CORREGIDAS.concat(dni));
            try {
                // open pdf and database file
                File[] list = folder.listFiles();
                for (File f : list) {
                    String ext = f.getName().substring(f.getName().lastIndexOf(".")+1);
                    if (ext.equalsIgnoreCase("pdf")) {
                        // open pdf, get NOTA PEC1 and update server
                        PdfReader reader = new PdfReader(f.getAbsolutePath());
                        AcroFields form = reader.getAcroFields();
                        Integer n = Integer.parseInt(form.getField("NOTA"));
                        this.d.updatePEC1(dni, a.getGrupo(), n);
                        reader.close();
                        
                        // update tableview to show new NOTAPEC
                        this.pbSearch(null);

                        // select item again
                        this.data.forEach((i) -> { 
                            if (i.getN().equals(a.getN())) {
                                table.getSelectionModel().select(i);
                                table.scrollTo(i);
                            }
                        });
                    }
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        }
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
            ResultSet rs = this.d.getCorrigePEC1Rs(filter);
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
            a.setGrupo(rs.getString("Grupo"));
            a.setDNI(rs.getString("DNI"));
            a.setPC(rs.getString("PC"));
            a.setName(rs.getString("nom"));
            a.setPEC1(rs.getString("PEC1"));
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
            this.data.removeAll(this.data);
            LoadTable("Periodo = '".concat(this.search.getText().trim()).concat("'"));
        }
    }
}
