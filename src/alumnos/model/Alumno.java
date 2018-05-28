
package alumnos.model;

public class Alumno {
    private String periodo;
    private String curso;
    private String grupo;
    private String dni;
    private String pc;
    private Boolean fijo;
    private String name;
    private String clase;
    private String pec1;
    private String pec;
    private String nota;
    
    public Alumno() {
    }
    
    public String getPeriodo() {
        return this.periodo;
    }

    public void setPeriodo(String c) {
        this.periodo = c;
    }

    public String getCurso() {
        return this.curso;
    }

    public void setCurso(String c) {
        this.curso = c;
    }

    public String getGrupo() {
        return this.grupo;
    }

    public void setGrupo(String c) {
        this.grupo = c;
    }
    
    public String getDNI() {
        return this.dni;
    }

    public void setDNI(String c) {
        this.dni = c;
    }

    public String getPC() {
        return this.pc;
    }

    public void setPC(String c) {
        this.pc = c;
    }
    
    public Boolean getFijo() {
        return this.fijo;
    }

    public void setFijo(Boolean l) {
        this.fijo = l;
    }
    
    public String getName() {
        return this.name;
    }

    public void setName(String c) {
        this.name = c;
    }
    
    public String getClase() {
        return this.clase;
    }

    public void setClase(String c) {
        this.clase = c;
    }

    public String getPEC1() {
        return this.pec1;
    }

    public void setPEC1(String c) {
        this.pec1 = c;
    }

    public String getPEC() {
        return this.pec;
    }

    public void setPEC(String c) {
        this.pec = c;
    }
    
    public String getNOTA() {
        return this.nota;
    }

    public void setNOTA(String c) {
        this.nota = c;
    }

/*  
    public SimpleStringProperty periodo = new SimpleStringProperty();
    public SimpleStringProperty curso = new SimpleStringProperty();
    public SimpleStringProperty grupo = new SimpleStringProperty();
    public SimpleStringProperty dni = new SimpleStringProperty();
    public SimpleIntegerProperty pc = new SimpleIntegerProperty();
    public SimpleStringProperty fijo = new SimpleStringProperty();
    public SimpleStringProperty name = new SimpleStringProperty();
    public SimpleStringProperty clase = new SimpleStringProperty();
    public SimpleStringProperty pec1 = new SimpleStringProperty();
    public SimpleStringProperty pec = new SimpleStringProperty();
    public SimpleFloatProperty nota = new SimpleFloatProperty();
    public SimpleStringProperty coment = new SimpleStringProperty();
    
    public final StringProperty dniProperty() {
        return this.dni;
    }
    
    public final String getDNI() {
        return this.dniProperty().get();
    }

    public final void setDNI(final String c) {
        this.dniProperty().set(c);
    }

    public final StringProperty grupoProperty() {
        return this.grupo;
    }
    
    public final String getGrupo() {
        return this.grupoProperty().get();
    }

    public final void setGrupo(final String c) {
        this.grupoProperty().set(c);
    }

    public final StringProperty cursoProperty() {
        return this.curso;
    }
    
    public final String getCurso() {
        return this.cursoProperty().get();
    }

    public final void setCurso(final String c) {
        this.cursoProperty().set(c);
    }

    public final StringProperty periodoProperty() {
        return this.periodo;
    }
    
    public final String getPeriodo() {
        return this.periodoProperty().get();
    }

    public final void setPeriodo(final String c) {
        this.periodoProperty().set(c);
    }

    

    
    public final StringProperty periodoProperty() {
        return this.periodo;
    }
    
    public final String getPeriodo() {
        return this.periodoProperty().get();
    }

    public final void setPeriodo(final String c) {
        this.periodoProperty().set(c);
    }
    
    
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
    }*/
}
