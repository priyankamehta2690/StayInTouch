package com.priyankamehta.hw8;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    Firebase ref;
    Firebase usersRef;
    EditText etUserName;
    EditText etEmail;
    EditText etPhone;
    EditText etPassword;
    EditText etConfirmPassword;
    Button btnSignUp;
    Button btnCancel;
    String email="",fullName="",phoneNumber="", password="", confirmPassword="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Firebase.setAndroidContext(this);
        ref = FirebaseRef.getRef();
        usersRef = ref.child("Users");

        etUserName = (EditText) findViewById(R.id.etUserName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhone = (EditText) findViewById(R.id.etPhoneNumber);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                fullName = etUserName.getText().toString();
                phoneNumber = etPhone.getText().toString();
                password = etPassword.getText().toString();
                confirmPassword = etConfirmPassword.getText().toString();

                if (confirmPassword.equals(password)) {
                    Firebase usernameRef = usersRef.child("Username");
                    Firebase userRef = usernameRef.push();
                    Map<String, String> userDetails = new HashMap<>();
                    userDetails.put("email", email);
                    userDetails.put("fullName", fullName);
                    userDetails.put("password", password);
                    userDetails.put("phoneNumber", phoneNumber);
                    userDetails.put("picture", BitMapToString());
                    userRef.setValue(userDetails);

                    ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> result) {
                            Log.d("here","here");
                            Toast.makeText(SignUpActivity.this, "The user is created", Toast.LENGTH_LONG).show();
                            Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(loginIntent);
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            Log.d("error",""+firebaseError);
                            if (firebaseError.getMessage().equals("The specified email address is already in use.")) {
                                Log.d("already","already");
                                Toast.makeText(SignUpActivity.this, "The account was not created. Please select a different email", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(SignUpActivity.this, "Password and Confirm Password do not match", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public String BitMapToString() {
        Bitmap defaultImage = BitmapFactory.decodeResource(getResources(), R.drawable.img_default);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        defaultImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}
