package com.btanhuecrng13ctong3.easyliving;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
        ImageButton imageButtonDelete;
        public MyViewHolder(View v, final OnItemClickListener listener) {
            super(v);
            title = v.findViewById(R.id.post_title);
            author = v.findViewById(R.id.post_author);
            imageButtonDelete = v.findViewById(R.id.imageButtonDelete);

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

            imageButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("button clicked");

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
        myViewHolder.author.setText(obj.getPOST_AUTHOR());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

//
//
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView title, body, group, author;
//        ImageButton imageButtonDelete;
//
//        public MyViewHolder(View v) {
//            super(v);
//            title = v.findViewById(R.id.post_title);
//            author = v.findViewById(R.id.post_author);
//            imageButtonDelete = v.findViewById(R.id.imageButtonDelete);
//        }
//    }
//    public MessageAdapter(ArrayList<MESSAGE_OBJ> myDataset) {
//        mDataset = myDataset;
////        ctx = context;
//    }
//    // Create new views (invoked by the layout manager)
//    @Override
//    public MessageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
//                                                     int viewType) {
//        // create a new view
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.my_layout, parent, false);
//        MyViewHolder vh = new MyViewHolder(v);
//        return vh;
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    @Override
//    public void onBindViewHolder(MessageAdapter.MyViewHolder holder, final int position) {
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//        final MESSAGE_OBJ message_obj = mDataset.get(position);
//
//        holder.title.setText(message_obj.getPOST_TITLE());
//        holder.author.setText(message_obj.getPOST_AUTHOR());
//        holder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                mDataset.remove(message_obj);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, mDataset.size());
//            }
//        });
//
////        holder.name.setText(supplies_object.getSUPPLY_NAME());
////        holder.buyer.setText(supplies_object.getSUPPLY_BUYER());
////        int supplyNum = supplies_object.getSUPPLY_NUM();
////        holder.num.setText("Quantity: "+ Integer.toString(supplyNum));
////        holder.price.setText(Integer.toString(supplies_object.getSUPPLY_PRICE()));
////        holder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                mDataset.remove(supplies_object);
////                notifyItemRemoved(position);
////                notifyItemRangeChanged(position, mDataset.size());
////                FirebaseDatabase.getInstance().getReference("Supplies").child(supplies_object.getSUPPLY_NAME()).removeValue();
////            }
////        });
//
////        holder.imageView.setImageDrawable(ctx.getResources().getDrawable(supplies_object.getSUPPLY_IMAGE(), null));
//
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    @Override
//    public int getItemCount() {
//        return mDataset.size();
//    }
}
