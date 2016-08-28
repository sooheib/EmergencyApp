package com.urgence.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.urgence.R;
import com.urgence.pojo.Patient;
import com.urgence.utils.AppController;
import com.urgence.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by SooheibSelmi
 */
public class ListPatientAdapter extends ArrayAdapter<Patient> {

    public ListPatientAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListPatientAdapter(Context context, int resource, List<Patient> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_list_patient, null);
        }

        Patient p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.name);

            if (tt1 != null) {
                tt1.setText(position + "-" + p.getNom() + " " + p.getPrenom() + "-");
            }
            getFichePatient(p.getId(), tt1, p.getDateInscirption());
        }

        return v;
    }

    private void getFichePatient(final int idPAtient, final TextView txt, final String dateInt) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                Utils.LINK_WS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray = new JSONArray(response);

                    JSONObject jsonPrfile = new JSONObject(jsonArray.getJSONObject(0).getString("fiche_json"));

                    txt.setText(txt.getText() + " " + jsonPrfile.getString("ccmu"));

                    if (!jsonPrfile.getString("scroe").equals("")) {


                        int score = Integer.parseInt(jsonPrfile.getString("scroe"));

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


                        try {

                            Date date = formatter.parse(dateInt);
                            if (score >= 10) {
                                //2015-05-29 11:29:16
                                txt.setText(txt.getText() + " Entrée immediate");
                            } else if (inRange(score, 5, 9)) {
                                Date newDate = addMinutesToDate(15, date);

                                Calendar now = Calendar.getInstance();

                                now.setTime(newDate);

                                txt.setText(txt.getText() + (now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE)));
                            } else if (inRange(score, 5, 9)) {

                                Date newDate = addMinutesToDate(15, date);
                                Calendar now = Calendar.getInstance();

                                now.setTime(newDate);

                                txt.setText(txt.getText() + (now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE)));

                            } else if (inRange(score, 1, 4)) {

                                Date newDate = addMinutesToDate(30, date);
                                Calendar now = Calendar.getInstance();

                                now.setTime(newDate);

                                txt.setText(txt.getText() + (now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE)));
                            } else {
                                Date newDate = addMinutesToDate(60, date);
                                Calendar now = Calendar.getInstance();

                                now.setTime(newDate);

                                txt.setText(txt.getText() + (now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE)));
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", Utils.TAG_GET_FICHE_PATIENT);
                params.put("id", String.valueOf(idPAtient));

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private boolean inRange(int number, int min, int max) {

        return (number >= min && number <= max);
    }

    private static Date addMinutesToDate(int minutes, Date beforeTime) {
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
        return afterAddingMins;
    }
}