package example.proyectocibertec.clases;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ClientApiExpositor {

    String JSONURL = "http://appcharla.azurewebsites.net/";

    @GET("api/ExpositorCharla/ListExpositorCharlaId/{IDCharla}")
    Call<List<ExpositorNew>> getExpositor(@Path("IDCharla") int id);



}
