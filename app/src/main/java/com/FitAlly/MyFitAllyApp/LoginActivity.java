package com.FitAlly.MyFitAllyApp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.TimeZone;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvSignUup,forgot_textview;
    Button btnLogin;
    EditText email_address_edittext,password_edittext;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser currentuser;



    private int RC_SIGN_IN=1;
    private String TAG="LoginWithGoogle",currentUser,deviceID;
    TimeZone tz;
    String timeZone,tmeID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    initializefields();
//
    }

    private void initializefields() {

   tvSignUup=findViewById(R.id.signup_textview);
   mAuth= FirebaseAuth.getInstance();
   btnLogin=findViewById(R.id.login_button);

        forgot_textview=findViewById(R.id.forgot_textview);
        forgot_textview.setOnClickListener(this);
   email_address_edittext=findViewById(R.id.email_address_edittext);
   progressDialog=new ProgressDialog(this);
   password_edittext=findViewById(R.id.password_edittext);
//attach widgets to variables
   tvSignUup.setOnClickListener(this);
   btnLogin.setOnClickListener(this);

        tz = TimeZone.getDefault();
        timeZone=tz.getDisplayName(false, TimeZone.SHORT);
        tmeID=tz.getID();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.signup_textview: //clicking the signup text will take them to registerActivity.
                Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_button:
               loginIntoAccount();
                break;

            case R.id.forgot_textview:
//                Intent intent2=new Intent(LoginActivity.this, ForgotPasswordActivity.class);
//                startActivity(intent2);
                break;
        }

    }





    private void loginIntoAccount() { //Function that loggs into account

    String email=email_address_edittext.getText().toString();

    String password=password_edittext.getText().toString();

    if (TextUtils.isEmpty(email))
    {
        email_address_edittext.setError("enter email");
        email_address_edittext.requestFocus();
    }
    if (TextUtils.isEmpty(password))
    {
        password_edittext.setError("enter password");
        password_edittext.requestFocus();
    }
    else
    {
        progressDialog.setTitle("Log in");
        progressDialog.setMessage("please wait");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    progressDialog.dismiss();
                  currentUser=  mAuth.getCurrentUser().getUid(); //get the current user id that is logged
                  startHomeActivity();


                }
                else
                {
                    String message=task.getException().toString();
                    Toast.makeText(LoginActivity.this, "Invalid Email or Password " , Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        });
    }

    }

    private void startHomeActivity() {
        Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        Toast.makeText(LoginActivity.this, "login successful", Toast.LENGTH_SHORT).show();
    }

}
