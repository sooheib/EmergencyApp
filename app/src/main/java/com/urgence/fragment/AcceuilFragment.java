


package com.urgence.fragment;

/**
 * Created by SooheibSelmi
 */
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.urgence.MainActivity;
import com.urgence.R;
import com.urgence.pojo.User;
import com.urgence.utils.Utils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcceuilFragment extends Fragment {
    private ProgressDialog pDialog;

    @InjectView(R.id.text_acceuil)
    TextView textAcceuil;

    public AcceuilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_acceuil, container, false);
        ButterKnife.inject(this, view);

        textAcceuil.setText(Utils.ACTIONNAME);

        MainActivity.SALLE = textAcceuil.getText().toString();
        //  getAllUser();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    ArrayList<User> listuser;


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
