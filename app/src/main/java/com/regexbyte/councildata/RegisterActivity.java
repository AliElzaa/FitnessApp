package com.regexbyte.councildata;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.regexbyte.councildata.Utils.ImageUtil;
import com.regexbyte.councildata.Utils.Preferences;
import com.regexbyte.councildata.editProfile.EditProfileActivity;


import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
   private Button register_button;
    private TextView signin_textview;
    private EditText name_edittext,lastname_edittext, email_address_edittext, phonenumber_edittext, password_edittext, confirm_password_edittext;
    private FirebaseAuth mauth;
    private ProgressDialog progressDialog;
    private DatabaseReference rootref;
    SharedPreferences sharedpreferences;
    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mauth= FirebaseAuth.getInstance();
        rootref= FirebaseDatabase.getInstance().getReference();
        initializefields();
    }

    private void initializefields() {
        preferences=new Preferences(getApplicationContext());
//        name_edittext = findViewById(R.id.name_edittext);
        register_button = findViewById(R.id.register_button);
        signin_textview = findViewById(R.id.signin_textview);
        password_edittext = findViewById(R.id.password_edittext);
//        lastname_edittext=findViewById(R.id.lastname_edittext);
//        phonenumber_edittext = findViewById(R.id.phonenumber_edittext);
        email_address_edittext = findViewById(R.id.email_address_edittext);
        confirm_password_edittext = findViewById(R.id.confirm_password_edittext);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        progressDialog=new ProgressDialog(this);
        register_button.setOnClickListener(this);
        signin_textview.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.register_button:
              createAccount();
                break;
                case R.id.signin_textview:
                    break;
        }
    }

    private void createAccount() {

//   String name=name_edittext.getText().toString();
//   String last=lastname_edittext.getText().toString();
   String email=email_address_edittext.getText().toString();
   String password=password_edittext.getText().toString();
   String repassword=confirm_password_edittext.getText().toString();
//   String phoneNo= phonenumber_edittext.getText().toString();

//   if (TextUtils.isEmpty(name))
//   {
//       name_edittext.setError("enter name");
//       name_edittext.requestFocus();
//   }
//        if (TextUtils.isEmpty(last))
//        {
//            lastname_edittext.setError("enter last name");
//            lastname_edittext.requestFocus();
//
//        }
//        if (TextUtils.isEmpty(phoneNo))
//        {
//            phonenumber_edittext.setError("enter phone no");
//            phonenumber_edittext.requestFocus();
//
//        }
   if (TextUtils.isEmpty(email))
   {
       email_address_edittext.setError("enter email");
       email_address_edittext.requestFocus();

   }
   if (TextUtils.isEmpty(password))
   {
       password_edittext.setError("enter email");
       password_edittext.requestFocus();
   }
   if (TextUtils.isEmpty(repassword))
   {
       confirm_password_edittext.setError("enter password");
       confirm_password_edittext.requestFocus();
   }
else
   {
       if (password.equals(repassword))
       {
       progressDialog.setTitle("Creating account");
       progressDialog.setMessage("Please wait...");
       progressDialog.setCanceledOnTouchOutside(true);
       progressDialog.show();


   mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
       @Override
       public void onComplete(@NonNull Task<AuthResult> task) {
      if (task.isSuccessful())
      {
          String userID=mauth.getCurrentUser().getUid();
          String imageUri = ImageUtil.getURLForResource(R.drawable.ic_stub);
          Map<String, String> parameters=new HashMap<String, String>();
          parameters.put("imageUri",imageUri);
          rootref.child("profiles").child(userID).setValue(parameters);
          Toast.makeText(RegisterActivity.this, "account created..", Toast.LENGTH_SHORT).show();
          SharedPreferences.Editor editor =  sharedpreferences.edit();
//          editor.putString(preferences.FirstName,name);
//          editor.putString(preferences.LASTName,last);
          editor.putString(preferences.EMAIL,email);
          editor.putString(preferences.PASSWORD,password);
          //editor.putString(preferences.imageUrlPath,imageUrl);

          editor.putBoolean(preferences.IsfirstTime,false);
          //editor.putString(preferences.PHONE,phoneNo);
          editor.apply();
          progressDialog.dismiss();
          Intent intent=new Intent(RegisterActivity.this, EditProfileActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

          startActivity(intent);
          finish();

      }
      else
      {
          String message=task.getException().toString();
          Toast.makeText(RegisterActivity.this, "error "+message, Toast.LENGTH_SHORT).show();
          progressDialog.dismiss();
      }
       }
   });
   }
   else
       {
           Toast.makeText(RegisterActivity.this, "please match password with repassword", Toast.LENGTH_SHORT).show();
       }
    }
    }
}
