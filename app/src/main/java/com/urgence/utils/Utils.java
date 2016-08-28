package com.urgence.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.urgence.R;

/**
 * Created by SooheibSelmi
 */
public class Utils {
    public static final String LINK_WS = "http://192.168.1.156/android_login_api/index.php";
    public static final String TAG_STORE = "store";
    public static final String TAG_LIST = "list";
    public static final String TAG_SALLE_MED = "salle_med";
    public static final String TAG_CALL = "appeller";
    public static final String TAG_CALL_LIST_SALLE="list_pat_salle";
    public static final String TAG_UPDATE_SALLE_PATIENT="update_pat_salle";
    public static final String TAG_UPDATE_FICHE_PATIENT="fiche";


    public static final String TAG_GET_FICHE_PATIENT="get_fiche_patient";


    public static final String TAG_SORTIE="sortie";


    public static final String TAG_GET_ALL_USER="list_user";


    public static final String TAG_STORE_USER="store_user";

    public static final  String TAG_UPDATEDATA_SALLE="update_data_salle";

    public static final String AUTH_SALLE="get_salle_auth";
   public static int ACTION = -1;
    public static String ACTIONNAME ="";
    public static String ALLSALLEEPRMITTED ="";

    public static int SALLE_BOX1=0;

    public static int SALLE_BOX2=1;

    public static int SALLE_BOX3=2;

    public static int SALLE_BOX4=3;

    public static int SALLE_BOX5=4;

    public static int SALLE_REANIMATINO=5;

    public static int SALLE_SAUV=6;

    public static int SALLE_UHCD=7;

    public static int SALLE_SOINS=11;

    public static int SALLE_RADIO_CENTRALE=8;

    public static int SALLE_RADIOURGENCE=9;


    public static void addFragment(Fragment frag, Bundle bundle, String backstack,
                                   boolean forceReplace, FragmentManager fm, int res) {

        Fragment fragment = fm.findFragmentById(res);
        FragmentTransaction ft = fm.beginTransaction();

        ft.setCustomAnimations(R.anim.activity_open_translate, R.anim.activity_close_scale);
        if (bundle != null) {
            frag.setArguments(bundle);
        }
        if (fragment != null || forceReplace) {
            ft.replace(res, frag, backstack);
            ft.addToBackStack(backstack);


        } else {
            ft.add(res, frag, backstack);
            ft.addToBackStack(backstack);
        }

        ft.commitAllowingStateLoss();
    }








}
