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

public class Patient implements Parcelable {
    public Patient(int id, String nom, String prenom, String dns, String pathimage, String sexe, String tel, String typecarnet, String dateInscirption, int _salle) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dns = dns;
        this.pathimage = pathimage;
        this.sexe = sexe;
        this.tel = tel;
        this.typecarnet = typecarnet;
        this.dateInscirption = dateInscirption;
        this.salle = _salle;


    }


    public Patient() {

    }


    public static Patient initFromJson(JSONObject json) {
        Patient patiens = new Patient();

        try {
            if (json.has("id")) {
                patiens.setId(json.getInt("id"));
            }

            if (json.has("nom")) {
                patiens.setNom(json.getString("nom"));
            }
            if (json.has("prenom")) {
                patiens.setPrenom(json.getString("prenom"));
            }
            if (json.has("dns")) {
                patiens.setDns(json.getString("dns"));
            }
            if (json.has("pathimage")) {
                patiens.setPathimage(json.getString("pathimage"));
            }

            if (json.has("sexe")) {
                patiens.setSexe(json.getString("sexe"));
            }

            if (json.has("tel")) {
                patiens.setTel(json.getString("tel"));
            }


            if (json.has("typecarnet")) {
                patiens.setTypecarnet(json.getString("typecarnet"));
            }

            if (json.has("dateInscirption")) {
                patiens.setDateInscirption(json.getString("dateInscirption"));
            }

            if (json.has("salle")) {
                patiens.setSalle(json.getInt("salle"));
            }

            if (json.has("gravity")) {
                patiens.setGravty(json.getString("gravity"));
            }

            if (json.has("delai")) {
                patiens.setDelai(json.getString("delai"));
            }

            if (json.has("profession")) {
                patiens.setProfession(json.getString("profession"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return patiens;
    }


    @Getter
    @Setter
    int id;
    @Getter
    @Setter
    String nom;
    @Getter
    @Setter
    String prenom;
    @Getter
    @Setter
    String dns;
    @Getter
    @Setter
    String pathimage;
    @Getter
    @Setter
    String sexe;
    @Getter
    @Setter
    String tel;
    @Getter
    @Setter
    String typecarnet;
    @Getter
    @Setter
    String dateInscirption;
    @Getter
    @Setter
    int salle;
    @Getter
    @Setter
    String delai;
    @Getter
    @Setter
    String gravty;
    @Getter
    @Setter
    String profession;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nom);
        dest.writeString(this.prenom);
        dest.writeString(this.dns);
        dest.writeString(this.pathimage);
        dest.writeString(this.sexe);
        dest.writeString(this.tel);
        dest.writeString(this.typecarnet);
        dest.writeString(this.dateInscirption);
        dest.writeInt(this.salle);
        dest.writeString(this.delai);
        dest.writeString(this.gravty);
        dest.writeString(this.profession);
    }

    private Patient(Parcel in) {
        this.id = in.readInt();
        this.nom = in.readString();
        this.prenom = in.readString();
        this.dns = in.readString();
        this.pathimage = in.readString();
        this.sexe = in.readString();
        this.tel = in.readString();
        this.typecarnet = in.readString();
        this.dateInscirption = in.readString();
        this.salle = in.readInt();
        this.delai = in.readString();
        this.gravty = in.readString();
        this.profession = in.readString();
    }

    public static final Creator<Patient> CREATOR = new Creator<Patient>() {
        public Patient createFromParcel(Parcel source) {
            return new Patient(source);
        }

        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };
}
