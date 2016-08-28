package com.urgence.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.urgence.R;
import com.urgence.adapter.ListPatientAdapter;
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
 */
public class DetailSalleFragment extends Fragment {
    ArrayList<Patient> patientsList;

    private ProgressDialog pDialog;
    @InjectView(R.id.nom_salle)
    EditText nomSalle;
    @InjectView(R.id.descprtion)
    EditText descprtion;
    @InjectView(R.id.list_des_personnes_attents)
    ListView listDesPersonnesAttents;

    public DetailSalleFragment() {
        // Required empty public constructor
    }


    Salle salle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_salle, container, false);
        ButterKnife.inject(this, view);
        patientsList = new ArrayList<Patient>();
        salle = (Salle) getArguments().get("salle");
        nomSalle.setText(salle.getNomsalle());
        descprtion.setText(salle.getDescriptionsalle());
        getAllPatient();

        listDesPersonnesAttents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Patient patent = (Patient) adapterView.getItemAtPosition(i);
                Bundle b = new Bundle();
                b.putParcelable("pat", patent);

                Utils.addFragment(new FichePatientFragment(), b, FichePatientFragment.class.getCanonicalName(), false, getActivity().getSupportFragmentManager(), R.id.fragment_container2);


            }
        });
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void getAllPatient() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Chargement en cours ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Utils.LINK_WS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                try {
                    JSONArray json = new JSONArray(response);
                    for (int i = 0; i < json.length(); i++) {

                        patientsList.add(Patient.initFromJson(json.getJSONObject(i)));
                    }
                    listDesPersonnesAttents.setAdapter(new ListPatientAdapter(getActivity(), R.layout.item_list_patient, patientsList));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


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
                params.put("tag", Utils.TAG_CALL_LIST_SALLE);
                params.put("id", String.valueOf(salle.getCodesalle()));

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
