package com.priyankamehta.hw8;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by PriyankaMehta on 4/16/16.
 */
public class ConversationsAdapter extends ArrayAdapter<Conversations> {

    ConversationsActivity mContext;
    ArrayList<Conversations> objects;


    public ConversationsAdapter(ConversationsActivity context, ArrayList<Conversations> objects) {
        super(context, R.layout.conversation_list, objects);
        this.mContext = context;
        this.objects = objects;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.conversation_list, parent, false);
            holder = new ViewHolder();

            holder.contactImage = (ImageView) convertView.findViewById(R.id.imgContactImage);
            holder.contactName = (TextView) convertView.findViewById(R.id.textViewName);
            holder.imgRedBubble = (ImageView) convertView.findViewById(R.id.imgRebBubble);
            holder.imgPhone = (ImageView) convertView.findViewById(R.id.imgPhone);
            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();
        ImageView contactImage = holder.contactImage;
        TextView contactName = holder.contactName;
        ImageView imgRedBubble = holder.imgRedBubble;
        ImageView imgPhone = holder.imgPhone;
        contactName.setText(objects.get(position).getName());
        Log.d("name",objects.get(position).getName());
        contactImage.setImageBitmap(StringToBitMap(objects.get(position).getUrlContactImage()));
        //imgRedBubble.setImageBitmap(StringToBitMap(objects.get(position).getUrlRedBubble()));
        //imgPhone.setImageBitmap(StringToBitMap(objects.get(position).getUrlPhone()));
        Log.d("here",""+objects.get(position).isFlag());
        if(objects.get(position).isFlag()==true) {
            Log.d("here", "" + objects.get(position).isFlag());
            imgRedBubble.setImageBitmap(null);

        }
        else
        {
            imgRedBubble.setImageResource(R.drawable.red_bubble_clipart_1);
        }
        imgPhone.setImageResource(R.drawable.phone_icon);

        imgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("here", "here");

                /*if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    Log.d("here", "here1");
                    try {*/
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + objects.get(position).getPhoneNumber()));
                        mContext.startActivity(intent);
                    //}catch(Exception e){

                    }
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    //return;
                //}



        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("inside","adapter");
                final String userEmail = ConversationsActivity.fullname;
                final String name=objects.get(position).getName();
                Firebase ref=FirebaseRef.getRef();
                final Firebase userref=ref.child("messages");
                userref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //messageUserCombines.clear();

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            if(postSnapshot.child("sender").getValue().equals(userEmail) &&
                                    postSnapshot.child("receiver").getValue().equals(name)){
                                Message message = postSnapshot.getValue(Message.class);
                                message.setRead("true");
                                String key=postSnapshot.getKey();
                                userref.child(key).setValue(message);
                                Log.d("here","here1");
                            }


                            if(postSnapshot.child("sender").getValue().equals(name) &&
                                    postSnapshot.child("receiver").getValue().equals(userEmail)){
                                Message message = postSnapshot.getValue(Message.class);
                                message.setRead("true");
                                String key=postSnapshot.getKey();
                                userref.child(key).setValue(message);
                                Log.d("here", "here1");
                            }







                        }
                    }


                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


                Intent i=new Intent(mContext,ViewMessagesActivity.class);
                i.putExtra("senderemail",userEmail);
                i.putExtra("receiveremail", objects.get(position).getName());
                i.putExtra("phone",objects.get(position).getPhoneNumber());
                i.putExtra("picture",objects.get(position).getUrlContactImage());
                i.putExtra("email",objects.get(position).getEmail());
                mContext.startActivity(i);
            }
        });


        return convertView;
    }

    static class ViewHolder{
        ImageView contactImage;
        TextView contactName;
        ImageView imgRedBubble;
        ImageView imgPhone;

    }
    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}