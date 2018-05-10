package com.inception.lapi.visionplus;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    // global object of TextToSpeech class
    TextToSpeech tto_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // creating OnOnitListener interface
        TextToSpeech.OnInitListener listener = new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status)
            {

            }
        };

        // creating object of TextToSpeech
        tto_s = new TextToSpeech(MainActivity.this , listener);


        final Handler h = new Handler();

       final  Runnable r = new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(MainActivity.this,loginActivity.class);
                startActivity(i);
                finish();

            }
        };

        final Runnable r1 = new Runnable() {
            @Override
            public void run() {


                tto_s.speak("welcome to vision plus" , TextToSpeech.QUEUE_FLUSH , null);

                h.postDelayed(r , 2000);

            }
        };

        h.postDelayed(r1, 4000);


    }

}
