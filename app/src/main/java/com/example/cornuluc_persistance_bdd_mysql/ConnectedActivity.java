package com.example.cornuluc_persistance_bdd_mysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConnectedActivity extends AppCompatActivity implements View.OnClickListener
{
    private static Button button = null;
    private ProgressDialog progressDialog = null;

    private void userSearch()
    {
        // Afficher la boîte de dialogue
        progressDialog.setMessage("Création utilisateur en cours...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constantes.URL_Delete,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(ConnectedActivity.this, jsonObject.getString("Message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ConnectedActivity.this, "erreur", Toast.LENGTH_LONG).show();
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

                        Toast.makeText(ConnectedActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("nom", SharedPreferenceManager.getUserName());
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
        setContentView(R.layout.connected);

        init();

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v == button)
        {
            Toast.makeText(this, SharedPreferenceManager.getUserName(), Toast.LENGTH_LONG).show();
            Toast.makeText(this, SharedPreferenceManager.getUserMail(), Toast.LENGTH_LONG).show();
            finish();
            Intent monIntent = new Intent(ConnectedActivity.this, RechercheActivity.class);
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
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Disconnect :
                finish();
                SharedPreferenceManager.getInstance(getApplicationContext()).logout();
                Intent monIntent = new Intent(ConnectedActivity.this, LoginActivity.class);
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
        button = (Button) findViewById(R.id.buttonConnect);
        // definition des variables situés plus haut (editTextUsername, editTextPassword, etc.)
        progressDialog = new ProgressDialog(this);
    }
}
