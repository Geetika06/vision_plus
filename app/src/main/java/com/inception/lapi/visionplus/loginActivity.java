package com.inception.lapi.visionplus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {
    TextToSpeech tto_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextToSpeech.OnInitListener listener = new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

            }
        };

        // creating object of TextToSpeech
        tto_s = new TextToSpeech(loginActivity.this, listener);
        tto_s.setSpeechRate(0);
        Handler h = new Handler();

          Runnable r = new Runnable() {
            @Override
            public void run() {

                tto_s.speak("press volume down key to skip login process" , TextToSpeech.QUEUE_ADD , null);
                Toast.makeText(loginActivity.this,"press volume down key to skip login process",Toast.LENGTH_SHORT).show();
            }
        };

          h.postDelayed(r,2000);

    }
    public boolean onKeyDown(int keyCode, KeyEvent event){

        if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){

            Intent i = new Intent(loginActivity.this, Module.class);
            startActivity(i);
            finish();
            new Handler().post(new Runnable() {

                                   @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                   @Override
                                   public void run() {
                                       tto_s.speak(" volume down key pressed" , TextToSpeech.QUEUE_ADD , null);
                                       tto_s.playSilentUtterance(1000,TextToSpeech.QUEUE_ADD,null);
                                       Toast.makeText(loginActivity.this, "Volume Down Pressed", Toast.LENGTH_SHORT).show();
                                   }
                               });


            return true;

        }

        else{
            return super.onKeyDown(keyCode , event);
        }

    }



    public void skip_1(View view) {
        Intent i = new Intent(loginActivity.this, Module.class);
        startActivity(i);
        finish();
    }
    public void sign_up(View view) {
        Intent i=new Intent(loginActivity.this,Signup.class);
        startActivity(i);

    }

    public void loginfun(View view) {
        EditText email_et=findViewById(R.id.email_et);
        EditText password_et=findViewById(R.id.password_et);
        String email = email_et.getText().toString();
        String password = password_et.getText().toString();
        final ProgressDialog progress_bar = new ProgressDialog(loginActivity.this);


        progress_bar.setTitle("Please Wait");

        progress_bar.setMessage("Logging in...");

        progress_bar.show();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progress_bar.hide();

                if(task.isSuccessful())
                {

                    Intent i = new Intent(loginActivity.this , Module.class);

                    startActivity(i);
                    finish();
                    new Handler().post(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void run() {
                            tto_s.speak("Login Successfully",TextToSpeech.QUEUE_ADD,null);
                            tto_s.playSilentUtterance(1000,TextToSpeech.QUEUE_ADD,null);
                            Toast.makeText(loginActivity.this,"Login Successfully",Toast.LENGTH_SHORT).show();

                        }
                    });



                    Toast.makeText(loginActivity.this , "Login Successfully" , Toast.LENGTH_SHORT).show();

                }

                else {
                    tto_s.speak(" Invalid Login" , TextToSpeech.QUEUE_ADD , null);

                    Toast.makeText(loginActivity.this , "invalid login" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        tto_s.stop();
    }
    }

