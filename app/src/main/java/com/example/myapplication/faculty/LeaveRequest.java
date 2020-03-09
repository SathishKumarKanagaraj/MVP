package com.example.myapplication.faculty;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.addRoles.AddRolesActivity;
import com.example.myapplication.addRoles.ResponseValues;
import com.example.myapplication.login.LoginUser;
import com.example.myapplication.server.IApiServices;
import com.example.myapplication.server.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaveRequest extends Activity {

    @BindView(R.id.role_spinner)
    Spinner hodListSpinner;

    @BindView(R.id.role_principal_spinner)
    Spinner principalSpinner;

    @BindView(R.id.select_role_layout_principal)
    LinearLayout selectRolePrincipal;

    @BindView(R.id.select_role_layout)
    LinearLayout selectRoleLayout;

    @BindView(R.id.reason_et)
    EditText reasonEditText;

    @BindView(R.id.date_et)
    EditText dateEditText;

    @BindView(R.id.leave_days_text)
    TextView leaveDaysText;

    @BindView(R.id.leave_type_spinner)
    Spinner leaveTypeSpinner;

    SharedPreferences sharedpreferences;

    String userid, role,uname;

    IApiServices iApiServices;

    List<LoginUser.Datum> hodList;

    String[] hodNameSpinnerItems, principalNameSpinnerItems;

    String[] hodIdSpinnerItems, principalIdSpinnerItems;

    String hodId, principalId;

    String reason, date;

    int leaveDays;

    String leaveType;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_request);

        ButterKnife.bind(this);

        sharedpreferences = getApplicationContext().getSharedPreferences("College", Context.MODE_PRIVATE);
        userid = sharedpreferences.getString("userid", null);
        uname = sharedpreferences.getString("name", null);
        role = sharedpreferences.getString("role", null);
        leaveDays = sharedpreferences.getInt("leave_days", 0);

        iApiServices = RestClient.getClient().create(IApiServices.class);

        leaveDaysText.setText(String.valueOf(leaveDays));

        if (role.equalsIgnoreCase("HOD")) {
            selectRolePrincipal.setVisibility(View.VISIBLE);
            selectRoleLayout.setVisibility(View.GONE);
            getPrincipalListApi();
        } else {
            getHodListApi();
        }

         setLeaveTypeSpinner();
    }

    private void getHodListApi() {
        Call<LoginUser> responseValuesCall = iApiServices.getHodList("hod_list", userid);
        responseValuesCall.enqueue(new Callback<LoginUser>() {
            @Override
            public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                LoginUser loginUser = response.body();
                hodList = loginUser.getData();
                setHodListSpinner();
            }

            @Override
            public void onFailure(Call<LoginUser> call, Throwable t) {

            }
        });
    }

    private void getPrincipalListApi() {
        Call<LoginUser> responseValuesCall = iApiServices.getHodList("principal_list", userid);
        responseValuesCall.enqueue(new Callback<LoginUser>() {
            @Override
            public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                LoginUser loginUser = response.body();
                hodList = loginUser.getData();
                setPrincipalSpinner();
            }

            @Override
            public void onFailure(Call<LoginUser> call, Throwable t) {

            }
        });
    }

    private void setLeaveTypeSpinner(){
        String[] leaveTypes={"CL","PL","SL"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                leaveTypes);
        leaveTypeSpinner.setAdapter(arrayAdapter);

        AdapterView.OnItemSelectedListener selectedListener=new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                leaveType=leaveTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        leaveTypeSpinner.setOnItemSelectedListener(selectedListener);
    }

    private void setHodListSpinner() {
        hodNameSpinnerItems = new String[hodList.size()];
        hodIdSpinnerItems = new String[hodList.size()];

        for (int i = 0; i < hodList.size(); i++) {
            hodNameSpinnerItems[i] = hodList.get(i).getName();
            hodIdSpinnerItems[i] = hodList.get(i).getId();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                hodNameSpinnerItems);
        hodListSpinner.setAdapter(arrayAdapter);

        AdapterView.OnItemSelectedListener selectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hodId = hodIdSpinnerItems[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        hodListSpinner.setOnItemSelectedListener(selectedListener);
    }

    private void setPrincipalSpinner() {
        principalNameSpinnerItems = new String[hodList.size()];
        principalIdSpinnerItems = new String[hodList.size()];

        for (int i = 0; i < hodList.size(); i++) {
            principalNameSpinnerItems[i] = hodList.get(i).getName();
            principalIdSpinnerItems[i] = hodList.get(i).getId();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                principalNameSpinnerItems);
        principalSpinner.setAdapter(arrayAdapter);

        AdapterView.OnItemSelectedListener selectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                principalId = principalIdSpinnerItems[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        principalSpinner.setOnItemSelectedListener(selectedListener);
    }

    @OnClick(R.id.submit_button)
    void submitClick() {
        reason = reasonEditText.getText().toString();
        date = dateEditText.getText().toString();
        if (role.equalsIgnoreCase("HOD")) {
            submitResponse(reason, date, userid, principalId,"Principal_Pending",leaveType,leaveDays);
        } else {
            submitResponse(reason, date, userid, hodId,"Pending",leaveType,leaveDays);
        }

    }

    private void submitResponse(String reason, String date, String userid,
                                String hodId,String status,String leaveType,int leaveDays) {
        JSONObject jsonObject = new JSONObject();

        final JSONArray jsonArray = new JSONArray();
        try {
            jsonObject.put("reason", reason);
            jsonObject.put("date", date);
            jsonObject.put("sender_id", userid);
            jsonObject.put("sender_name", uname);
            jsonObject.put("receiver_id", hodId);
            jsonObject.put("leave_days", leaveDays);
            jsonObject.put("leave_type", leaveType);
            jsonObject.put("status", status);
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String roleVal = jsonArray.toString();

        Call<ResponseValues> responseValuesCall = iApiServices.postLeaveReq("leave_request", roleVal);

        responseValuesCall.enqueue(new Callback<ResponseValues>() {
            @Override
            public void onResponse(Call<ResponseValues> call, Response<ResponseValues> response) {
                if (response.body().getSuccess().equalsIgnoreCase("true")) {
                    Toast.makeText(LeaveRequest.this, "Leave Requested", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LeaveRequest.this, "Failed to Add", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseValues> call, Throwable t) {

            }
        });
    }
}
