package com.priyankamehta.hw8;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewMessagesActivity extends AppCompatActivity {
    static String contact;
    Firebase ref;
    Firebase usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_messages);

        Firebase.setAndroidContext(this);
        ref = FirebaseRef.getRef();
        usersRef = ref.child("messages");
        Intent i = getIntent();
        final String sender = i.getStringExtra("senderemail");
        final String receiver = i.getStringExtra("receiveremail");
        contact = receiver;
        final ArrayList<Message> messageArrayList = new ArrayList<Message>();

        Firebase root = new Firebase("https://boiling-torch-2456.firebaseio.com/messages");

        messageArrayList.clear();
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //messageUserCombines.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Message message = postSnapshot.getValue(Message.class);
                    Log.d("message", message.getReceiver());
                    //Filter message to match user and user's current pick friend
                    if (message.getSender().equals(sender) && message.getReceiver().equals(receiver)||message.getSender().equals(receiver) && message.getReceiver().equals(sender)) {
                        messageArrayList.add(message);
                    }

                }

                ListView l = (ListView) findViewById(R.id.listView);
                MessageAdapter messageAdapter = new MessageAdapter(ViewMessagesActivity.this, R.layout.view, messageArrayList);
                Log.d("message size",""+messageArrayList.size());
                l.setAdapter(messageAdapter);

                //messageAdapter.notifyDataSetChanged();
                //messageAdapter.setNotifyOnChange(true);

                /*ViewMessageAdapter viewMessageAdapter = new ViewMessageAdapter(ViewMessages.this, R.layout.custom_listview_viewmessage, messageUserCombines);
                lv = (ListView) findViewById(R.id.listViewMessage);
                lv.setAdapter(viewMessageAdapter);
                viewMessageAdapter.setNotifyOnChange(true);*/
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        //get current date time with Date()


        findViewById(R.id.send)

                .

                        setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   int flag = 0;
                                                   EditText msg = (EditText) findViewById(R.id.editText);
                                                   String message = msg.getText().toString();
                                                   if (message.isEmpty()) {
                                                       flag = 1;
                                                       Toast.makeText(ViewMessagesActivity.this, "Text box is empty", Toast.LENGTH_LONG).show();
                                                   }

                                                   if (message.length() == 140) {
                                                       flag = 1;
                                                       Toast.makeText(ViewMessagesActivity.this, "Text box exceeds the accepted character length", Toast.LENGTH_LONG).show();
                                                   }


                                                   if (flag == 0) {
                                                       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                                       Date date = new Date();
                                                       String dte = dateFormat.format(date);
                                                       Intent i = getIntent();
                                                       String sender = i.getStringExtra("senderemail");
                                                       String receiver = i.getStringExtra("receiveremail");

                                                       Firebase newPostRef = usersRef.push();
// Add some data to the new location
                                                       //  Double amt = Double.parseDouble(amount.getText().toString());
                                                       Map<String, String> post1 = new HashMap<String, String>();
                                                       Message msg1 = new Message();
                                                       msg1.setRead("false");
                                                       msg1.setSender(sender);
                                                       msg1.setReceiver(receiver);
                                                       msg1.setText(message);
                                                       msg1.setTimestamp(dte);
                                                       newPostRef.setValue(msg1);

                                                   }
                                               }
                                           }

                        );


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout_view, menu);
        MenuInflater inflater = getMenuInflater();
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.callContact) {
            Intent i = getIntent();
            String no = i.getStringExtra("phone");
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + no));
            //if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                startActivity(intent);

                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //return true;
            //}


        }
        if (id == R.id.ViewContact) {
            Intent i=getIntent();
            String name=i.getStringExtra("receiveremail");
            String phone=i.getStringExtra("phone");
            String picture=i.getStringExtra("picture");
            String email=i.getStringExtra("email");
            Intent a=new Intent(ViewMessagesActivity.this,ViewContact.class);
            a.putExtra("name",name);
            a.putExtra("phone",phone);
            a.putExtra("picture",picture);
            a.putExtra("email",email);
            startActivity(a);
        }
        return super.onOptionsItemSelected(item);
    }




    }
