package com.example.kervel.controleur;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.kervel.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    EditText identifiant, mdp;
    Button btnConnexion;
    TextView txtv;
    private static final String TAG = "Login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btnConnexion = findViewById(R.id.btn_connexion);
        identifiant = findViewById(R.id.identifiant);
        mdp = findViewById(R.id.password);

        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }

            private void loginUser() {
                String email = identifiant.getText().toString().trim();
                String password = mdp.getText().toString().trim();

                if (email.isEmpty()) {
                    identifiant.setError("saisissez votre adresse mail");
                    identifiant.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    mdp.setError("saisissez votre mdp");
                    mdp.requestFocus();
                    return;
                }

                Call<ResponseBody> call = new RetrofitClient()
                        .getService()
                        .login(email,password);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        JsonParser parser = new JsonParser();
                        Gson gson = new GsonBuilder()
                                .setLenient()
                                .create();

                        String result = gson.toJson(response.body());
                        System.out.println(result);


                        try {
                            JsonObject jsonObject = new JsonParser().parse(response.body().string()).getAsJsonObject();
                            boolean error = jsonObject.get("error").getAsBoolean();
                            String message = jsonObject.get("message").getAsString();

                            if (error == false){

                                int type = jsonObject.get("user").getAsJsonObject().get("user_type").getAsInt();
                                if (type != 3){
                                    Toast.makeText(Login.this, "seul les gestionnaires qui ont le droit d'acceder", Toast.LENGTH_SHORT).show();
                                }else {
                                    String name = jsonObject.get("user").getAsJsonObject().get("username").getAsString();
                                    Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                                    /*Intent intent = new Intent(Login.this, EventForm.class);
                                    startActivity(intent);*/
                                    //String text = identifiant.getText().toString();
                                    Intent intent = new Intent(Login.this, EventForm.class);
                                    intent.putExtra("name",name );
                                    startActivity(intent);


                                }
                            }else {
                                Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //System.out.println("messssssage est " +loginResponse.toString());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println("onFailure "+t.getLocalizedMessage() + t.getStackTrace() + t.getCause());
                        Log.e( "call Error", t.getMessage());

                    }

                });


            }
        });
    }
}