package com.regexbyte.councildata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.regexbyte.councildata.Utils.DatabaseHelper;
import com.regexbyte.councildata.Utils.GlideApp;
import com.regexbyte.councildata.Utils.ImageUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailActivity extends AppCompatActivity implements View.OnClickListener{


    public static final String IMAGE="image";
    public static final String NAME="name";
    public static final String WINS="wins";
    public static final String LOSSES="losses";
    public static final String CURRENT="current";
    public static final String USERID="userid";
    public static final String TARGET="target";
    public static final String CHARITY="charity";
    public static final String STEPS="steps";
    public static final String AGE="age";
    public static final String HEIGHT="height";
    String name,image,wins,losses,current,steps,age,height;
    CircleImageView imageUser;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    private DatabaseHelper databaseHelper;
    private FirebaseAuth mAuth;
    TextView tv_name,tv_current,tv_wins,tv_losses;
    Button addButton;
    AlertDialog dialog;
    EditText ed_competition_name;
    String userID,groupID,memberID;
    private String groupname,target,charity;
    HashMap<String,String> groupMap=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        memberID=getIntent().getStringExtra(USERID);
      name=  getIntent().getStringExtra(NAME);
      charity=getIntent().getStringExtra(CHARITY);
      image=  getIntent().getStringExtra(IMAGE);
      wins= getIntent().getStringExtra(WINS);
        steps= getIntent().getStringExtra(STEPS);
       losses= getIntent().getStringExtra(LOSSES);
       age=getIntent().getStringExtra(AGE);
       height=getIntent().getStringExtra(HEIGHT);
       current= getIntent().getStringExtra(CURRENT);
       target=getIntent().getStringExtra(TARGET);

        imageUser=findViewById(R.id.imageUser);
        tv_name=findViewById(R.id.tv_name);
        tv_current=findViewById(R.id.tv_current);
        tv_wins=findViewById(R.id.tv_wins);
        tv_losses=findViewById(R.id.tv_losses);
        addButton=findViewById(R.id.addButton);
        addButton.setOnClickListener(this);
        mAuth=FirebaseAuth.getInstance();
        mAuth= FirebaseAuth.getInstance();
        userID=mAuth.getUid();
        database= FirebaseDatabase.getInstance();

        databaseReference= database.getReference("groups");




        populateData();
    }

    private void populateData() {
        ImageUtil.loadImage(GlideApp.with(this), String.valueOf(image), imageUser);
        tv_current.setText("Current Weight : "+current);
        tv_losses.setText("Losses : "+losses);
        tv_wins.setText("Wins : "+wins);
        tv_name.setText("Name : "+name);
        groupMap.put("charity",charity);
        groupMap.put("SecondUserName",name);
        groupMap.put("SecondCurrent_weight",current);
        groupMap.put("SecondImage",image);
        groupMap.put("SecondTarget",target);
        groupMap.put("SecondUserID",memberID);
        groupMap.put("SecondAge",age);
        groupMap.put("SecondUserHeight",height);
        groupMap.put("totalSteps",steps);
        groupMap.put("status","active");
        groupMap.put("winner","pending");
        groupMap.put("weightdifference_second",String.valueOf(Double.parseDouble(current)-Double.parseDouble(target)));
        groupMap.put("totalSteps","100");


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.addButton:
                openDialogCreateGroup();
                break;
        }
    }

    public void openDialogCreateGroup() {

        AlertDialog.Builder builder=new AlertDialog.Builder(UserDetailActivity.this,R.style.AlertDialog);
        View vw = getLayoutInflater().inflate(R.layout.layout_dialog_like, null);

       CircleImageView circleImageClose=vw.findViewById(R.id.imgClose);
        ed_competition_name=vw.findViewById(R.id.edgroupName);
        Button btnCreate=vw.findViewById(R.id.dialog_like_bt);
        builder.setView(vw);
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
        circleImageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupname=  ed_competition_name .getText().toString();

                if (TextUtils.isEmpty(groupname))
                {
                    ed_competition_name.setError("Competition name is required");
                    ed_competition_name.requestFocus();

                }
                else
                {
                    sendDataToFirebase(groupname);
                }
            }
        });
    }


    private void sendDataToFirebase(String name) {
        databaseReference= database.getReference("groups");
        groupID=databaseReference.push().getKey();

        groupMap.put("groupname",name);
        groupMap.put("groupId",groupID);

//        groupMap.put("userID",mAuth.getUid());
//        groupMap.put("userType","admin");
        RetreiveUserInfo();



    }

    private void createGroupsFirebase(HashMap<String, String> groupMap,String groupName) {
        databaseReference= database.getReference("groupDetails");

        databaseReference.child(groupName).setValue(groupMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {

                    dialog.dismiss();
                    Toast.makeText(UserDetailActivity.this, groupname+" created successfuly", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(UserDetailActivity.this, "Some thing went wrong,please try again.", Toast.LENGTH_SHORT).show();


                }

            }
        });
    }

    private void RetreiveUserInfo() {
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("profiles").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ((dataSnapshot.exists())&&(dataSnapshot.hasChild("first"))&&(dataSnapshot.hasChild("photoUrl")))//if the user has created account or not
                {



                    String userID=dataSnapshot.child("userID").getValue().toString();
                    String username=dataSnapshot.child("first").getValue().toString();
                    String current_weight=dataSnapshot.child("current_weight").getValue().toString();
                    String wins=dataSnapshot.child("WINS").getValue().toString();
                    String losses=dataSnapshot.child("LOSSES").getValue().toString();
                    String image=dataSnapshot.child("photoUrl").getValue().toString();
                    String target=dataSnapshot.child("target").getValue().toString();
                    String age=dataSnapshot.child("age").getValue().toString();
                    String height=dataSnapshot.child("height").getValue().toString();
                    groupMap.put("FirstuserID",userID);
                    groupMap.put("Firstcurrent_weight",current_weight);
                    groupMap.put("FirstTarget",target);
                    groupMap.put("Firstimage",image);
                    groupMap.put("FirstUserName",username);
                    groupMap.put("FirstUserAge",age);
                    groupMap.put("FirstUserHeight",height);
                    groupMap.put("weightdifference_first",String.valueOf(Double.parseDouble(current_weight)-Double.parseDouble(target)));                    createCompetition();





                }
                else
                {
                    Toast.makeText(UserDetailActivity.this, "User not active,complete profile", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void createCompetition()
    {
        databaseReference= database.getReference("groups");
        databaseReference.child(groupID) .setValue(groupMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    dialog.dismiss();
                    Toast.makeText(UserDetailActivity.this, groupname+" created successfuly", Toast.LENGTH_SHORT).show();
//                    createGroupsFirebase(groupMap,name);
                }
                else
                {
                    Toast.makeText(UserDetailActivity.this, "Some thing went wrong,please try again.", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

//    private void addMember()
//    {
//        HashMap<String,String> groupMap=new HashMap<>();
//        groupMap.put("groupId",groupID);
//        groupMap.put("userID",memberID);
//        groupMap.put("userType","user");
//        groupMap.put("groupname",groupname);
//
//        databaseReference.child(groupID).child(memberID).setValue(groupMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful())
//                {
//                    dialog.dismiss();
//                    Toast.makeText(UserDetailActivity.this, groupname+" created successfuly", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(UserDetailActivity.this, "Some thing went wrong,please try again.", Toast.LENGTH_SHORT).show();
//
//                }
//
//            }
//        });
//    }

}
