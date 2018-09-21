package com.example.laptophome.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    ImageView img;
    Button login,signup;

    String email, pass,display_nasme,paaaa;
    EditText sign_email, sign_passwd,display;
    FirebaseAuth mauth;
    FirebaseUser user;
    ProgressBar pro;
    TextView custom;
    TextView error,error_sign;

    @Override
    protected void onStart() {
        super.onStart();

        if(mauth.getCurrentUser()!=null)
        {
            finish();
            startActivity(new Intent(this,Main_page.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.imageView);
        mauth = FirebaseAuth.getInstance();
        login=(Button)findViewById(R.id.Login_btn);
        login.setOnClickListener(this);
        signup=(Button)findViewById(R.id.Signup_btn);
        signup.setOnClickListener(this);

        custom=(TextView)findViewById(R.id.textView2);

        img.setMaxWidth(300);





    }

public void Signin()
{
    email=sign_email.getText().toString().trim();
    pass=sign_passwd.getText().toString().trim();
    if (email.isEmpty()) {
        sign_email.setError("Email is Required");
        sign_email.requestFocus();
        return;
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        sign_email.setError("please enter valid email address");
        sign_email.requestFocus();
        return;
    }
    if (pass.isEmpty()) {
        sign_passwd.setError("password is required");
        sign_passwd.requestFocus();
        return;
    }

    if (pass.length() < 6) {
        sign_passwd.setError("minimum password should be more than 5");
        sign_passwd.requestFocus();
        return;
    }
    pro.setVisibility(View.VISIBLE);
    mauth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            pro.setVisibility(View.GONE);
       if(task.isSuccessful())
       {
           Intent main_page=new Intent(MainActivity.this,Main_page.class);
           startActivity(main_page);
       }
       else

       {   Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();

       }

        }
    });
}

    public void registeruser() {
        email=sign_email.getText().toString().trim();
        pass=sign_passwd.getText().toString().trim();
        display_nasme=display.getText().toString().trim();
        if (email.isEmpty()) {
            sign_email.setError("Email is Required");
            sign_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            sign_email.setError("please enter valid email address");
            sign_email.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            sign_passwd.setError("password is required");
            sign_passwd.requestFocus();
            return;
        }
        if(display_nasme.isEmpty())
        {
            display.setError("display name required");
            display.requestFocus();
            return;
        }

        if (pass.length() < 6) {
            sign_passwd.setError("minimum password should be more than 5");
            sign_passwd.requestFocus();
            return;
        }
         user=mauth.getCurrentUser();

        pro.setVisibility(View.VISIBLE);
        mauth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pro.setVisibility(View.GONE);
    if(task.isSuccessful())
    {


            UserProfileChangeRequest profile=new UserProfileChangeRequest.Builder()
                    .setDisplayName(display_nasme)
                    .build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
               if(task.isSuccessful())
               {

                   paaaa=user.getDisplayName();

               }
                }
            });

            Toast.makeText(getApplicationContext(),"user is created",Toast.LENGTH_LONG).show();







        Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_LONG).show();
    }
    else
    {
        if(task.getException() instanceof FirebaseAuthUserCollisionException) {
            Toast.makeText(getApplicationContext(),"you are already registered",Toast.LENGTH_LONG).show();

        }
        else

        {
            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();

        }
    }
            }
        });

    }



    public void Dialouge_Signup()
    { AlertDialog.Builder mbuild = new AlertDialog.Builder(MainActivity.this);
        View vv = getLayoutInflater().inflate(R.layout.sign_up_dialouge, null);
        sign_email = (EditText) vv.findViewById(R.id.mail);
        sign_passwd = (EditText) vv.findViewById(R.id.passwod);
        display=(EditText)vv.findViewById(R.id.display_name);
        pro=(ProgressBar)vv.findViewById(R.id.progressBar);
        error_sign=(TextView)findViewById(R.id.sign_up_error);
        Button signup_btn = (Button) vv.findViewById(R.id.Sign_uup);
        custom=(TextView)vv.findViewById(R.id.already_member);
        custom.setOnClickListener(this);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registeruser();

            }
        });
        mbuild.setView(vv);
        AlertDialog dialog = mbuild.create();
        dialog.show();

    }

    public void Dialouge_Signin()
    {
        AlertDialog.Builder sign_in_Dialouge=new AlertDialog.Builder(MainActivity.this);
        View vv2=getLayoutInflater().inflate(R.layout.dialouge_layout,null);
        sign_email=(EditText)vv2.findViewById(R.id.ed_signin_mail);
        sign_passwd=(EditText)vv2.findViewById(R.id.ed_signin_password);
        pro=(ProgressBar)vv2.findViewById(R.id.progressBar1);
        custom=(TextView)vv2.findViewById(R.id.dont_have_account);
        custom.setOnClickListener(this);
        Button login_btn=(Button)vv2.findViewById(R.id.Login);
         error=(TextView)findViewById(R.id.error);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signin();
            }
        });
        sign_in_Dialouge.setView(vv2);
        AlertDialog dialog=sign_in_Dialouge.create();
        dialog.show();

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {
            case R.id.already_member:
                Dialouge_Signin();
                break;
            case R.id.dont_have_account:
                Dialouge_Signup();
                break;
            case R.id.Login_btn:
                Dialouge_Signin();
                break;
            case R.id.Signup_btn:
                Dialouge_Signup();
                break;

        }
    }
}