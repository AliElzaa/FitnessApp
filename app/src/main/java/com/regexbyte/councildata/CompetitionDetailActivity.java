package com.regexbyte.councildata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;

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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.regexbyte.councildata.Utils.ImageUtil;
import com.regexbyte.councildata.Utils.Preferences;
import com.regexbyte.councildata.editProfile.EditProfileActivity;
import com.regexbyte.councildata.ui.MainActivity;
import com.regexbyte.councildata.ui.StepCountReporter;
import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult;
import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthPermissionManager;
import com.regexbyte.councildata.Utils.GlideApp;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Math.round;

public class CompetitionDetailActivity extends AppCompatActivity {

    public static final String APP_TAG = "SimpleHealth";
    public static final String FIRST_AGE = "age";
    public static final String FIRST_HEIGHT ="height" ;
    public static final String SECOND_AGE ="secondage" ;
    public static final String SECOND_HEIGHT = "secondheight";
    public static final String TOTAL_STEPS ="totalSteps" ;
    public static final String FIRST_USER_ID = "firstID";
    public static final String SECOND_USER_ID = "secondID";
    public static final String WINNER = "winner";
    public static final String STATUS = "status";
    public static final String GROUP_ID = "groupid";
    String weightdiff_first;
    FirebaseAuth auth;
    AlertDialog dialog;

    @BindView(R.id.editHealthDateValue1)
    EditText mStepCountTv;
    String weightdiff_second;
    private HealthDataStore mStore;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    // note that these credentials will differ between live & sandbox environments.
   // private static final String CONFIG_CLIENT_ID 0=  "access_token$sandbox$cn9stywrwgzt7gw6$8f95e45478484d5089f4f78bb5097a38";
    private static final int REQUEST_CODE_PAYMENT = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId("AW-ht1QnNd6R1J_yQd_hznZr4sZDXHsIb8vx3yUdlsJV6TCIBF55RZLb4t_Tbl1oUP5Sgfr77IOxbxfT");
    Button btn_pay,btn_calculate;
    private StepCountReporter mReporter;
    DatabaseReference databaseReference;
    CircleImageView imageUser,imageSecondUser;
    TextView tv_name,tv_current,tv_target,tv_Secondname,tv_Secondcurrent,tv_Secondtarget,tv_progress;
    String groupid, winner,status, first_user_id,second_user_id, name, current, target,totalSteps, Secondname, Secondcurrent, Secondtarget,imageFirst,imageSecond,charity,age,height,secondAge,secondHeight;

    public static final String FIRST_USERNAME="name";
    public static final String FIRST_IMAGE="image";
    public static final String FIRST_CURRENT="current";
    public static final String FIRST_TARGET="target";
    public static final String SECOND_USERNAME="secondname";
    public static final String SECOND_IMAGE="secondimage";
    public static final String SECOND_CURRENT="secondcurrent";
    public static final String SECOND_TARGET="secondtarget";
    public static final String CHARITY="carity";
    private String currentUser;
    FirebaseDatabase database;
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntries;
    String charity_payment[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition_detail);
        ButterKnife.bind(this);
        //mStore = new HealthDataStore(this, mConnectionListener);
        // Request the connection to the health data store
        //mStore.connectService();
        database= FirebaseDatabase.getInstance();
        first_user_id=getIntent().getStringExtra(FIRST_USER_ID);
        second_user_id=getIntent().getStringExtra(SECOND_USER_ID);
        status=getIntent().getStringExtra(STATUS);
        winner=getIntent().getStringExtra(WINNER);
        name=getIntent().getStringExtra(FIRST_USERNAME);
        current=getIntent().getStringExtra(FIRST_CURRENT);
        imageFirst=getIntent().getStringExtra(FIRST_IMAGE);
        target=getIntent().getStringExtra(FIRST_TARGET);
        groupid=getIntent().getStringExtra(GROUP_ID);

        totalSteps=getIntent().getStringExtra(TOTAL_STEPS);
        Secondname=getIntent().getStringExtra(SECOND_USERNAME);
        Secondcurrent=getIntent().getStringExtra(SECOND_CURRENT);
        imageSecond=getIntent().getStringExtra(SECOND_IMAGE);
        Secondtarget=getIntent().getStringExtra(SECOND_TARGET);
        age=getIntent().getStringExtra(FIRST_AGE);
        height=getIntent().getStringExtra(FIRST_HEIGHT);
        secondAge=getIntent().getStringExtra(SECOND_AGE);
        secondHeight=getIntent().getStringExtra(SECOND_HEIGHT);
        charity=getIntent().getStringExtra(CHARITY);

