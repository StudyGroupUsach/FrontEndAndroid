package com.studygroup.studygroup.Poco;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dllob on 03-07-2016.
 {
     "_id": {
         "$oid": "575e348245f9b10a5bbf2acb"
     }-
     "usuarioId": "13"
     "usuario": [1]
     0:  {
         "usuarioId": "25"
         "mail": "juan.perez@usach.cl"
         "nombre": "Juan"
         "apellidos": "Perez"
         "numeroMovil": "11111"
     }-
     -
 }
 */
public class EncuentrosPrevios {

    @SerializedName("id")
    public Id Id;

    public class Id{

        @SerializedName("$oid")
        public String $oid;

    }

    @SerializedName("usuarioId")
    public int usuarioId;

    @SerializedName("usuario")
    public List<Usuario> usuarios;


    @Override
    public String toString() {
        return " "+this.usuarioId+" "+usuarios.toString()+" " ;
    }

    public EncuentrosPrevios(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public EncuentrosPrevios(){}

}
