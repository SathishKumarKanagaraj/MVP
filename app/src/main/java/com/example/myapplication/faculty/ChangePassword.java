package com.example.myapplication.faculty;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.addRoles.ResponseValues;
import com.example.myapplication.server.IApiServices;
import com.example.myapplication.server.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends Activity {

    @BindView(R.id.new_pass_et)
    EditText newPassEditText;

    @BindView(R.id.password_et)
    EditText passEditText;

    @BindView(R.id.login_button)
    Button submitButton;

    String newPassword,password;

    SharedPreferences sharedpreferences;

    String userid;

    IApiServices iApiServices;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);

        sharedpreferences = getApplicationContext().getSharedPreferences("College", Context.MODE_PRIVATE);
        userid = sharedpreferences.getString("userid", null);

        iApiServices = RestClient.getClient().create(IApiServices.class);

    }

    @OnClick(R.id.login_button)
    void onSubmit(){
        newPassword=newPassEditText.getText().toString();
        password=passEditText.getText().toString();
        if(newPassword.equalsIgnoreCase(password)){
            submitValues(password);
        }
        else {
            Toast.makeText(ChangePassword.this,"Password doesnt matches",Toast.LENGTH_SHORT).show();
        }

    }

    private void submitValues(String newPassword){
        JSONObject jsonObject = new JSONObject();

        final JSONArray jsonArray = new JSONArray();
        try {
            jsonObject.put("id", userid);
            jsonObject.put("password", newPassword);
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String roleVal = jsonArray.toString();

        Call<ResponseValues> responseValuesCall = iApiServices.changePassword("change_password", roleVal);

        responseValuesCall.enqueue(new Callback<ResponseValues>() {
            @Override
            public void onResponse(Call<ResponseValues> call, Response<ResponseValues> response) {
                if (response.body().getSuccess().equalsIgnoreCase("true")) {
                    Toast.makeText(ChangePassword.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ChangePassword.this, "Password not Updated", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseValues> call, Throwable t) {

            }
        });
    }
}
