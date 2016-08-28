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
import com.urgence.pojo.Patient;
import com.urgence.pojo.Salle;
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
 *//
public class NewSalleOrientationFragment extends Fragment {
    Patient patientSelct;
    private ProgressDialog pDialog;

    @InjectView(R.id.new_ccmu)
    EditText newCcmu;
    @InjectView(R.id.spin_box)
    Spinner spinBox;
    @InjectView(R.id.buttoncancel)
    ButtonRectangle buttoncancel;
    @InjectView(R.id.buttonValider)
    ButtonRectangle buttonValider;

    public NewSalleOrientationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_salle_orientation, container, false);
        ButterKnife.inject(this, view);
        getAllSalle();
        buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        buttonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (newCcmu.getText().toString().equals("")) {

                    Toast.makeText(getActivity(), "Veuillez saisir le nouveau CCMU", Toast.LENGTH_LONG).show();
                    return;
                }
                updateSallePatient();
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

    private void updateSallePatient() {
        patientSelct = getArguments().getParcelable("pat");
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Affectation en cours ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Utils.LINK_WS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                hideDialog();

                for (int i = 0; i < getActivity().getSupportFragmentManager().getBackStackEntryCount(); i++) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", Utils.TAG_UPDATE_SALLE_PATIENT);
                params.put("id", String.valueOf(patientSelct.getId()));


                int poistionSlected = spinBox.getSelectedItemPosition();
                params.put("salle", String.valueOf(listSalle.get(poistionSlected).getCodesalle()));


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


}
