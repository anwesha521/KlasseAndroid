package com.example.asus.klasseandroid;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity{

    EditText email;
    EditText pw;
    String type;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();


        setContentView(R.layout.activity_student_login);
        Intent i=getIntent();
        Bundle b=i.getExtras();
        if(b!=null)
        {
            type=b.getString("type");
        }

        Button login=findViewById(R.id.signin);
        email=findViewById(R.id.userId);
        pw=findViewById(R.id.password);
       // Log.i("anwesha",email.getText().toString());



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String em=email.getText().toString();


                final String pass=pw.getText().toString();
                /*Log.i("anwesha",em+pass);
                mAuth.createUserWithEmailAndPassword(em, pass).addOnCompleteListener(Login.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            String user_id = mAuth.getCurrentUser().getUid();
                            Log.i("anwesha",user_id);


                        }
                        else
                            Log.i("anwesha",task.getException().getMessage());
                    }
                });
*/
                mAuth.signInWithEmailAndPassword(em, pass)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (pass.length() < 6) {
                                        pw.setError("Minimum length=6");
                                    } else {

                                        Toast.makeText(Login.this, "Authentication failed", Toast.LENGTH_LONG).show();
                                      
                                    }
                                } else
                                {
                                    //to get user info from database


                                    if(type.equals("student"))
                                        startStudent();
                                    else
                                        startInstructor();
                                    finish();
                                }
                            }
                        });
            }
        });

            }

    public void startStudent()
    {
        Intent launch = new Intent(this, studentMain.class);
        startActivity(launch);
    }
    public void startInstructor()
    {
        Intent launch = new Intent(this, instructorMain.class);
        startActivity(launch);
    }
}