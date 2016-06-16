package com.studygroup.studygroup.Poco;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dllob on 15-06-2016.

        "grupoTemporalId":"11",
        "descripcionTemporal":"Nueva descripciÃ³n del gruppo",
        "duracionTemporal":"20:00:00",
        "inicioTemporal":"Mon Jun 13 02:30:40 EDT 2016",
        "idLugar":"3",
        "ramoId":"8",
        "nombreRamo":"INFO2",
        "usuarioId":"25",
        "nombre":"Juan"
 */
public class GruposTemporales {
    //@SerializedName("carreraId")
    //public int carreraId;
    @SerializedName("grupoTemporalId")
    public int grupoTemporalId;

    @SerializedName("descripcionTemporal")
    public String descripcionTemporal;

    @SerializedName("duracionTemporal")
    public String duracionTemporal;

    @SerializedName("inicioTemporal")
    public String inicioTemporal;

    @SerializedName("idLugar")
    public int idLugar;

    @SerializedName("ramoId")
    public int ramoId;

    @SerializedName("nombreRamo")
    public String nombreRamo;

    @SerializedName("usuarioId")
    public int usuarioId;

    @SerializedName("nombre")
    public String nombre;

    public int getIdLugar() {
        return idLugar;
    }

    public int getGrupoTemporalId(){
        return grupoTemporalId;
    }

    @Override
    public String toString() {
        if(descripcionTemporal.equals("")||descripcionTemporal.equals(null)){return "nada";}
        return " "+this.grupoTemporalId+" " ;
        //+ this.descripcionTemporal+" "+ this.duracionTemporal+" "+
        //this.inicioTemporal+" "+ this.idLugar+" "+this.ramoId+" "+this.nombreRamo+" "+
        //        this.usuarioId+" "+ this.nombre+" "
    }

}
