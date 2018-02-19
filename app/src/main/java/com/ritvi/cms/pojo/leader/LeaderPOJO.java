package com.ritvi.cms.pojo.leader;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 05-02-2018.
 */

public class LeaderPOJO {
    @SerializedName("up_user_profile_id")
    String up_user_profile_id;
    @SerializedName("up_user_role")
    String up_user_role;
    @SerializedName("up_first_name")
    String up_first_name;
    @SerializedName("up_middle_name")
    String up_middle_name;
    @SerializedName("up_last_name")
    String up_last_name;
    @SerializedName("up_created_on")
    String up_created_on;
    @SerializedName("up_status")
    String up_status;
    @SerializedName("up_image")
    String up_image;
    @SerializedName("up_mobile")
    String up_mobile;
    @SerializedName("up_alt_mobile")
    String up_alt_mobile;
    @SerializedName("my_favourite")
    String my_favourite;


    public String getUp_user_profile_id() {
        return up_user_profile_id;
    }

    public void setUp_user_profile_id(String up_user_profile_id) {
        this.up_user_profile_id = up_user_profile_id;
    }

    public String getUp_user_role() {
        return up_user_role;
    }

    public void setUp_user_role(String up_user_role) {
        this.up_user_role = up_user_role;
    }

    public String getUp_first_name() {
        return up_first_name;
    }

    public void setUp_first_name(String up_first_name) {
        this.up_first_name = up_first_name;
    }

    public String getUp_middle_name() {
        return up_middle_name;
    }

    public void setUp_middle_name(String up_middle_name) {
        this.up_middle_name = up_middle_name;
    }

    public String getUp_last_name() {
        return up_last_name;
    }

    public void setUp_last_name(String up_last_name) {
        this.up_last_name = up_last_name;
    }

    public String getUp_created_on() {
        return up_created_on;
    }

    public void setUp_created_on(String up_created_on) {
        this.up_created_on = up_created_on;
    }

    public String getUp_status() {
        return up_status;
    }

    public void setUp_status(String up_status) {
        this.up_status = up_status;
    }

    public String getUp_image() {
        return up_image;
    }

    public void setUp_image(String up_image) {
        this.up_image = up_image;
    }

    public String getUp_mobile() {
        return up_mobile;
    }

    public void setUp_mobile(String up_mobile) {
        this.up_mobile = up_mobile;
    }

    public String getUp_alt_mobile() {
        return up_alt_mobile;
    }

    public void setUp_alt_mobile(String up_alt_mobile) {
        this.up_alt_mobile = up_alt_mobile;
    }

    public String getMy_favourite() {
        return my_favourite;
    }

    public void setMy_favourite(String my_favourite) {
        this.my_favourite = my_favourite;
    }
}
