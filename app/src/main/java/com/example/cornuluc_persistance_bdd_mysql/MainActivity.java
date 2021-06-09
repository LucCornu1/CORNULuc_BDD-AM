package com.example.cornuluc_persistance_bdd_mysql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static EditText editTextUsername = null;
    private static EditText editTextPassword = null;
    private static EditText editTextEmail = null;
    private static EditText editTextLocalite = null;
    private static EditText editTextDate = null;

    private static Button button = null;

    private ProgressDialog progressDialog = null;

    private void registerUser()
    {
        // Récupérer les valeurs des 3 champs et ôter les espaces avec .trim()
        final String username = (String) editTextUsername.getText().toString().trim();
        final String password = (String) editTextPassword.getText().toString().trim();
        final String email = (String) editTextEmail.getText().toString().trim();
        final String localite = (String) editTextLocalite.getText().toString().trim();
        final String date = (String) editTextDate.getText().toString().trim();

        if (username.equals(""))
        {
            Toast.makeText(MainActivity.this, "Username required", Toast.LENGTH_LONG).show();
            return;
        }else if(email.equals("")){
            Toast.makeText(MainActivity.this, "Email required", Toast.LENGTH_LONG).show();
            return;
        }else if(password.equals("")){
            Toast.makeText(MainActivity.this, "No password", Toast.LENGTH_LONG).show();
            return;
        }

        // Afficher la boîte de dialogue
        progressDialog.setMessage("Création utilisateur en cours...");
        progressDialog.show();

        // Créer la requête. POST vu qu'on insère en base
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constantes.URL_Register,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(MainActivity.this, jsonObject.getString("Message"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressDialog.hide();
                        NetworkResponse NetError = error.networkResponse;
                        if (NetError != null)
                        {
                            Log.e("NetworkError", "Error : "+String.valueOf(NetError.statusCode));
                        }

                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("nom", username);
                params.put("mail", email);
                params.put("mdp", password);
                params.put("localite", localite);
                params.put("date", date);
                return params;
            }
        };

        // Version 1 non optimisée
        /*RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/

        // Version 2 optimisée avec "singleton" RequestQueue
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        if (SharedPreferenceManager.getInstance(getApplicationContext()).isLoggedIn())
        {
            finish();
            SharedPreferenceManager.getInstance(getApplicationContext()).logout();
            Intent monIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(monIntent);
            return;
        }

        button.setOnClickListener(this);
        findViewById(R.id.buttonConnect).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v == button)
        {
            registerUser();
        }

        if (v == findViewById(R.id.buttonConnect))
        {
            Intent monIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(monIntent);
        }
    }

    private void init()
    {
        editTextUsername = (EditText) findViewById(R.id.editTextPersonName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextLocalite = (EditText) findViewById(R.id.editTextLocalite);
        editTextDate = (EditText) findViewById(R.id.editTextDate);

        button = (Button) findViewById(R.id.buttonSave);
        // definition des variables situés plus haut (editTextUsername, editTextPassword, etc.)
        progressDialog = new ProgressDialog(this);
    }
}