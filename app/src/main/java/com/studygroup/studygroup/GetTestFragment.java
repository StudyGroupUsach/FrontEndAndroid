package com.studygroup.studygroup;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.studygroup.studygroup.Poco.Usuario;
import com.studygroup.studygroup.VolleyHelper.GsonRequest;
import com.studygroup.studygroup.VolleyHelper.VolleySingleton;


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
}
