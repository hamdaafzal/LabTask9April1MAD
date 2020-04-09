package com.example.labtask9april;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText emailET, paswwordET;
    private Button signup, signin;
    private ProgressBar bar;
    private FirebaseAuth objectFirebaseAuth;
    private TextView text, text_user;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        connectXML();
        objectFirebaseAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        objectFirebaseAuth = FirebaseAuth.getInstance();


    }
    private void connectXML() {
        try {
            emailET = findViewById(R.id.emailET);
            paswwordET = findViewById(R.id.passwordET);
            bar = findViewById(R.id.ProgressBar);
            signup = findViewById(R.id.SignUpButton);
            signin=findViewById(R.id.LogInButton);

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkIfUserExists();
                }
            });
            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sigIn();
                }
            });




        } catch (Exception ex) {
            Toast.makeText(this, "Connect To XML Error" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private  void sigIn(){


        objectFirebaseAuth.signInWithEmailAndPassword(emailET.getText().toString(), paswwordET.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "You are Logged In", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                            startActivity(intent);


                        } else {
                            Toast.makeText(MainActivity.this, "You are Failed To logIN", Toast.LENGTH_LONG).show();

                        }

                    }
                });}


    private  void signupUser(){

        try {

            if (!emailET.getText().toString().isEmpty() &&
                    !paswwordET.getText().toString().isEmpty()
            ){
                if(objectFirebaseAuth!=null){
                    bar.setVisibility(View.VISIBLE);
                    signup.setEnabled(false);

                    objectFirebaseAuth.createUserWithEmailAndPassword(emailET.getText().toString(),paswwordET.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            bar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this, "SuccessFullyCreated", Toast.LENGTH_SHORT).show();
                            if(authResult.getUser()!=null){
                                objectFirebaseAuth.signOut();
                                emailET.setText("");
                                paswwordET.setText("");

                                signup.setEnabled(true);
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            bar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this, "Failed to Add"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            signup.setEnabled(true);
                            emailET.requestFocus();
                            bar.setVisibility(View.INVISIBLE);
                        }
                    });

                }
            }
            else if(emailET.getText().toString().isEmpty()){


                bar.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Enter The Email", Toast.LENGTH_SHORT).show();
                emailET.requestFocus();
            }
            else if(paswwordET.getText().toString().isEmpty()){
                paswwordET.requestFocus();
                Toast.makeText(this, "Enter The Password", Toast.LENGTH_SHORT).show();
            }



        }catch (Exception e)
        {

            bar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "signUpUser"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void checkIfUserExists()
    {
        try
        {
            if(!emailET.getText().toString().isEmpty())
            {
                if(objectFirebaseAuth!=null)
                {
                    bar.setVisibility(View.VISIBLE);

                    objectFirebaseAuth.fetchSignInMethodsForEmail(emailET.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                    boolean check=task.getResult().getSignInMethods().isEmpty();
                                    if(!check)
                                    {

                                        bar.setVisibility(View.INVISIBLE);

                                        Toast.makeText(MainActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(check)
                                    {

                                        signupUser(); //Step 6
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    bar.setVisibility(View.INVISIBLE);

                                    Toast.makeText(MainActivity.this, "Fails to check if user exists:"
                                            +e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
            else
            {
                emailET.requestFocus();
                Toast.makeText(this, "Please enter the email", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {

            bar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "checkIfUserExists:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}


