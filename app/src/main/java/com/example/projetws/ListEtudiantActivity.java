package com.example.projetws;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.projetws.adapter.EtudiantAdapter;
import com.example.projetws.beans.Etudiant;

public class ListEtudiantActivity extends AppCompatActivity {

    private ListView listView;
    private List<Etudiant> etudiantList;
    private EtudiantAdapter adapter;
    private String url = "http://10.0.2.2/php_volley/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_etudiant);

        listView = findViewById(R.id.listView);

        // Initialize the Return to Add Student button
        Button returnToAddButton = findViewById(R.id.return_add_student);
        returnToAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListEtudiantActivity.this, addEtudiant.class);
                startActivity(intent); // Launch the addEtudiant activity
            }
        });

        retrieveStudentsData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Etudiant selectedEtudiant = etudiantList.get(position);

                new AlertDialog.Builder(ListEtudiantActivity.this)
                        .setTitle("Choisir une action")
                        .setItems(new CharSequence[]{"Editer", "Supprimer"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    showEditDialog(selectedEtudiant);
                                } else {
                                    showDeleteConfirmation(selectedEtudiant);
                                }
                            }
                        })
                        .show();
            }
        });
    }

    private void retrieveStudentsData() {
        String loadUrl = this.url + "ws/loadEtudiant.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, loadUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        handleJsonResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListEtudiantActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void handleJsonResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            etudiantList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Etudiant etudiant = new Etudiant(
                        jsonObject.getInt("id"),
                        jsonObject.getString("nom"),
                        jsonObject.getString("prenom"),
                        jsonObject.getString("ville"),
                        jsonObject.getString("sexe")
                );
                etudiantList.add(etudiant);
            }

            adapter = new EtudiantAdapter(this, etudiantList);
            listView.setAdapter((ListAdapter) adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showEditDialog(final Etudiant etudiant) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editer l'étudiant");

        // Create a LinearLayout to hold the EditText fields
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create EditText fields for each property
        final EditText nomEditText = new EditText(this);
        nomEditText.setHint("Name");
        nomEditText.setText(etudiant.getNom());
        layout.addView(nomEditText);

        final EditText prenomEditText = new EditText(this);
        prenomEditText.setHint("Surname");
        prenomEditText.setText(etudiant.getPrenom());
        layout.addView(prenomEditText);

        final EditText villeEditText = new EditText(this);
        villeEditText.setHint("City");
        villeEditText.setText(etudiant.getVille());
        layout.addView(villeEditText);

        final EditText sexeEditText = new EditText(this);
        sexeEditText.setHint("Gender");
        sexeEditText.setText(etudiant.getSexe());
        layout.addView(sexeEditText);

        builder.setView(layout); // set the layout to the dialog

        // Set positive button action
        builder.setPositiveButton("Enregister", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Update etudiant object with new values
                etudiant.setNom(nomEditText.getText().toString());
                etudiant.setPrenom(prenomEditText.getText().toString());
                etudiant.setVille(villeEditText.getText().toString());
                etudiant.setSexe(sexeEditText.getText().toString());

                adapter.notifyDataSetChanged(); // Notify the adapter about the changes
                sendUpdateRequest(etudiant); // Send the update request to the server
            }
        });

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void sendUpdateRequest(Etudiant etudiant) {
        String updateUrl = url + "controller/updateEtudiant.php"; // Adjust this URL to your update endpoint

        StringRequest stringRequest = new StringRequest(Request.Method.POST, updateUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Informations mises à jour avec succès", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("UpdateError", error.toString());
                        Toast.makeText(getApplicationContext(), "Update failed!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(etudiant.getId()));
                params.put("nom", etudiant.getNom());
                params.put("prenom", etudiant.getPrenom());
                params.put("ville", etudiant.getVille());
                params.put("sexe", etudiant.getSexe());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showDeleteConfirmation(final Etudiant etudiant) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Voulez-vous vraiment supprimer cet étudiant ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        etudiantList.remove(etudiant);
                        adapter.notifyDataSetChanged();
                        sendDeleteRequest(etudiant);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void sendDeleteRequest(Etudiant etudiant) {
        String deleteUrl = url + "controller/deleteEtudiant.php?id=" + etudiant.getId();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.DELETE, deleteUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Etudiant supprimé avec succès", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error + "");
            }
        });

        requestQueue.add(request);
    }
}