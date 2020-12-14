/**
 * Copyright (C) 2014 Samsung Electronics Co., Ltd. All rights reserved.
 *
 * Mobile Communication Division,
 * Digital Media & Communications Business, Samsung Electronics Co., Ltd.
 *
 * This software and its documentation are confidential and proprietary
 * information of Samsung Electronics Co., Ltd.  No part of the software and
 * documents may be copied, reproduced, transmitted, translated, or reduced to
 * any electronic medium or machine-readable form without the prior written
 * consent of Samsung Electronics.
 *
 * Samsung Electronics makes no representations with respect to the contents,
 * and assumes no responsibility for any errors that might appear in the
 * software and documents. This publication and the contents hereof are subject
 * to change without notice.
 */

package com.regexbyte.councildata.ui;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.regexbyte.councildata.R;
import com.regexbyte.councildata.addUserToGroup.AddUsersActivity;
import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult;
import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthDataService;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthPermissionManager;
import com.samsung.android.sdk.healthdata.HealthPermissionManager.PermissionKey;
import com.samsung.android.sdk.healthdata.HealthPermissionManager.PermissionType;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class MainActivity extends Activity {

    public static final String APP_TAG = "SimpleHealth";

    @BindView(R.id.editHealthDateValue1)
    EditText mStepCountTv;

    String charity;
    private HealthDataStore mStore;
    private StepCountReporter mReporter;

    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList  barEntries = new ArrayList<>();
    ArrayList<String> list= new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    public String currentUser;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("main activity", "main act");
        database= FirebaseDatabase.getInstance();
        barEntries = new ArrayList<>();
        barChart = findViewById(R.id.newchartnew);

        databaseReference= database.getReference("profiles");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Profiledata xx=dataSnapshot.getValue(Profiledata.class);

                list.add(xx.getWINS());
               //  addittolist(xx.getWINS());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       // Log.e("here", list.get(0));

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                    barEntries.add(new BarEntry(2f, Float.parseFloat(list.get(0))));
                    barEntries.add(new BarEntry(4f, Float.parseFloat(list.get(1))));
                    barEntries.add(new BarEntry(6f, Float.parseFloat(list.get(2))));
                    barEntries.add(new BarEntry(8f, Float.parseFloat(list.get(3))));
                    barEntries.add(new BarEntry(7f, Float.parseFloat(list.get(4))));

                barDataSet = new BarDataSet(barEntries, "Wins");
                barData = new BarData(barDataSet);
                barChart.setData(barData);
                barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                barDataSet.setValueTextColor(Color.BLACK);
                barDataSet.setValueTextSize(18f);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            public void onCancelled(FirebaseError firebaseError) { }
        });


        barDataSet = new BarDataSet(barEntries, "All wins");
        barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(18f);
//
    }
    private void addittolist(String wins)
    {

        barEntries.add(new BarEntry(2f, Float.parseFloat(wins)));
        barEntries.add(new BarEntry(4f, 10));
        barEntries.add(new BarEntry(6f, 10));
        barEntries.add(new BarEntry(8f, 10));
        barEntries.add(new BarEntry(7f, 10));

    }
}


