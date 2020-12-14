package com.regexbyte.councildata.addUserToGroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.regexbyte.councildata.AddUserAdapter;
import com.regexbyte.councildata.AddUserGroupData;
import com.regexbyte.councildata.R;
import com.regexbyte.councildata.UserDetailActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import customfonts.EditText_Roboto_Regular;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddUsersActivity   extends AppCompatActivity implements AddUserAdapter.MyViewHolder.OnItemSkillClickListener{
    FloatingActionButton floatingActionButton;
    CircleImageView circleImageView,circleImageClose;
    EditText_Roboto_Regular groupNameField;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    String charity;


    private FirebaseAuth mAuth;
    String downloadUrl;
    String groupIDforImage,groupID;
    String groupname;
    AlertDialog dialog; private ArrayList<AddUserGroupData> dataArrayList;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private AddUserAdapter listadapter; private RecyclerView recyclerView;
    private androidx.appcompat.widget.SearchView searchView;
    private String steps;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
        charity = getIntent().getStringExtra("charity").toString();
       // steps = getIntent().getStringExtra("steps").toString();
        //ye wali line comment kee maine
//        groupname=getIntent().getStringExtra("groupname").toString();
//        downloadUrl=getIntent().getStringExtra("url");
        initializeFields();


    }


    private void initializeFields() {

        database= FirebaseDatabase.getInstance();

//        databaseReference= database.getReference("groups");
        dataArrayList=new ArrayList<>();
        mAuth= FirebaseAuth.getInstance();
//        initSwipeRefreshlayout();
        initRecycleView();

    }

    private void initRecycleView() {
        recyclerView=findViewById(R.id.recycler_view_groups);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
//
        if (recyclerView != null)
        {
            retreivelistgroups();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

//    private void initSwipeRefreshlayout() {
//        mSwipeRefreshLayout=findViewById(R.id.swipe_container);
//
//            this.mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                @Override
//                public void onRefresh() {
//
//                        retreivelistgroups();
//                        mSwipeRefreshLayout.setRefreshing(false);
//
//
//
//                }
//            });
//
//
//
//    }
    private void retreivelistgroups() {

        databaseReference=  FirebaseDatabase.getInstance().getReference().child("profiles");
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataArrayList.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    String name=snapshot.child("first").getValue(String.class) ;
                    AddUserGroupData newData;
                  if (name!=null)
                  {
                      dataArrayList.add(newData = new AddUserGroupData(name, snapshot.child("photoUrl").getValue(String.class),snapshot.child("userID").getValue(String.class),snapshot.child("current_weight").getValue(String.class)
                              , snapshot.child("WINS").getValue(String.class) ,snapshot.child("LOSSES").getValue(String.class) ,snapshot.child("target").getValue(String.class),snapshot.child("height").getValue(String.class)
                              ,snapshot.child("age").getValue(String.class)));

                  }


                }
                Collections.sort(dataArrayList, new Comparator<AddUserGroupData>() {
                    @Override
                    public int compare(AddUserGroupData o1, AddUserGroupData o2) {
                        return 0;
                    }
                });

                listadapter=new AddUserAdapter(AddUsersActivity.this,dataArrayList,AddUsersActivity.this::OnItemSkillClickListener);
                recyclerView.setAdapter(listadapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void OnItemSkillClickListener(View view, int position, long id, int viewType) {
        AddUserGroupData addUserGroupData=dataArrayList.get(position);
        Intent   intent=new Intent(AddUsersActivity.this, UserDetailActivity.class);
        intent.putExtra(UserDetailActivity.USERID,addUserGroupData.getUserID());
        intent.putExtra(UserDetailActivity.NAME,addUserGroupData.getUsername());
        intent.putExtra(UserDetailActivity.IMAGE,addUserGroupData.getDownloadUrl());
        intent.putExtra(UserDetailActivity.WINS,addUserGroupData.getWins());
        intent.putExtra(UserDetailActivity.LOSSES,addUserGroupData.getLosses());
        intent.putExtra(UserDetailActivity.CURRENT,addUserGroupData.getCurrentWei());
        intent.putExtra(UserDetailActivity.TARGET,addUserGroupData.getTarget());
        intent.putExtra(UserDetailActivity.CHARITY,charity);
        intent.putExtra(UserDetailActivity.STEPS,steps);
        intent.putExtra(UserDetailActivity.AGE,addUserGroupData.getAge());
        intent.putExtra(UserDetailActivity.HEIGHT,addUserGroupData.getHeight());
        startActivity(intent);


    }
}

