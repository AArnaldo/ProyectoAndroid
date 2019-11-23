package example.proyectocibertec.clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Productos {
    @SerializedName("IDProducto")
    @Expose
    private int id;
    @SerializedName("Nombre")
    @Expose
    private String nombre;
    @SerializedName("Costo")
    @Expose
    private double costo;
    @SerializedName("Descripcion")
    @Expose
    private String descripcion;
    @SerializedName("Foto")
    @Expose
    private String imagen;
    @SerializedName("base64String")
    @Expose
    private String base64String;

    public Productos() {

    }
    public Productos(int id, String nombre, double costo, String descripcion, String imagen, String base64String) {
        this.id = id;
        this.nombre = nombre;
        this.costo = costo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.base64String = base64String;
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

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getBase64String() {
        return base64String;
    }

    public void setBase64String(String base64String) {
        this.base64String = base64String;
    }
}
