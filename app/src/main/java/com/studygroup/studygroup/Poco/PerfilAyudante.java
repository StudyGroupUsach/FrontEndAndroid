package com.studygroup.studygroup.Poco;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dllob on 02-07-2016.
 [{
 "perfilAyudanteId":"1",
 "nombre":"Lobos",
 "apellidos":"Daniel",
 "usuarioId":"16",
 "estado":"Pagado",
 "valoracionPromedio":"0",
 "grupoHorarios":[]
 }]
 */
public class PerfilAyudante {

    @SerializedName("perfilAyudanteId")
    public String ayudanteId;

    @SerializedName("nombre")
    public String nombre;

    @SerializedName("apellidos")
    public String apellidos;

    @SerializedName("usuarioId")
    public int usuarioId;

    @SerializedName("pagado")
    public String pagado;

    @SerializedName("valoracionPromedio")
    public int valoracionPromedio;



}
