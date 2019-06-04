package com.btanhuecrng13ctong3.easyliving;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ChoresAdapter extends RecyclerView.Adapter<ChoresAdapter.MyViewHolder> {

    private ArrayList<CHORES_OBJECT> mDataset;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    private Context ctx;
    public OnItemClickListener mListener;

    public ChoresAdapter(ArrayList<CHORES_OBJECT> chores) {
        mDataset = chores;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onChoreStatusClick(int position);
        void onImageClick(int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView chore, date, creator;
        CheckBox done_status;
        public MyViewHolder(View v, final OnItemClickListener listener) {
            super(v);
            chore = v.findViewById(R.id.textViewChoreName);
            date = v.findViewById(R.id.textViewAssDate);
            creator = v.findViewById(R.id.textViewUserID);
            done_status = v.findViewById(R.id.checkBoxChore);

            done_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onChoreStatusClick(position);
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chores_layout, viewGroup, false);
        MyViewHolder vh = new MyViewHolder(v, mListener);
        ctx = viewGroup.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        CHORES_OBJECT obj = mDataset.get(i);
        myViewHolder.chore.setId(i);
        myViewHolder.chore.setText(obj.CHORE_NAME);

        myViewHolder.creator.setId(i);
        myViewHolder.creator.setText(obj.USER_ID);

        myViewHolder.date.setId(i);
        myViewHolder.date.setText(obj.ASS_DATE);

        myViewHolder.done_status.setId(i);
        myViewHolder.done_status.setChecked(obj.CHORE_DONE);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
