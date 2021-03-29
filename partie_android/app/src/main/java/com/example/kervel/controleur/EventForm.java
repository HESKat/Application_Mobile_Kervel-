package com.example.kervel.controleur;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kervel.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener ,DatePickerDialog.OnDateSetListener, View.OnClickListener{
    Spinner spinner;
    TextView nameUsr, viewDate, textV;
    ImageView mapImg;
    EditText numParcel,paramNumUgb, paramIdBete;
    Button btnInsert;
    NumberPicker numParcelle;
    LinearLayout lparamNumUgb,lparamIdBete;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventform);
        //recuperer l'intent
        Intent intentRecu = getIntent();
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        btnInsert = findViewById(R.id.btnInsert);
        numParcelle = findViewById(R.id.numParcelle);
        paramNumUgb = findViewById(R.id.paramNumUgb);
        paramIdBete = findViewById(R.id.paramIdBete);
        // textV = findViewById(R.id.textView);
        lparamNumUgb = findViewById(R.id.li1);
        lparamIdBete = findViewById(R.id.li2);

        numParcelle.setMinValue(0);
        numParcelle.setMaxValue(200);
        numParcelle.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d",value);

            }
        });

        String name = intentRecu.getStringExtra("name");
        nameUsr = (TextView)findViewById(R.id.viewNameU);
        nameUsr.setText("Bienvenu "+ name);


        mapImg = findViewById(R.id.map);
        mapImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventForm.this, LocalisationMap.class);
                String name = intentRecu.getStringExtra("name");
                intent.putExtra("name",name );
                startActivity(intent);
            }
        });

        viewDate = findViewById(R.id.date);
        viewDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                dateJour();
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insert();

            }
        });
    }

    private void insert()  {
        int numPar = numParcelle.getValue();
        String getDate = (String) viewDate.getText();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        java.util.Date datee = null;
        try {
            datee = formatter.parse(getDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(datee);

        String event = spinner.getSelectedItem().toString().trim();
        String parame ="";
        System.out.println(event);
        switch (event) {
            case "fauche":
            case "epareuse":
            case "elagage":
                parame ="";
                break;
            case "entree":
                parame = paramNumUgb.getText().toString().trim();
                break;
            case "saillie":
                parame = paramIdBete.getText().toString().trim();
                break;
        }

        Call<ResponseBody> call = new RetrofitClient()
                .getService()
                .createEvent(date, event,parame, numPar);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                numParcelle.setDisplayedValues(null);
                spinner.setSelection(0);
                viewDate.setText(R.string.default_name);
                Toast.makeText(EventForm.this, "l'événement a été bien ajouté", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Toast.makeText(EventForm.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("onFailure "+t.getLocalizedMessage() +
                        t.getStackTrace() + t.getCause() + " messageee est "+ t.getMessage()) ;
                Log.e(
                        "call Error", t.getMessage());
            }
        });


    }

    public void dateJour(){

        DatePickerDialog dialog = new DatePickerDialog(this,this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month +1;
        String dateDay =String.format("%02d",dayOfMonth) +"-"+String.format("%02d",month) +"-"+year;
        viewDate.setText(dateDay);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
            case 2:
            case 3:
                lparamNumUgb.setVisibility(View.INVISIBLE);
                lparamIdBete.setVisibility(View.INVISIBLE);
                break;
            case 1:
                lparamNumUgb.setVisibility(View.VISIBLE);
                lparamIdBete.setVisibility(View.INVISIBLE);
                break;
            case 4:
                lparamNumUgb.setVisibility(View.INVISIBLE);
                lparamIdBete.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

    }
}