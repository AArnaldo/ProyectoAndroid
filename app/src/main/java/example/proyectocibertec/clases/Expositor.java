package example.proyectocibertec.clases;

import android.media.Image;

public class Expositor {

    private int id_expositor;
    private String nombre;
    private String descripcion;
    private int foto;

    public int getId_expositor() {
        return id_expositor;
    }

    public void setId_expositor(int id_expositor) {
        this.id_expositor = id_expositor;
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

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public Expositor(int id_expositor, String nombre, String descripcion, int foto) {
    //public Expositor(int id_expositor, String nombre, String descripcion) {
        this.id_expositor = id_expositor;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
    }


}
