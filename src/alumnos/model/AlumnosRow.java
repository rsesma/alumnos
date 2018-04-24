
package alumnos.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AlumnosRow {
    public SimpleStringProperty itemPeriodo = new SimpleStringProperty();
    public SimpleStringProperty itemCurso = new SimpleStringProperty();
    public SimpleStringProperty itemGrupo = new SimpleStringProperty();
    public SimpleStringProperty itemDNI = new SimpleStringProperty();
    public SimpleStringProperty itemPC = new SimpleStringProperty();
    public SimpleStringProperty itemName = new SimpleStringProperty();
 
    public String getItemPeriodo() {
        return itemPeriodo.get();
    }

    public String getItemCurso() {
        return itemCurso.get();
    }
    
    public String getItemGrupo() {
        return itemGrupo.get();
    }
        
    public String getItemDNI() {
        return itemDNI.get();
    }

    public String getItemPC() {
        return itemPC.get();
    }
    
    public String getItemName() {
        return itemName.get();
    }
}
