package com.studygroup.studygroup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.studygroup.studygroup.Adapters.CarreraAdapter;
import com.studygroup.studygroup.Poco.AuthenticationToken;
import com.studygroup.studygroup.Poco.Carrera;
import com.studygroup.studygroup.Poco.Ramo;
import com.studygroup.studygroup.Poco.RegisterToken;
import com.studygroup.studygroup.Poco.Usuario;
import com.studygroup.studygroup.VolleyHelper.GsonRequest;
import com.studygroup.studygroup.VolleyHelper.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RegistrarseActivity extends AppCompatActivity {

    private EditText nombre;
    private EditText apellido;
    private EditText mail;
    private EditText telefono;
    private EditText pass;
    private TextView codigoCarrera;
    private String nombreCarrera;

    private Spinner carreraSpinner;
    ArrayList<Carrera> mCarreraList = new ArrayList<>();
    CarreraAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        nombre = (EditText) findViewById(R.id.text_nombre);
        apellido = (EditText) findViewById(R.id.text_apellido);
        mail = (EditText) findViewById(R.id.text_email);
        telefono = (EditText) findViewById(R.id.text_telefono);
        pass = (EditText) findViewById(R.id.text_pass);

        codigoCarrera = (TextView) findViewById(R.id.text_codigo_carrera);

        Button mRegisterInButton = (Button) findViewById(R.id.button_submit);
        mRegisterInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptRegister();
            }
        });

        carreraSpinner = (Spinner) findViewById(R.id.spinner_carrera);
        carreraSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                    long id)
            {
                String showId = mCarreraList.get(position).carreraId + "";

                codigoCarrera.setText(showId);
                nombreCarrera = mCarreraList.get(position).nombreCarrera;
                //Carrera obj = (ArrayAdapter<Carrera> parent.getAdapter()).getItem(position); // or just raidList.get(position) if raidList is a field variable
                //Toast.makeText(getBaseContext(), showId, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                codigoCarrera.setHint("Cargando Carreras");
            }
        });

        Type type = new TypeToken<ArrayList<Carrera>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, getResources().getString(R.string.url_carreras), type, null, new Response.Listener<ArrayList<Carrera>>() {
            @Override
            public void onResponse(ArrayList<Carrera> response) {
                //manage response code
                mCarreraList =response;
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
        adapter = new CarreraAdapter(this, R.layout.list_view_row, R.id.lbl_name, mCarreraList);
        carreraSpinner.setAdapter(adapter);
    }

    public void attemptRegister(){

        if(TextUtils.isEmpty(nombre.getText())) {
            nombre.setError(getResources().getString(R.string.error_field_required));
        }
        else if(TextUtils.isEmpty(apellido.getText())) {
            apellido.setError(getResources().getString(R.string.error_field_required));
        }
        else if(TextUtils.isEmpty(mail.getText())) {
            mail.setError(getResources().getString(R.string.error_field_required));
        }
        else if(TextUtils.isEmpty(pass.getText())) {
            pass.setError(getResources().getString(R.string.error_field_required));
        }
        else if(!mail.getText().toString().contains("@usach.cl")){
            mail.setError(getResources().getString(R.string.error_invalid_email));
        }
        else{
            userRegister();
        }
    }

    public void userRegister(){
        String rNombre = nombre.getText().toString().trim();
        String rApellido = apellido.getText().toString().trim();
        String rMail = mail.getText().toString().trim();
        String rTelefono = telefono.getText().toString().trim();
        String rPassword = pass.getText().toString().trim();
        String rCodigoCarreda = codigoCarrera.getText().toString().trim();

        Usuario user = new Usuario(rNombre,rApellido,rMail,rTelefono,rPassword,Integer.parseInt(rCodigoCarreda));

        GsonRequest gRequest = new GsonRequest(Request.Method.POST,getResources().getString(R.string.url_usuarios), RegisterToken.class, null, user.toJsonObject(),
                new Response.Listener<RegisterToken>() {
                    @Override
                    public void onResponse(RegisterToken response) {

                        if(response.isRegistered){

                            Toast.makeText(RegistrarseActivity.this,"Registro completo",Toast.LENGTH_LONG).show();
                            openLoginActivity();
                        }
                        else{
                            Toast.makeText(RegistrarseActivity.this,getResources().getString(R.string.error_incorrect_info),Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //hideProgressDialog();
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(gRequest);
    }

    public void openLoginActivity(){
        Intent myIntent = new Intent(RegistrarseActivity.this,LoginActivity.class);
        RegistrarseActivity.this.startActivity(myIntent);
    }
}
