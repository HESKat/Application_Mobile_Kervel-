package com.example.kervel.controleur;

import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.kervel.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocalisationMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelle);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Intent intentRecu = getIntent();
        String name = intentRecu.getStringExtra("name");
        System.out.println("le nom recupere est: "+name);
        mMap = googleMap;
        Call<ResponseBody> call = new RetrofitClient()
                .getService()
                .loadParcelle(name);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //récupérer les données du fichier json
                JsonParser parser = new JsonParser();
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                String result = null;
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JsonObject jsonObject = null;
                jsonObject = parser.parse(result).getAsJsonObject();

                JsonArray tab = jsonObject.get("coordinates").getAsJsonArray();
                List<List<LatLng>> coordinates = new ArrayList<>();
                int i=0;

                for(JsonElement e : tab){
                    coordinates.add(new ArrayList<>());
                    for(JsonElement node : e.getAsJsonArray()){
                        coordinates.get(i).add(new LatLng(node.getAsJsonArray().get(1).getAsDouble(),node.getAsJsonArray().get(0).getAsDouble()));
                    }
                    i++;

                }
                //récupérer les coordonnées de chaque point et les afficher sur la maps
                Polygon[] polygons = new Polygon[coordinates.size()];
                i = 0;
                for(List<LatLng> poly:coordinates){
                    polygons[i]=googleMap.addPolygon(new PolygonOptions().clickable(true).addAll(poly));
                    polygons[i].setTag("alpha");
                    i++;
                }

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(polygons[0].getPoints().get(0), 15));

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println(t.getMessage());
                System.out.println("FAIL");

            }
        });
        mMap.setMyLocationEnabled(true);
    }
}