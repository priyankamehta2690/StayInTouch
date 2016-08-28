package com.priyankamehta.hw8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class ConversationsActivity extends AppCompatActivity {

    static String fullname;
    static String key;
    String fullname1;
    String loggedInName;
    int flag;
    ArrayList<Conversations> conversationsList = new ArrayList<>();
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);

        Firebase usernameRef = FirebaseRef.getRef().child("Users").child("Username");
        final Firebase root = new Firebase("https://boiling-torch-2456.firebaseio.com/messages");

        usernameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userEmail = FirebaseRef.getRef().getAuth().getProviderData().get("email").toString();

                Log.d("here", "" + dataSnapshot.getChildrenCount());
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String email = (String) data.child("email").getValue();
                    if (email.equals(userEmail)) {
                        loggedInName = (String) data.child("fullName").getValue();
                        Log.d("loggedInName", loggedInName);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

















                    usernameRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String userEmail = FirebaseRef.getRef().getAuth().getProviderData().get("email").toString();

                            Log.d("here", "" + dataSnapshot.getChildrenCount());
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                String email = (String) data.child("email").getValue();
                                fullname1 = (String) data.child("fullName").getValue();
                                String picture = (String) data.child("picture").getValue();
                                String phoneNumber = (String) data.child("phoneNumber").getValue();
                                String password = (String) data.child("password").getValue();


                                if (!(email.equals(userEmail))) {

                                    root.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            //messageUserCombines.clear();
                                            flag = 0;
                                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                Message message = postSnapshot.getValue(Message.class);
                                                Log.d("message", message.getReceiver());
                                                //Filter message to match user and user's current pick friend
                                                if (message.getSender().equals(fullname1) && message.getReceiver().equals(loggedInName)) {

                                                    if (message.getRead().equals("false")) {
                                                        flag = 1;
                                                    }


                                                } else if (message.getSender().equals(loggedInName) && message.getReceiver().equals(fullname1)) {

                                                    if (message.getRead().equals("false")) {
                                                        flag = 1;
                                                    }

                                                }
                                            }
                                        }


                                        @Override
                                        public void onCancelled(FirebaseError firebaseError) {

                                        }
                                    });


                                    Conversations objConv = new Conversations(picture, fullname1);
                                    objConv.setPhoneNumber(phoneNumber);
                                    objConv.setEmail(email);

                                    if (flag == 1) {
                                        objConv.setFlag(false);
                                    } else {
                                        objConv.setFlag(true);
                                    }

                                    conversationsList.add(objConv);
                                    Log.d("size", "" + conversationsList.size());
                                } else {
                                    key = data.getKey();
                                    fullname = (String) data.child("fullName").getValue();
                                    user = new User();
                                    user.setName(fullname);
                                    user.setEmail(email);
                                    user.setImage(picture);
                                    user.setPassword(password);
                                    user.setPhone(phoneNumber);

                                }
                            }

                            ListView listViewConversations = (ListView) findViewById(R.id.listViewConversations);
                            Log.d("size1", "" + conversationsList.size());
                            ConversationsAdapter objArrayAdapter = new ConversationsAdapter(ConversationsActivity.this, conversationsList);
                            listViewConversations.setAdapter(objArrayAdapter);
                            listViewConversations.setFocusable(true);


                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout_menu, menu);
        MenuInflater inflater = getMenuInflater();
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.editProfile) {
          Intent i=new Intent(ConversationsActivity.this,Edit_Profile_Activity.class);
            i.putExtra("class",user);
            startActivity(i);
        }
        if (id == R.id.logout) {
            FirebaseRef.getRef().unauth();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
