package com.urgence.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gc.materialdesign.views.ButtonRectangle;
import com.urgence.R;
import com.urgence.pojo.Salle;
import com.urgence.pojo.User;
import com.urgence.utils.AppController;
import com.urgence.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by SooheibSelmi
 */
public class GererFragment extends Fragment {

    private ProgressDialog pDialog;
    @InjectView(R.id.spin_box)
    Spinner spinBox;

    @InjectView(R.id.spin_box_reponsable)
    Spinner spinBoxResponsable;


    @InjectView(R.id.login_alle)
    EditText loginAlle;
    @InjectView(R.id.pass_salle)
    EditText passSalle;
    @InjectView(R.id.confirmepass_salle)
    EditText confirmepassSalle;
    @InjectView(R.id.buttoncancel)
    ButtonRectangle buttoncancel;
    @InjectView(R.id.buttonValider)
    ButtonRectangle buttonValider;
    @InjectView(R.id.login)
    EditText login;
    @InjectView(R.id.pass)
    EditText pass;
    @InjectView(R.id.spin_grade)
    Spinner spinGrade;
    @InjectView(R.id.valider)
    ButtonRectangle valider;
    @InjectView(R.id.buttonReset)
    ButtonRectangle buttonReset;

    public GererFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gerer, container, false);
        ButterKnife.inject(this, view);
        getAllSalle();


        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setText("");
                pass.setText("");
            }
        });


        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (login.getText().toString().equals("") || pass.getText().toString().equals("")) {

                    Toast.makeText(getActivity(), "Veuillez saisir le nom et le prénom", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    storeNewUser();
                }

            }
        });


        buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAlle.setText("");
                passSalle.setText("");
                confirmepassSalle.setText("");
            }
        });


        buttonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (loginAlle.getText().toString().equals("") || confirmepassSalle.getText().toString().equals("") || passSalle.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();

                    return;
                }

                if (!passSalle.getText().toString().equals(confirmepassSalle.getText().toString())) {
                    Toast.makeText(getActivity(), "Les mot de pases ne sont pas identiques", Toast.LENGTH_LONG).show();

                    return;
                }

                updateSalle();
            }
        });
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    ArrayList<Salle> listSalle;
    ArrayList<User> listuser;


    public void updateSalle() {
        String tag_string_req = "req_login";

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Modification de la salle en cours ...");
        showDialog();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                Utils.LINK_WS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                loginAlle.setText("");
                passSalle.setText("");
                confirmepassSalle.setText("");
                hideDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("tag", Utils.TAG_UPDATEDATA_SALLE);
                params.put("id", String.valueOf(listSalle.get(spinBox.getSelectedItemPosition()).getCodesalle()));
                params.put("id_resp", "2");
                params.put("pass", passSalle.getText().toString());
                params.put("login", loginAlle.getText().toString());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    public void storeNewUser() {

        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Sauvgarde de nouveau utilisateur en cours ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Utils.LINK_WS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                login.setText("");
                pass.setText("");
                getAllUser();


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("tag", Utils.TAG_STORE_USER);
                params.put("nom", login.getText().toString());
                params.put("prenom", pass.getText().toString());
                params.put("grade", spinGrade.getSelectedItem().toString());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }


    private void getAllUser() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                Utils.LINK_WS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                listuser = new ArrayList<User>();
                try {
                    JSONArray json = new JSONArray(response);
                    for (int i = 0; i < json.length(); i++) {

                        listuser.add(User.initFromJson(json.getJSONObject(i)));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String[] salels = new String[listuser.size()];
                for (int i = 0; i < listuser.size(); i++) {
                    salels[i] = listuser.get(i).getNom() + " " + listuser.get(i).getPrenom();

                }
                ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item,
                        salels);
                spinBoxResponsable.setAdapter(spinnerArrayAdapter);
                hideDialog();


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", Utils.TAG_GET_ALL_USER);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void getAllSalle() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Chargement des box en cours ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Utils.LINK_WS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                listSalle = new ArrayList<Salle>();
                try {
                    JSONArray json = new JSONArray(response);
                    for (int i = 0; i < json.length(); i++) {

                        listSalle.add(Salle.initFromJsonObject(json.getJSONObject(i)));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String[] salels = new String[listSalle.size()];
                for (int i = 0; i < listSalle.size(); i++) {
                    salels[i] = listSalle.get(i).getNomsalle();

                }
                ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item,
                        salels);
                spinBox.setAdapter(spinnerArrayAdapter);
                getAllUser();


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", Utils.TAG_SALLE_MED);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
