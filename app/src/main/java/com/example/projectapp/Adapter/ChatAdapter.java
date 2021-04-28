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
import com.example.projectapp.Model.Chat;
import com.example.projectapp.Model.User;
import com.example.projectapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context Context;
    private List<User> Users;

    String theLastMessage;


    public ChatAdapter(Context Context, List<User> Users, boolean b) {
        this.Users = Users;
        this.Context = Context;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(Context).inflate(R.layout.chatlist, parent, false);
        return new ChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final User user = Users.get(position);
        holder.username.setText(user.getUsername());
        lastMessage(user.getId(), holder.last_message);
        if (user.getImageURL().equals("default")){
            //if default set image
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {
            //if not load user image url from database into profile image
            Glide.with(Context).load(user.getImageURL()).into(holder.profile_image);
        }

        //on item view click open message activity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Context, Message_Activity.class);
                intent.putExtra("userid", user.getId());
                Context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {

        //find out how many elements in the array list
        return Users.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView profile_image;
        private TextView last_message;

        public ViewHolder (View itemView){
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            last_message = itemView.findViewById(R.id.last_message);

        }

    }

    //check for last message
    private void lastMessage(final String userid, final TextView last_msg){
        theLastMessage = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (firebaseUser != null && chat != null) {
                        if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
                                chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())) {
                            //store the message in the last message variable
                            theLastMessage = chat.getMessage();
                        }
                    }
                }

                if ("default".equals(theLastMessage)) {
                    //if the last message is default set text as no message
                    last_msg.setText("No Message");
                } else {
                    //else set text to the message stored in the last message variable
                    last_msg.setText(theLastMessage);
                }
                //store variable to default
                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

