package example.proyectocibertec;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import example.proyectocibertec.clases.ClientApi;
import example.proyectocibertec.clases.UsuarioEdit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ImageButton ibtnLinkedIn, ibtnYouTube, ibtnInstagram;
    private Button btnIngresar;
    private EditText etUsuario, etPassword;
    private String Link;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializar
        ibtnLinkedIn = findViewById(R.id.ibtn_LinkedIn);
        ibtnYouTube = findViewById(R.id.ibtn_YouTube);
        ibtnInstagram = findViewById(R.id.ibtn_Instagram);
        btnIngresar = findViewById(R.id.btnIngresar);

        etUsuario = (EditText)findViewById(R.id.txt_Usuario);
        etPassword = (EditText)findViewById(R.id.txt_Password);
        etUsuario.setText("e.ramirez@gmail.com");
        etPassword.setText("123456");

        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sp_file_mensajes_key),MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void Ingresar(View view){
        String nombre = etUsuario.getText().toString();
        String password = etPassword.getText().toString();

        if (nombre.length() == 0){
            Toast.makeText(this, "Ingrese el usuario", Toast.LENGTH_SHORT).show();
        }
        if (password.length() == 0){
            Toast.makeText(this, "Ingrese la Contraseña", Toast.LENGTH_SHORT).show();
        }

        if (nombre.length()!= 0 && password.length() != 0){
            Toast.makeText(this, "Validando Credenciales...", Toast.LENGTH_SHORT).show();
            btnIngresar.setEnabled(false);
            scale(btnIngresar,0f);
            callService();
            /*Intent intent = new Intent(MainActivity.this, DrawerActivity.class);
            startActivity(intent);*/

            /*
            btnIngresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, DrawerActivity.class);
                    startActivity(intent);
                }
            });
            * */
        }
    }

    private void goLinkedIn(View view){
        String URLLinkedInd = "www.https://www.linkedin.com/school/cibertec/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(URLLinkedInd));
        startActivity(i);
    }

    private void callService(){
        //progressBar.setVisibility(View.VISIBLE);
        //textViewMessage.setVisibility(View.GONE);
        // LLAMAREMOS AL SERVICIO
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://appcharla.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ClientApi clientApi = retrofit.create(ClientApi.class);
        Call<UsuarioEdit> call = clientApi.getUsuarioEdit(etUsuario.getText().toString(),etPassword.getText().toString());
        call.enqueue(new Callback<UsuarioEdit>() {
            @Override
            public void onResponse(Call<UsuarioEdit> call, Response<UsuarioEdit> response) {
                btnIngresar.setEnabled(true);

                //textViewMessage.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.GONE);
                if(!response.isSuccessful()){
                    Toast.makeText(getBaseContext(), "Error al validar credenciales: " + response.code(), Toast.LENGTH_SHORT).show();
                    scale(btnIngresar,1f);
                    //textViewMessage.setText("Code: " + response.code());
                } else {
                    //Toast.makeText(getBaseContext(), "Response: OK", Toast.LENGTH_SHORT).show();
                    UsuarioEdit usuarioEdit = response.body();
                    if(usuarioEdit == null)
                    {
                        Toast.makeText(getBaseContext(), "Usuario y/o Password errados", Toast.LENGTH_SHORT).show();
                        scale(btnIngresar,1f);
                    }
                    else
                    {
                        editor.putString(getResources().getString(R.string.session_campo_correo),usuarioEdit.getCorreo());
                        editor.apply();

                        Intent intent = new Intent(MainActivity.this, DrawerActivity.class);
                        intent.putExtra("Usuario",usuarioEdit);
                        startActivity(intent);
                        etUsuario.setText("");
                        etPassword.setText("");
                    }
                    /*for(Post post : posts) {
                        String content = "";
                        content += "Id: " + post.getId() + "\n";
                        content += "userId: " + post.getUserId() + "\n";
                        content += "Title: " + post.getTitle() + "\n";
                        content += "Body: " + post.getText() + "\n\n";
                        textViewMessage.append(content);
                    }*/
                }
            }

            @Override
            public void onFailure(Call<UsuarioEdit> call, Throwable t) {
                /*textViewMessage.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                textViewMessage.setText(t.getMessage());*/
                t.printStackTrace();
                btnIngresar.setEnabled(true);
            }
        });
    }


    private void scale(Button objAnimated,float scale){
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, scale);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, scale);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(objAnimated,
                scaleX, scaleY);
        //animator.setRepeatMode(ObjectAnimator.REVERSE);
        //animator.setRepeatCount(1);
        disableViewDuringAnimation(animator, objAnimated);
        animator.start();
    }

    private void disableViewDuringAnimation(ObjectAnimator animator, final View view){
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                view.setEnabled(false);
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                view.setEnabled(true);
            }
            @Override
            public void onAnimationCancel(Animator animator) { }
            @Override
            public void onAnimationRepeat(Animator animator) { }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        scale(btnIngresar,1f);
        /*etUsuario.setText("");
        etPassword.setText("");*/
    }
}
