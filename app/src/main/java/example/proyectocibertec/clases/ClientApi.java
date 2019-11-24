package example.proyectocibertec.clases;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ClientApi {
    String JSONURL = "https://demonuts.com/Demonuts/JsonTest/Tennis/";

    @GET("json_parsing.php")
    Call<String> getString();

    @GET("api/Usuario/loginusuario")
    Call<UsuarioEdit> getUsuarioEdit(@Query("Correo") String email, @Query("Password") String password);

    @POST("api/Usuario/updateusuario")
    @FormUrlEncoded
    Call<UsuarioEdit> updateUsuario(@Field("IDUsuario") int IDUsuario
            , @Field("Nombres") String Nombres
            , @Field("Apellidos") String Apellidos
            , @Field("Direccion") String Direccion
            , @Field("Latitud") String Latitud
            , @Field("Longitud") String Longitud
            , @Field("Correo") String Correo
            , @Field("Clave") String Clave
            , @Field("TipoUsuario") int TipoUsuario
            , @Field("Foto") String Foto
            , @Field("Telefono") String Telefono
    );
}
