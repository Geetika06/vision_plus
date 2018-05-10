package com.inception.lapi.visionplus;

import android.app.ProgressDialog;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class Signup extends AppCompatActivity {
    TextView show_password ,confirm_show;
    TextToSpeech tto_s;
    EditText firstname, lastname, mail, pswd, confirm;
    String name, lname, email, password, confirmpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        pswd =  findViewById(R.id.pswd);

        show_password=findViewById(R.id.show_password);
        show_password.setVisibility(View.GONE);


        pswd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (pswd.getText().length()>0){
                    show_password.setVisibility(View.VISIBLE);
                }
                else {
                    show_password.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        show_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (show_password.getText()=="Show"){
                    show_password.setText("Hide");
                    pswd.setTransformationMethod(null);

                    pswd.setSelection(pswd.length());
                }
                else{
                    show_password.setText("Show");

                   pswd.setTransformationMethod(new PasswordTransformationMethod());


                   pswd.setSelection(pswd.length());


                }

            }
        });
        confirm=findViewById(R.id.confirm);
        confirm_show=findViewById(R.id.confirm_pass);
        confirm_show.setVisibility(View.GONE);
        confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (confirm.getText().length()>0){
                    confirm_show.setVisibility(View.VISIBLE);
                }
                else {
                    confirm_show.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        confirm_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (confirm_show.getText()=="Show"){
                    confirm_show.setText("Hide");
                    confirm.setTransformationMethod(null);

                    confirm.setSelection(confirm.length());
                }
                else{
                    confirm_show.setText("Show");

                    confirm.setTransformationMethod(new PasswordTransformationMethod());


                    confirm.setSelection(confirm.length());


                }

            }
        });

        TextToSpeech.OnInitListener listener = new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

            }
        };

        // creating object of TextToSpeech
        tto_s = new TextToSpeech(Signup.this, listener);
        tto_s.setSpeechRate(0);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        mail = (EditText) findViewById(R.id.email);
        pswd = (EditText) findViewById(R.id.pswd);
        confirm = (EditText) findViewById(R.id.confirm);
    }

    public void submit(View view) {


        name = firstname.getText().toString();

        if (name.length() <3) {
            firstname.setError("name must be of minimum 3 characters");
            return;
        }

        lname = lastname.getText().toString();

        if (lname.length() <4) {
            lastname.setError("name must be of minimum 4 characters");
            return;
        }


        email = mail.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mail.setError("please enter email");

            return;
        }


        password = pswd.getText().toString();

        if (password.length() <8) {
            pswd.setError("password should contain atleast 8 characters");
            return;
        }

        confirmpass = confirm.getText().toString();

        if (!password.equals(confirmpass))

        {

            confirm.setError("password and confirm password do  not match");

            return;
        }
        final ProgressDialog progress_bar = new ProgressDialog(Signup.this);


        progress_bar.setTitle("Please Wait");

        progress_bar.setMessage("Creating account...");

        progress_bar.show();

        FirebaseAuth f_auth = FirebaseAuth.getInstance();

        OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progress_bar.hide();

                if (task.isSuccessful()) {

                    ProfileData data = new ProfileData(name, lname,email);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    database.getReference().child("user").child(email.replace(".","")).setValue(data);
                    tto_s.speak("Account created", TextToSpeech.QUEUE_FLUSH , null);
                    Toast.makeText(Signup.this, "Account Created ", Toast.LENGTH_SHORT).show();
                }
                else {
                    tto_s.speak("email already exists", TextToSpeech.QUEUE_FLUSH , null);
                    Toast.makeText(Signup.this, "email already exists", Toast.LENGTH_SHORT).show();
                    return;
                }

            }

        };
        f_auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }
}