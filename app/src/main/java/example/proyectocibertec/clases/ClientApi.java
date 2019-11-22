package example.proyectocibertec.clases;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ClientApi {
    String JSONURL = "https://demonuts.com/Demonuts/JsonTest/Tennis/";

    @GET("json_parsing.php")
    Call<String> getString();

    @GET("api/Usuario/loginusuario")
    Call<UsuarioEdit> getUsuarioEdit(@Query("Correo") String email, @Query("Password") String password);
}
