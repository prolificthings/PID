package com.example.geofencebarcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.geofencebarcodescanner.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        binding.login2rgstr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.login2rgstr.setPaintFlags(binding.login2rgstr.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
  }

    private void loginUser() {
        String Email = binding.emailTxt.getText().toString();
        String Password = binding.pswrdTxt.getText().toString();

        if (TextUtils.isEmpty(Email)){
            binding.emailTxt.setError("Please enter your email");
            binding.emailTxt.requestFocus();
        } else if (TextUtils.isEmpty(Password)){
            binding.pswrdTxt.setError("Please enter your password");
            binding.pswrdTxt.requestFocus();
        } else{
            mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MapsActivity.class));
                        finish();
                    } else{
                        Toast.makeText(LoginActivity.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}