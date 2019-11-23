package example.proyectocibertec.clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpositorNew {

    @SerializedName("IDExpositor")
    @Expose
    private int id;
    @SerializedName("Nombre")
    @Expose
    private String nombre;
    @SerializedName("Descripcion")
    @Expose
    private String descripcion;

    private String foto;

    public ExpositorNew() {

    }

    public ExpositorNew(int id, String nombre, String descripcion, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
