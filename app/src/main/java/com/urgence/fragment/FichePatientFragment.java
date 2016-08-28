package com.urgence.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gc.materialdesign.views.ButtonRectangle;
import com.urgence.R;
import com.urgence.pojo.Patient;
import com.urgence.utils.AppController;
import com.urgence.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by SooheibSelmi
 */
public class FichePatientFragment extends Fragment {
    @InjectView(R.id.name)

    TextView textName;
    @InjectView(R.id.buttonSortie)
    ButtonRectangle buttonSortie;
    @InjectView(R.id.detail_fiche)
    TextView detailFiche;

    @InjectView(R.id.buttonReorienter)
    ButtonRectangle buttonReorienter;
    private ProgressDialog pDialog;

    @InjectView(R.id.nom)
    EditText nom;
    @InjectView(R.id.prenom)
    EditText prenom;
    @InjectView(R.id.login)
    EditText dns;
    @InjectView(R.id.sexe)
    EditText sexe;
    @InjectView(R.id.tel)
    EditText tel;
    @InjectView(R.id.date_inscrption)
    EditText dateInscrption;

    @InjectView(R.id.profession)
    EditText professoin;
    @InjectView(R.id.aucuneidee)
    EditText typeCarnet;


    Patient patientFinale;
    @InjectView(R.id.buttoncancel)
    ButtonRectangle buttoncancel;
    @InjectView(R.id.buttonValider)
    ButtonRectangle buttonValider;

    public FichePatientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fiche_patient, container, false);
        ButterKnife.inject(this, view);
        patientFinale = getArguments().getParcelable("pat");
        professoin.setText(patientFinale.getProfession());

        textName.setText(patientFinale.getNom() + " " + patientFinale.getPrenom());
        nom.setText(patientFinale.getNom());
        prenom.setText(patientFinale.getPrenom());
        dns.setText(patientFinale.getDns());
        sexe.setText(patientFinale.getSexe());
        dateInscrption.setText(patientFinale.getDateInscirption());
        tel.setText(patientFinale.getTel());
        typeCarnet.setText(patientFinale.getTypecarnet());

        if (Utils.ACTION == Utils.SALLE_BOX1) {
            buttonValider.setVisibility(View.GONE);
            buttonSortie.setVisibility(View.GONE);
            buttonReorienter.setVisibility(View.GONE);
        }
        buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();

            }
        });

        buttonReorienter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.addFragment(new NewSalleOrientationFragment(), getArguments(), NewSalleOrientationFragment.class.getCanonicalName(), false, getActivity().getSupportFragmentManager(), R.id.fragment_container2);
            }
        });


        buttonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPAtient();
            }
        });


        buttonSortie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //faire la sortie
                sortiePatient();
            }
        });

        getFichePatient();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    private void sortiePatient() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Sortie en cours ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Utils.LINK_WS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                hideDialog();

                for (int i = 0; i < getActivity().getSupportFragmentManager().getBackStackEntryCount(); i++) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }

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
                params.put("tag", Utils.TAG_SORTIE);
                params.put("id", String.valueOf(patientFinale.getId()));

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void callPAtient() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Appelle en cours ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Utils.LINK_WS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                hideDialog();

                Toast.makeText(getActivity(), "Le patient a été appellé avec succés", Toast.LENGTH_LONG).show();
                for (int i = 0; i < getActivity().getSupportFragmentManager().getBackStackEntryCount(); i++) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }


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
                params.put("tag", Utils.TAG_CALL);
                params.put("id", String.valueOf(patientFinale.getId()));

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void getFichePatient() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                Utils.LINK_WS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray = new JSONArray(response);

                    JSONObject jsonPrfile = new JSONObject(jsonArray.getJSONObject(0).getString("fiche_json"));
                    String message = "CCMU : " + jsonPrfile.getString("ccmu") + "\nScore : " + jsonPrfile.getString("scroe") + "\nOrigine : " +
                            jsonPrfile.getString("origine") + "\nFR : " + jsonPrfile.getString("fr") + "\nTransport : " +
                            jsonPrfile.getString("transport") + "\nPA : " + jsonPrfile.getString("pa") + "\nCisconstance : " + jsonPrfile.getString("circ") + "\nTempérature : " +
                            jsonPrfile.getString("t") + "\nATCD : " + jsonPrfile.getString("atcd") + "\nFC : " + jsonPrfile.getString("fc") + "\nType : " + jsonPrfile.getString("type") + "\nEVA : " + jsonPrfile.getString("eva")
                            + "\nSpO2 : " + jsonPrfile.getString("sp");

                    detailFiche.setText(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                params.put("tag", Utils.TAG_GET_FICHE_PATIENT);
                params.put("id", String.valueOf(patientFinale.getId()));

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
