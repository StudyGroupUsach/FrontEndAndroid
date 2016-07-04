package com.studygroup.studygroup.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;
import com.studygroup.studygroup.Poco.Lugar;
import com.studygroup.studygroup.Poco.PreferenciaDeEstudio;
import com.studygroup.studygroup.Poco.Ramo;
import com.studygroup.studygroup.Poco.Usuario;
import com.studygroup.studygroup.R;
import com.studygroup.studygroup.VolleyHelper.GsonRequest;
import com.studygroup.studygroup.VolleyHelper.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAgregarRamos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAgregarRamos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAgregarRamos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Usuario usuario;
    public void setUsuario(Usuario usuario){this.usuario=usuario;}

    ArrayList<PreferenciaDeEstudio> mPreferenciasDeEstudio = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    ArrayList<Ramo> mLugarList = new ArrayList<>();

    ArrayAdapter<Ramo> lugarAdapter;

    ArrayList<Ramo>mRamoAEscribir= new ArrayList<>();

    Ramo ramo;

    private int error;
    public void setError(int error){this.error=error;}

    private View view;

    ListView listView;

    Button button;

    int terminar=0;

    FragmentRamos fragment;

    PostUsuarioRest postUsuarioRest;
    PutUsuarioRest putUsuarioRest;

    public FragmentAgregarRamos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAgregarRamos.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAgregarRamos newInstance(String param1, String param2) {
        FragmentAgregarRamos fragment = new FragmentAgregarRamos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_fragment_agregar_ramos, container, false);

        button=(Button) view.findViewById(R.id.button_selecionar_ramos);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new FragmentRamos();
                fragment.setUsuario(usuario);
                postUsuarioRest=new PostUsuarioRest();
                postUsuarioRest.execute();
                if(terminar==1){
                    }
            }
        });

        listView=(ListView)view.findViewById(R.id.list_view_ramo_a_agregar);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentRamos fragment = new FragmentRamos();
                fragment.setUsuario(usuario);
                ramo=mLugarList.get(position);
                mRamoAEscribir.add(mLugarList.get(position));
            }
        });

        Type type = new TypeToken<ArrayList<Ramo>>() {}.getType();
        GsonRequest gsonRequestLugares = new GsonRequest(Request.Method.GET, getResources().getString(R.string.url_ramos_a_elegir)+""+usuario.usuarioId+"", type, null, new Response.Listener<ArrayList<Ramo>>() {
            @Override
            public void onResponse(ArrayList<Ramo> lugar) {
                mLugarList =lugar;
                completarAdapter();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //if(volleyError != null) Log.e("MainActivity", volleyError.getMessage());
            }
        });

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(gsonRequestLugares);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void completarAdapter(){
        lugarAdapter= new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,mLugarList);
        listView.setAdapter(lugarAdapter);
    }

    public class PostUsuarioRest extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            String verificar ="";//variable que entrega un veficador dependiendo de la respuesta del REST
            try {
                //abrir la coneccion a la URL
                HttpURLConnection urlConn;
                URL url = new URL(getResources().getString(R.string.url_preferencia_ramos)+""+usuario.usuarioId);
                urlConn = (HttpURLConnection) url.openConnection();
                urlConn.setDoInput(true);
                urlConn.setDoOutput(true);
                urlConn.setUseCaches(false);
                urlConn.setRequestProperty("Content-Type", "application/json");
                urlConn.connect();
                //Creo json para añadir los datos
                JSONArray obj = new JSONArray();
                try {
                    for(int i=0;i<1;i++){
                        JSONObject list1 = new JSONObject();
                        list1.put("ramoId",ramo.ramoId);
                        list1.put("nombreRamo",ramo.nombreRamo);
                        JSONObject carreraJson= new JSONObject();
                        carreraJson.put("carreraId",ramo.carreraId);
                        carreraJson.put("nombreCarrera",ramo.nombreCarrera);
                        list1.put("carrera",carreraJson);

                        obj.put(list1);
                    }
                } catch (JSONException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                // envio para el post
                OutputStream outputStream = urlConn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(obj.toString());
                writer.flush();
                writer.close();// se termina la escritura
                //variable para respuesta del servidor
                int respuesta = urlConn.getResponseCode();
                StringBuilder result = new StringBuilder();
                if (respuesta == HttpURLConnection.HTTP_OK) {
                }else{verificar ="error";}
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return verificar;
        }
        @Override
        protected void onCancelled(String s) {

        }
        @Override
        protected void onPostExecute(String s) {
            putUsuarioRest=new PutUsuarioRest();
            putUsuarioRest.execute();
            //super.onPostExecute(s);
        }
    }
    public class PutUsuarioRest extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            String verificar ="";//variable que entrega un veficador dependiendo de la respuesta del REST
            try {
                //abrir la coneccion a la URL
                HttpURLConnection urlConn;
                URL url = new URL(getResources().getString(R.string.url_preferencia_ramos)+""+usuario.usuarioId);
                urlConn = (HttpURLConnection) url.openConnection();
                urlConn.setDoInput(true);
                urlConn.setDoOutput(true);
                urlConn.setUseCaches(false);
                urlConn.setRequestProperty("Content-Type", "application/json");
                urlConn.setRequestMethod("PUT");
                urlConn.connect();
                //Creo json para añadir los datos
                JSONArray obj = new JSONArray();
                try {
                    for(int i=0;i<mRamoAEscribir.size();i++){
                        // 1st object
                        JSONObject list1 = new JSONObject();
                        list1.put("ramoId",mRamoAEscribir.get(i).ramoId);
                        list1.put("nombreRamo",mRamoAEscribir.get(i).nombreRamo);
                        JSONObject carreraJson= new JSONObject();
                        carreraJson.put("carreraId",mRamoAEscribir.get(i).carreraId);
                        carreraJson.put("nombreCarrera",mRamoAEscribir.get(i).nombreCarrera);
                        list1.put("carrera",carreraJson);

                        obj.put(list1);
                    }
                } catch (JSONException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }catch (RuntimeException e1){
                    e1.printStackTrace();
                }
                // envio para el post
                OutputStream outputStream = urlConn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(obj.toString());
                writer.flush();
                writer.close();// se termina la escritura
                //variable para respuesta del servidor
                int respuesta = urlConn.getResponseCode();
                StringBuilder result = new StringBuilder();
                if (respuesta == HttpURLConnection.HTTP_OK) {
                }else{verificar ="error";}
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return verificar;
        }
        @Override
        protected void onCancelled(String s) {

        }
        @Override
        protected void onPostExecute(String s) {
            terminar=1;
            Toast.makeText(getActivity().getApplicationContext(),mRamoAEscribir.get(1).toString(),Toast.LENGTH_LONG).show();
            FragmentTransaction fragmentTransaction;
            fragmentTransaction=getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_main,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            //super.onPostExecute(s);
        }
    }
}
