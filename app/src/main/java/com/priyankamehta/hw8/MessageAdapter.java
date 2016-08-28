package com.priyankamehta.hw8;

import android.content.Context;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 4/17/2016.
 */
public class MessageAdapter extends ArrayAdapter<Message>{

    Context mContext;
    int mResource;
    List<Message> Objects;

    public MessageAdapter(Context context, int resource,List<Message> objects) {
        super(context, resource,objects);
        Objects=objects;
        mResource=resource;
        mContext=context;



    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.view,parent,false);
            holder=new ViewHolder();
            holder.fname=(TextView)convertView.findViewById(R.id.fname);
            holder.message=(TextView)convertView.findViewById(R.id.message);
            holder.date=(TextView)convertView.findViewById(R.id.date);
            holder.iv=(ImageView)convertView.findViewById(R.id.imageView2);


            convertView.setTag(holder);
        }

        holder=(ViewHolder)convertView.getTag();
        TextView fname=holder.fname;

        TextView message=holder.message;
        TextView date=holder.date;
        ImageView iv=holder.iv;
        fname.setText(ViewMessagesActivity.contact);
        message.setText(Objects.get(position).getText());
        date.setText(Objects.get(position).getTimestamp());
        iv.setImageResource(R.drawable.bin);
        if(Objects.get(position).getReceiver().equals(ViewMessagesActivity.contact)){
            convertView.setBackgroundColor(Color.GRAY);
        }

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView t=(TextView)v.findViewById(R.id.message);
                final String msg=Objects.get(position).getText();
                final String sender=Objects.get(position).getSender();
                final String receiver=Objects.get(position).getReceiver();
                Firebase ref=FirebaseRef.getRef();
                final Firebase userref=ref.child("messages");
                userref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //messageUserCombines.clear();

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            if(postSnapshot.child("text").getValue().equals(msg.toString()) &&
                                    postSnapshot.child("sender").getValue().equals(sender) &&
                                    postSnapshot.child("receiver").getValue().equals(receiver))
                                userref.child(postSnapshot.getKey()).removeValue();

                               Log.d("delete","delete");
                        }
                    }


                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


            }
        });


        return convertView;
    }
    static class ViewHolder{
        TextView fname;
        TextView message;
        TextView date;
        ImageView iv;

    }




















}
