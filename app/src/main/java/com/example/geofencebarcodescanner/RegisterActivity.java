package com.example.geofencebarcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.geofencebarcodescanner.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        binding.rgstrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                createUser();
            }



        });

        binding.rgstr2login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.rgstr2login.setPaintFlags(binding.rgstr2login.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void createUser() {
        String UserName = binding.usernameTxt.getText().toString();
        String Email = binding.emailTxt.getText().toString();
        String Password = binding.pswrdTxt.getText().toString();

        if (TextUtils.isEmpty(UserName)){
            binding.usernameTxt.setError("Please enter username");
            binding.usernameTxt.requestFocus();
        } else if (TextUtils.isEmpty(Email)){
            binding.emailTxt.setError("Please enter your email");
            binding.emailTxt.requestFocus();
        } else if (TextUtils.isEmpty(Password)){
            binding.pswrdTxt.setError("Please enter your password");
            binding.pswrdTxt.requestFocus();
        } else{
            mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        db.getReference().child("Users").child(mAuth.getUid())
                                .child("name").setValue(UserName);



                        Toast.makeText(RegisterActivity.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        finish();
                    } else{
                        Toast.makeText(RegisterActivity.this, "Registration unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }



}