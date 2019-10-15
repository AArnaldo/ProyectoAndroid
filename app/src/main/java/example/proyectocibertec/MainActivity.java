package example.proyectocibertec;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton ibtnLinkedIn, ibtnYouTube, ibtnInstagram;
    private String Link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializar
        ibtnLinkedIn = findViewById(R.id.ibtn_LinkedIn);
        ibtnYouTube = findViewById(R.id.ibtn_YouTube);
        ibtnInstagram = findViewById(R.id.ibtn_Instagram);
    }

    private void goLinkedIn(View view){
        String URLLinkedInd = "www.https://www.linkedin.com/school/cibertec/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(URLLinkedInd));
        startActivity(i);
    }



}
