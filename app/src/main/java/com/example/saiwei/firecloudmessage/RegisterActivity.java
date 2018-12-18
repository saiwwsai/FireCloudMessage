package com.example.saiwei.firecloudmessage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameBox;
    private EditText emailBox;
    private EditText pwBox;
    private Button regiBt;
    private Button to_logginBt;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        emailBox = findViewById(R.id.email_rg);
        pwBox = findViewById(R.id.pw_rg);
        regiBt = findViewById(R.id.register);
        to_logginBt = findViewById(R.id.to_loggin);
        nameBox = findViewById(R.id.name);

        to_logginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        regiBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameBox.getText().toString();
                String email = emailBox.getText().toString();
                String pw = pwBox.getText().toString();

                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email)
                        &&!TextUtils.isEmpty(pw)){

                    mAuth.createUserWithEmailAndPassword(email, pw)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){

                                        String use_id = mAuth.getCurrentUser().getUid();
                                        mFirestore.collection("Users").document(use_id);

                                        Toast.makeText(RegisterActivity.this,
                                                "YOU REGISTERED!",Toast.LENGTH_SHORT).show();

                                        Intent introIntent = new Intent(RegisterActivity.this, Intro.class);
                                        startActivity(introIntent);

                                       // finish(); // go back to loggin

                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this,
                                                "Error: "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }

}
