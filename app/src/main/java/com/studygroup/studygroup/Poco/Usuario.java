package com.studygroup.studygroup.Poco;

import com.android.volley.toolbox.StringRequest;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.Streams;

import java.util.List;

/**
 * Created by mmora on 6/10/2016.
 */
public class Usuario {

    @SerializedName("usuarioId")
    public int usuarioId;

    @SerializedName("nombre")
    public String nombre;

    @SerializedName("apellidos")
    public String apellidos;

    @SerializedName("descripcion")
    public String descripcion;

    @SerializedName("mail")
    public String mail;

    @SerializedName("numeroMovil")
    public String numeroMovil;

    @SerializedName("pass")
    public String pass;

    @SerializedName("carreraId")
    public int carreraId;

    @SerializedName("nombreCarrera")
    public String nombreCarrera;

    public List<Ramo> grupoTemporals;
}
