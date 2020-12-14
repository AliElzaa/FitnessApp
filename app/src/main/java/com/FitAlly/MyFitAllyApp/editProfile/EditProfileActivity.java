/*
 * Copyright 2018 Rozdoum
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.FitAlly.MyFitAllyApp.editProfile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;


import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.FitAlly.MyFitAllyApp.HomeActivity;
import com.FitAlly.MyFitAllyApp.R;
import com.FitAlly.MyFitAllyApp.Utils.ApplicationHelper;
import com.FitAlly.MyFitAllyApp.Utils.DatabaseHelper;
import com.FitAlly.MyFitAllyApp.Utils.GlideApp;
import com.FitAlly.MyFitAllyApp.Utils.ImageUtil;
import com.FitAlly.MyFitAllyApp.Utils.Preferences;
import com.FitAlly.MyFitAllyApp.Utils.UploadImagePrefix;
import com.FitAlly.MyFitAllyApp.pickImageBase.PickImageActivity;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity<V extends EditProfileView, P extends EditProfilePresenter<V>> extends PickImageActivity<V, P> implements EditProfileView {

    private static final String TAG = EditProfileActivity.class.getSimpleName();
    // UI references.
    private EditText nameEditText;
    CircleImageView imageView;
    private ProgressBar avatarProgressBar;
    Button btnUpdate;
    private EditText name_edittext,ed_email, ed_target, ed_current_weight ,ed_height;
    ProgressDialog progressDialog;

    String name,  age,kg_per_week,current_wei,target_wei,height;
    String currentUser;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    SharedPreferences sharedPreferences;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    String groupIDforImage, deviceID;

    Uri downloadUrl,UriFromFirebase;
    Context context;
    Spinner spinner;
Preferences preferences;
    String[] array1 = new String[]{"0.25 kg", "0.50 kg","0.75 kg","1.0 kg","1.25 kg"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

//
        initializefields();




        imageView.setOnClickListener(this::onSelectImageClick);

//

    }
    private void initializefields() {
        context=getApplicationContext();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        preferences=new Preferences(this);
        progressDialog=new ProgressDialog(this);
        mAuth= FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser().getUid();
        name_edittext=findViewById(R.id.ed_first);
        ed_email=findViewById(R.id.ed_email);
        ed_current_weight=findViewById(R.id.ed_current_weight);
        ed_target=findViewById(R.id.ed_target);
        ed_height=findViewById(R.id.ed_height);
//Identifying the variables to the widgets on screen
        spinner=findViewById(R.id.spinner);

        imageView =(CircleImageView) findViewById(R.id.img_profile);

        initUpdateButton();
        initDataSpinner();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { //function to select a weight to lose from the drop down list
                kg_per_week=  array1[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initDataSpinner() {



        spinner.setAdapter(new ArrayAdapter<String>(
                getApplicationContext() ,
                R.layout.items_view,
                array1
        ));

    }

    private void initUpdateButton() {

        btnUpdate = findViewById(R.id.btn_create_profile);
        if (btnUpdate != null) {
            btnUpdate.setOnClickListener(v -> presenter.onUpdateAccount(btnUpdate));
        }

    }

    public void updateAccount() { //function to execute the updates from the user-entered information.

        name=name_edittext.getText().toString();

        age=ed_email.getText().toString();
        current_wei=ed_current_weight.getText().toString();
        target_wei=ed_target.getText().toString();
        height=ed_height.getText().toString();

        if (TextUtils.isEmpty(name))
        {
            name_edittext.setError("enter name");
            name_edittext.requestFocus();
        }else if (TextUtils.isEmpty(age))
        {
            ed_email.setError("enter age");
            ed_email.requestFocus();

        }
        else if (TextUtils.isEmpty(current_wei))
        {
            ed_current_weight.setError("enter Weight");
            ed_current_weight.requestFocus();

        }
        else if (TextUtils.isEmpty(target_wei))
        {
            ed_target.setError("enter Target");
            ed_target.requestFocus();

        }
        else if (TextUtils.isEmpty(height))
        {
            ed_height.setError("enter height");
            ed_height.requestFocus();
        }
        else if (name!=null &&age!=null &&current_wei!=null &&target_wei!=null &&height!=null)
        {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),  // CHECKING FOR LOCATION PERMISSIONS
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                progressDialog.setTitle("Updating account");
                progressDialog.setMessage("Please wait...");
                progressDialog.setCanceledOnTouchOutside(true);
                progressDialog.show();
                presenter.DataToFirebase();
            }
            else
            {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 7011);
                }

            }

        }





    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) { //ask for permission
        if (requestCode == 7011) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void sendDataToFirebase() {

//        email=mAuth.getCurrentUser().getEmail();
        if (imageUri!= null)
        {
      //  saveImageToFirebase();
         //preferences.setImageUrl(String.valueOf(imageUri));
            presenter.saveImage();
//            showToast("uri not null " + String.valueOf(imageUri));
        }
        else
        {
//
            Bitmap bm=((BitmapDrawable)imageView.getDrawable()).getBitmap();
            imageUri=getImageUri(this,bm);
            //imageUri=UriFromFirebase;
//            showToast("uri null " + String.valueOf(imageUri));
//            showToast("uri view " + String.valueOf(imageUri));
            presenter.saveImage();
        }
    }
    public void saveImageToFirebase() { //the image that is uploaded by the user can get saved to firebase

        DatabaseHelper databaseHelper= ApplicationHelper.getDatabaseHelper();
        groupIDforImage=databaseReference.push().getKey();
        final String imageTitle = ImageUtil.generateImageTitle(UploadImagePrefix.USER,currentUser);
        UploadTask uploadTask = databaseHelper.uploadImage(imageUri, imageTitle);

        StorageReference riversRef = databaseHelper.getStorageReference().child(DatabaseHelper.IMAGES_STORAGE_KEY + "/" + imageTitle);


        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return riversRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    downloadUrl = task.getResult();

//                    preferences.setImage(downloadUrl.toString());
                    createProfileFirebase();

                    showSnackBar("image uploaded");

                } else {
                    showSnackBar("upload failed");

                    progressDialog.dismiss();
                }
            }
        });

//


    }

    private void createProfileFirebase() {

        HashMap<String,String> profileMap=new HashMap<>();
        profileMap.put("userID",currentUser);
        profileMap.put("first",name);

        profileMap.put("photoUrl",String.valueOf(downloadUrl));
        profileMap.put("imageUri",String.valueOf(imageUri));
        profileMap.put("age",age);
        profileMap.put("current_weight",current_wei);

        profileMap.put("target",target_wei);
        profileMap.put("kg_per_week",kg_per_week);
        profileMap.put("WINS", String.valueOf(0));
        profileMap.put("LOSSES","0");
        profileMap.put("registrationToken"," ");
        profileMap.put("height",height);
        databaseReference.child("profiles").child(currentUser).setValue(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
////
                    showSnackBar("account updated");
                    progressDialog.dismiss();
                    Intent intent=new Intent(EditProfileActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
//                    profileManager = ProfileManager.getInstance(context.getApplicationContext());
//                    profileManager.addRegistrationToken(deviceID,currentUser);
//                   // Toast.makeText(EditProfileActivity.this, "account updated", Toast.LENGTH_SHORT).show();
//                    sendDataToServer();

                }
                else
                {
                    String message=task.getException().toString();
                    showSnackBar("error"+message);
                 //   Toast.makeText(EditProfileActivity.this, "error"+message, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });

    }





    @NonNull
    @Override
    public P createPresenter() {
        if (presenter == null) {
            return (P) new EditProfilePresenter(this);
        }
        return presenter;
    }

    protected void initContent() {
        presenter.loadProfile();
    }

    @Override
    public ProgressBar getProgressView() {
        return avatarProgressBar;
    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }


    @Override
    public void onImagePikedAction() {
        startCropImageActivity();
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle result of pick image chooser
        super.onActivityResult(requestCode, resultCode, data);
        handleCropImageResult(requestCode, resultCode, data);
    }

    @Override
    public void setName(String username) {
       // nameEditText.setText(username);
    }

    @Override
    public void setProfilePhoto(String photoUrl) {
        ImageUtil.loadImage(GlideApp.with(this), photoUrl, imageView, new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                avatarProgressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                avatarProgressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }

    @Override
    public String getNameText() {
        return "";//nameEditText.getText().toString();
    }

    @Override
    public void setNameError(@Nullable String string) {
//        nameEditText.setError(string);
//        nameEditText.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.save:
//                presenter.attemptCreateProfile(imageUri);
//                return true;
//            default:
                return super.onOptionsItemSelected(item);
//        }
    }
    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    protected void onStart() {
        super.onStart();
    RetreiveUserInfo();
    }

    private void RetreiveUserInfo() {
        databaseReference.child("profiles").child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ((dataSnapshot.exists())&&(dataSnapshot.hasChild("first"))&&(dataSnapshot.hasChild("photoUrl")))//if the user has created account or not
                {
                    String username=dataSnapshot.child("first").getValue().toString();
                    String target=dataSnapshot.child("target").getValue().toString();
                    String current_weight=dataSnapshot.child("current_weight").getValue().toString();
                    String image=dataSnapshot.child("photoUrl").getValue().toString();
                    String age=dataSnapshot.child("age").getValue().toString();
                    String height=dataSnapshot.child("height").getValue().toString();
//                    preferences.setFirst(username);
//                    preferences.setLast(last);
                    UriFromFirebase= Uri.parse(String.valueOf(dataSnapshot.child("imageUri").getValue())) ;
                    name_edittext.setText(username);
                    ed_email.setText(age);
                    ed_current_weight.setText(current_weight);
                    ed_target.setText(target);
                    ed_height.setText(height);




                }
                else if ((dataSnapshot.exists())&&(dataSnapshot.hasChild("imageUri")))
                {
                    UriFromFirebase= Uri.parse(String.valueOf(dataSnapshot.child("imageUri").getValue())) ;
                }
                else
                {
                    showSnackBar("please update your profile");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

