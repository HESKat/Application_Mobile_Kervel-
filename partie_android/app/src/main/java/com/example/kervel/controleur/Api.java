package com.example.kervel.controleur;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {
//definir les operations http
/*Chaque méthode à l'intérieur d'une interface représente un appel d'API possible. Il doit avoir une
    annotation HTTP (GET, POST, etc.) pour spécifier le type de requête et l'URL relative. La valeur
    de retour encapsule la réponse dans un objet Call avec le type du résultat attendu.*/

    @Multipart
    @POST("/test/api/createEvent.php")
    Call<ResponseBody> createEvent (
            @Part("date") String date,
            @Part("type") String type,
            @Part("parameter") String parameter,
            @Part("id_parcelle") int id_parcelle

            );


    @Multipart
    @POST("/test/api/login.php")
    Call<ResponseBody> login (
            @Part("email") String email,
            @Part("password") String password

    );
    @Multipart
    @POST("/test/api/loadParcelle.php")
    Call<ResponseBody> loadParcelle(@Part("username") String username);
}
