package example.proyectocibertec.clases;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ClientApiProductos {

    String JSONURL = "http://appcharla.azurewebsites.net/";

   @POST("api/Producto/ListProducto")
   @FormUrlEncoded
   Call<List<Productos>> obtenerProductos(@Field("IDProducto") int IDProducto
                                       , @Field("Nombre") String Nombre
                                       , @Field("Costo") Double Costo
                                       , @Field("Descripcion") String Descripcion
                                       , @Field("Foto") String Foto);

    @POST("api/Producto/InsertProducto")
    @FormUrlEncoded
    Call<Productos> insertProducto(@Field("IDProducto") int IDProducto
            , @Field("Nombre") String Nombre
            , @Field("Costo") Double Costo
            , @Field("Descripcion") String Descripcion
            , @Field("Foto") String Foto);

    @POST("api/Producto/UpdateProducto")
    @FormUrlEncoded
    Call<Productos> updateProducto(@Field("IDProducto") int IDProducto
            , @Field("Nombre") String Nombre
            , @Field("Costo") Double Costo
            , @Field("Descripcion") String Descripcion
            , @Field("Foto") String Foto);

    @POST("api/Producto/SubirImagen")
    @FormUrlEncoded
    Call<Productos> subirImagen(@Field("IDProducto") int IDProducto
            , @Field("Nombre") String Nombre
            , @Field("Costo") Double Costo
            , @Field("Descripcion") String Descripcion
            , @Field("Foto") String Foto
            , @Field("base64String") String base64String);

    @GET("api/Producto/DeleteProducto")
    Call<Productos> deleteProducto(@Query("IDProducto") int IDProducto);

}
