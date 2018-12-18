package com.example.saiwei.firecloudmessage;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RingActivity extends AppCompatActivity {

   // private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("sample/recordRing");
    private DocumentReference mDocRef;
    private Button ringBt = null;
    private String TAG = "NOTIFICATION";
    public final String RING_REQUEST = "Ring Request";
    private TextView tv = null;

    private String mUserId;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;



    // private TextView trigger;
    private AudioAttributes aa = null;
    private SoundPool soundPool = null;
    private int ringSound = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);

        ringBt = findViewById(R.id.ring_bt);
        tv = findViewById(R.id.tv_test);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mUserId = mAuth.getCurrentUser().getUid();
        //getIntent().getStringExtra("user_id");

      //  mDocRef = FirebaseFirestore.getInstance().document("Users/" + mUserId + "/Ring Requests");


    }

    public void button_clicked(View view){

        // save in a map
  /*      String buttonText = (String) ringBt.getText();
        Map<String, Object> dataToSave = new HashMap<String, Object>();
        dataToSave.put(RING_REQUEST,buttonText); */

        String request = "Now Ring";
        Map<String, Object> dataToSave = new HashMap<String, Object>();
        dataToSave.put(RING_REQUEST,request);


        mFirestore.collection("Users/" + mUserId + "/Ring Request")
                .add(dataToSave).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG,"Text on ring button has been saved");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Text on ring button NOT save");
            }
        });


/*
        // save to the cloud
        mDocRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG,"Text on ring button has been saved");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Text on ring button NOT save");
            }
        });
/*
        // Get things from the cloud
        mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                    // todo two phone both ring or neither ring

                if(documentSnapshot.exists()){
                    String ringStatus = documentSnapshot.getString(RING_MODE); //RINGING
                    if (ringStatus.equals("RINGING")){
                        tv.setText("THE ALARM IS GOING ON");
                        soundPool.play(ringSound, 1, 1, 1, 5, 1f);
                    }
                }
                else{
                    Log.d(TAG,"YOU FAILED");
                }
            }
        });
*/
    }
}
