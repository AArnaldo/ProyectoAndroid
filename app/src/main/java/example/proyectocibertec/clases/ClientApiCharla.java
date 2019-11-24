package example.proyectocibertec.clases;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ClientApiCharla {

    String JSONURL = "http://appcharla.azurewebsites.net/";

    @POST("api/Charla/InsertCharla")
    @FormUrlEncoded
    Call<CharlaNew> insertCharla(@Field("IDCharla") int IDCharla
            , @Field("Nombre") String nombre
            , @Field("Descripcion") String descripcion
            , @Field("Fecha") String fecha
            , @Field("Cupos") int capacidad
            , @Field("Observacion") String observaciones
            , @Field("Foto") String foto
            , @Field("Direccion") String direccion
            , @Field("Latitud") String latitud
            , @Field("Longitud") String longitud);

    @POST("api/Charla/SubirImagen")
    @FormUrlEncoded
    Call<CharlaNew> subirImagen(@Field("IDCharla") int IDProducto
            , @Field("Nombre") String nombre
            , @Field("Descripcion") String descripcion
            , @Field("Fecha") String fecha
            , @Field("Cupos") int capacidad
            , @Field("Observacion") String observaciones
            , @Field("Foto") String foto
            , @Field("Direccion") String direccion
            , @Field("Latitud") String latitud
            , @Field("Longitud") String longitud
            , @Field("base64String") String base64String);

    @GET("api/Charla/ListAllCharla")
    Call<List<CharlaNew>> getCharlas();

}
