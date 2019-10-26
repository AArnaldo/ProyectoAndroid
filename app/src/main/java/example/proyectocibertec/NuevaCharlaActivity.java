package example.proyectocibertec;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import example.proyectocibertec.clases.TipoCharla;

public class NuevaCharlaActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spTipo;
    private Button btnFechaInicio,btnFechaFin,btnHoraInicio,btnHoraFin,btnSiguiente;
    private EditText txtFechaInicio,txtFechaFin,txtHoraInicio,txtHoraFin;

    private int dia,mes,anio,hora,minuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_charla);

        setTitle("Registro de Charla");
        
        inicializarControles();

        btnFechaInicio.setOnClickListener(this);
        btnFechaFin.setOnClickListener(this);
        btnHoraInicio.setOnClickListener(this);
        btnHoraFin.setOnClickListener(this);
        btnSiguiente.setOnClickListener(this);
    }

    private void inicializarControles() {
        spTipo = findViewById(R.id.tiet_newCharla_Tipo);
        btnFechaInicio = findViewById(R.id.btnFechaInicio);
        btnFechaFin = findViewById(R.id.btnFechaFin);
        btnHoraInicio = findViewById(R.id.btnHoraInicio);
        btnHoraFin = findViewById(R.id.btnHoraFin);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        txtFechaInicio = findViewById(R.id.tiet_newCharla_FechaInicio);
        txtFechaFin = findViewById(R.id.tiet_newCharla_FechaFin);
        txtHoraInicio = findViewById(R.id.tiet_newCharla_HoraInicio);
        txtHoraFin = findViewById(R.id.tiet_newCharla_HoraFin);

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
            case R.id.btnFechaFin:
                Calendar c3 = Calendar.getInstance();
                dia = c3.get(Calendar.DAY_OF_MONTH);
                mes = c3.get(Calendar.MONTH);
                anio = c3.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
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
                        txtFechaFin.setText(formattedDayOfMonth + "/" + formattedMonth + "/" + year);
                    }
                },anio,mes,dia);
                datePickerDialog2.show();
                break;
            case R.id.btnHoraFin:
                Calendar c4 = Calendar.getInstance();
                hora = c4.get(Calendar.HOUR_OF_DAY);
                minuto = c4.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog2 = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
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
                        txtHoraFin.setText(formattedHour+":"+formattedMinute);
                    }
                },hora,minuto,false);
                timePickerDialog2.show();
                break;
            case R.id.btnSiguiente:
                Intent intent = new Intent(this, CharlaExpositoresActivity.class);
                startActivity(intent);
                break;
        }
    }
}
