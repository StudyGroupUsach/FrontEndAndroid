package com.studygroup.studygroup.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.studygroup.studygroup.Poco.AuthenticationToken;
import com.studygroup.studygroup.Poco.HorarioLibre;
import com.studygroup.studygroup.Poco.PerfilAyudante;
import com.studygroup.studygroup.Poco.Usuario;
import com.studygroup.studygroup.R;
import com.studygroup.studygroup.VolleyHelper.GsonRequest;
import com.studygroup.studygroup.VolleyHelper.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentValorarAyudantes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentValorarAyudantes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentValorarAyudantes extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Usuario usuario;
    public  void setUsuario(Usuario usuario){this.usuario=usuario;}

    private PerfilAyudante perfilAyudante;
    public void setPerfilAyudante(PerfilAyudante perfilAyudante){this.perfilAyudante=perfilAyudante;}

    HorarioLibre horarioLibre;

    private View view;

    Button button;
    EditText editText;
    TextView textView;
    TextView textViewNombre;

    public FragmentValorarAyudantes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentValorarAyudantes.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentValorarAyudantes newInstance(String param1, String param2) {
        FragmentValorarAyudantes fragment = new FragmentValorarAyudantes();
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
        view=inflater.inflate(R.layout.fragment_fragment_valorar_ayudantes, container, false);
        button=(Button)view.findViewById(R.id.buttonPublicarValoracion);
        editText=(EditText)view.findViewById(R.id.editTextValoracionAyudante);
        textView=(TextView)view.findViewById(R.id.textViewVerHorariosDisponibles);
        textViewNombre=(TextView)view.findViewById(R.id.textViewNombreAyudante);
        String nombrar =perfilAyudante.nombre+" "+perfilAyudante.apellidos;
        textViewNombre.setText(nombrar);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int valoracionEntero= Integer.parseInt(editText.getText().toString());
                JSONObject valorarJson = new JSONObject();
                try {
                    valorarJson.put("valoracionAyudante", valoracionEntero);
                    valorarJson.put("usuarioId", usuario.usuarioId);
                }
                catch (JSONException e) { }

                GsonRequest gRequest = new GsonRequest(Request.Method.PUT,getResources().getString(R.string.url_valorar_ayudante)+""+perfilAyudante.ayudanteId+"", null, null, valorarJson,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });

                VolleySingleton.getInstance(getActivity()).addToRequestQueue(gRequest);
            }
        });

        GsonRequest gsonRequest = new GsonRequest(Request.Method.GET,getResources().getString(R.string.url_ayudante_hora_libre)+""+perfilAyudante.ayudanteId+"", HorarioLibre.class, null,
                new Response.Listener<HorarioLibre>() {

            @Override
            public void onResponse(HorarioLibre response) {
                //manage response code
                horarioLibre=response;
                textView.setText(horarioLibre.horario);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //if(volleyError != null) Log.e("MainActivity", volleyError.getMessage());
            }
        });
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(gsonRequest);

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
}
