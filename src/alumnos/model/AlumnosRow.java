
package alumnos.model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AlumnosRow {
    public SimpleStringProperty itemPeriodo = new SimpleStringProperty();
    public SimpleStringProperty itemCurso = new SimpleStringProperty();
    public SimpleStringProperty itemGrupo = new SimpleStringProperty();
    public SimpleStringProperty itemDNI = new SimpleStringProperty();
    public SimpleIntegerProperty itemPC = new SimpleIntegerProperty();
    public SimpleStringProperty itemFijo = new SimpleStringProperty();
    public SimpleStringProperty itemName = new SimpleStringProperty();
    public SimpleStringProperty itemCLASE = new SimpleStringProperty();
    public SimpleStringProperty itemPEC1 = new SimpleStringProperty();
    public SimpleStringProperty itemPEC = new SimpleStringProperty();
    public SimpleFloatProperty itemNOTA = new SimpleFloatProperty();
 
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

    public Integer getItemPC() {
        return itemPC.get();
    }
    
    public String getItemFijo() {
        return itemFijo.get();
    }
    
    public String getItemName() {
        return itemName.get();
    }

    public String getItemCLASE() {
        return itemCLASE.get();
    }    
    
    public String getItemPEC1() {
        return itemPEC1.get();
    }
    
    public String getItemPEC() {
        return itemPEC.get();
    }
    
    public Float getItemNOTA() {
        return itemNOTA.get();
    }
}
