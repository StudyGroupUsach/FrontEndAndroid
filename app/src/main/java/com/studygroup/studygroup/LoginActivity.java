package com.studygroup.studygroup;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.studygroup.studygroup.Poco.AuthenticationToken;
import com.studygroup.studygroup.Poco.Usuario;
import com.studygroup.studygroup.VolleyHelper.GsonRequest;
import com.studygroup.studygroup.VolleyHelper.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */


    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private View mRegisterFormView;

    AuthenticationToken authenticationToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        mPasswordView = (EditText) findViewById(R.id.password);
        mEmailView = (EditText) findViewById(R.id.email);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptLogin();
            }
        });
        Button mRegisterFormView = (Button) findViewById(R.id.register_button);
        mRegisterFormView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                openRegister();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    public void attemptLogin(){

        /**if(mEmailView.getText().toString().equals("yolo")){
           openMainActivity();
        }*/
        if(TextUtils.isEmpty(mEmailView.getText())) {
            mEmailView.setError(getResources().getString(R.string.error_field_required));
        }
        else if(!mEmailView.getText().toString().contains("@usach.cl")){
            mEmailView.setError(getResources().getString(R.string.error_invalid_email));
        }
        else if(TextUtils.isEmpty(mPasswordView.getText())) {
            mPasswordView.setError(getResources().getString(R.string.error_field_required));
        }
        else{
            userLogin();
        }
    }

    public void userLogin(){
        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();


        JSONObject loginJson = new JSONObject();
        try {
            loginJson.put("mail", email);
            loginJson.put("pass", password);
        }
        catch (JSONException e) { }

        GsonRequest gRequest = new GsonRequest(Request.Method.POST,getResources().getString(R.string.url_login), AuthenticationToken.class, null, loginJson,
                new Response.Listener<AuthenticationToken>() {
                    @Override
                    public void onResponse(AuthenticationToken response) {

                        if(response.isConnected){
                            int enteroId=response.userId;
                            showProgress(true);
                            openMainActivity(enteroId);
                        }
                        else{
                            Toast.makeText(LoginActivity.this,getResources().getString(R.string.error_incorrect_info),Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //hideProgressDialog();
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(gRequest);
    }

    public void openMainActivity(int enteroId){
        Intent myIntent = new Intent(LoginActivity.this,MainActivity.class);
        myIntent.putExtra("usuarioId",enteroId);
        LoginActivity.this.startActivity(myIntent);
    }

    public void openRegister(){
        Intent myIntent = new Intent(LoginActivity.this,RegistrarseActivity.class);
        LoginActivity.this.startActivity(myIntent);
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@usach.cl");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}

