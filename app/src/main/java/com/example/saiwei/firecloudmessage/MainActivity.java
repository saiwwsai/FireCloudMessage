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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Access a Cloud Firestore instance from your Activity

    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("sample/recordRing");
    private Button ringBt = null;
    private String TAG = "NOTIFICATION";
    public final String RING_MODE = "Button text";
    private TextView tv = null;


    // private TextView trigger;
    private AudioAttributes aa = null;
    private SoundPool soundPool = null;
    private int ringSound = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.aa = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_GAME).build();
        this.soundPool = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(aa).build();
        this.ringSound = this.soundPool.load(this, R.raw.alarm, 1);

    }

    public void button_clicked(View view){
        ringBt = findViewById(R.id.ring_bt);
        tv = findViewById(R.id.tv_test);

        ringBt.setText(R.string.ringing);

        // save in a map
        String buttonText = (String) ringBt.getText();

        Map<String, Object> dataToSave = new HashMap<String, Object>();
        dataToSave.put(RING_MODE,buttonText);


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

        // Get things from the cloud
        mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
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
    }







}
