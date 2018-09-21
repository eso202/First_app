package com.example.laptophome.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class recycle extends RecyclerView.Adapter<recycle.Viewfinder> {

    private Context mcontext;
    private ArrayList<laptop_item>listo;
    FirebaseAuth mauth= FirebaseAuth.getInstance();
    FirebaseUser user=mauth.getCurrentUser();
    sqllite sqllite;
    private Context mcon;


    public recycle(Context mcontext, ArrayList<laptop_item> listo) {
        this.mcontext = mcontext;
        this.listo = listo;
    }

    @Override
    public Viewfinder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vv= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleitem,null);
        Viewfinder viewfinder=new Viewfinder(vv);
        return viewfinder;
    }

    @Override
    public void onBindViewHolder(final Viewfinder holder, final int position) {
        Bitmap bitmap= BitmapFactory.decodeByteArray(listo.get(position).getImage(),0,listo.get(position).getImage().length);

                holder.imgo.setImageBitmap(bitmap);

                holder.price.setText(Integer.toString(listo.get(position).getPrice())+"$");
                holder.title.setText(listo.get(position).getTitle());
                        holder.buy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent=new Intent(v.getContext(),acceptpayment.class);
                                intent.putExtra("price",holder.price.getText().toString());
                                intent.putExtra("title",holder.title.getText().toString());
                                intent.putExtra("category",listo.get(position).getCategory());
                                intent.putExtra("desc",listo.get(position).getDescprtion());
                                holder.imgo.buildDrawingCache();
                                Bitmap bi=holder.imgo.getDrawingCache();
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bi.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] byteArray = stream.toByteArray();
                                intent.putExtra("image",byteArray);
                                v.getContext().startActivity(intent);

                            }
                        });






    }




    @Override
    public int getItemCount() {
        return listo.size();
    }

    public void arraylist(List<laptop_item>name)
    {
        listo=new ArrayList<>();
        listo.addAll(name);
        notifyDataSetChanged();
    }


    public class Viewfinder extends RecyclerView.ViewHolder
    {
            TextView title,category,price;
            ImageView imgo;
            Button buy;


        public Viewfinder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.brand);
            price=itemView.findViewById(R.id.price);
            category=itemView.findViewById(R.id.category);
            buy=itemView.findViewById(R.id.buy);
            imgo=itemView.findViewById(R.id.image22);

        }
    }

}
