package com.example.saiwei.firecloudmessage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Intro extends AppCompatActivity {

    private Button helper;
    private Button fun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        helper = findViewById(R.id.help);
        fun = findViewById(R.id.fun);

        helper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ringIntent = new Intent(Intro.this, RingActivity.class);
                startActivity(ringIntent);
            }
        });
    }



}
