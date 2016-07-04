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
import com.studygroup.studygroup.Poco.Lugar;
import com.studygroup.studygroup.Poco.PerfilAyudante;
import com.studygroup.studygroup.Poco.PreferenciaDeEstudio;
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
 * {@link FragmentListarPreferenciasAyudantes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentListarPreferenciasAyudantes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListarPreferenciasAyudantes extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private PerfilAyudante perfilAyudante;
    public void setPerfilAyudante(PerfilAyudante perfilAyudante) {
        this.perfilAyudante = perfilAyudante;
    }

    private Usuario usuario;
    public void setUsuario(Usuario usuario){this.usuario=usuario;}

    PreferenciaDeEstudio preferenciaDeEstudio;

    ListView listViewPreferencias;

    private View myFragmentView;

    ArrayList<PreferenciaDeEstudio> mPreferenciasDeEstudio = new ArrayList<>();

    ArrayAdapter<Ramo> ramoArrayAdapter;

    public FragmentListarPreferenciasAyudantes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentListarPreferenciasAyudantes.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListarPreferenciasAyudantes newInstance(String param1, String param2) {
        FragmentListarPreferenciasAyudantes fragment = new FragmentListarPreferenciasAyudantes();
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
        myFragmentView=inflater.inflate(R.layout.fragment_fragment_listar_preferencias_ayudantes, container, false);
        listViewPreferencias=(ListView) myFragmentView.findViewById(R.id.list_view_ver_ramos);
        listViewPreferencias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentCrearGrupoHorario fragment = new FragmentCrearGrupoHorario();
                fragment.setRamo(mPreferenciasDeEstudio.get(0).ramo.get(position));
                fragment.setPerfilAyudante(perfilAyudante);
                FragmentTransaction fragmentTransaction;
                fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_main,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        Type type = new TypeToken<ArrayList<PreferenciaDeEstudio>>() {}.getType();
        GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, getResources().getString(R.string.url_preferencia_ramos)+usuario.usuarioId+"",type, null,
                new Response.Listener<ArrayList<PreferenciaDeEstudio>>() {
                    @Override
                    public void onResponse(ArrayList<PreferenciaDeEstudio> response) {
                        mPreferenciasDeEstudio=response;
                        completarAdapter();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //if(volleyError != null) Log.e("MapsActivity", volleyError.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),"timeout",Toast.LENGTH_LONG).show();
            }
        });
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(gsonRequest);

        return myFragmentView;
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
        ramoArrayAdapter= new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,mPreferenciasDeEstudio.get(0).ramo);
        listViewPreferencias.setAdapter(ramoArrayAdapter);
    }

}
