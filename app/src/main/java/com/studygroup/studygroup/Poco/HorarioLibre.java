package com.studygroup.studygroup.Poco;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dllob on 04-07-2016.
 {
     "_id": {
         "$oid": "5776e4e426574a1694fa5738"
     }-
     "perfilAyudanteId": "1"
     "horario": "S1"
 }
 */
public class HorarioLibre {

    @SerializedName("id")
    public Id Id;

    public class Id{

        @SerializedName("$oid")
        public String $oid;

    }

    @SerializedName("perfilAyudanteId")
    public int perfilAyudanteId;

    @SerializedName("horario")
    public String horario;

}
