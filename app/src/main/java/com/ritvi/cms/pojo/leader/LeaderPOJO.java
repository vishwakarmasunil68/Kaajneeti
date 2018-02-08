package com.ritvi.cms.pojo.leader;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 05-02-2018.
 */

public class LeaderPOJO {
    @SerializedName("user_id")
    String user_id;
    @SerializedName("user_fullname")
    String user_fullname;
    @SerializedName("user_image")
    String user_image;
    @SerializedName("user_created_on")
    String user_created_on;
    @SerializedName("user_username")
    String user_username;
    @SerializedName("my_favourite")
    String my_favourite;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    public void setUser_fullname(String user_fullname) {
        this.user_fullname = user_fullname;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_created_on() {
        return user_created_on;
    }

    public void setUser_created_on(String user_created_on) {
        this.user_created_on = user_created_on;
    }

    public String getUser_username() {
        return user_username;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public String getMy_favourite() {
        return my_favourite;
    }

    public void setMy_favourite(String my_favourite) {
        this.my_favourite = my_favourite;
    }
}
