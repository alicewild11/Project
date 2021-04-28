package com.example.projectapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    EditText send_email;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Toolbar toolbar = findViewById(R.id.navigationbar);
        setSupportActionBar(toolbar);
        //setting toolbar title to Reset Password
        getSupportActionBar().setTitle("Reset Password");
        //will return to parent activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        send_email = findViewById(R.id.send_email);
        reset = findViewById(R.id.btn_reset);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = send_email.getText().toString();
                if (email.equals("")) {
                    Toast.makeText(ResetPassword.this, "Please Fill Email", Toast.LENGTH_SHORT).show();
                } else {
                    //sends a password reset to the email given in the edit text box
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //if task is successful display a view containing a message
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPassword.this, "Please check you Email", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ResetPassword.this, Login_Activity.class));
                            }
                            //if unsuccessful display a view containing error
                            else {
                                String error = task.getException().getMessage();
                                Toast.makeText(ResetPassword.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

        });
    }
}