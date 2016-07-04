package com.studygroup.studygroup.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.studygroup.studygroup.MostarDetalleLugar;
import com.studygroup.studygroup.Poco.GruposTemporales;
import com.studygroup.studygroup.Poco.Lugar;
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
 * {@link FragmentCambiarLugar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentCambiarLugar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCambiarLugar extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Usuario usuario;
    public void setUsuario(Usuario usuario){this.usuario=usuario;}

    ArrayList<Lugar> mLugarList = new ArrayList<>();

    ArrayList<GruposTemporales>gruposTemporalesArrayList=new ArrayList<>();

    ArrayAdapter<Lugar> lugarAdapter;

    Lugar lugar;

    ListView listViewLugar;

    private View view;

    public FragmentCambiarLugar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCambiarLugar.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCambiarLugar newInstance(String param1, String param2) {
        FragmentCambiarLugar fragment = new FragmentCambiarLugar();
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
        view=inflater.inflate(R.layout.fragment_fragment_cambiar_lugar, container, false);
        listViewLugar=(ListView)view.findViewById(R.id.list_view_cambiar_lugar);

        listViewLugar.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lugar=mLugarList.get(position);
            }

        });

        Type type = new TypeToken<ArrayList<Lugar>>() {}.getType();
        GsonRequest gsonRequestLugares = new GsonRequest(Request.Method.GET, getResources().getString(R.string.url_lugares), type, null, new Response.Listener<ArrayList<Lugar>>() {

            @Override
            public void onResponse(ArrayList<Lugar> lugar) {
                //manage response code
                //getTextView.setText(lugar.nombre);
                mLugarList =lugar;
                //textViewDePrueba.setText(mLugarList.get(0).toString());
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
        listViewLugar.setAdapter(lugarAdapter);
    }

    public void gruposTemporales(){
        Type type = new TypeToken<ArrayList<GruposTemporales>>() {}.getType();
        GsonRequest gsonRequestLugares = new GsonRequest(Request.Method.GET, getResources().getString(R.string.url_lugares), type, null, new Response.Listener<ArrayList<GruposTemporales>>() {

            @Override
            public void onResponse(ArrayList<GruposTemporales> lugar) {
                //manage response code
                //getTextView.setText(lugar.nombre);
                gruposTemporalesArrayList =lugar;
                //textViewDePrueba.setText(mLugarList.get(0).toString());
                verUsuario();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //if(volleyError != null) Log.e("MainActivity", volleyError.getMessage());
            }
        });

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(gsonRequestLugares);

    }
    public void verUsuario(){
        for(int i=0;i<gruposTemporalesArrayList.size();i++){
            if(gruposTemporalesArrayList.get(i).usuarioId==usuario.usuarioId){
                JSONObject serAyudante = new JSONObject();
                try {
                    serAyudante.put("idLugar", gruposTemporalesArrayList.get(i).idLugar);
                }
                catch (JSONException e) { }

                GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT,getResources().getString(R.string.url_grupos_temporales)+gruposTemporalesArrayList.get(i).grupoTemporalId, null, null, serAyudante,
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
    }
}