//        charity=    getIntent().getStringExtra("charity");
//        Button btn_next=findViewById(R.id.btn_next);
//        btn_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//              String steps=  mStepCountTv.getText().toString();
//              if (TextUtils.isEmpty(steps))
//              {
//                  Toast.makeText(MainActivity.this, "Enter Steps", Toast.LENGTH_SHORT).show();
//              }
//              else {
//                  Intent intent=new Intent(MainActivity.this, AddUsersActivity.class);
//                  intent.putExtra("charity",charity);
//                  intent.putExtra("steps",steps);
//                  startActivity(intent);
//              }
//
//            }
//        });
//        // Create a HealthDataStore instance and set its listener
//        mStore = new HealthDataStore(this, mConnectionListener);
//        // Request the connection to the health data store
//        mStore.connectService();
//    }
//
//    @Override
//    public void onDestroy() {
//        mStore.disconnectService();
//        super.onDestroy();
//    }
//
//    private final HealthDataStore.ConnectionListener mConnectionListener = new HealthDataStore.ConnectionListener() {
//
//        @Override
//        public void onConnected() {
//            Log.d(APP_TAG, "Health data service is connected.");
//            mReporter = new StepCountReporter(mStore);
//            if (isPermissionAcquired()) {
//                mReporter.start(mStepCountObserver);
//            } else {
//                requestPermission();
//            }
//        }
//
//        @Override
//        public void onConnectionFailed(HealthConnectionErrorResult error) {
//            Log.d(APP_TAG, "Health data service is not available.");
//            showConnectionFailureDialog(error);
//        }
//
//        @Override
//        public void onDisconnected() {
//            Log.d(APP_TAG, "Health data service is disconnected.");
//            if (!isFinishing()) {
//                mStore.connectService();
//            }
//        }
//    };
//
//    private void showPermissionAlarmDialog() {
//        if (isFinishing()) {
//            return;
//        }
//
//        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
//        alert.setTitle(R.string.notice)
//                .setMessage(R.string.msg_perm_acquired)
//                .setPositiveButton(R.string.ok, null)
//                .show();
//    }
//
//    private void showConnectionFailureDialog(final HealthConnectionErrorResult error) {
//        if (isFinishing()) {
//            return;
//        }
//
//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//
//        if (error.hasResolution()) {
//            switch (error.getErrorCode()) {
//                case HealthConnectionErrorResult.PLATFORM_NOT_INSTALLED:
//                    alert.setMessage(R.string.msg_req_install);
//                    break;
//                case HealthConnectionErrorResult.OLD_VERSION_PLATFORM:
//                    alert.setMessage(R.string.msg_req_upgrade);
//                    break;
//                case HealthConnectionErrorResult.PLATFORM_DISABLED:
//                    alert.setMessage(R.string.msg_req_enable);
//                    break;
//                case HealthConnectionErrorResult.USER_AGREEMENT_NEEDED:
//                    alert.setMessage(R.string.msg_req_agree);
//                    break;
//                default:
//                    alert.setMessage(R.string.msg_req_available);
//                    break;
//            }
//        } else {
//            alert.setMessage(R.string.msg_conn_not_available);
//        }
//
//        alert.setPositiveButton(R.string.ok, (dialog, id) -> {
//            if (error.hasResolution()) {
//                error.resolve(MainActivity.this);
//            }
//        });
//
//        if (error.hasResolution()) {
//            alert.setNegativeButton(R.string.cancel, null);
//        }
//
//        alert.show();
//    }
//
//    private boolean isPermissionAcquired() {
//        PermissionKey permKey = new PermissionKey(HealthConstants.StepCount.HEALTH_DATA_TYPE, PermissionType.READ);
//        HealthPermissionManager pmsManager = new HealthPermissionManager(mStore);
//        try {
//            // Check whether the permissions that this application needs are acquired
//            Map<PermissionKey, Boolean> resultMap = pmsManager.isPermissionAcquired(Collections.singleton(permKey));
//            return resultMap.get(permKey);
//        } catch (Exception e) {
//            Log.e(APP_TAG, "Permission request fails.", e);
//        }
//        return false;
//    }
//
//    private void requestPermission() {
//        PermissionKey permKey = new PermissionKey(HealthConstants.StepCount.HEALTH_DATA_TYPE, PermissionType.READ);
//        HealthPermissionManager pmsManager = new HealthPermissionManager(mStore);
//        try {
//            // Show user permission UI for allowing user to change options
//            pmsManager.requestPermissions(Collections.singleton(permKey), MainActivity.this)
//                    .setResultListener(result -> {
//                        Log.d(APP_TAG, "Permission callback is received.");
//                        Map<PermissionKey, Boolean> resultMap = result.getResultMap();
//
//                        if (resultMap.containsValue(Boolean.FALSE)) {
//                            updateStepCountView("");
//                            showPermissionAlarmDialog();
//                        } else {
//                            // Get the current step count and display it
//                            mReporter.start(mStepCountObserver);
//                        }
//                    });
//        } catch (Exception e) {
//            Log.e(APP_TAG, "Permission setting fails.", e);
//        }
//    }
//
//    private StepCountReporter.StepCountObserver mStepCountObserver = count -> {
//        Log.d(APP_TAG, "Step reported : " + count);
//        updateStepCountView(String.valueOf(count));
//    };
//
//    private void updateStepCountView(final String count) {
//        runOnUiThread(() -> mStepCountTv.setText(count));
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(android.view.MenuItem item) {
//
//        if (item.getItemId() == R.id.connect) {
//            requestPermission();
//        }
//
//        return true;
//    }
//
//
//}
