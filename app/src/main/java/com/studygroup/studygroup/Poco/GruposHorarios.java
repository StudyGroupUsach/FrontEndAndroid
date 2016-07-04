package com.studygroup.studygroup.Poco;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dllob on 02-07-2016.
 {
     "grupoHorarioId":"6",
     "descripcionHorario":"ver introduccion",
     "fechaInicio":"Wed Dec 31 19:00:00 EST 1969",
     "fechaTermino":"Wed Dec 31 19:00:00 EST 1969",
     "mediosPago":"Efectivo",
     "tipoPago":"Acordar con el Vendedor",
     "idLugar":"1",
     "nombreLugar":"Ingenieria Informatica",
     "usuarioId":"16",
     "perfilAyudanteId":"1",
     "nombre":"Lobos",
     "apellidos":"Daniel",
     "ramoId":"8",
     "nombreRamo":"INFO2"
 },
 */
public class GruposHorarios {

    @SerializedName("grupoHorarioId")
    public int grupoHorarioId;
    @SerializedName("descripcionHorario")
    public String descripcionHorario;
    @SerializedName("fechaInicio")
    public String fechaInicio;
    @SerializedName("fechaTermino")
    public String fechaTermino;
    @SerializedName("mediosPago")
    public String mediosPago;
    @SerializedName("tipoPago")
    public String TipoPago;
    @SerializedName("idLugar")
    public int idLugar;
    @SerializedName("nombreLugar")
    public String nombreLugar;
    @SerializedName("usuarioId")
    public int usuarioId;
    @SerializedName("perfiAyudanteId")
    public int perfilAyudanteId;
    @SerializedName("nombre")
    public String nombre;
    @SerializedName("apellidos")
    public String apellidos;
    @SerializedName("ramodId")
    public int ramodId;
    @SerializedName("nombreRamo")
    public String nombreRamo;


    public int getIdLugar() {
        return idLugar;
    }
    @Override
    public String toString(){
        return this.nombre+this.nombreRamo+"\n"+this.mediosPago+"\n"+this.fechaInicio+"\n"+this.fechaTermino;
    }
}
