package com.priyankamehta.hw8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class LoginActivity extends AppCompatActivity {

    Firebase ref;
    Button btnCreateNew;
    Button btnLogin;
    EditText etEmail;
    EditText etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Firebase.setAndroidContext(this);
        ref = FirebaseRef.getRef();


        btnCreateNew = (Button)findViewById(R.id.btnCreateNew);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);

        btnCreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpActivity = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUpActivity);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                Log.d("email",""+email);
                Log.d("pass",""+password);
                ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {

                        Intent conversationIntent = new Intent(LoginActivity.this, ConversationsActivity.class);
                        startActivity(conversationIntent);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Log.d("error",""+firebaseError);
                        Toast.makeText(LoginActivity.this, "Login was not successful", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
