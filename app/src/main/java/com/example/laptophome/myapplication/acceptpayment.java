package com.example.laptophome.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class acceptpayment extends AppCompatActivity {
    TextView txttitle,txtcategory,txtprice,txtdescription;
    String title,category,amount,descirption;
    Button pay,cancel;
    ImageView imgo;
    public static final int paypal_request=7171;
   public PayPalConfiguration configuratin=new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(paypal_config.client_id);


    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                if(requestCode==paypal_request)
                {
                    if(resultCode==RESULT_OK)
                    {
                        PaymentConfirmation confirmation=data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                        if(confirmation!=null)
                        {
                            try
                            {
                                String paymentdetails=confirmation.toJSONObject().toString(4);
                                startActivity(new Intent(this,paymentdetailsss.class)
                                        .putExtra("paymentdetails",paymentdetails)
                                        .putExtra("paymentamount",amount)

                                );

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else if(resultCode== Activity.RESULT_CANCELED)
                        Toast.makeText(this," The payment process Canceled",Toast.LENGTH_LONG).show();
                }
                    else if(resultCode==PaymentActivity.RESULT_EXTRAS_INVALID)
                        Toast.makeText(this,"Invald",Toast.LENGTH_LONG).show();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptpayment);
        pay=(Button)findViewById(R.id.button);
        imgo=(ImageView)findViewById(R.id.imageView5);
        cancel=(Button)findViewById(R.id.cancel);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentprocess();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Main_page.class));
            }
        });
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imgo.setImageBitmap(bmp);

        txttitle=(TextView)findViewById(R.id.txttitle);
        txtcategory=(TextView)findViewById(R.id.txtcategory);
        txtprice=(TextView)findViewById(R.id.txtprice);
        txtdescription=(TextView)findViewById(R.id.txtdescription);
        txtdescription.setText(getIntent().getStringExtra("desc"));
        txttitle.setText(getIntent().getStringExtra("title"));
        txtprice.setText(getIntent().getStringExtra("price"));
        amount=txtprice.getText().toString();
        txtcategory.setText(getIntent().getStringExtra("category"));
        Intent intent=new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,configuratin);
        startService(intent);


    }

    public void paymentprocess()
    {
        PayPalPayment payPalPayment=new PayPalPayment(new BigDecimal(String.valueOf(amount)),"USD","The Price",PayPalPayment.PAYMENT_INTENT_ORDER);
        Intent intent=new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,configuratin);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,paypal_request);

    }
}
