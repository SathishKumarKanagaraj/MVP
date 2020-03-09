package com.example.myapplication.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.addRoles.AddRolesActivity;
import com.example.myapplication.faculty.FacultyActivity;
import com.example.myapplication.server.IApiServices;
import com.example.myapplication.server.RestClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    @BindView(R.id.user_name_et)
    EditText userNameEditText;

    @BindView(R.id.password_et)
    EditText passwordEditText;

    IApiServices iApiServices;

    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        iApiServices = RestClient.getClient().create(IApiServices.class);
        sharedpreferences = getSharedPreferences("College", Context.MODE_PRIVATE);

    }

    @OnClick(R.id.login_button)
    void onLogin() {
        String name = userNameEditText.getText().toString();
        String pass = passwordEditText.getText().toString();
        login(name, pass);
    }


    private void login(String name, String pass) {
        Call<LoginUser> userCall = iApiServices.postLoginReq("login", name, pass);
        userCall.enqueue(new Callback<LoginUser>() {
            @Override
            public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                LoginUser loginUser = response.body();
                String message = loginUser.getSuccess();
                String userid;
                String role="";
                String name;
                int leaveDays=0;
                if (message.equals("false")) {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                } else {
                    List<LoginUser.Datum> data = loginUser.data;

                    for (LoginUser.Datum datum : data) {
                        userid = datum.getId();
                        role = datum.getRole();
                        name = datum.getName();
                        leaveDays = datum.getLeaveDays();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("userid", userid);
                        editor.putString("name", name);
                        editor.putString("role",role);
                        editor.putInt("leave_days",leaveDays);
                        editor.commit();
                    }
                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();

                    if(role.equalsIgnoreCase("admin")){
                        Intent intent=new Intent(LoginActivity.this, AddRolesActivity.class);
                        startActivity(intent);
                    }else{
                        Intent intent=new Intent(LoginActivity.this, FacultyActivity.class);
                        startActivity(intent);
                    }


                }
            }

            @Override
            public void onFailure(Call<LoginUser> call, Throwable t) {

            }
        });
    }
}
