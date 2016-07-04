package com.studygroup.studygroup.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.reflect.TypeToken;
import com.studygroup.studygroup.Poco.Lugar;
import com.studygroup.studygroup.Poco.Ramo;
import com.studygroup.studygroup.Poco.Usuario;
import com.studygroup.studygroup.R;
import com.studygroup.studygroup.VolleyHelper.GsonRequest;
import com.studygroup.studygroup.VolleyHelper.VolleySingleton;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMapa.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMapa#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMapa extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int opcion;
    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    private Ramo ramo;
    public void setRamo(Ramo ramo){this.ramo=ramo;}

    private Usuario usuario;
    public void setUsuario(Usuario usuario){this.usuario=usuario;}

    private Lugar lugar;
    public void setLugar(Lugar lugar){this.lugar=lugar;}

    ArrayList<Lugar> mLugarList = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    private GoogleMap mMap;

    private View view;

    public FragmentMapa() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMapa.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMapa newInstance(String param1, String param2) {
        FragmentMapa fragment = new FragmentMapa();
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
        view=inflater.inflate(R.layout.fragment_fragment_mapa, container, false);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SupportMapFragment fragment = new SupportMapFragment();
        transaction.add(R.id.map, fragment);
        transaction.commit();
        fragment.getMapAsync(this);
        if(opcion==2){
            Type type = new TypeToken<ArrayList<Lugar>>() {}.getType();
            GsonRequest gsonRequestLugares = new GsonRequest(Request.Method.GET, getResources().getString(R.string.url_lugares), type, null, new Response.Listener<ArrayList<Lugar>>() {

                @Override
                public void onResponse(ArrayList<Lugar> lugar) {
                    mLugarList =lugar;
                    crearMarcas();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    //if(volleyError != null) Log.e("MainActivity", volleyError.getMessage());
                }
            });

            VolleySingleton.getInstance(getActivity()).addToRequestQueue(gsonRequestLugares);
        }


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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng usach = new LatLng(-33.4488, -70.6837);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(usach,14));
        if(opcion==1){
            LatLng lugares = new LatLng(lugar.latitudLugar,lugar.longitudLugar);
            mMap.addMarker(new MarkerOptions()
            .position(lugares));
        }

    }
    public void crearMarcas(){
        for(int i=0;i<mLugarList.size();i++){
            LatLng lugares= new LatLng(mLugarList.get(i).latitudLugar,mLugarList.get(i).longitudLugar);

            mMap.addMarker(new MarkerOptions()
            .position(lugares)
            .title(mLugarList.get(i).gruposTemporales.size()+" Personas "));
        }

    }

}
