
package alumnos;

import alumnos.model.getAlumnosData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author r
 */
public class Alumnos extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {

        Boolean test = false;
        Boolean ok = true;
        getAlumnosData d = null;
        
        if (!test) {
            // Launch login window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLlogin.fxml")); 
            Parent r0 = (Parent) fxmlLoader.load();
            Stage stage0 = new Stage(); 
            stage0.initModality(Modality.WINDOW_MODAL);
            stage0.setTitle("Registro");
            stage0.setScene(new Scene(r0));
            FXMLloginController login = fxmlLoader.<FXMLloginController>getController();
            stage0.showAndWait();
            
            ok = login.ok;
            if (ok) d = login.d;
        } else {
            d = new getAlumnosData(true);
        }
        
        if (ok) {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("FXMLalumnos.fxml"));
            Parent root = (Parent) fxml.load();
            FXMLalumnosController alumnos = fxml.<FXMLalumnosController>getController();
            alumnos.SetData(d);

            Scene scene = new Scene(root);
            stage.setTitle("Alumnos cursos Stata");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
