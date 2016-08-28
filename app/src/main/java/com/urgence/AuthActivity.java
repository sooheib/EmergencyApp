package com.urgence;
/**
 * Created by SooheibSelmi
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gc.materialdesign.views.ButtonRectangle;
import com.urgence.fragment.GererFragment;
import com.urgence.utils.AppController;
import com.urgence.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class AuthActivity extends FragmentActivity {
    private ProgressDialog pDialog;
    @InjectView(R.id.img_src)
    ImageView imgSrc;
    @InjectView(R.id.login)
    EditText login;
    @InjectView(R.id.pass)
    EditText pass;
    @InjectView(R.id.buttonAuth)
    ButtonRectangle buttonAuth;
    @InjectView(R.id.buttonReset)
    ButtonRectangle buttonReset;


    @OnClick
            (R.id.gerer_salle)
    public void OpenGererSalle() {
        if (login.getText().toString().equals("") || pass.getText().toString().equals("")) {


            Toast.makeText(AuthActivity.this, "Introduisez le login et le mot de passe", Toast.LENGTH_LONG).show();
            return;
        } else {
            if (login.getText().toString().equals("admin") && pass.getText().toString().equals("admin")) {
                Utils.addFragment(new GererFragment(), null, GererFragment.class.getCanonicalName(), false, getSupportFragmentManager(), R.id.fragment_container);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_auth);
        ButterKnife.inject(this);
        buttonAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login.getText().toString().equals("") || pass.getText().toString().equals("")) {


                    Toast.makeText(AuthActivity.this, "Introduisez le login et le mot de apsse", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    getAuth();


                }

            }
        });
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setText("");
                pass.setText("");
            }
        });

    }


    private void getAuth() {
        String tag_string_req = "req_login";

        pDialog = new ProgressDialog(AuthActivity.this);
        pDialog.setMessage("Authentification  en cours ...");
        showDialog();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                Utils.LINK_WS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {



                try {
                    JSONArray responArray = new JSONArray(response);

                    Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                    intent.putExtra("from", responArray.getJSONObject(0).getString("code_salle"));
                    Utils.ACTION = Integer.parseInt(responArray.getJSONObject(0).getString("code_salle"));
                    Utils.ACTIONNAME = responArray.getJSONObject(0).getString("nomsalle");
                    Utils.ALLSALLEEPRMITTED = responArray.getJSONObject(0).getString("salees");

                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(AuthActivity.this, "Aucune salle ne correspond à ces identifiants", Toast.LENGTH_LONG).show();
                }


                hideDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AuthActivity.this, "Aucune salle ne correspond à ces identifiants", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("tag", Utils.AUTH_SALLE);

                params.put("pass", pass.getText().toString());
                params.put("login", login.getText().toString());

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
