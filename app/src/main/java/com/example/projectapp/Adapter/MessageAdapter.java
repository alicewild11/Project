package com.example.projectapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectapp.Model.Chat;
import com.example.projectapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    //assign 0 to variable left
    public static  final int LEFT = 0;
    //assign 1 to variable right
    public static  final int RIGHT = 1;

    private Context Context;
    private List<Chat> Chat;
    private String imageurl;

    FirebaseUser fuser;

    public MessageAdapter(Context Context, List<Chat> Chat, String imageurl) {
        this.Chat = Chat;
        this.Context = Context;
        this.imageurl = imageurl;

    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == RIGHT) {
            //if the view type right set the layout to chat right
            View view = LayoutInflater.from(Context).inflate(R.layout.chatright, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else
        {
            //if the view type else set the layout to left
            View view = LayoutInflater.from(Context).inflate(R.layout.chatleft, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {

        Chat chat = Chat.get(position);

        holder.message.setText(chat.getMessage());

        if (imageurl.equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(Context).load(imageurl).into(holder.profile_image);
        }

        if (position == Chat.size()-1){
            if (chat.isIsseen()){
                //set text to seen
                holder.txt_seen.setText("Seen");
            } else {
                //set text to delivered
                holder.txt_seen.setText("Delivered");
            }
        } else {
            holder.txt_seen.setVisibility(View.GONE);
        }
    }

    //get the amount of elements in the array list
    @Override
    public int getItemCount() {

        return Chat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView message;
        public ImageView profile_image;
        public TextView txt_seen;

        public ViewHolder (View itemView){
            super(itemView);

            message = itemView.findViewById(R.id.message);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_seen = itemView.findViewById(R.id.txt_seen);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (Chat.get(position).getSender().equals(fuser.getUid())){
            return RIGHT;
        } else {
            return LEFT;
        }
    }

}