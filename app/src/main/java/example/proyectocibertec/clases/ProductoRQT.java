package example.proyectocibertec.clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductoRQT {

    @SerializedName("IDProducto")
    @Expose
    private int IDProducto;
    @SerializedName("Nombre")
    @Expose
    private String Nombre;
    @SerializedName("Costo")
    @Expose
    private double Costo;
    @SerializedName("Descripcion")
    @Expose
    private String Descripcion;
    @SerializedName("Foto")
    @Expose
    private String Foto;


    public ProductoRQT(int IDProducto, String Nombre, double Costo, String Descripcion, String  Foto) {
        this.IDProducto = IDProducto;
        this.Nombre = Nombre;
        this.Costo = Costo;
        this.Descripcion = Descripcion;
        this.Foto = Foto;
    }


    public int getIDProducto() {
        return IDProducto;
    }

    public void setIDProducto(int IDProducto) {
        this.IDProducto = IDProducto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public double getCosto() {
        return Costo;
    }

    public void setCosto(double costo) {
        Costo = costo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }
}
