package com.example.laptophome.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.paypal.android.sdk.payments.PayPalService;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Random;

public class third_page extends AppCompatActivity implements View.OnClickListener {
    Button logout, Done, Upload;
    TextView hello;
    EditText title, price, category, descrptiion;
    FirebaseAuth mauth;
    FirebaseUser user;
    String downloadurl;
    ImageView img;
    final int image_request = 71;
    Uri firepath;
    byte[] image = null;
    sqllite sqllite;


    String titstring,catestring,descristring;
    int pricestring;

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_page);
        sqllite=new sqllite(this);
        logout = (Button) findViewById(R.id.Log_out);
        img = (ImageView) findViewById(R.id.imageView3);
        mauth = FirebaseAuth.getInstance();
        user = mauth.getCurrentUser();
        hello = (TextView) findViewById(R.id.displa_name);
        hello.setText("Welcome " + user.getDisplayName());
        title = (EditText) findViewById(R.id.editTitle);
        price = (EditText) findViewById(R.id.editprice);
        category = (EditText) findViewById(R.id.editcategory);
        descrptiion = (EditText) findViewById(R.id.editdescription);
        Done = (Button) findViewById(R.id.done);
        Upload = (Button) findViewById(R.id.upload);
        Done.setOnClickListener(this);
        Upload.setOnClickListener(this);
        logout.setOnClickListener(this);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == image_request && resultCode == RESULT_OK && data != null && data.getData() != null)

        {
            firepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(firepath);
                Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(decodeStream);

                image = getBytes(decodeStream);
            } catch (Exception e) {
                Log.e("e",e.getMessage());
            }
        }


    }


    public  static byte[] getBytes(Bitmap bitmap)
    {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,stream);
        return stream.toByteArray();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {
            case R.id.Log_out:
                mauth.signOut();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.done:

                titstring=title.getText().toString();
                catestring=category.getText().toString();
                descristring=descrptiion.getText().toString();
                pricestring=Integer.parseInt(price.getText().toString());
                BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                image = getBytes(bitmap);
                laptop_item laptop_item=new laptop_item(titstring,catestring,descristring,image,pricestring);
                sqllite.addpost(laptop_item);
                Intent i=new Intent(this,Main_page.class);
                startActivity(i);

                break;

            case R.id.upload:
                choseimage();
                break;


        }
    }



    public void choseimage()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select picture"),image_request);

    }
}
