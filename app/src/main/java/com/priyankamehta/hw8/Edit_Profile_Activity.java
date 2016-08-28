package com.priyankamehta.hw8;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Edit_Profile_Activity extends AppCompatActivity {
    String select="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile_);
        Firebase.setAndroidContext(this);
        Firebase ref = FirebaseRef.getRef();

        Intent i=getIntent();
        User user= (User) i.getSerializableExtra("class");

        Firebase usersRef = ref.child("Users");
        final Firebase usernameRef = usersRef.child("Username");
        Log.d("key",ConversationsActivity.key);
        final Firebase firebase = usernameRef.child(ConversationsActivity.key);

        EditText name = (EditText) findViewById(R.id.name);
        EditText email = (EditText) findViewById(R.id.email);
        EditText phone = (EditText) findViewById(R.id.phone);
        EditText password = (EditText) findViewById(R.id.password);
        name.setText(user.getName());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());
        password.setText(user.getPassword());

        ImageView iv=(ImageView)findViewById(R.id.imageView);
        Log.d("image",user.getImage());
        byte [] encodeByte= Base64.decode(user.getImage(), Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        if(bitmap!=null) {


            iv.setImageBitmap(bitmap);
        }

        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            EditText name = (EditText) findViewById(R.id.name);
            EditText email = (EditText) findViewById(R.id.email);
            EditText phone = (EditText) findViewById(R.id.phone);
            EditText password = (EditText) findViewById(R.id.password);


            @Override
            public void onClick(View v) {
                ;
                Map<String, Object> userDetails = new HashMap<>();
                userDetails.put("email", email.getText().toString());
                userDetails.put("fullName", name.getText().toString());
                userDetails.put("password", password.getText().toString());
                userDetails.put("phoneNumber", phone.getText().toString());
                userDetails.put("picture", select);
                firebase.setValue(userDetails);
                Toast.makeText(Edit_Profile_Activity.this, "Your details have been successfully saved", Toast.LENGTH_LONG).show();
                finish();


            }
        });


        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"),123);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        select=BitMapToString(bitmap);
                        ImageView i=(ImageView)findViewById(R.id.imageView);
                        i.setImageBitmap(bitmap);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (resultCode ==RESULT_CANCELED) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public String BitMapToString(Bitmap bm) {
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }










}
