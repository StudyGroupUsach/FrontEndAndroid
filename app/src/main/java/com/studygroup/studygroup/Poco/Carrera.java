package com.studygroup.studygroup.Poco;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mmora on 6/10/2016.
 */
public class Carrera {

    @SerializedName("carreraId")
    public int carreraId;

    @SerializedName("nombreCarrera")
    public String nombreCarrera;

    List<Ramo> ramo;

    public String getName(){return this.nombreCarrera;}

}
