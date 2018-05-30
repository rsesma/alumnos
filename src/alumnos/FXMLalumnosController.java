/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumnos;

import alumnos.model.Alumno;
import alumnos.model.Notas;
import alumnos.model.getAlumnosData;
import java.io.File;
import java.io.FileInputStream;
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
import javafx.scene.control.Button;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

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
    TableColumn comentCol;
    @FXML
    Label ntotal;
    @FXML
    TextField search;
    @FXML
    Button btSearch;
    @FXML
    Button btClean;
    
    final ObservableList<Alumno> data = FXCollections.observableArrayList();
    
    getAlumnosData d;
    
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
        this.PCCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Alumno, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Alumno, String> event) {
                TablePosition<Alumno, String> pos = event.getTablePosition();                
                Alumno a = event.getTableView().getItems().get(pos.getRow());
                a.setPC(event.getNewValue());
                a.setChanged(true);
            }
        });

        
        this.comentCol.setCellValueFactory(new PropertyValueFactory<>("Coment"));
        this.comentCol.setCellFactory(TextFieldTableCell.<Alumno> forTableColumn());
        this.comentCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Alumno, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Alumno, String> event) {
                TablePosition<Alumno, String> pos = event.getTablePosition();                
                Alumno a = event.getTableView().getItems().get(pos.getRow());
                a.setComent(event.getNewValue());
                a.setChanged(true);
            }
        });
        
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
                            Boolean newValue) { 
                        a.setFijo(newValue);
                        a.setChanged(true);
                    }
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
                Alumno a = event.getTableView().getItems().get(pos.getRow());
                a.setClase(event.getNewValue().getCode());
                a.setChanged(true);
            }
        });
        
        table.setItems(this.data);
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
            LoadAlumnosTable(filter);
        }
    }

    @FXML
    public void pbClean(ActionEvent event) {
        this.data.removeAll(this.data);
        this.search.setText("");
        LoadAlumnosTable("");
    }

    @FXML
    public void pbGrabar(ActionEvent event) {
        this.data.forEach((a) -> { 
            if (a.getChanged()) {
                this.d.updateAlumno(a);
            }
        });
    }
    
    @FXML
    public void pbImportar(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Resource File");
        chooser.setInitialDirectory(new File(System.getProperty("user.home"))); 
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            try {
                FileInputStream input = new FileInputStream(file.getAbsolutePath());
                HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(input));
                HSSFSheet sheet = wb.getSheetAt(0);
                org.apache.poi.ss.usermodel.Row row;
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);
                    this.d.importExcelFile(row);
                    
/*                    double Level = row.getCell(2).getNumericCellValue();
                    double A = row.getCell(3).getNumericCellValue();
                    double B = row.getCell(4).getNumericCellValue();
                    double C = row.getCell(5).getNumericCellValue();
                    String sql = "INSERT INTO description_process_level VALUES('" + Process + "','" + Level + "','" + A + "',`" + B + "`,`" + C +)";
                    pstm = (PreparedStatement) con.prepareStatement(sql);
                    pstm.execute();
                    System.out.println("Import rows " + i);*/
                }
                input.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }            
        }
    }
    
    @FXML
    public void pbCerrar(ActionEvent event) {
        closeWindow();
    }
    
    public void SetData(getAlumnosData d) {
        this.d = d;
        LoadAlumnosTable("");
    }
    
    public void LoadAlumnosTable(String filter) {
        int count = 0;        
        try{
            ResultSet rs = this.d.getAlumnosRs(filter);
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
                a.setComent(rs.getString("Comentario"));
                
                this.data.add(a);
                
                count++;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        this.ntotal.setText(count + " registros");
    }
    
    private void closeWindow() {
        Stage stage = (Stage) this.search.getScene().getWindow();
        stage.close();
    }    
}
