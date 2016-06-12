package com.studygroup.studygroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.studygroup.studygroup.Poco.Ramo;
import com.studygroup.studygroup.Poco.Usuario;
import com.studygroup.studygroup.VolleyHelper.GsonRequest;
import com.studygroup.studygroup.VolleyHelper.VolleySingleton;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RegistrarseActivity extends AppCompatActivity {

    private EditText nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        nombre = (EditText) findViewById(R.id.text_nombre);

        Type type = new TypeToken<ArrayList<Ramo>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, getResources().getString(R.string.url_ramos), type, null, new Response.Listener<ArrayList<Ramo>>() {

            @Override
            public void onResponse(ArrayList<Ramo> ramos) {
                //manage response code
                //getTextView.setText(user.nombre);
                nombre.setText(ramos.get(0).nombreCarrera);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //if(volleyError != null) Log.e("MainActivity", volleyError.getMessage());
                Toast.makeText(RegistrarseActivity.this,"timeout",Toast.LENGTH_LONG).show();
            }
        });
        gsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the queue
        VolleySingleton.getInstance(this).addToRequestQueue(gsonRequest);
    }
}
