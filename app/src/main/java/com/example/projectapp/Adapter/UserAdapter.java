package com.example.projectapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectapp.Message_Activity;
import com.example.projectapp.Model.User;
import com.example.projectapp.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context Context;
    private List<User> Users;


    public UserAdapter(Context Context, List<User> Users, boolean b) {
        this.Users = Users;
        this.Context = Context;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(Context).inflate(R.layout.userlist, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final User user = Users.get(position);
        holder.username.setText(user.getUsername());
        holder.block.setText(user.getBlock());
        holder.course.setText(user.getCourse());
        if (user.getImageURL().equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(Context).load(user.getImageURL()).into(holder.profile_image);
        }

        //on click open meessage activity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Context, Message_Activity.class);
                intent.putExtra("userid", user.getId());
                Context.startActivity(intent);
            }
        });

    }

    //get number of elements of in the array list
    @Override
    public int getItemCount() {

        return Users.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public TextView block;
        public TextView flat;
        public TextView course;
        public ImageView profile_image;

        public ViewHolder (View itemView){
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            block = itemView.findViewById(R.id.block);
            flat = itemView.findViewById(R.id.flat);
            course = itemView.findViewById(R.id.course);

        }

    }



}