package com.example.btl_androidv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {
    Button btnSignUp,btnSignIn;
    TextView txtLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignIn=(Button) findViewById(R.id.btnSignIn);
        btnSignUp=(Button) findViewById(R.id.btnSignUp);
        txtLogo = (TextView) findViewById(R.id.txtLogo);
        Typeface face = Typeface.createFromAsset(getAssets(),"NABILA.TTF");
        txtLogo.setTypeface(face);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent signin = new Intent(MainActivity.this,SignInActivity.class);
               startActivity(signin);
           }
       });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(MainActivity.this,SignupActivity.class);
                startActivity(signup);

            }
        });

    }



}