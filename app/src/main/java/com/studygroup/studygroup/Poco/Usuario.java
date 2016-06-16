package com.studygroup.studygroup.Poco;

import com.android.volley.toolbox.StringRequest;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.Streams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by mmora on 6/10/2016.
 */
public class Usuario {

    public Usuario(){}

    public Usuario(String nombre, String apellidos, String mail, String numeroMovil, String pass, int carreraId) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.mail = mail;
        this.numeroMovil = numeroMovil;
        this.pass = pass;
        this.carreraId = carreraId;
    }

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

    public JSONObject toJsonObject(){
        JSONObject carreraJson = new JSONObject();
        try {
            carreraJson.put("carreraId", carreraId);
        }
        catch (JSONException e) { }
        JSONObject usuarioJson = new JSONObject();
        try {
            usuarioJson.put("nombre", nombre);
            usuarioJson.put("apellidos", apellidos);
            usuarioJson.put("descripcion","");
            usuarioJson.put("mail", mail);
            usuarioJson.put("numeroMovil", numeroMovil);
            usuarioJson.put("pass", pass);
            usuarioJson.put("carrera",carreraJson);
        }
        catch (JSONException e) { }

        return usuarioJson;
    }
}
