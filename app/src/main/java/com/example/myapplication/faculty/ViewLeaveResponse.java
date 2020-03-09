package com.example.myapplication.faculty;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.login.LoginUser;
import com.example.myapplication.server.IApiServices;
import com.example.myapplication.server.RestClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewLeaveResponse extends Activity {

    @BindView(R.id.leave_recycler)
    RecyclerView leaveRecycler;

    ViewLeaveAdapter viewLeaveAdapter;

    List<LoginUser.Datum> attendanceList;

    IApiServices iApiServices;

    SharedPreferences sharedpreferences;

    String userid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_response);
        ButterKnife.bind(this);

        attendanceList=new ArrayList<>();

        sharedpreferences = getApplicationContext().getSharedPreferences("College", Context.MODE_PRIVATE);
        userid = sharedpreferences.getString("userid", null);

        iApiServices = RestClient.getClient().create(IApiServices.class);

        getViewLeaveApi();


    }

    private void getViewLeaveApi() {
        Call<LoginUser> responseValuesCall = iApiServices.getHodList("leave_request_list", userid);
        responseValuesCall.enqueue(new Callback<LoginUser>() {
            @Override
            public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                LoginUser loginUser = response.body();
                attendanceList = loginUser.getData();
                setAdapter();
            }

            @Override
            public void onFailure(Call<LoginUser> call, Throwable t) {

            }
        });
    }

    private void setAdapter() {
        if(attendanceList!=null){
            viewLeaveAdapter = new ViewLeaveAdapter(this, attendanceList);
            leaveRecycler.setHasFixedSize(true);
            leaveRecycler.setLayoutManager(new LinearLayoutManager(this));
            leaveRecycler.setAdapter(viewLeaveAdapter);
        }

    }
}
