package com.urgence.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by SooheibSelmi
 */

public class Salle implements Parcelable {

    public static Salle initFromJsonObject(JSONObject json) {
        Salle salle = new Salle();


        try {
            if (json.has("id")) {
                salle.setId(json.getInt("id"));
            }
            if (json.has("nomsalle")) {
                salle.setNomsalle(json.getString("nomsalle"));
            }
            if (json.has("descriptionsalle")) {
                salle.setDescriptionsalle(json.getString("descriptionsalle"));

            }
            if (json.has("code_salle")) {

                salle.setCodesalle(json.getInt("code_salle"));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return salle;

    }


    @Getter
    @Setter
    int id;
    @Getter
    @Setter
    String nomsalle;
    @Getter
    @Setter
    String descriptionsalle;
    @Getter
    @Setter
    int id_responsable;
    @Getter
    @Setter
    int codesalle;

    public Salle() {
    }

    public Salle(int id, String nomsalle, String descriptionsalle, int id_responsable, int codeSalleF) {
        this.id = id;
        this.nomsalle = nomsalle;
        this.descriptionsalle = descriptionsalle;
        this.id_responsable = id_responsable;
        this.codesalle = codeSalleF;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nomsalle);
        dest.writeString(this.descriptionsalle);
        dest.writeInt(this.id_responsable);
    }

    private Salle(Parcel in) {
        this.id = in.readInt();
        this.nomsalle = in.readString();
        this.descriptionsalle = in.readString();
        this.id_responsable = in.readInt();
    }

    public static final Parcelable.Creator<Salle> CREATOR = new Parcelable.Creator<Salle>() {
        public Salle createFromParcel(Parcel source) {
            return new Salle(source);
        }

        public Salle[] newArray(int size) {
            return new Salle[size];
        }
    };
}
