package com.example.labtask9april;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {
        private Button Back,upload1,download1,logOutBtn;
        TextView name, mail;
        private FirebaseAuth mAuth;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Back = findViewById(R.id.back);
            logOutBtn=findViewById(R.id.logout);

            logOutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(LoginPage.this, MainActivity.class));
                    finish();
                }
            });

            upload1=findViewById(R.id.Upload);
            download1= findViewById(R.id.Download);
            download1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        startActivity(new Intent(LoginPage.this, upload_page.class));

                    } catch (Exception e) {
                        Toast.makeText(LoginPage.this, "Error Opening Download Page "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            upload1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        startActivity(new Intent(LoginPage.this, download_page.class));
                    } catch (Exception e) {
                        Toast.makeText(LoginPage.this, "Failed TO Open Upload Page", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            Back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    change();
                }
            });
        }

        public void change() {
            startActivity(new Intent(LoginPage.this, MainActivity.class));
            finish();
        }



    }


