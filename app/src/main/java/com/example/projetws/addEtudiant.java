package com.example.projetws;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.projetws.ListEtudiantActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class addEtudiant extends AppCompatActivity implements View.OnClickListener {

    private EditText nom;
    private EditText prenom;
    private Spinner ville;
    private RadioButton m;
    private RadioButton f;
    private Button add;
    private Button viewList;
    RequestQueue requestQueue;
    String insertUrl = "http://10.0.2.2/php_volley/ws/createEtudiant.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_etudiant);

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        ville = findViewById(R.id.ville);
        add = findViewById(R.id.add);
        viewList = findViewById(R.id.view_list);
        m = findViewById(R.id.m);
        f = findViewById(R.id.f);

        add.setOnClickListener(this);
        viewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addEtudiant.this, ListEtudiantActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == add) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());

            String sexe = m.isChecked() ? "Male" : "Female";
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("nom", nom.getText().toString());
                jsonBody.put("prenom", prenom.getText().toString());
                jsonBody.put("ville", ville.getSelectedItem().toString());
                jsonBody.put("sexe", sexe);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Requête pour récupérer un JSONObject
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, insertUrl, jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("addEtud", response.toString());
                            // Traitez la réponse ici
                            Toast.makeText(getApplicationContext(), "Étudiant créé avec succès !", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(addEtudiant.this, ListEtudiantActivity.class);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("error", error.toString());
                            Toast.makeText(getApplicationContext(), "Erreur lors de la création de l'étudiant.", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            requestQueue.add(jsonObjectRequest);
        }
    }
}
