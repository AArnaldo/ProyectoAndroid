package example.proyectocibertec.clases;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharlaNew implements Parcelable {

    @SerializedName("IDCharla")
    @Expose
    private int id;
    @SerializedName("Nombre")
    @Expose
    private String nombre;
    @SerializedName("Descripcion")
    @Expose
    private String descripcion;
    @SerializedName("Fecha")
    @Expose
    private String fechaHoraInicio;
    @SerializedName("Cupos")
    @Expose
    private int capacidad;
    @SerializedName("Observacion")
    @Expose
    private String observaciones;

    //Multimedia
    @SerializedName("Foto")
    @Expose
    private String imagen;

    //Ubicacion
    @SerializedName("Direccion")
    @Expose
    private String direccion;
    @SerializedName("Latitud")
    @Expose
    private String latitud;
    @SerializedName("Longitud")
    @Expose
    private String longitud;

    @SerializedName("base64String")
    @Expose
    private String base64String;

    private List<ExpositorNew> listExpositor;
    private List<Productos> listProducto;

    public CharlaNew() {

    }

    public CharlaNew(int id, String nombre, String descripcion, String fechaHoraInicio, int capacidad, String observaciones, String imagen, String direccion, String latitud, String longitud, String base64String,List<ExpositorNew> listExpositor, List<Productos> listProducto) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaHoraInicio = fechaHoraInicio;
        this.capacidad = capacidad;
        this.observaciones = observaciones;
        this.imagen = imagen;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.base64String = base64String;

        this.listExpositor = listExpositor;
        this.listProducto = listProducto;
    }

    protected CharlaNew(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        descripcion = in.readString();
        fechaHoraInicio = in.readString();
        capacidad = in.readInt();
        observaciones = in.readString();
        imagen = in.readString();
        direccion = in.readString();
        latitud = in.readString();
        longitud = in.readString();
        base64String = in.readString();
    }

    public static final Creator<CharlaNew> CREATOR = new Creator<CharlaNew>() {
        @Override
        public CharlaNew createFromParcel(Parcel in) {
            return new CharlaNew(in);
        }

        @Override
        public CharlaNew[] newArray(int size) {
            return new CharlaNew[size];
        }
    };

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

    public String getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(String fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getBase64String() {
        return base64String;
    }

    public void setBase64String(String base64String) {
        this.base64String = base64String;
    }

    public List<ExpositorNew> getListExpositor() {
        return listExpositor;
    }

    public void setListExpositor(List<ExpositorNew> listExpositor) {
        this.listExpositor = listExpositor;
    }

    public List<Productos> getListProducto() {
        return listProducto;
    }

    public void setListProducto(List<Productos> listProducto) {
        this.listProducto = listProducto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nombre);
        parcel.writeString(descripcion);
        parcel.writeString(fechaHoraInicio);
        parcel.writeInt(capacidad);
        parcel.writeString(observaciones);
        parcel.writeString(imagen);
        parcel.writeString(direccion);
        parcel.writeString(latitud);
        parcel.writeString(longitud);
        parcel.writeString(base64String);
        parcel.writeList(listExpositor);
        parcel.writeList(listProducto);
    }
}
