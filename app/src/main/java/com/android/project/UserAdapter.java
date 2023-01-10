package com.android.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private Context context;
    private ArrayList userID, userEmail, userLogin, userPassword;

    public UserAdapter(Context context, ArrayList userID, ArrayList userEmail, ArrayList userLogin, ArrayList userPassword)
    {
        this.context = context;
        this.userID = userID;
        this.userEmail = userEmail;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
    }

    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, int position) {
        holder.userID.setText(String.valueOf(userID.get(position)));
        holder.userEmail.setText(String.valueOf(userEmail.get(position)));
        holder.userLogin.setText(String.valueOf(userLogin.get(position)));
        holder.userPassword.setText(String.valueOf(userPassword.get(position)));
    }

    @Override
    public int getItemCount() {
        return userID.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView userID, userEmail, userLogin, userPassword;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userID = itemView.findViewById(R.id.userID);
            userEmail = itemView.findViewById(R.id.userEmail);
            userLogin = itemView.findViewById(R.id.userLogin);
            userPassword = itemView.findViewById(R.id.userPassword);
        }
    }
}
