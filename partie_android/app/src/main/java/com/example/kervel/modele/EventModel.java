package com.example.kervel.modele;

import java.sql.Date;

public class EventModel {

    private String date;
    private String type;
    private String parameter;
    private int id_parcelle;

    public EventModel(String date, String type, String parameter, int id_parcelle) {
        this.date = date;
        this.type = type;
        this.parameter = parameter;
        this.id_parcelle = id_parcelle;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getParameter() {
        return parameter;
    }

    public int getId_parcelle() {
        return id_parcelle;
    }
}
