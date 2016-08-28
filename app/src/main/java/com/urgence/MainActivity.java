package com.urgence;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.urgence.adapter.ActionsAdapter;
import com.urgence.fragment.AcceuilFragment;
import com.urgence.fragment.AddPatientFragment;
import com.urgence.fragment.InterfaceTriEtOrientation;
import com.urgence.fragment.ListPatientFragment;
import com.urgence.fragment.ListSalleFragment;
import com.urgence.pojo.Patient;
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
public class MainActivity extends FragmentActivity {

    @InjectView(R.id.action_list)
    ListView actionList;
    ActionsAdapter adapter;
    @InjectView(R.id.text_acceuil)
    TextView textAcceuil;
    private ProgressDialog pDialog;


    public static String SALLE = "";


    private void deconnecter() {
        Utils.ACTION = -1;
        Intent intent = new Intent(MainActivity.this, AuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        if (Utils.ACTION == Utils.SALLE_BOX1) {
            adapter = new ActionsAdapter(MainActivity.this, 0);
            textAcceuil.setText("Agent administratif");
        } else if (Utils.ACTION == Utils.SALLE_BOX2) {
            adapter = new ActionsAdapter(MainActivity.this, 1);
            textAcceuil.setText("Infirmier d'accueil et d'orientation");
        } else{
            adapter = new ActionsAdapter(MainActivity.this, 1);
            textAcceuil.setText("Médecin");
        }
        actionList.setAdapter(adapter);


        actionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
                    getSupportFragmentManager().popBackStack();

                }

                if (Utils.ACTION == Utils.SALLE_BOX1) {
                    if (position == 0) {
                        Utils.addFragment(new AddPatientFragment(), null, AddPatientFragment.class.getCanonicalName(), false, getSupportFragmentManager(), R.id.fragment_container);

                    } else if (position == 1) {
                        Utils.addFragment(new ListPatientFragment(), null, ListPatientFragment.class.getCanonicalName(), false, getSupportFragmentManager(), R.id.fragment_container);

                    } else {
                        deconnecter();
                    }

                } else if (Utils.ACTION == Utils.SALLE_BOX2) {
                    if (position == 0) {
                        //  Utils.addFragment(new ListPatientFragment(), null, ListPatientFragment.class.getCanonicalName(), false, getSupportFragmentManager(), R.id.fragment_container);
                        patientsList = new ArrayList<Patient>();
                        getAllPatient();

                    } else if (position == 1) {
                        Utils.addFragment(new ListPatientFragment(), null, ListPatientFragment.class.getCanonicalName(), false, getSupportFragmentManager(), R.id.fragment_container);

                    } else if (position == 2) {
                        Utils.addFragment(new ListSalleFragment(), null, ListSalleFragment.class.getCanonicalName(), false, getSupportFragmentManager(), R.id.fragment_container);

                    } else {
                        deconnecter();
                    }


                } else  {


                    if (position == 0) {
                        patientsList = new ArrayList<Patient>();

                        getAllPatientBySalle(Utils.ACTION);


                    } else if (position == 1) {

                        Bundle b = new Bundle();
                        b.putInt("salle", Utils.ACTION);
                        Utils.addFragment(new ListPatientFragment(), b, ListPatientFragment.class.getCanonicalName(), false, getSupportFragmentManager(), R.id.fragment_container);

                    } else if (position == 2) {
                        Utils.addFragment(new ListSalleFragment(), null, ListSalleFragment.class.getCanonicalName(), false, getSupportFragmentManager(), R.id.fragment_container);

                    } else {
                        deconnecter();
                    }

                }
            }
        });


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AcceuilFragment hello = new AcceuilFragment();
        fragmentTransaction.add(R.id.acceil, hello, "HELLO");
        fragmentTransaction.commit();
    }

    ArrayList<Patient> patientsList;


    private void getAllPatient() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Appel en cours ...");
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
                    if (patientsList.size() > 0) {
                        callPAtient();
                    } else {
                        hideDialog();
                        Toast.makeText(MainActivity.this, "Aucun patient n'est pas disponible dans la liste d'attente", Toast.LENGTH_LONG).show();
                    }

                    // listPatient.setAdapter(new ListPatientAdapter(getActivity(), R.layout.item_list_patient, patientsList));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Toast.makeText(MainActivity.this, "Aucun patient n'est pas disponible dans la liste d'attente", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", Utils.TAG_LIST);


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

    private void callPAtient() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Utils.LINK_WS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                hideDialog();

                Toast.makeText(MainActivity.this, "Le patient " + patientsList.get(0).getNom() + " " + patientsList.get(0).getPrenom() + " a été appellé avec succés", Toast.LENGTH_LONG).show();
                Bundle b = new Bundle();
                b.putParcelable("pat", patientsList.get(0));
                Utils.addFragment(new InterfaceTriEtOrientation(), b, InterfaceTriEtOrientation.class.getCanonicalName(), false, getSupportFragmentManager(), R.id.fragment_container);


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
                params.put("id", String.valueOf(patientsList.get(0).getId()));

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void getAllPatientBySalle(final int salleBicha) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Appel en cours ...");
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
                    if (patientsList.size() > 0) {
                        callPAtient();
                    } else {
                        hideDialog();
                        Toast.makeText(MainActivity.this, "Aucun patient n'est disponible dans la liste d'attente", Toast.LENGTH_LONG).show();
                    }

                    // listPatient.setAdapter(new ListPatientAdapter(getActivity(), R.layout.item_list_patient, patientsList));
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
                params.put("id", String.valueOf(salleBicha));


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


}
