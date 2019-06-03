package com.btanhuecrng13ctong3.easyliving;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;

//adapter for supplies
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    Context ctx;
    private ArrayList<SUPPLIES_OBJECT> mDataset;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView name, buyer, num, price;
        ImageView imageView;
        ImageButton imageButtonDelete;
        ImageButton imgBtnDecrement;
        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.textViewTitle);
            buyer = v.findViewById(R.id.textViewBuyer);
            price = v.findViewById(R.id.textViewPrice);
            num = v.findViewById(R.id.textViewNum);
            imageView = v.findViewById(R.id.imageView);
            imageButtonDelete = v.findViewById(R.id.imageButtonDelete);
            imgBtnDecrement = v.findViewById(R.id.imageButtonDecrement);
        }
    }
    public MyAdapter(ArrayList<SUPPLIES_OBJECT> myDataset) {
        mDataset = myDataset;
//        ctx = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final SUPPLIES_OBJECT supplies_object = mDataset.get(position);
        DecimalFormat df = new DecimalFormat("0.00");
        holder.name.setText(supplies_object.SUPPLY_NAME);
        holder.buyer.setText(supplies_object.SUPPLY_BUYER);
        int supplyNum = supplies_object.SUPPLY_NUM;
        holder.num.setText("Quantity: "+ Integer.toString(supplyNum));
        holder.price.setText("Price: $"+(df.format(supplies_object.SUPPLY_PRICE)));
        if(supplies_object.SUPPLY_NUM == 0){
            holder.num.setBackgroundColor(Color.parseColor("#b30000"));
        }else if(supplies_object.SUPPLY_NUM <= 4){
            holder.num.setBackgroundColor(Color.parseColor("#f9690e"));
        }
        holder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataset.remove(supplies_object);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mDataset.size());
                FirebaseDatabase.getInstance().getReference("Supplies").child(supplies_object.SUPPLY_NAME).removeValue();
            }
        });
        holder.imgBtnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curAmount = supplies_object.SUPPLY_NUM;
                curAmount = curAmount -1;
                if(curAmount>=0) {
                    FirebaseDatabase.getInstance().getReference("Supplies").child(supplies_object.SUPPLY_NAME).child("SUPPLY_NUM").setValue(curAmount);
                }
            }
        });


//        holder.imageView.setImageDrawable(ctx.getResources().getDrawable(supplies_object.getSUPPLY_IMAGE(), null));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
