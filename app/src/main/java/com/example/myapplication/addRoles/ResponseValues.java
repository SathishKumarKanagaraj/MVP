package com.example.myapplication.addRoles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseValues {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private String data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
