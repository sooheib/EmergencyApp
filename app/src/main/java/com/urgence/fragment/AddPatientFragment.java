package com.urgence.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.CheckBox;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.urgence.R;
import com.urgence.utils.AppController;
import com.urgence.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by SooheibSelmi
 */
public class AddPatientFragment extends Fragment {

    @InjectView(R.id.profession)
    Spinner profession;
    private ProgressDialog pDialog;
    @InjectView(R.id.profile)
    CircularImageView profile;
    @InjectView(R.id.nom)
    EditText nom;
    @InjectView(R.id.prenom)
    EditText prenom;
    @InjectView(R.id.login)
    EditText login;
    @InjectView(R.id.sexhomme)
    CheckBox sexhomme;
    @InjectView(R.id.sexfemme)
    CheckBox sexfemme;

    @InjectView(R.id.aucuneidee)
    Spinner aucuneidee;
    @InjectView(R.id.tel)
    EditText tel;
    @InjectView(R.id.buttoncancel)
    ButtonRectangle buttoncancel;
    @InjectView(R.id.buttonValider)
    ButtonRectangle buttonValider;

    public AddPatientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_patient, container, false);
        ButterKnife.inject(this, view);

        sexfemme.setOncheckListener(new CheckBox.OnCheckListener() {
            @Override
            public void onCheck(boolean b) {
                if (b)
                    sexhomme.setChecked(false);
            }
        });


        sexhomme.setOncheckListener(new CheckBox.OnCheckListener() {
            @Override
            public void onCheck(boolean b) {
                if (b)
                    sexfemme.setChecked(false);
            }
        });
        buttonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (nom.getText().toString().equals("") || prenom.getText().toString().equals("") || login.getText().toString().equals("") || tel.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
                    return;
                }
                storeNEwPatient();
            }
        });
        buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nom.setText("");
                prenom.setText("");
                login.setText("");

                tel.setText("");

            }
        });
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    /**
     * function to verify login details in mysql db
     */
    private void storeNEwPatient() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Enregistrement ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Utils.LINK_WS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                hideDialog();

                Toast.makeText(getActivity(), "Enregistrement effectué avec succés", Toast.LENGTH_LONG).show();

                nom.setText("");
                prenom.setText("");
                login.setText("");
                tel.setText("");

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
                params.put("tag", Utils.TAG_STORE);
                params.put("nom", nom.getText().toString());
                params.put("prenom", prenom.getText().toString());
                params.put("dns", login.getText().toString());
                params.put("path", "");


                params.put("sexe", sexhomme.isCheck() ? "Homme" : "Femme");
                params.put("tel", tel.getText().toString());
                params.put("type", aucuneidee.getSelectedItem().toString());

                params.put("profession", profession.getSelectedItem().toString());
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
