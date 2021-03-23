package com.example.projectapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Objects;

public class Register_Activity extends AppCompatActivity {

    MaterialEditText username, email, password, block, flat, course;
    Button btn_register;

    FirebaseAuth auth;
    //To read or write data from the database, you need an instance of DatabaseReference
    //declare database reference
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

        Toolbar toolbar = findViewById(R.id.navigationbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //finding the view by the ID stated in xml file
        username = findViewById(R.id.username);
        email = findViewById(R.id.emailAddress);
        password = findViewById(R.id.password);
        block = findViewById(R.id.block);
        flat = findViewById(R.id.flat);
        course = findViewById(R.id.course);
        btn_register = findViewById(R.id.btn_register);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_username = username.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_block = Objects.requireNonNull(block.getText()).toString();
                String txt_flat = flat.getText().toString();
                String txt_course = course.getText().toString();

                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    //if any of the username, email or password are empty then display message ensuring all fields should be filled
                    Toast.makeText(Register_Activity.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 6 ){
                    //if the password is less than 6 characters display a message ensuring the password is a minimum of six characters
                    Toast.makeText(Register_Activity.this, "Password Must Be A Minimum of Six Characters", Toast.LENGTH_SHORT).show();
                } else {
                    //if other statements are not true then store following fields.
                    register(txt_username, txt_email, txt_password, txt_block, txt_flat, txt_course);
                }
            }
        });



    }

    private void register(final String username, String email, String password, String block, String flat, String course) {

        //Create a new account by passing the new user's email
        // address and password to createUserWithEmailAndPassword:
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            database = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username);
                            hashMap.put("imageURL", "default");
                            hashMap.put("block", block);
                            hashMap.put("flat", flat);
                            hashMap.put("course", course);
                            hashMap.put("search", username.toLowerCase());

                            database.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(Register_Activity.this, Main_Activity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(Register_Activity.this, "You can't Register with this Email or Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}