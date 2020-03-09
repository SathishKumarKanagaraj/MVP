package com.example.myapplication.faculty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.addRoles.ResponseValues;
import com.example.myapplication.login.LoginUser;
import com.example.myapplication.server.IApiServices;
import com.example.myapplication.server.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewLeaveApprovalAdapter extends RecyclerView.Adapter<ViewLeaveApprovalAdapter.ViewLeaveHolder> {

    Context context;


    List<LoginUser.Datum> attendanceList;

    IApiServices iApiServices;

    String role;

    public ViewLeaveApprovalAdapter(Context context, List<LoginUser.Datum> attendanceList, String role) {
        this.context = context;
        this.attendanceList = attendanceList;
        this.role = role;
        iApiServices = RestClient.getClient().create(IApiServices.class);
    }

    @NonNull
    @Override
    public ViewLeaveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_leaves_approval, parent, false);
        return new ViewLeaveApprovalAdapter.ViewLeaveHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewLeaveHolder holder, int position) {
        if (role.equalsIgnoreCase("HOD")) {
            holder.leaveText.setText(attendanceList.get(position).getName() + " applied leave on " +
                    attendanceList.get(position).date + " for the reason " + attendanceList.get(position).getReason());
        } else {
            holder.leaveText.setText(attendanceList.get(position).getName() + " applied leave on " +
                    attendanceList.get(position).date + "for the reason " + attendanceList.get(position).getReason());
        }

        holder.leaveTypeText.setText("Leave Type:"+attendanceList.get(position).getLeaveType());
        holder.noOfDaysLeave.setText("Leave Left:"+attendanceList.get(position).getLeaveDays());

        holder.approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(role.equalsIgnoreCase("HOD")){
                    approvalApiCall("Principal_Pending", attendanceList.get(position).getId(),position);
                }else {
                    approvalApiCall("Approved", attendanceList.get(position).getId(),position);
                }
            }
        });

        holder.rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approvalApiCall("Rejected", attendanceList.get(position).getId(),position);
            }
        });
    }

    private void approvalApiCall(String status, String id,int position) {
        JSONObject jsonObject = new JSONObject();

        final JSONArray jsonArray = new JSONArray();
        try {
            jsonObject.put("id", id);
            jsonObject.put("status", status);
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String roleVal = jsonArray.toString();

        Call<ResponseValues> responseValuesCall = iApiServices.postLeaveReq("leave_approval", roleVal);

        responseValuesCall.enqueue(new Callback<ResponseValues>() {
            @Override
            public void onResponse(Call<ResponseValues> call, Response<ResponseValues> response) {
                if (response.body().getSuccess().equalsIgnoreCase("true")) {
                    Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    userTableApiCall(attendanceList.get(position).getSenderId(),attendanceList.get(position).getLeaveDays()-1);
                } else {
                    Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseValues> call, Throwable t) {

            }
        });
    }

    private void userTableApiCall(int senderId, int leaveDays) {
        JSONObject jsonObject = new JSONObject();

        final JSONArray jsonArray = new JSONArray();
        try {
            jsonObject.put("id", senderId);
            jsonObject.put("leave_days", leaveDays);
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String roleVal = jsonArray.toString();

        Call<ResponseValues> responseValuesCall = iApiServices.postLeaveReq("leave_approval_users", roleVal);

        responseValuesCall.enqueue(new Callback<ResponseValues>() {
            @Override
            public void onResponse(Call<ResponseValues> call, Response<ResponseValues> response) {
                if (response.body().getSuccess().equalsIgnoreCase("true")) {
                    Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseValues> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public class ViewLeaveHolder extends RecyclerView.ViewHolder {

        TextView leaveText;

        Button approveButton;

        Button rejectButton;

        TextView leaveTypeText;

        TextView noOfDaysLeave;

        public ViewLeaveHolder(@NonNull View itemView) {
            super(itemView);

            leaveText = itemView.findViewById(R.id.leave_text);
            approveButton = itemView.findViewById(R.id.approve_button);
            rejectButton = itemView.findViewById(R.id.reject_button);
            leaveTypeText=itemView.findViewById(R.id.leave_type_text);
            noOfDaysLeave=itemView.findViewById(R.id.no_of_days);
        }
    }
}
