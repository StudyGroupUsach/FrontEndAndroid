package com.studygroup.studygroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.studygroup.studygroup.Adapters.CarreraAdapter;
import com.studygroup.studygroup.Poco.Carrera;
import com.studygroup.studygroup.Poco.Ramo;
import com.studygroup.studygroup.VolleyHelper.GsonRequest;
import com.studygroup.studygroup.VolleyHelper.VolleySingleton;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RegistrarseActivity extends AppCompatActivity {

    private EditText nombre;
    private AutoCompleteTextView ramosList;
    ArrayList<Carrera> mCarreraList = new ArrayList<>();
    CarreraAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        nombre = (EditText) findViewById(R.id.text_nombre);
        ramosList = (AutoCompleteTextView) findViewById(R.id.autoCompleteRamosRegister);
        ramosList.setThreshold(1);

        Type type = new TypeToken<ArrayList<Carrera>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, getResources().getString(R.string.url_carreras), type, null, new Response.Listener<ArrayList<Carrera>>() {

            @Override
            public void onResponse(ArrayList<Carrera> response) {
                //manage response code
                //getTextView.setText(user.nombre);
                //nombre.setText(ramos.get(0).nombreCarrera);
                mCarreraList =response;
                nombre.setText(mCarreraList.get(0).getName());
                completarAdapter();
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
    public void completarAdapter(){
        adapter = new CarreraAdapter(this, R.layout.activity_main, R.id.lbl_name, mCarreraList);
        ramosList.setAdapter(adapter);
    }
}
