package com.example.myapplication.faculty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FacultyActivity extends Activity {

    @BindView(R.id.leave_req_button)
    Button leaveRequestButton;

    @BindView(R.id.view_leave_resp_button)
    Button viewLeaveRespButton;

    @BindView(R.id.change_password_button)
    Button changePasswordButton;

    @BindView(R.id.leave_approval_button)
    Button leaveApprovalButton;

    @BindView(R.id.logout_button)
    Button logoutButton;

    SharedPreferences sharedpreferences;

    String userid,role;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);
        ButterKnife.bind(this);

        sharedpreferences = getApplicationContext().getSharedPreferences("College", Context.MODE_PRIVATE);
        userid = sharedpreferences.getString("userid", null);
        role = sharedpreferences.getString("role", null);

        if(role.equalsIgnoreCase("HOD")){
            leaveApprovalButton.setVisibility(View.VISIBLE);
        }else if(role.equalsIgnoreCase("Principal")){
            leaveApprovalButton.setVisibility(View.VISIBLE);
            leaveRequestButton.setVisibility(View.GONE);
            viewLeaveRespButton.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.leave_req_button)
    void onClickLeaveReq(){
        Intent intent=new Intent(this,LeaveRequest.class);
        startActivity(intent);
    }


    @OnClick(R.id.view_leave_resp_button)
    void onClickLeaveResp(){
        Intent intent=new Intent(this,ViewLeaveResponse.class);
        startActivity(intent);
    }

    @OnClick(R.id.change_password_button)
    void onClickChangePassword(){
        Intent intent=new Intent(this,ChangePassword.class);
        startActivity(intent);
    }

    @OnClick(R.id.leave_approval_button)
    void onClickLeaveApproval(){
        Intent intent=new Intent(this,LeaveApproval.class);
        startActivity(intent);
    }

    @OnClick(R.id.logout_button)
    void onLogout(){
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
