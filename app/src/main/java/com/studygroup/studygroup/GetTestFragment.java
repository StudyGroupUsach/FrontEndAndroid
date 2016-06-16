package com.studygroup.studygroup;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.reflect.TypeToken;
import com.studygroup.studygroup.Adapters.CarreraAdapter;
import com.studygroup.studygroup.Adapters.LugarAdapter;
import com.studygroup.studygroup.Poco.Carrera;
import com.studygroup.studygroup.Poco.Lugar;
import com.studygroup.studygroup.Poco.Usuario;
import com.studygroup.studygroup.VolleyHelper.GsonRequest;
import com.studygroup.studygroup.VolleyHelper.VolleySingleton;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GetTestFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GetTestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GetTestFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView textViewDePrueba;
    ArrayList<Lugar> mLugarList = new ArrayList<>();

    ArrayAdapter<Lugar> lugarAdapter;

    ListView listViewLugar;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public GetTestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GetTestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GetTestFragment newInstance(String param1, String param2) {
        GetTestFragment fragment = new GetTestFragment();
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

    //instancia de este fragment
    private View myFragmentView;
    //instancia del textview de pruebas
    private TextView getTextView;
    //url de pruebas
    String MobyDickurlTEST = "http://httpbin.org/html";
    String JsonUrlTEST ="http://mongostudygroup-app4tbd.rhcloud.com/service/usuarios/8";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //se infla el layout para poder acceder a los componentes
        myFragmentView = inflater.inflate(R.layout.fragment_get_test, container, false);
        //se recibe la referencia al textview
        getTextView = (TextView)myFragmentView.findViewById(R.id.getTextView);

        listViewLugar = (ListView) myFragmentView.findViewById(R.id.list_view_lugar);
        listViewLugar.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MostarDetalleLugar fragment = new MostarDetalleLugar();
                fragment.setLugar(mLugarList.get(position));
                FragmentTransaction fragmentTransaction;
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_main,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        textViewDePrueba =(TextView)myFragmentView.findViewById(R.id.textDePrueba);

        GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, JsonUrlTEST, Usuario.class, null, new Response.Listener<Usuario>() {

            @Override
            public void onResponse(Usuario user) {
                //manage response code
                getTextView.setText(user.nombre);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //if(volleyError != null) Log.e("MainActivity", volleyError.getMessage());
            }
        });

        gsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Add the request to the queue
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(gsonRequest);

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
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();

        return myFragmentView;
    }
    public void pasarFragmento(){

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    public void completarAdapter(){
        lugarAdapter= new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,mLugarList);
        listViewLugar.setAdapter(lugarAdapter);
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
