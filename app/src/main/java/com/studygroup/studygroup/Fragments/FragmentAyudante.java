package com.studygroup.studygroup.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.reflect.TypeToken;
import com.studygroup.studygroup.Poco.AuthenticationToken;
import com.studygroup.studygroup.Poco.PerfilAyudante;
import com.studygroup.studygroup.Poco.Usuario;
import com.studygroup.studygroup.R;
import com.studygroup.studygroup.VolleyHelper.GsonRequest;
import com.studygroup.studygroup.VolleyHelper.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAyudante.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAyudante#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAyudante extends Fragment implements FragmentListarPreferenciasAyudantes.OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Usuario usuario;
    public void setUsuario(Usuario usuario){this.usuario=usuario;}

    private OnFragmentInteractionListener mListener;

    ArrayList<PerfilAyudante> mPerfilAyudante = new ArrayList<>();

    private Button buttonSerAyudante;
    private Button buttonCrearGrupoHorario;
    private Button buttonPublicarHorarios;

    EditText editText;

    PerfilAyudante perfilAyudante;

    private View view;

    int esAyudante=0;

    public FragmentAyudante() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAyudante.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAyudante newInstance(String param1, String param2) {
        FragmentAyudante fragment = new FragmentAyudante();
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
        view=inflater.inflate(R.layout.fragment_fragment_ayudante, container, false);
        buttonCrearGrupoHorario=(Button)view.findViewById(R.id.buttonCrearGrupoHorario);
        buttonSerAyudante=(Button)view.findViewById(R.id.buttonSerAyudante);
        buttonPublicarHorarios=(Button)view.findViewById(R.id.buttonPublicarHorarios);
        editText=(EditText)view.findViewById(R.id.editTextHorariosLibres);
        buttonCrearGrupoHorario.setVisibility(View.INVISIBLE);
        buttonSerAyudante.setVisibility(View.INVISIBLE);
        buttonPublicarHorarios.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.INVISIBLE);
        CompararAyudante();
        buttonSerAyudante.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                JSONObject serAyudante = new JSONObject();
                try {
                    serAyudante.put("estado", "Pagado");
                }
                catch (JSONException e) { }

                GsonRequest gsonRequest = new GsonRequest(Request.Method.POST,getResources().getString(R.string.url_ser_ayudante)+usuario.usuarioId, null, null, serAyudante,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity().getApplicationContext(),"listo",Toast.LENGTH_LONG).show();
                            }
                        });

                VolleySingleton.getInstance(getActivity()).addToRequestQueue(gsonRequest);
                Toast.makeText(getActivity().getApplicationContext(),"boton",Toast.LENGTH_LONG).show();
                CompararAyudante();
            }
        });


        buttonCrearGrupoHorario.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentListarPreferenciasAyudantes fragment = new FragmentListarPreferenciasAyudantes();
                fragment.setPerfilAyudante(perfilAyudante);
                fragment.setUsuario(usuario);
                FragmentTransaction fragmentTransaction;
                fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_main,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        buttonPublicarHorarios.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PostHorario();

            }
        });


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

    @Override
    public void onFragmentInteraction(Uri uri) {

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

    public void VerBotones(){

        buttonCrearGrupoHorario.setVisibility(View.VISIBLE);
        buttonPublicarHorarios.setVisibility(View.VISIBLE);
        editText.setVisibility(View.VISIBLE);
        buttonSerAyudante.setVisibility(View.INVISIBLE);
    }
    public void NoVerBotones(){
        buttonSerAyudante.setVisibility(View.VISIBLE);
    }

    public void CompararAyudante(){
        Type type = new TypeToken<ArrayList<PerfilAyudante>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, getResources().getString(R.string.url_ser_ayudante),type, null,
                new Response.Listener<ArrayList<PerfilAyudante>>() {
                    @Override
                    public void onResponse(ArrayList<PerfilAyudante> response) {
                        mPerfilAyudante=response;
                        ObtenerIdAyudante();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //if(volleyError != null) Log.e("MapsActivity", volleyError.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),"timeout",Toast.LENGTH_LONG).show();
            }
        });
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(gsonRequest);
    }

    public void ObtenerIdAyudante(){
        for(int i=0;i<mPerfilAyudante.size();i++){
            if(mPerfilAyudante.get(i).usuarioId==usuario.usuarioId){
                perfilAyudante=mPerfilAyudante.get(i);
                esAyudante=1;
                VerBotones();
                break;
            }
            else{
                esAyudante=0;
                NoVerBotones();}
        }
    }
    public void PostHorario(){
        JSONObject serAyudante = new JSONObject();
        try {
            serAyudante.put("horario", editText.getText().toString());
        }
        catch (JSONException e) { }

        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST,getResources().getString(R.string.url_ayudante_hora_libre)+perfilAyudante.ayudanteId, null, null, serAyudante,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        PutHorario();
                    }
                });

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(gsonRequest);
    }
    public void PutHorario(){
        JSONObject serAyudante = new JSONObject();
        try {
            serAyudante.put("horario", editText.getText().toString());
        }
        catch (JSONException e) { }

        GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT,getResources().getString(R.string.url_ayudante_hora_libre)+perfilAyudante.ayudanteId, null, null, serAyudante,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(),"listo",Toast.LENGTH_LONG).show();
                    }
                });

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(gsonRequest);
    }
}
