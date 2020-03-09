package com.example.myapplication.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginUser {


    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("data")
    @Expose
    public List<Datum> data = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }


    public class Datum {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("role")
        @Expose
        public String role;

        @SerializedName("date")
        @Expose
        public String date;

        @SerializedName("reason")
        @Expose
        public String reason;

        @SerializedName("status")
        @Expose
        public  String status;

        @SerializedName("leave_days")
        @Expose
        public  int leaveDays;

        @SerializedName("leave_type")
        @Expose
        public  String leaveType;

        @SerializedName("sender_id")
        @Expose
        public int senderId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRole() {
            return role;
        }

        public String getDate() {
            return date;
        }

        public String getStatus() {
            return status;
        }

        public String getReason() {
            return reason;
        }

        public int getLeaveDays() {
            return leaveDays;
        }

        public String getLeaveType() {
            return leaveType;
        }

        public int getSenderId() {
            return senderId;
        }
    }
}
