package com.example.myapplication.faculty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.login.LoginUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewLeaveAdapter extends RecyclerView.Adapter<ViewLeaveAdapter.ViewLeaveHolder> {

    Context context;


    List<LoginUser.Datum> attendanceList;

    public ViewLeaveAdapter(Context context,List<LoginUser.Datum> attendanceList){
        this.context=context;
        this.attendanceList=attendanceList;
    }

    @NonNull
    @Override
    public ViewLeaveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_leaves,parent,false);
        return new ViewLeaveAdapter.ViewLeaveHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewLeaveHolder holder, int position) {
         holder.leaveText.setText("Your leave applied on "+attendanceList.get(position).date+" is "+attendanceList.get(position).status);
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public class ViewLeaveHolder extends RecyclerView.ViewHolder{

        TextView leaveText;

        public ViewLeaveHolder(@NonNull View itemView) {
            super(itemView);

          leaveText=itemView.findViewById(R.id.leave_text);

        }
    }
}
