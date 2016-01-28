package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cvlhyd on 26-01-2016.
 */
public class AdminLogin {

    @SerializedName("AdminLoginID")
    int AdminLoginID;

    @SerializedName("AdminPassword")
    String AdminPassword;

    public AdminLogin(int AdminLoginID, String AdminPassword ) {
        this.AdminLoginID = AdminLoginID;
        this.AdminPassword = AdminPassword;
    }
}
