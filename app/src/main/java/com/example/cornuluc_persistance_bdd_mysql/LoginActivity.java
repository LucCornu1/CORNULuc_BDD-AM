package com.example.cornuluc_persistance_bdd_mysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private static EditText editTextUsername = null;
    private static EditText editTextPassword = null;

    private static Button button = null;
    private static Button button_Switch = null;

    private ProgressDialog progressDialog = null;

    private void userLogin()
    {
        // Récupérer les valeurs des 2 champs et ôter les espaces avec .trim() getTet().toString().trim()
        final String username = (String) editTextUsername.getText().toString().trim();
        final String password = (String) editTextPassword.getText().toString().trim();

        // Afficher la boîte de dialogue
        progressDialog.setMessage("Recherche utilisateur en cours...");
        progressDialog.show();

        // Créer la requête.
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constantes.URL_Login+"?Log_Nom="+username+"&Log_Mdp="+password,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (!jsonObject.getBoolean("error"))
                            {
                                SharedPreferenceManager.getInstance(getApplicationContext()).userLogin(jsonObject.getString("Nom"), jsonObject.getString("Mail"));
                                Intent monIntent = new Intent(LoginActivity.this,ConnectedActivity.class);
                                startActivity(monIntent);
                                finish();
                            }

                            Toast.makeText(LoginActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Aucun résultat", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressDialog.hide();
                        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("Log_Nom", username);
                params.put("Log_Mdp", password);
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
        setContentView(R.layout.login);

        init();

        button.setOnClickListener(this);
        button_Switch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v == button)
        {
            userLogin();
        }

        if (v == button_Switch)
        {
            finish();
            Intent monIntent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(monIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        // return super.onOptionsItemSelected(item);
        switch (item.getItemId())
        {
            case R.id.Settings :
                Toast.makeText(LoginActivity.this, SharedPreferenceManager.getUserName(), Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginActivity.this, SharedPreferenceManager.getUserMail(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.Disconnect :
                finish();
                SharedPreferenceManager.getInstance(getApplicationContext()).logout();
                Intent monIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(monIntent);
                break;
            default:
                break;
        }
        //return super.onOptionsItemSelected(item);
        return true;
    }

    private void init()
    {
        editTextUsername = (EditText) findViewById(R.id.editTextPersonNameLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPasswordLogin);
        button = (Button) findViewById(R.id.buttonConnect);
        button_Switch = (Button) findViewById(R.id.buttonSwitch);

        // definition des variables situés plus haut (editTextUsername, editTextPassword, etc.)
        progressDialog = new ProgressDialog(this);
    }
}
