package com.example.laptophome.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class paymentdetailsss extends AppCompatActivity {
    TextView txtid, txtamount, txtstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentdetailsss);
        txtid = (TextView) findViewById(R.id.txtid);
        txtamount = (TextView) findViewById(R.id.txtamount);
        txtstatus = (TextView) findViewById(R.id.txtstatus);
        Intent intent = getIntent();

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("paymentdetails"));
            showdetails(jsonObject.getJSONObject("response"), intent.getStringExtra("paymentamount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showdetails(JSONObject response, String paymentamount) {
        try{
            txtid.setText(response.getString("id"));
            txtstatus.setText(response.getString("state"));
            txtamount.setText(response.getString(String.format("$"+paymentamount)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}