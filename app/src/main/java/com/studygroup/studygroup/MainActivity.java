package com.studygroup.studygroup;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.studygroup.studygroup.Fragments.FragmentBuscarGrupo;
import com.studygroup.studygroup.Fragments.FragmentEncontrarEstudiantesGrupoAnterior;
import com.studygroup.studygroup.Fragments.FragmentEncontrarOtrosEstudiantes;
import com.studygroup.studygroup.Fragments.FragmentRamos;
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
        GetTestFragment.OnFragmentInteractionListener,
        PostTestFragment.OnFragmentInteractionListener,
        FragmentRamos.OnFragmentInteractionListener,
        FragmentBuscarGrupo.OnFragmentInteractionListener,
        FragmentEncontrarOtrosEstudiantes.OnFragmentInteractionListener,
        FragmentEncontrarEstudiantesGrupoAnterior.OnFragmentInteractionListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        Fragment fragment = null;// para pasar de un fragmento a otro

        if (id == R.id.buscar_grupo) {
            //fragment = new GetTestFragment();
            //fragmentos= true;
            fragment = new FragmentBuscarGrupo();// pasa a fragment de grupo
            fragmentos = true;
        }
        else if (id == R.id.crear_grupo) {
            fragment = new GetTestFragment();
            fragmentos= true;
            //fragment=new FragmentCrearGrupo();
            //fragmentos= true;
        }
        else if (id == R.id.carrera) {
            //fragment = new FragmentCarrera();
            //fragmentos = true;
        }
        else if (id == R.id.ramos) {
            fragment = new FragmentRamos();
            fragmentos= true;
        }
        else if (id == R.id.buscar_usuarios) {
            fragment = new FragmentEncontrarOtrosEstudiantes();
            fragmentos= true;
        }
        else if (id == R.id.nav_enviar) {
            fragment = new FragmentEncontrarEstudiantesGrupoAnterior();
            fragmentos = true;
        }

        if(fragmentos){// al ver que se ejecuta un fragmento se encarga de pasar al otro
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main,fragment)
                    .commit();

            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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
