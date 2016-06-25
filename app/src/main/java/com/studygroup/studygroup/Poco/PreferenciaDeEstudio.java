package com.studygroup.studygroup.Poco;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dllob
 0:  {
        "_id": {
            "$oid": "575e443426574a7909558512"
        }-
        "usuarioId": "33"
        "ramo": [1]
        0:  {
            "nombreRamo": "INFO2"
            "ramoId": "7"
            "carreraId": "10"
            "nombreCarrera": "Prueba"
        }-
 -
 }
 */
public class PreferenciaDeEstudio {

    @SerializedName("id")
    public Id Id;

    public class Id{

        @SerializedName("$oid")
        public String $oid;

    }

    @SerializedName("usuarioId")
    public int usuarioId;

    @SerializedName("ramo")
    public List<Ramo> ramo;


    @Override
    public String toString() {
        return " "+this.usuarioId+" "+ramo.toString()+" " ;
    }

    public PreferenciaDeEstudio(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public PreferenciaDeEstudio(){}
}
