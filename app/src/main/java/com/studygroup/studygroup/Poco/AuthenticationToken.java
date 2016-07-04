package com.studygroup.studygroup.Poco;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mmora on 6/11/2016.
 */
public class AuthenticationToken {

    @SerializedName("usuarioId")
    public int userId;

    @SerializedName("usuarioConectado")
    public boolean isConnected;

}