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
import android.widget.TextView;

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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by SooheibSelmi
 */
public class InterfaceTriEtOrientation extends Fragment {

    int sommeTalle = 0;
    String delai;


    @InjectView((R.id.name))
    TextView mTextName;
    @InjectView(R.id.buttoncancel)
    ButtonRectangle buttoncancel;
    @InjectView(R.id.buttonValider)
    ButtonRectangle buttonValider;
    @InjectView(R.id.fr_edit)
    EditText frEdit;
    @InjectView(R.id.sp_edit)
    EditText spEdit;
    @InjectView(R.id.FC_edit)
    EditText FCEdit;
    @InjectView(R.id.paedit)
    EditText paedit;
    @InjectView(R.id.t_edit)
    EditText tEdit;
    @InjectView(R.id.eva_edit)
    EditText evaEdit;

    @InjectView(R.id.etat)
    TextView etat;
    private ProgressDialog pDialog;

    @InjectView(R.id.spin_transport)
    Spinner spinTransport;
    @InjectView(R.id.spin_origine)
    Spinner spinOrigine;
    @InjectView(R.id.spin_cir)
    Spinner spinCir;

    @InjectView(R.id.score)
    EditText score;
    @InjectView(R.id.ccmu)
    EditText ccmu;
    @InjectView(R.id.buttoncalculer)
    ButtonRectangle buttoncalculer;
    @InjectView(R.id.hdm)
    EditText hdm;
    @InjectView(R.id.motif_consultation)
    EditText motifConsultation;
    @InjectView(R.id.spin_atcd)
    Spinner spinAtcd;
    @InjectView(R.id.spin_type)
    Spinner spinType;
    @InjectView(R.id.spin_box)
    Spinner spinBox;

