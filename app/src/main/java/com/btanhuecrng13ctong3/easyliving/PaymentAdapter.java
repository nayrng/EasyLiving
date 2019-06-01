package com.btanhuecrng13ctong3.easyliving;


import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;

import com.btanhuecrng13ctong3.easyliving.PAYMENT_OBJ;
import com.btanhuecrng13ctong3.easyliving.R;
import com.btanhuecrng13ctong3.easyliving.paymentDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {
    private ArrayList<PAYMENT_OBJ> mDataset;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser curUser = firebaseAuth.getCurrentUser();
    Boolean youOwe;
    private Context ctx;
    public OnItemClickListener mListener;



    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDetailClick(int position);
        void onImageClick(int position);
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView product, price, oweOrNot;
        ImageButton details;
        ImageView youOweImg;


        public MyViewHolder(View v, final OnItemClickListener listener) {
            super(v);
            product = v.findViewById(R.id.view_payment_textView_Title);
            price = v.findViewById(R.id.view_payment_textViewPrice);
            oweOrNot = v.findViewById(R.id.view_payment_textViewDescription);
            details = v.findViewById(R.id.view_payment_detail);
            youOweImg = v.findViewById(R.id.view_payment_image_view);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("detail got clicked");
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDetailClick(position);
                        }
                    }
                }
            });

            youOweImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onImageClick(position);
                        }
                    }
                }
            });
        }

    }

    public PaymentAdapter(ArrayList<PAYMENT_OBJ> myDataset, boolean owe) {
        mDataset = myDataset;
        youOwe = owe;
        Log.d("Position", "Iterated");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_payment_card_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v, mListener);
        ctx = parent.getContext();
        return vh;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, final int position) {
        DecimalFormat df = new DecimalFormat("0.00");
        PAYMENT_OBJ payment_object = mDataset.get(position);
        holder.product.setId(position);
        holder.product.setText(payment_object.product);

        holder.price.setId(position);
        if (youOwe==true){
            holder.oweOrNot.setText("Requested by "+ payment_object.sender);
            if(payment_object.chargecompleted.contains(curUser.getEmail())){
                ArrayList<String> completed = payment_object.chargecompleted;
                int dateIndex = payment_object.chargecompleted.indexOf(curUser.getEmail()) + 1;
                Log.d("Index", ":" + dateIndex);
                String completedChargeTxt = "Completed on: " + completed.get(dateIndex);
                holder.price.setText(completedChargeTxt);
                holder.youOweImg.setImageDrawable(ctx.getResources().getDrawable(R.drawable.venmocomp, null));
            }else{
                holder.youOweImg.setImageDrawable(ctx.getResources().getDrawable(R.drawable.venmoicon, null));
                holder.price.setText("$" + (df.format(payment_object.price / payment_object.receivers.size())));
            }
        }else if(youOwe == false){
            //this is what is seen by the "charger"
            Double owed = Double.parseDouble(df.format(balance(payment_object)));
            Log.d("owed", ": " + owed);
            if(owed==0.0){
                //if everyone has completed the charge
                holder.youOweImg.setImageDrawable(ctx.getResources().getDrawable(R.drawable.venmosendercomp, null)); // green check
                holder.oweOrNot.setText("Amount owed to you: ");
                Log.d("Kirari", ": " + owed);
                holder.price.setText("$"+(df.format(balance(payment_object))));
            }else if(payment_object.chargecompleted.size()==1){
                //if no one has completed the charge
                holder.youOweImg.setImageDrawable(ctx.getResources().getDrawable(R.drawable.venmosenderncomp, null)); // green check
                holder.oweOrNot.setText("Amount owed to you: ");
                holder.price.setText("$"+(df.format(balance(payment_object))));
            } else{
                //if some have completed the charge
                holder.youOweImg.setImageDrawable(ctx.getResources().getDrawable(R.drawable.venmosenderycomp, null));
                holder.oweOrNot.setText("Amount owed to you: ");
                holder.price.setText("$"+(df.format(balance(payment_object))));
            }
        }

        //holder.youOweImg.setImageDrawable(ctx.getResources().getDrawable(R.drawable.venmoicon, null));
//        holder.youOweImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = v.getContext().getPackageManager().getLaunchIntentForPackage("com.venmo");
//                ctx.startActivity(i);
//            }
//        });

        //holder.details.setOnClickListener(new customOnClickListener(payment_object, holder));

    }
    public Double balance(PAYMENT_OBJ obj){
        //input should be the price
        //originally divided as (total price)/(total members)
        //expected output should be
        // newCost = (total price) - ((1+(numofCompletions-1/2))((total price)/(total members))
        double totalCost = obj.price;
        int numofMembers = obj.receivers.size();
        int payCompleted = obj.chargecompleted.size();
        double newCost;
        //if(obj.chargecompleted.size() != 1 && (obj.chargecompleted.size()%2)==1){
        newCost = totalCost - ((1+((payCompleted-1)/2))*(totalCost/numofMembers));
        //}
        return newCost;
    }

//    public class customOnClickListener implements View.OnClickListener{
//        PAYMENT_OBJ obj;
//        public customOnClickListener(PAYMENT_OBJ obj, MyViewHolder holder){
//            this.obj = obj;
//        }
//        @Override
//        public void onClick(View v){
//            Log.d("CustomOnClick: ",obj.product );
//            Intent intent = new Intent(ctx.getApplicationContext(), paymentDetails.class);
//            intent.putExtra("product_name",obj.product);
//            intent.putStringArrayListExtra("receivers_list", obj.receivers);
//            v.getContext().startActivity(intent);
//        }
//    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
