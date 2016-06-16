package com.studygroup.studygroup.Poco;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mmora on 6/10/2016.
 * {
 "nombreRamo":"INFO",
 "ramoId":"7",
 "carreraId":"10",
 "nombreCarrera":"Prueba"
 }
 */
public class Ramo {

    @SerializedName("nombreRamo")
    public String nombreRamo;

    @SerializedName("ramoId")
    public int ramoId;

    @SerializedName("carreraId")
    public int carreraId;

    @SerializedName("nombreCarrera")
    public String nombreCarrera;

    public String getName(){return this.nombreRamo;}

}
