package com.btanhuecrng13ctong3.easyliving;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private ArrayList<MESSAGE_OBJ> mDataset;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    private Context ctx;
    public OnItemClickListener mListener;

    public MessageAdapter(ArrayList<MESSAGE_OBJ> messages) {

        mDataset = messages;
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDetailClick(int position);
        void onImageClick(int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, body, group, author;
        Boolean anon;
        ImageButton details;
        public MyViewHolder(View v, final OnItemClickListener listener) {
            super(v);
            title = v.findViewById(R.id.view_title);
            author = v.findViewById(R.id.view_author);
            details = v.findViewById(R.id.view_post_details);

            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("button clicked");
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDetailClick(position);
                        }
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_message_card_layout, viewGroup, false); //TODO: finish a message cardview layout
        MyViewHolder vh = new MyViewHolder(v, mListener);
        ctx = viewGroup.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {

        MESSAGE_OBJ obj = mDataset.get(i);
        myViewHolder.title.setId(i);
        myViewHolder.title.setText(obj.getPOST_TITLE());
        myViewHolder.author.setId(i);
        if (obj.getPOST_ANON()) {
            myViewHolder.author.setText("Anonymous");
        }
        else {
            myViewHolder.author.setText(obj.getPOST_AUTHOR());
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
