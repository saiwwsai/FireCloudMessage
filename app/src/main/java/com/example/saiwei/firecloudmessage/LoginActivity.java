package com.example.saiwei.firecloudmessage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {


    private EditText emailBox;
    private EditText pwBox;
    private Button loginBt;
    private Button to_regiBt;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        emailBox = findViewById(R.id.email_lg);
        pwBox = findViewById(R.id.pw_lg);
        loginBt = findViewById(R.id.login);
        to_regiBt = findViewById(R.id.to_regi);

        to_regiBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regiIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(regiIntent);
            }
        });

        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailBox.getText().toString();
                String pw = pwBox.getText().toString();


                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pw)) {

                    mAuth.signInWithEmailAndPassword(email,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                mAuth.getCurrentUser().getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                                    @Override
                                    public void onSuccess(GetTokenResult getTokenResult) {
                                        String token_id = getTokenResult.getToken();
                                        String current_id = mAuth.getCurrentUser().getUid();

                                        Map<String, Object> tokenMap = new HashMap<>();
                                        tokenMap.put("token_id", token_id);

                                        mFirestore.collection("Users").document(current_id).set(tokenMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                Log.d(TAG,"Token saved");
                                                sendToMain();
                                            }


                                        });

                                    }
                                });
                            }

                            }
                    });


                }
                }
            });
        }

        public void sendToMain(){

        }

}

