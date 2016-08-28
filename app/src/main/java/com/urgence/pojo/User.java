package com.urgence.pojo;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by SooheibSelmi
 */
public class User {
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

    String grade;


    public static User initFromJson(JSONObject json) {
        User user = new User();


        try {
            if (json.has("id")) {

                user.setId(json.getInt("id"));

            }
            if (json.has("nom")) {

                user.setNom(json.getString("nom"));

            }
            if (json.has("prenom")) {

                user.setPrenom(json.getString("prenom"));

            }
            if (json.has("grade")) {

                user.setGrade(json.getString("grade"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;

    }
}
