package com.studygroup.studygroup.Poco;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dllob on 15-06-2016.
 * "idLugar":"3",
 "latitudLugar":"-33.4491",
 "longitudLugar":"-70.6835",
 "grupoTemporals":[{
 "grupoTemporalId":"11",
 "descripcionTemporal":"Nueva descripciÃ³n del gruppo",
 "duracionTemporal":"20:00:00",
 "inicioTemporal":"Mon Jun 13 02:30:40 EDT 2016",
 "idLugar":"3",
 "ramoId":"8",
 "nombreRamo":"INFO2",
 "usuarioId":"25",
 "nombre":"Juan"
 }],
 "grupoHorarios":[]
 */
public class Lugar{

    @SerializedName("idLugar")
    public int idLugar;

    @SerializedName("latitudLugar")
    public double latitudLugar;

    @SerializedName("longitudLugar")
    public double longitudLugar;

    @SerializedName("nombreLugar")
    public String nombreLugar;

    @SerializedName("grupoTemporals")
    public List<GruposTemporales> gruposTemporales;

    public Lugar(double latitudLugar, double longitudLugar) {
        this.latitudLugar = latitudLugar;
        this.longitudLugar = longitudLugar;
    }

    public int getIdLugar() {
        return idLugar;
    }



    @Override
    public String toString() {
        return " "+this.nombreLugar+" ";
    }

}
