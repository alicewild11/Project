package com.example.projectapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.Adapter.ChatAdapter;
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

import java.util.ArrayList;
import java.util.List;


public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;

    private ChatAdapter chatAdapter;
    private List<User> users;

    FirebaseUser fuser;
    DatabaseReference reference;


    private List<String> chatList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        //create an array list called userList
        chatList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //used to marshall the data contained in this snapshot into chat class
                    Chat chat = snapshot.getValue(Chat.class);
                    assert chat != null;
                    //if the get sender is equal to the fusers id
                    if (chat.getSender().equals(fuser.getUid())) {
                        //add the reciever to the userList array
                        chatList.add(chat.getReceiver());
                    }
                    //if the get reciever is equal to the fuser id
                    if (chat.getReceiver().equals(fuser.getUid())) {
                        //add the sender to the userList array
                        chatList.add(chat.getSender());
                    }
                }

                //call readChats method
                readChats();

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;


    }


    private void readChats() {


        //create an array list called users
        users = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //used to marshall the data contained in this snapshot into user class
                    User user = snapshot.getValue(User.class);

                    assert user != null;
                    assert fuser != null;
                    if (!user.getId().equals(fuser.getUid())) {
                        users.add(user);
                    }
                }

                chatAdapter = new ChatAdapter(getContext(), users, false);
                recyclerView.setAdapter(chatAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
