package com.priyankamehta.hw8;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        Intent i=getIntent();
        String name=i.getStringExtra("name");
        String phone=i.getStringExtra("phone");
        String picture=i.getStringExtra("picture");
        String email=i.getStringExtra("email");

        TextView hname=(TextView)findViewById(R.id.idHeadingContactName);
        hname.setText(name);
        TextView name1=(TextView)findViewById(R.id.idName);
        name1.setText(name);
        TextView emailid=(TextView)findViewById(R.id.idEmailID);
        emailid.setText(email);
        TextView phone1 =(TextView)findViewById(R.id.idPhoneValue);
        phone1.setText(phone);

        Log.d("picture1",picture);
        byte [] encodeByte=Base64.decode(picture, Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        Log.d("bitmap",""+bitmap);
        ImageView iv=(ImageView)findViewById(R.id.contactview);
        iv.setImageBitmap(bitmap);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
