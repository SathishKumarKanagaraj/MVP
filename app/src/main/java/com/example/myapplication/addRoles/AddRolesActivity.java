package com.example.myapplication.addRoles;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.R;
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

public class AddRolesActivity extends Activity implements AdapterView.OnItemSelectedListener {


    @BindView(R.id.role_spinner)
    Spinner roleSpinner;

    @BindView(R.id.user_name_et)
    EditText userNameEditText;

    @BindView(R.id.password_et)
    EditText passwordEditText;

    @BindView(R.id.leave_et)
    EditText leaveEditText;

    String[] spinnerItems = {"Principal", "HOD", "Faculty"};

    String roleSelected;

    IApiServices iApiServices;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roles);
        ButterKnife.bind(this);
        iApiServices = RestClient.getClient().create(IApiServices.class);

        roleSpinner.setOnItemSelectedListener(this);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        roleSpinner.setAdapter(arrayAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        roleSelected = spinnerItems[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @OnClick(R.id.submit_button)
    void onSubmit() {

        String name = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String leave=leaveEditText.getText().toString();

        submitResponse(name, password, roleSelected,leave);
    }


    private void submitResponse(String name, String password, String roleSelected,String leave) {

        JSONObject jsonObject = new JSONObject();

        final JSONArray jsonArray = new JSONArray();
        try {
            jsonObject.put("name", name);
            jsonObject.put("role", roleSelected);
            jsonObject.put("password", password);
            jsonObject.put("leave_days", leave);
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String roleVal = jsonArray.toString();

        Call<ResponseValues> responseValuesCall = iApiServices.postRoles("add_roles", roleVal);

        responseValuesCall.enqueue(new Callback<ResponseValues>() {
            @Override
            public void onResponse(Call<ResponseValues> call, Response<ResponseValues> response) {
                if (response.body().getSuccess().equalsIgnoreCase("true")) {
                    Toast.makeText(AddRolesActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AddRolesActivity.this, "Failed to Add", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseValues> call, Throwable t) {

            }
        });

    }
}
