package com.studygroup.studygroup;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.studygroup.studygroup.Fragments.FragmentAgregarRamos;
import com.studygroup.studygroup.Fragments.FragmentAyudante;
import com.studygroup.studygroup.Fragments.FragmentAyudantias;
import com.studygroup.studygroup.Fragments.FragmentBuscadoGrupo;
import com.studygroup.studygroup.Fragments.FragmentBuscarGrupo;
import com.studygroup.studygroup.Fragments.FragmentCambiarLugar;
import com.studygroup.studygroup.Fragments.FragmentCreadoGrupo;
import com.studygroup.studygroup.Fragments.FragmentCrearGrupo;
import com.studygroup.studygroup.Fragments.FragmentCrearGrupoHorario;
import com.studygroup.studygroup.Fragments.FragmentEncontrarEstudiantesGrupoAnterior;
import com.studygroup.studygroup.Fragments.FragmentEncontrarOtrosEstudiantes;
import com.studygroup.studygroup.Fragments.FragmentListarAyudantes;
import com.studygroup.studygroup.Fragments.FragmentListarPreferenciasAyudantes;
import com.studygroup.studygroup.Fragments.FragmentMapa;
import com.studygroup.studygroup.Fragments.FragmentRamos;
import com.studygroup.studygroup.Fragments.FragmentValorarAyudantes;
import com.studygroup.studygroup.Fragments.FragmentVerOtrosEstudiantes;
import com.studygroup.studygroup.Poco.Usuario;
import com.studygroup.studygroup.VolleyHelper.GsonRequest;
import com.studygroup.studygroup.VolleyHelper.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentAgregarRamos.OnFragmentInteractionListener,
        FragmentAyudante.OnFragmentInteractionListener,
        FragmentAyudantias.OnFragmentInteractionListener,
        FragmentBuscadoGrupo.OnFragmentInteractionListener,
        FragmentBuscarGrupo.OnFragmentInteractionListener,
        FragmentCreadoGrupo.OnFragmentInteractionListener,
        FragmentCrearGrupo.OnFragmentInteractionListener,
        FragmentCrearGrupoHorario.OnFragmentInteractionListener,
        FragmentEncontrarEstudiantesGrupoAnterior.OnFragmentInteractionListener,
        FragmentEncontrarOtrosEstudiantes.OnFragmentInteractionListener,
        FragmentListarAyudantes.OnFragmentInteractionListener,
        FragmentListarPreferenciasAyudantes.OnFragmentInteractionListener,
        FragmentMapa.OnFragmentInteractionListener,
        FragmentRamos.OnFragmentInteractionListener,
        FragmentValorarAyudantes.OnFragmentInteractionListener,
        GetTestFragment.OnFragmentInteractionListener,
        FragmentCambiarLugar.OnFragmentInteractionListener,
        PostTestFragment.OnFragmentInteractionListener{


    private Usuario usuario;
    public void setUsuario(Usuario usuario){this.usuario=usuario;}

    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        int usuarioId=intent.getIntExtra("usuarioId",0);

        GsonRequest gsonRequest = new GsonRequest(Request.Method.GET,getResources().getString(R.string.url_usuarios)+""+usuarioId+"", Usuario.class, null, new Response.Listener<Usuario>() {

            @Override
            public void onResponse(Usuario user) {
                //manage response code
                usuario=user;
                Toast.makeText(getApplication(),"hola "+usuario.nombre,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //if(volleyError != null) Log.e("MainActivity", volleyError.getMessage());
                Toast.makeText(getApplication(),"timeout",Toast.LENGTH_LONG).show();
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(gsonRequest);

        /**FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });**/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //fragment por defecto que sera mostrado
        //displayView(R.id.GET_TESTS);
        mProgressView = findViewById(R.id.login_progress);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        boolean fragmentos= false;//indica si estamos en un fragmento o no
        // para pasar de un fragmento a otro

        if (id == R.id.buscar_grupo) {
            //fragment = new GetTestFragment();
            //fragmentos= true;
            FragmentBuscarGrupo fragment = new FragmentBuscarGrupo();// pasa a fragment de grupo
            fragment.setUsuario(usuario);
            fragmentos = true;
            PasarFragmento(fragment,fragmentos,item);
        }
        else if (id == R.id.crear_grupo) {
            FragmentCrearGrupo fragment=new FragmentCrearGrupo();
            fragment.setUsuario(usuario);
            fragmentos= true;
            PasarFragmento(fragment,fragmentos,item);
        }
        else if(id== R.id.ver_densidad){
            FragmentMapa fragment= new FragmentMapa();
            fragment.setUsuario(usuario);
            fragment.setOpcion(2);
            fragmentos=true;
            PasarFragmento(fragment,fragmentos,item);
        }
        else if(id== R.id.ver_densidad){
            FragmentCambiarLugar fragment= new FragmentCambiarLugar();
            fragment.setUsuario(usuario);
            fragmentos=true;
            PasarFragmento(fragment,fragmentos,item);
        }
        else if (id == R.id.ramos) {
            FragmentRamos fragment = new FragmentRamos();
            fragment.setUsuario(usuario);
            fragmentos= true;
            PasarFragmento(fragment,fragmentos,item);
        }
        else if (id == R.id.buscar_usuarios) {
            FragmentEncontrarOtrosEstudiantes fragment = new FragmentEncontrarOtrosEstudiantes();
            fragment.setUsuario(usuario);
            fragmentos= true;
            PasarFragmento(fragment,fragmentos,item);
        }
        else if (id == R.id.nav_enviar) {
            FragmentEncontrarEstudiantesGrupoAnterior fragment = new FragmentEncontrarEstudiantesGrupoAnterior();
            fragment.setUsuario(usuario);
            fragmentos = true;
            PasarFragmento(fragment,fragmentos,item);
        }
        else if(id==R.id.ver_ayudantes){
            FragmentListarAyudantes fragment= new FragmentListarAyudantes();
            fragment.setUsuario(usuario);
            fragmentos=true;
            PasarFragmento(fragment,fragmentos,item);
        }
        else if(id==R.id.ver_ayudantias){
            FragmentAyudantias fragment= new FragmentAyudantias();
            fragmentos=true;
            PasarFragmento(fragment,fragmentos,item);
        }
        else if (id== R.id.ayudantia){
            FragmentAyudante fragment= new FragmentAyudante();
            fragment.setUsuario(usuario);
            fragmentos=true;
            PasarFragmento(fragment,fragmentos,item);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void PasarFragmento(Fragment fragment, boolean fragmentos,MenuItem item){
        if(fragmentos){// al ver que se ejecuta un fragmento se encarga de pasar al otro
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main,fragment)
                    .commit();

            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
/**
    public void displayView(int viewId) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (viewId) {
            case R.id.GET_TESTS:
                fragment = new GetTestFragment();
                title  = "News";

                break;
            case R.id.POST_TESTS:
                fragment = new PostTestFragment();
                title = "Events";
                break;

        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }*/
}
