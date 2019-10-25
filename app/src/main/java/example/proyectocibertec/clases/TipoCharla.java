package example.proyectocibertec.clases;

public class TipoCharla {

    private int id_tipo_charla;
    private String nombre_tipo_charla;

    public int getId_tipo_charla() {
        return id_tipo_charla;
    }

    public void setId_tipo_charla(int id_tipo_charla) {
        this.id_tipo_charla = id_tipo_charla;
    }

    public String getNombre_tipo_charla() {
        return nombre_tipo_charla;
    }

    public void setNombre_tipo_charla(String nombre_tipo_charla) {
        this.nombre_tipo_charla = nombre_tipo_charla;
    }

    public TipoCharla(int id_tipo_charla, String nombre_tipo_charla) {
        this.id_tipo_charla = id_tipo_charla;
        this.nombre_tipo_charla = nombre_tipo_charla;
    }

    public String toString(){
        return nombre_tipo_charla;
    }
}
