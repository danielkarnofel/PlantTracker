package com.example.planttracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planttracker.database.AppRepository;
import com.example.planttracker.database.entities.User;
import com.example.planttracker.databinding.ActivitySignUpBinding;
import com.example.planttracker.utilities.IntentFactory;
import com.example.planttracker.utilities.ToastMaker;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        repository = AppRepository.getRepository(getApplication());

        binding.signUpActivitySignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the username entered in the signup
                String mUsername = binding.signUpActivityUsernameEditText.getText().toString();
                String mPassword1 = binding.signUpActivityPasswordEditText.getText().toString();
                String mPassword2 = binding.signUpActivityConfirmEditText.getText().toString();

                if (signupInformationComplete(mUsername, mPassword1, mPassword2)) {
                    if (repository.checkUsernameExists(mUsername)) {
                        ToastMaker.makeToast(getApplicationContext(), "Username already exists.");
                    } else {
                        repository.insertUser(new User(mUsername, mPassword1, false));
                        showSuccessDialog();
                    }
                }
            }
        });

        binding.signUpActivityCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = IntentFactory.loginActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private boolean signupInformationComplete(String username, String password1, String password2) {
        if (username.isEmpty()) {
            ToastMaker.makeToast(this, "Missing username!");
            binding.signUpActivityUsernameEditText.setSelection(0);
            return false;
        }

        if (password1.isEmpty()) {
            ToastMaker.makeToast(this, "Missing password!");
            binding.signUpActivityPasswordEditText.setSelection(0);
            return false;
        }

        if (password2.isEmpty()) {
            ToastMaker.makeToast(this, "Missing password confirmation!");
            binding.signUpActivityConfirmEditText.setSelection(0);
            return false;
        }

        if (!password1.equals(password2)) {
            ToastMaker.makeToast(this, "Passwords does not match!");
            binding.signUpActivityPasswordEditText.setSelection(0);
            return false;
        }

        return true;
    }

    private void showSuccessDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SignUpActivity.this);
        alertBuilder.setTitle("Success");
        alertBuilder.setMessage("Your account was successfully created.");
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = IntentFactory.loginActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        alertBuilder.create().show();
    }
}