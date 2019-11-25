package example.proyectocibertec;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import example.proyectocibertec.clases.CharlaNew;
import example.proyectocibertec.clases.TipoCharla;
import example.proyectocibertec.clases.UsuarioEdit;

public class NuevaCharlaActivity extends AppCompatActivity implements View.OnClickListener {

    private CharlaNew charla;
    private Spinner spTipo;
    private ImageButton btnFechaInicio,btnHoraInicio,btnSiguiente, btnAtras;
    private TextInputEditText txtNombreCharla,txtDescripcionCharla,txtFechaInicio,txtHoraInicio, txtCapacidad, txtObservaciones;

    private int dia,mes,anio,hora,minuto;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_charla);

        setTitle("Registro de Charla");
        
        inicializarControles();

        btnFechaInicio.setOnClickListener(this);
        btnHoraInicio.setOnClickListener(this);
        btnSiguiente.setOnClickListener(this);
        btnAtras.setOnClickListener(this);


    }



    private void inicializarControles() {
        spTipo = findViewById(R.id.tiet_newCharla_Tipo);
        btnFechaInicio = findViewById(R.id.btnFechaInicio);
        btnHoraInicio = findViewById(R.id.btnHoraInicio);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnAtras = findViewById(R.id.btnAtras);
        txtNombreCharla = (TextInputEditText) findViewById(R.id.tiet_newCharla_Nombre);
        txtDescripcionCharla = (TextInputEditText) findViewById(R.id.tiet_newCharla_Descrip);
        txtFechaInicio = (TextInputEditText) findViewById(R.id.tiet_newCharla_FechaInicio);
        txtHoraInicio = (TextInputEditText) findViewById(R.id.tiet_newCharla_HoraInicio);
        txtCapacidad = (TextInputEditText) findViewById(R.id.tiet_newCharla_Capacidad);
        txtObservaciones = (TextInputEditText) findViewById(R.id.tiet_newCharla_Observaciones);

        MostrarListaTipo();
    }

    private void MostrarListaTipo() {
        ArrayList<TipoCharla> listaTipoCharla = new ArrayList<TipoCharla>();
        TipoCharla tipo1 = new TipoCharla(1,"EVENTO");
        TipoCharla tipo2 = new TipoCharla(2,"CONFERENCIA");
        listaTipoCharla.add(tipo1);
        listaTipoCharla.add(tipo2);

        ArrayAdapter<TipoCharla> dataAdapter = new ArrayAdapter<TipoCharla>(this,android.R.layout.simple_spinner_dropdown_item,listaTipoCharla);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipo.setAdapter(dataAdapter);

        spTipo.setSelection(0);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnFechaInicio:
                Calendar c1 = Calendar.getInstance();
                dia = c1.get(Calendar.DAY_OF_MONTH);
                mes = c1.get(Calendar.MONTH);
                anio = c1.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int month = monthOfYear + 1;
                        String formattedMonth = "" + month;
                        String formattedDayOfMonth = "" + dayOfMonth;
                        if(month < 10){

                            formattedMonth = "0" + month;
                        }
                        if(dayOfMonth < 10){

                            formattedDayOfMonth = "0" + dayOfMonth;
                        }
                        txtFechaInicio.setText(formattedDayOfMonth + "/" + formattedMonth + "/" + year);
                    }
                },anio,mes,dia);
                datePickerDialog.show();
                break;
            case R.id.btnHoraInicio:
                Calendar c2 = Calendar.getInstance();
                hora = c2.get(Calendar.HOUR_OF_DAY);
                minuto = c2.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String formattedHour = "" + hourOfDay;
                        String formattedMinute = "" + minute;
                        if(hourOfDay < 10){

                            formattedHour = "0" + hourOfDay;
                        }
                        if(minute < 10){

                            formattedMinute = "0" + minute;
                        }
                        txtHoraInicio.setText(formattedHour+":"+formattedMinute);
                    }
                },hora,minuto,false);
                timePickerDialog.show();
                break;
            case R.id.btnSiguiente:
                if (txtNombreCharla.length() == 0){
                    Toast.makeText(this, "Debe ingresar un nombre a la Charla", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (txtDescripcionCharla.length() == 0){
                    Toast.makeText(this, "Debe ingresar una descripción a la Charla", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (txtFechaInicio.length() == 0){
                    Toast.makeText(this, "Debe ingresar una fecha a la Charla", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (txtHoraInicio.length() == 0){
                    Toast.makeText(this, "Debe ingresar una hora a la Charla", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (txtCapacidad.length() == 0){
                    Toast.makeText(this, "Debe ingresar una capacidad a la Charla", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (txtObservaciones.length() == 0){
                    Toast.makeText(this, "Debe ingresar unas observaciones a la Charla", Toast.LENGTH_SHORT).show();
                    return;
                }

                charla = new CharlaNew();
                charla.setNombre(txtNombreCharla.getText().toString());
                charla.setDescripcion(txtDescripcionCharla.getText().toString());

                //Formateando la fecha y hora
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat stringFormat = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    Date fecha = dateFormat.parse(txtFechaInicio.getText().toString());
                    charla.setFechaHoraInicio(stringFormat.format(fecha) + " " + txtHoraInicio.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                charla.setCapacidad(Integer.parseInt(txtCapacidad.getText().toString()));
                charla.setObservaciones(txtObservaciones.getText().toString());

                Intent intent = new Intent(this, CharlaExpositoresActivity.class);
                intent.putExtra("objCharla", charla);
                startActivity(intent);
                break;
            case R.id.btnAtras:
                Intent intentAtras = new Intent(this, DrawerActivity.class);
                startActivity(intentAtras);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Registro Charla")
                .setMessage("Aún no termina con el registro de la Charla, Desea Salir?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sp_file_mensajes_key),MODE_PRIVATE);

                        //Obtener correo de Session
                        UsuarioEdit objUsuario = new UsuarioEdit();
                        objUsuario.setCorreo(sharedPreferences.getString("campo_correo","0"));

                        Intent intent = new Intent(NuevaCharlaActivity.this, DrawerActivity.class);
                        intent.putExtra("Usuario",objUsuario);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar",null);
        builder.show();
    }
}
