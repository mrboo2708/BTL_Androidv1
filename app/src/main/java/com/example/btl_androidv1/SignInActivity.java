package com.example.btl_androidv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.btl_androidv1.Model.User;
import com.example.btl_androidv1.Stuff.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignInActivity extends AppCompatActivity {
    EditText editPhone,editPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        editPassword = (MaterialEditText)findViewById(R.id.textPassword);
        editPhone = (MaterialEditText)findViewById(R.id.textPhone);
        btnSignIn = (Button)findViewById(R.id.btnSignInInside);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        btnSignIn.setOnClickListener(view -> {
            final ProgressDialog mDialog = new ProgressDialog(SignInActivity.this);
            mDialog.setMessage("Please waiting...");
            mDialog.show();
            table_user.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child(editPhone.getText().toString()).exists()) {
                        mDialog.dismiss();
                    User user = snapshot.child(editPhone.getText().toString()).getValue(User.class);
                        user.setPhone(editPhone.getText().toString());
                        if (user != null) {
                            if (user.getPassword().equals(editPassword.getText().toString())) {
                                {
                                    Intent homeIntent= new Intent(SignInActivity.this,Homepage.class);
                                    Common.currentUser = user;
                                    startActivity(homeIntent);

                                }

                        } else {
                            Toast.makeText(SignInActivity.this, "Wrong phone or password", Toast.LENGTH_LONG).show();
                        }
                        }

                    }
                    else{
                        mDialog.dismiss();
                        Toast.makeText(SignInActivity.this,"User not exist",Toast.LENGTH_LONG).show();
                    }
            }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }
}