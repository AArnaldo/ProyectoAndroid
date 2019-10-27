package example.proyectocibertec.clases;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ClientApi {
    String JSONURL = "https://demonuts.com/Demonuts/JsonTest/Tennis/";

    @GET("json_parsing.php")
    Call<String> getString();
}
