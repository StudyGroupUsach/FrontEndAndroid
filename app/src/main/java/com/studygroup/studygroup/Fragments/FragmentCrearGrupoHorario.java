package com.studygroup.studygroup.Fragments;

import android.content.Context;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.studygroup.studygroup.Poco.PerfilAyudante;
import com.studygroup.studygroup.Poco.Ramo;
import com.studygroup.studygroup.Poco.Usuario;
import com.studygroup.studygroup.R;
import com.studygroup.studygroup.VolleyHelper.GsonRequest;
import com.studygroup.studygroup.VolleyHelper.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentCrearGrupoHorario.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentCrearGrupoHorario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCrearGrupoHorario extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Ramo ramo;
    public void setRamo(Ramo ramo) {
        this.ramo = ramo;
    }

    private Usuario usuario;
    public void setUsuario(Usuario usuario){this.usuario=usuario;}

    private PerfilAyudante perfilAyudante;
    public void setPerfilAyudante(PerfilAyudante perfilAyudante) {
        this.perfilAyudante = perfilAyudante;
    }

    private View view;

    public FragmentCrearGrupoHorario() {
        // Required empty public constructor
    }

    EditText editTextDescripcion;
    EditText editTextPago;
    EditText editTextMetodo;
    DatePicker datePicker;
    TimePicker timePickerInicio;
    TimePicker timePickerTermino;
    Button buttonCrear;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCrearGrupoHorario.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCrearGrupoHorario newInstance(String param1, String param2) {
        FragmentCrearGrupoHorario fragment = new FragmentCrearGrupoHorario();
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
        view= inflater.inflate(R.layout.fragment_fragment_crear_grupo_horario, container, false);
        editTextDescripcion=(EditText)view.findViewById(R.id.editTextDescripcion);
        editTextPago=(EditText)view.findViewById(R.id.editTextPago);
        editTextMetodo=(EditText)view.findViewById(R.id.editTextMetodo);
        datePicker=(DatePicker)view.findViewById(R.id.datePicker);
        timePickerInicio=(TimePicker)view.findViewById(R.id.timePickerInicio);
        timePickerInicio.setIs24HourView(true);
        timePickerTermino=(TimePicker)view.findViewById(R.id.timePickerTermino);
        timePickerTermino.setIs24HourView(true);
        buttonCrear=(Button) view.findViewById(R.id.buttonCrear);
        buttonCrear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                CrearGrupoHorario();
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

    public void CrearGrupoHorario(){

        int hour= timePickerInicio.getCurrentHour();
        int minute= timePickerInicio.getCurrentMinute();
        int hour2=timePickerTermino.getCurrentHour();
        int minute2= timePickerTermino.getCurrentMinute();
        int day= datePicker.getDayOfMonth();
        int mes= datePicker.getMonth();
        int year= datePicker.getYear();
        String dayOfWeek= DateFormat.format("EEE",new Date(year,mes,day)).toString();
        String diaDeLaSemana="";
        if (dayOfWeek.equals("lun")){diaDeLaSemana="mon";}
        else if(dayOfWeek.equals("mar")){diaDeLaSemana="tue";}
        else if(dayOfWeek.equals("mié")){diaDeLaSemana="wed";}
        else if(dayOfWeek.equals("jue")){diaDeLaSemana="thu";}
        else if(dayOfWeek.equals("vie")){diaDeLaSemana="fri";}
        else if(dayOfWeek.equals("sab")){diaDeLaSemana="sat";}
        else if(dayOfWeek.equals("dom")){diaDeLaSemana="sun";}

        String month=DateFormat.format("MMM",new Date(year,mes,day)).toString();
        String monthMes="";
        if(month.equals("ene")){monthMes="jan";}
        else if(month.equals("feb")){monthMes="feb";}
        else if(month.equals("mar")){monthMes="mar";}
        else if(month.equals("abr")){monthMes="apr";}
        else if(month.equals("may")){monthMes="may";}
        else if(month.equals("jun")){monthMes="jun";}
        else if(month.equals("jul")){monthMes="jul";}
        else if(month.equals("ago")){monthMes="aug";}
        else if(month.equals("sep")){monthMes="sep";}
        else if(month.equals("oct")){monthMes="oct";}
        else if(month.equals("nov")){monthMes="nov";}
        else if(month.equals("dic")){monthMes="dec";}

        String diaString;
        if(day<10){diaString="0"+day;}
        else{diaString=""+day+"";}

        String mesAString;
        if(mes<10){mesAString="0"+month;}
        else{mesAString=""+mes+"";}

        String hourString;
        if(hour<10){hourString="0"+hour;}
        else{hourString=""+hour+"";}

        String hour2String;
        if(hour<10){hour2String="0"+hour2;}
        else{hour2String=""+hour2+"";}

        String minuteString;
        if(hour<10){minuteString="0"+minute;}
        else{minuteString=""+minute+"";}

        String minute2String;
        if(hour<10){minute2String="0"+minute2;}
        else{minute2String=""+minute2+"";}


        long startDate=0;
        long finishDate=0;
        try {
            String dateString=dayOfWeek+" "+month+" "+diaString+" "+hourString+":"+minuteString+":00 GMT-04 "+year+"";
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            String dateString2=dayOfWeek+" "+month+" "+diaString+" "+hour2String+":"+minute2String+":00 GMT-04 "+year+"";
            SimpleDateFormat simpleDateFormat2= new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            Date date = simpleDateFormat.parse(dateString);
            Date date2= simpleDateFormat2.parse(dateString2);
            startDate=date.getTime();
            finishDate=date2.getTime();
        }catch (ParseException e){
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        JSONObject crearGrupo = new JSONObject();
        try {
            crearGrupo.put("ramoId", ramo.ramoId);
            crearGrupo.put("idLugar",1);
            crearGrupo.put("descripcionHorario",editTextDescripcion.getText().toString());
            crearGrupo.put("tipoPago",editTextPago.getText().toString());
            crearGrupo.put("metodosPago",editTextMetodo.getText().toString());
            JSONObject fechaInicio= new JSONObject();
            fechaInicio.put("$date",startDate);
            JSONObject fechaTermino= new JSONObject();
            fechaTermino.put("$date",finishDate);
            crearGrupo.put("fechaInicio",fechaInicio);
            crearGrupo.put("fechaTermino",fechaTermino);
            //crearGrupo.put("fechaInicio",diaDeLaSemana+" "+monthMes+" "+diaString+" "+hour+":"+minute+":00 EDT "+year+"");
            //crearGrupo.put("fechaTermino",diaDeLaSemana+" "+monthMes+" "+diaString+" "+hour2+":"+minute2+":00 EDT "+year+"");
        }
        catch (JSONException e) { }

        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST,getResources().getString(R.string.url_crear_grupo_horario)+perfilAyudante.ayudanteId+"", null, null, crearGrupo,
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