        imageUser=findViewById(R.id.imageUser);
        tv_name=findViewById(R.id.tv_name);
        tv_current=findViewById(R.id.tv_current);
        tv_target=findViewById(R.id.tv_target);
        imageSecondUser=findViewById(R.id.imageSecondUser);
        tv_Secondname=findViewById(R.id.tv_Secondname);
        tv_Secondcurrent=findViewById(R.id.tv_Secondcurrent);
        tv_Secondtarget=findViewById(R.id.tv_Secondtarget);
        tv_progress=findViewById(R.id.tv_progress);
        btn_pay=findViewById(R.id.btn_pay);
        charity_payment= charity.split(",");


        btn_pay.setText("£"+charity_payment[0]);



        auth= FirebaseAuth.getInstance();
        currentUser=auth.getCurrentUser().getUid();
        btn_calculate=findViewById(R.id.btn_calculate);
        if (winner.equals("pending"))
        {
            btn_calculate.setVisibility(View.VISIBLE);
        }
        else if (winner.equals(currentUser))
        {
            openDialogWin("You Win!");

        }
        else
        {
            Preferences preferences=new Preferences(getApplicationContext());
            String value=preferences.getLOSS();
            int res= Integer.parseInt(value );
            int v=res+1;
            String val= String.valueOf(v);

            databaseReference= database.getReference("profiles");
            databaseReference .child(currentUser).child("LOSSES").setValue(val).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        openDialogloss("You Loss!");
                    }
                    else
                    {
                        String message=task.getException().toString();

                    }
                }
            });

        }

        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentSteps=  mStepCountTv.getText().toString();
             if (TextUtils.isEmpty(currentSteps))
             {
                 Toast.makeText(CompetitionDetailActivity.this, "Enter Steps", Toast.LENGTH_SHORT).show();
             }
                else{

                 if (winner.equals("pending"))
                 {
                     if (currentUser.equals(first_user_id)||currentUser.equals(second_user_id))
                     {
                         double total=round(Double.parseDouble(totalSteps));
                         Log.e("totalsteps",totalSteps);
                         double current1= round(Double.parseDouble(currentSteps));
                         Log.e("csteps",currentSteps);

                         double finalSteps=round(current1)-round(total);
                         Log.e("finalst", String.valueOf(finalSteps));

                         mStepCountTv.setText(String.valueOf(finalSteps));
                         double new_target=round(Double.parseDouble(target))*3500;
                         // Log.e("new target/first target", String.valueOf(new_target));

                         double current_wei= round(Double.parseDouble(current));

                         double height_f= round(Double.parseDouble(height));
                         //FORMULA FOR CALORIE CALCULATION BELOW.
                         double MET = 2.5;
                         float SPH = 7000;

                         double calories = MET * current_wei * (finalSteps/SPH)*10; //current_wei = weight and Finalsteps = user step input
                         //2.5 * weight *(finalSteps*StepsPerHour)
                         tv_progress.setText("Weight Lost : "+calories/7700);
                         tv_progress.setText("Calories burned : "+ (float)calories);
                         //if second is logged in //change seconds weight
                         Log.e("first username",first_user_id); //hcvv
                         Log.e("currentUser",currentUser); //Nxzg

//

                          if (calories< new_target*7700)
                         {
                             //when he doesnt win
                             double kglost=round(calories)/7700;
                             String kglost_string= String.valueOf(kglost);
                             //openDialogWin("In Progress");
                             // fetch his old current weight,
                             if(currentUser.equals(first_user_id))
                             {

                                 Double  firstcurrent_double=current_wei - kglost;
                                 String xx1=firstcurrent_double.toString();

                                 databaseReference= database.getReference("groups");
                                 databaseReference .child(groupid).child("Firstcurrent_weight").setValue(xx1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) {
                                         if (task.isSuccessful())
                                         {
                                             getEntries();

                                             Toast.makeText(CompetitionDetailActivity.this, "First person's weight updated to: "+xx1, Toast.LENGTH_SHORT).show();
                                             if(Double.parseDouble(xx1)>Double.parseDouble(target))
                                             {
                                                 Toast.makeText(CompetitionDetailActivity.this, "you need to cut "+String.valueOf(round(Double.parseDouble(xx1)-Double.parseDouble(target)))+ " more kg", Toast.LENGTH_SHORT).show();
                                             }
                                             if(Double.parseDouble(xx1)<=Double.parseDouble(target))
                                             {
                                                 Toast.makeText(CompetitionDetailActivity.this, "you have achieved your goal! ", Toast.LENGTH_SHORT).show();
                                                 updateProfile();

                                             }
                                         }
                                         else
                                         {
                                             String message=task.getException().toString();

                                         }
                                     }
                                 });
                             }
                             else if (currentUser.equals(second_user_id))
                             {
                              Double   secondcurrent_dou=Double.parseDouble(Secondcurrent);
                               Double  Secondcurrent_double=secondcurrent_dou - kglost;
                               String xx=Secondcurrent_double.toString();

                                 databaseReference= database.getReference("groups");
                                 databaseReference .child(groupid).child("SecondCurrent_weight").setValue(xx).addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) {
                                         if (task.isSuccessful())
                                         {
                                             getEntries();

                                             Toast.makeText(CompetitionDetailActivity.this, "second weight updated to"+String.valueOf(Secondcurrent_double), Toast.LENGTH_SHORT).show();
                                             Toast.makeText(CompetitionDetailActivity.this, "First person's weight updated to: "+xx, Toast.LENGTH_SHORT).show();
                                             if(Double.parseDouble(xx)>Double.parseDouble(Secondtarget))
                                             {
                                                 Toast.makeText(CompetitionDetailActivity.this, "you need to cut more "+String.valueOf(Double.parseDouble(xx)-Double.parseDouble(target))+ "kg", Toast.LENGTH_SHORT).show();
                                             }
                                             if(Double.parseDouble(xx)<=Double.parseDouble(Secondtarget))
                                             {
                                                 Toast.makeText(CompetitionDetailActivity.this, "you have acheived your goal/You won/ ", Toast.LENGTH_SHORT).show();
                                                 updateProfile();
                                             }
                                         }
                                         else
                                         {
                                             String message=task.getException().toString();

                                         }
                                     }
                                 });
                               //save this new
                             }





                         }
                          else
                          {

                          }
                     }
                 }

             }

            }
        });
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBuyPressed();
            }
        });
        populate();

    }

    private void getEntries()
    {


        databaseReference= database.getReference("groups");
        Query q = databaseReference .child(groupid).child("weightdifference_first");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                weightdiff_first  = dataSnapshot.getValue(String.class);
                Log.e("datafirst",weightdiff_first);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Query q2 = databaseReference .child(groupid).child("weightdifference_second");
        q2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 weightdiff_second = dataSnapshot.getValue(String.class);
                barEntries = new ArrayList<>();
                barChart = findViewById(R.id.newchart);
                Log.e("datasecond",weightdiff_second);
                barEntries.add(new BarEntry(2f, Float.parseFloat(weightdiff_first)));
                barEntries.add(new BarEntry(4f, Float.parseFloat(weightdiff_second)));
                barDataSet = new BarDataSet(barEntries, "");
                barData = new BarData(barDataSet);
                barChart.setData(barData);
                barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                barDataSet.setValueTextColor(Color.BLACK);
                barDataSet.setValueTextSize(18f);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void openDialogWin(String status) {

        androidx.appcompat.app.AlertDialog.Builder builder=new androidx.appcompat.app.AlertDialog.Builder(CompetitionDetailActivity.this,R.style.AlertDialog);
        View vw = getLayoutInflater().inflate(R.layout.layout_dialog_status, null);

        CircleImageView circleImageClose=vw.findViewById(R.id.imgClose);
        TextView tvgroup=vw.findViewById(R.id.tvgroup);
        Button dialog_like_bt=vw.findViewById(R.id.dialog_like_bt);
        tvgroup.setText(status);

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



    }
    public void openDialogloss(String status) {

        androidx.appcompat.app.AlertDialog.Builder builder=new androidx.appcompat.app.AlertDialog.Builder(CompetitionDetailActivity.this,R.style.AlertDialog);
        View vw = getLayoutInflater().inflate(R.layout.layout_dialog_status, null);

        CircleImageView circleImageClose=vw.findViewById(R.id.imgClose);
        TextView tvgroup=vw.findViewById(R.id.tvgroup);


        tvgroup.setText(status);

        Button btnCreate=vw.findViewById(R.id.dialog_like_bt);
        btnCreate.setVisibility(View.VISIBLE);
        builder.setView(vw);
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
        String  charity_payment[ ]= charity.split(",");

        btnCreate.setText("£"+charity_payment[0]);
        btnCreate.setOnClickListener(new
                                             View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View view) {

                                                     onBuyPressed();
                                                 }
                                             });
        circleImageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
    private void populate() {

        tv_name.setText(name);
        tv_current.setText("Current :"+ current);
        tv_target.setText("Target :"+target);
        tv_Secondname.setText(Secondname);
        tv_Secondcurrent.setText("Current :"+Secondcurrent);
        tv_Secondtarget.setText("Target :"+Secondtarget);

        ImageUtil.loadImage(GlideApp.with(this), String.valueOf(imageSecond), imageSecondUser);
        ImageUtil.loadImage(GlideApp.with(this), String.valueOf(imageFirst), imageUser);




    }

    @Override
    public void onDestroy() {
        //mStore.disconnectService();
        super.onDestroy();
    }

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
//        AlertDialog.Builder alert = new AlertDialog.Builder(CompetitionDetailActivity.this);
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
//                error.resolve(CompetitionDetailActivity.this);
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
//        HealthPermissionManager.PermissionKey permKey = new HealthPermissionManager.PermissionKey(HealthConstants.StepCount.HEALTH_DATA_TYPE, HealthPermissionManager.PermissionType.READ);
//        HealthPermissionManager pmsManager = new HealthPermissionManager(mStore);
//        try {
//            // Check whether the permissions that this application needs are acquired
//            Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = pmsManager.isPermissionAcquired(Collections.singleton(permKey));
//            return resultMap.get(permKey);
//        } catch (Exception e) {
//            Log.e(APP_TAG, "Permission request fails.", e);
//        }
//        return false;
//    }
//
//    private void requestPermission() {
//        HealthPermissionManager.PermissionKey permKey = new HealthPermissionManager.PermissionKey(HealthConstants.StepCount.HEALTH_DATA_TYPE, HealthPermissionManager.PermissionType.READ);
//        HealthPermissionManager pmsManager = new HealthPermissionManager(mStore);
//        try {
//            // Show user permission UI for allowing user to change options
//            pmsManager.requestPermissions(Collections.singleton(permKey), CompetitionDetailActivity.this)
//                    .setResultListener(result -> {
//                        Log.d(APP_TAG, "Permission callback is received.");
//                        Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = result.getResultMap();
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
//        runOnUiThread(() -> mStepCountTv.setText(count)
//
//
//        );
//
//
//
//    }

    private void updateProfile() {
        HashMap<String,String>params=new HashMap<>();
        params.put("WINS","1");
        Preferences preferences=new Preferences(getApplicationContext());
        String value=preferences.getWINS();
        int res= Integer.parseInt(value );
       int v=res+1;
       String val= String.valueOf(v);

        databaseReference= database.getReference("profiles");
        databaseReference .child(currentUser).child("WINS").setValue(val).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    openDialogWin("You Win!");
                }
                else
                {
                    String message=task.getException().toString();

                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {

        if (item.getItemId() == R.id.connect) {
            //requestPermission();
        }

        return true;
    }

    public void onBuyPressed() {
        try {
            PayPalPayment thingToBuy = new  PayPalPayment(new BigDecimal(charity_payment[0]), "GBP", charity_payment[1],PayPalPayment.PAYMENT_INTENT_SALE);
            Intent intent = new Intent(CompetitionDetailActivity.this, PaymentActivity.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
            startActivityForResult(intent, REQUEST_CODE_PAYMENT);
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "Check Paypal config key"+ ex, Toast.LENGTH_SHORT).show();
        }

    }
//    private PayPalPayment getThingToBuy(String paymentIntent)
//    {
////        Double amo_unt = Double.parseDouble(charity);
//
//
////        Double total = tax + amo_unt;
////        Log.e("Total",""+total);
////        Log.e("Current Balance",""+user_balance);
////        Double remeaing_amount =total-user_balance;
////        Log.e("Remaing Balance",""+remeaing_amount);
////        String am=""+remeaing_amount;
//
//       return new PayPalPayment(new BigDecimal("25"), "USD", "SRD Foundation",
//               paymentIntent);
//    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {

                    } catch (Exception e)
                    {
                       Log.e("excep", "excep"+ e);
                    }
                }
            }
            else if (resultCode == Activity.RESULT_CANCELED)
            {
                Log.e("excep1", "dgf");

            }
            else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            {
                Log.e("excep2", "dgf");

            }
        }
    }
}
