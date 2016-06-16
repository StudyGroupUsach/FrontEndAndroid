package com.studygroup.studygroup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.studygroup.studygroup.Poco.GruposTemporales;
import com.studygroup.studygroup.Poco.Lugar;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MostarDetalleLugar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MostarDetalleLugar extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Lugar lugar;

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<GruposTemporales> mLugarList = new ArrayList<>();

    ArrayAdapter<GruposTemporales> lugarAdapter;

    ListView listViewLugar;

    TextView textView;

    public MostarDetalleLugar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MostarDetalleLugar.
     */
    // TODO: Rename and change types and number of parameters
    public static MostarDetalleLugar newInstance(String param1, String param2) {
        MostarDetalleLugar fragment = new MostarDetalleLugar();
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
        View view= inflater.inflate(R.layout.fragment_mostar_detalle_lugar, container, false);
        textView = (TextView)view.findViewById(R.id.texto_fragmento_detalles_grupos);
        textView.setText(lugar.toString());
        return view;
    }

}
