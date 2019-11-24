package example.proyectocibertec;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import example.proyectocibertec.clases.UsuarioEdit;

public class UsuarioPicture extends AppCompatActivity {

    ImageView iv_usuario_foto_full;
    private UsuarioEdit usuarioEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_picture);
        iv_usuario_foto_full = findViewById(R.id.iv_usuario_foto_full);
        usuarioEdit = (UsuarioEdit) getIntent().getSerializableExtra("Usuario");
        setProfilePhoto();
    }

    public void setProfilePhoto() {
        String sPath= "";
        try {
            Glide.with(getApplicationContext())
                    .load(usuarioEdit.getFoto())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_person_black_24dp))
                    .into(iv_usuario_foto_full);

        }catch (Exception ex)
        {
        }
    }
}
