package com.example.btl_androidv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.btl_androidv1.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignupActivity extends AppCompatActivity {

    MaterialEditText textPhone,textName,textPassword;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        textName = (MaterialEditText) findViewById(R.id.textNameSU);
        textPhone = (MaterialEditText) findViewById(R.id.textPhoneSU);
        textPassword = (MaterialEditText) findViewById(R.id.textPasswordSU);
        btnSignUp = (Button) findViewById(R.id.btnSignUpInside);
         FirebaseDatabase database = FirebaseDatabase.getInstance();
         DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 ProgressDialog mDialog = new ProgressDialog(SignupActivity.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(textPhone.getText().toString()).exists()){
                            mDialog.dismiss();
                            Toast.makeText(SignupActivity.this,"Phone Number is already exists",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            mDialog.dismiss();
                            User user = new User(textName.getText().toString(),textPassword.getText().toString());
                            table_user.child(textPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignupActivity.this,"Sign up success",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}