    public InterfaceTriEtOrientation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_interface_tri_et_orientation, container, false);
        ButterKnife.inject(this, view);
        patientSelct = getArguments().getParcelable("pat");
        mTextName.setText("Nom et prénom : " + patientSelct.getNom() + "   " + patientSelct.getPrenom() + "\nSexe : " + patientSelct.getSexe() + "\nDate de naissence : " + patientSelct.getDns() + "\nProfession : "
                + patientSelct.getProfession() + "\nCarnet : " + patientSelct.getTypecarnet() + "\nTéléphone : " + patientSelct.getTel() + "\nDate inscription : " + patientSelct.getDateInscirption());

        etat.setText("Etat : très urgent");
        getAllSalle();

        buttonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSallePatient();
            }
        });


        buttoncalculer.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {


                                                  int value1 = Integer.parseInt(frEdit.getText().toString());
                                                  int value2 = Integer.parseInt(spEdit.getText().toString());
                                                  int value3 = Integer.parseInt(FCEdit.getText().toString());
                                                  int value4 = Integer.parseInt(paedit.getText().toString());
                                                  int value5 = Integer.parseInt(tEdit.getText().toString());
                                                  int value6 = Integer.parseInt(evaEdit.getText().toString());


                                                  int number1 = 0;
                                                  int number2 = 0;
                                                  int number3 = 0;
                                                  int number4 = 0;
                                                  int number5 = 0;
                                                  int number6 = 0;
                                                  sommeTalle = 0;
                                                  if (inRange(value1, 36, 6)) {
                                                      number1 = 4;
                                                  }

                                                  if (inRange(value1, 39, 40) || inRange(value1, 6, 9)) {
                                                      number1 = 3;
                                                  }
                                                  if (inRange(value1, 24, 28) || inRange(value1, 10, 11)) {
                                                      number1 = 2;
                                                  }

                                                  if (inRange(value1, 21, 23) || inRange(value1, 12, 13)) {
                                                      number1 = 1;
                                                  }
                                                  if (inRange(value1, 14, 10)) {
                                                      number1 = 0;
                                                  }
                                                  if (value2 < 88) {
                                                      number2 = 4;
                                                  }

                                                  if (inRange(value2, 88, 89)) {
                                                      number2 = 3;

                                                  }


                                                  if (inRange(value2, 90, 93)) {
                                                      number2 = 1;

                                                  }

                                                  if (value2 > 93) {
                                                      number2 = 0;
                                                  }


                                                  if (inRange(value3, 180, 40)) {
                                                      number3 = 4;

                                                  }

                                                  if (inRange(value3, 80, 160)) {
                                                      number3 = 0;

                                                  }

                                                  if (inRange(value3, 140, 179) || inRange(value3, 40, 54)) {
                                                      number3 = 3;

                                                  }


                                                  if (inRange(value3, 110, 139) || inRange(value3, 55, 69)) {
                                                      number3 = 2;

                                                  }


                                                  if (value5 > 41 || value5 < 30) {
                                                      number5 = 4;
                                                  }


                                                  if (inRange(value5, 39, 40) || inRange(value5, 30, 32)) {
                                                      number5 = 3;
                                                  }


                                                  if (inRange(value5, 38, 39) || inRange(value5, 34, 36)) {
                                                      number5 = 1;
                                                  }

                                                  if (inRange(value5, 32, 34)) {
                                                      number5 = 2;
                                                  }

                                                  if (inRange(value5, 36, 39)) {
                                                      number5 = 0;
                                                  }


                                                  if (value4 > 200 || value4 < 55) {
                                                      number4 = 4;

                                                  }

                                                  if (inRange(value4, 160, 199) || inRange(value4, 55, 69)) {
                                                      number4 = 2;
                                                  }
                                                  if (inRange(value4, 80, 160)) {
                                                      number4 = 0;
                                                  }


                                                  if (value6 > 70) {
                                                      number6 = 4;
                                                  }


                                                  if (value6 < 10) {
                                                      number6 = 0;
                                                  }
                                                  if (inRange(value6, 50, 69)) {
                                                      number6 = 3;
                                                  }
                                                  if (inRange(value6, 30, 49)) {
                                                      number6 = 2;
                                                  }
                                                  if (inRange(value6, 10, 29)) {
                                                      number6 = 1;
                                                  }
                                                  sommeTalle = number1 + number2 + number3 + number4 + number5 + number6;

                                                  score.setText(String.valueOf(sommeTalle));

                                                  if (sommeTalle >= 10) {
                                                      ccmu.setText("Réanimation");
                                                      delai = "0Heure";
                                                  } else if (inRange(sommeTalle, 5, 9)) {
                                                      ccmu.setText("Très Urgent");
                                                      delai = "15 minutes";
                                                  } else if (inRange(sommeTalle, 5, 9)) {
                                                      ccmu.setText("Très Urgent");
                                                      delai = "15 minutes";
                                                  } else if (inRange(sommeTalle, 1, 4)) {
                                                      ccmu.setText("Urgent");
                                                      delai = "30 minutes";
                                                  } else {
                                                      ccmu.setText("Non Urgent");
                                                      delai = "1 heure";
                                                  }

                                              }


                                          }

        );
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    ArrayList<Salle> listSalle;
    ArrayList<Salle> listSalleFinale;

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
                listSalleFinale = new ArrayList<>();

                if (Utils.ALLSALLEEPRMITTED.equals("all")) {
                    listSalleFinale = listSalle;
                } else {
                    String[] jsonArray = Utils.ALLSALLEEPRMITTED.split(",");

                    for (int j = 0; j < listSalle.size(); j++) {
                        for (int i = 0; i < jsonArray.length; i++) {
                            if (jsonArray[i].equals(String.valueOf(listSalle.get(j).getCodesalle()))) {
                                listSalleFinale.add(listSalle.get(j));
                            }
                        }

                    }

                }
                String[] salels = new String[listSalleFinale.size()];
                for (int i = 0; i < listSalleFinale.size(); i++) {
                    salels[i] = listSalleFinale.get(i).getNomsalle();
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

    Patient patientSelct;

    private void updateFichePatient() {

        patientSelct = getArguments().getParcelable("pat");
        // Tag used to cancel the request
        String tag_string_req = "req_login";


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
                params.put("tag", Utils.TAG_UPDATE_FICHE_PATIENT);
                params.put("id", String.valueOf(patientSelct.getId()));


                params.put("json", makeJsonFiche());


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


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


                updateFichePatient();
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
                params.put("tag", Utils.TAG_UPDATE_SALLE_PATIENT);
                params.put("id", String.valueOf(patientSelct.getId()));


                int poistionSlected = spinBox.getSelectedItemPosition();
                params.put("salle", String.valueOf(listSalleFinale.get(poistionSlected).getCodesalle()));


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


    private boolean inRange(int number, int min, int max) {

        return (number >= min && number <= max);
    }


    private String makeJsonFiche() {
        JSONObject json = new JSONObject();

        try {
            json.put("transport", spinTransport.getSelectedItem().toString());
            json.put("origine", spinOrigine.getSelectedItem().toString());
            json.put("circ", spinCir.getSelectedItem().toString());
            json.put("fr", frEdit.getText().toString());
            json.put("sp", spEdit.getText().toString());
            json.put("fc", FCEdit.getText().toString());
            json.put("pa", paedit.getText().toString());
            json.put("t", tEdit.getText().toString());
            json.put("eva", evaEdit.getText().toString());
            json.put("scroe", score.getText().toString());
            json.put("ccmu", ccmu.getText().toString());
            json.put("salle", spinBox.getSelectedItem().toString());
            json.put("delai", delai);
            json.put("hdm", hdm.getText().toString());
            json.put("motif", motifConsultation.getText().toString());
            json.put("type", spinType.getSelectedItem().toString());
            json.put("salle", spinBox.getSelectedItem().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}
