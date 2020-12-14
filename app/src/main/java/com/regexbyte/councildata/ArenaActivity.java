package com.regexbyte.councildata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.regexbyte.councildata.Utils.ApplicationHelper;
import com.regexbyte.councildata.Utils.DatabaseHelper;
import com.regexbyte.councildata.addUserToGroup.AddUsersActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ArenaActivity extends AppCompatActivity implements CompetitionAdapter.MyViewHolder.OnItemSkillClickListener{

    DatabaseReference databaseReference;
    FirebaseDatabase database;
    private DatabaseHelper databaseHelper;
    private FirebaseAuth mAuth;
    private String userID;
    private ArrayList<CompetitionData> dataArrayList;
    ArrayList<String>arraylist_groupid;
    CompetitionAdapter listadapter;
    RecyclerView recycler_view_groups;
    String secondweighht="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena);
        database= FirebaseDatabase.getInstance();
        databaseHelper = ApplicationHelper.getDatabaseHelper();
        databaseReference= database.getReference("groups");

        mAuth=FirebaseAuth.getInstance();
        userID=mAuth.getUid();
        dataArrayList=new ArrayList<>();
        arraylist_groupid=new ArrayList<>();
        recycler_view_groups=findViewById(R.id.recycler_view_groups);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view_groups.setLayoutManager(mLayoutManager);
        recycler_view_groups.setItemAnimator(new DefaultItemAnimator());
        recycler_view_groups.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        getGroupDetails();
    }

    private void getGroupDetails() {
        arraylist_groupid.clear();
        databaseReference=  FirebaseDatabase.getInstance().getReference().child("groups");
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataArrayList.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {

                        String groupId=snapshot.child("groupId").getValue(String.class);


                    CompetitionData  competitionData;
                    dataArrayList.add(competitionData = new CompetitionData(snapshot.child("FirstuserID").getValue(String.class),
                            snapshot.child("Firstcurrent_weight").getValue(String.class),snapshot.child("FirstTarget").getValue(String.class),
                            snapshot.child("Firstimage").getValue(String.class),
                            snapshot.child("FirstUserName").getValue(String.class),
                            snapshot.child("FirstUserAge").getValue(String.class),
                            snapshot.child("FirstUserHeight").getValue(String.class),
                            snapshot.child("charity").getValue(String.class),
                            snapshot.child("SecondUserName").getValue(String.class),
                            snapshot.child("SecondCurrent_weight").getValue(String.class),
                            snapshot.child("SecondImage").getValue(String.class),
                            snapshot.child("SecondTarget").getValue(String.class),
                            snapshot.child("SecondUserID").getValue(String.class),snapshot.child("groupname").getValue(String.class)
                            ,snapshot.child("SecondAge").getValue(String.class),snapshot.child("SecondUserHeight").getValue(String.class)
                            ,snapshot.child("totalSteps").getValue(String.class)
                            ,snapshot.child("status").getValue(String.class)
                            ,snapshot.child("winner").getValue(String.class)
                            ,groupId));
                    secondweighht=snapshot.child("SecondCurrent_weight").getValue(String.class);
                }

                listadapter = new CompetitionAdapter(ArenaActivity.this, dataArrayList,ArenaActivity.this::OnItemSkillClickListener);
                recycler_view_groups.setAdapter(listadapter);
//                Collections.sort(dataArrayList, new Comparator<AddUserGroupData>() {
//                    @Override
//                    public int compare(AddUserGroupData o1, AddUserGroupData o2) {
//                        return 0;
//                    }
//                });

//                listadapter=new AddUserAdapter(AddUsersActivity.this,dataArrayList,AddUsersActivity.this::OnItemSkillClickListener);
//                recyclerView.setAdapter(listadapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        DatabaseReference databaseReference = databaseHelper
//                .getDatabaseReference()
//                .child("groups");
//        ValueEventListener valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                dataArrayList.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
//                    String groupId=snapshot.child("groupId").getValue(String.class);
//                    Toast.makeText(ArenaActivity.this, ""+groupId, Toast.LENGTH_SHORT).show();
//                    arraylist_groupid.add(groupId);
//
//                }
////                getGroupMembers(arraylist_groupid);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
////                LogUtil.logError(TAG, "getCommentsList(), onCancelled", new Exception(databaseError.getMessage()));
//            }
//        });
    }


    @Override
    public void OnItemSkillClickListener(View view, int position, long id, int viewType) {
       CompetitionData competitionData= dataArrayList.get(position);
        Intent intent=new Intent(ArenaActivity.this,CompetitionDetailActivity.class);
        intent.putExtra(CompetitionDetailActivity.FIRST_USERNAME,competitionData.getFirstUserName());
        intent.putExtra(CompetitionDetailActivity.FIRST_IMAGE,competitionData.getFirstimage());
        intent.putExtra(CompetitionDetailActivity.FIRST_TARGET,competitionData.getFirstTarget());
        intent.putExtra(CompetitionDetailActivity.FIRST_CURRENT,competitionData.getFirstcurrent_weight());
        intent.putExtra(CompetitionDetailActivity.SECOND_TARGET,competitionData.getSecondTarget());
        intent.putExtra(CompetitionDetailActivity.SECOND_IMAGE,competitionData.getSecondImage());
              intent.putExtra(CompetitionDetailActivity.SECOND_CURRENT,secondweighht);
              Log.e("getsecondcurrentweight", secondweighht);
        intent.putExtra(CompetitionDetailActivity.SECOND_USERNAME,competitionData.getSecondUserName());
        intent.putExtra(CompetitionDetailActivity.CHARITY,competitionData.getCharity());
        intent.putExtra(CompetitionDetailActivity.FIRST_AGE,competitionData.getFirstUserAge());
        intent.putExtra(CompetitionDetailActivity.FIRST_HEIGHT,competitionData.getFirstUserHeight());
        intent.putExtra(CompetitionDetailActivity.SECOND_AGE,competitionData.getSecondAge());
        intent.putExtra(CompetitionDetailActivity.SECOND_HEIGHT,competitionData.getSecondUserHeight());
        intent.putExtra(CompetitionDetailActivity.TOTAL_STEPS,competitionData.getTotalSteps());
        intent.putExtra(CompetitionDetailActivity.FIRST_USER_ID,competitionData.getFirstuserID());
        intent.putExtra(CompetitionDetailActivity.SECOND_USER_ID,competitionData.getSecondUserID());
        intent.putExtra(CompetitionDetailActivity.STATUS,competitionData.getStatus());
        intent.putExtra(CompetitionDetailActivity.WINNER,competitionData.getWinner());
        intent.putExtra(CompetitionDetailActivity.GROUP_ID,competitionData.getGroupid());

       //intent.putExtra(CompetitionDetailActivity.GROUP_ID,competitionData.getSecondCurrent_weight());


        startActivity(intent);

    }
}
