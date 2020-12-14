package com.FitAlly.MyFitAllyApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.FitAlly.MyFitAllyApp.Utils.GlideApp;
import com.FitAlly.MyFitAllyApp.Utils.ImageUtil;
import com.FitAlly.MyFitAllyApp.Utils.Preferences;
import com.FitAlly.MyFitAllyApp.ui.StepCountReporter;
import com.samsung.android.sdk.healthdata.HealthDataStore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;


import de.hdodenhof.circleimageview.CircleImageView;

public class CompetitionDetailActivity extends AppCompatActivity {

    public static final String APP_TAG = "SimpleHealth";
    public static final String FIRST_AGE = "age";
    public static final String FIRST_HEIGHT ="height" ;
    public static final String SECOND_AGE ="secondage" ;
    public static final String SECOND_HEIGHT = "secondheight";
    public static final String TOTAL_STEPS ="totalSteps" ;
    public static final String FIRST_USER_ID = "firstID";
    public static final String SECOND_USER_ID = "secondID";
    public static final String WINNER = "winer";
    public static final String STATUS = "status";
    public static final String GROUP_ID = "groupid";
    String weightdiff_first;
    FirebaseAuth auth;
    AlertDialog dialog;
// variables made here for the competition and creating instances of imported classes as well as the alert dialog.
    EditText mStepCountTv;
    String weightdiff_second;

    private HealthDataStore mStore; //instance of the Samsung Health API.
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID =
            "AW-ht1QnNd6R1J_yQd_hznZr4sZDXHsIb8vx3yUdlsJV6TCIBF55RZLb4t_Tbl1oUP5Sgfr77IOxbxfT"; //Key for Paypal API
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID);
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

//        mStore = new HealthDataStore(this, mConnectionListener);
        // Request the connection to the health data store
//        mStore.connectService();
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
        mStepCountTv=findViewById(R.id.editHealthDateValue1);
      String  charity_payment[ ]= charity.split(",");
      Log.e("charity", charity_payment[0]);
        Log.e("charity", charity_payment[1]);

        btn_pay.setText("€"+charity_payment[0]);



        auth= FirebaseAuth.getInstance();
        currentUser=auth.getCurrentUser().getUid();
        btn_calculate=findViewById(R.id.btn_calculate);
        if (winner.equals("pending")) //if no one has won yet, then the calculation button will be available
        {
            btn_calculate.setVisibility(View.VISIBLE);
        }
        else if (winner.equals(currentUser)) //if the id of the user is in the winner box, then he has won else he loses.
        {
            openDialogWin("You Win!");

        }
        else
        {
            Preferences preferences=new Preferences(getApplicationContext());
            String value=preferences.getLOSS(); //it gets the current losses as a string from firebase
            int res= Integer.parseInt(value ); //convert into a INT so that we can apply arithmetics on it
            int v=res+1;
            String val= String.valueOf(v); //convert back to string

            databaseReference= database.getReference("profiles");
            databaseReference .child(currentUser).child("LOSSES").setValue(val).addOnCompleteListener(new OnCompleteListener<Void>() { //example of a specific value gotten from FireBase.
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
                 Toast.makeText(CompetitionDetailActivity.this, "Enter Steps", Toast.LENGTH_SHORT).show(); //UI enter steps
             }
                else{

                 if (winner.equals("pending"))
                 {
                     if (currentUser.equals(first_user_id)||currentUser.equals(second_user_id)) //initialising both users and their ID's.
                     {
                         double total=Double.parseDouble(totalSteps);
                         Log.e("totalsteps",totalSteps);
                         double current1= Double.parseDouble(currentSteps);
                         Log.e("csteps",currentSteps);

                         double finalSteps=current1-total;
                         Log.e("finalst", String.valueOf(finalSteps));

                         mStepCountTv.setText(String.valueOf(finalSteps));
                         double new_target=Double.parseDouble(target)*3500;
                        // Log.e("new target/first target", String.valueOf(new_target));

                         double current_wei= Double.parseDouble(current);

                         double height_f= Double.parseDouble(height);
                       //FORMULA FOR CALORIE CALCULATION BELOW.
                         double MET = 2.5;
                         float SPH = 7000;

                         double calories = MET * current_wei * (finalSteps/SPH); //current_wei = weight and Finalsteps = user step input
                         //2.5 * weight *(finalSteps*StepsPerHour)
                         tv_progress.setText("Weight Lost : "+(float)calories/7700);
                         tv_progress.setText("Calories burned : "+ (float)calories);
                         //if second is logged in //change seconds weight

//                         if (calories>=new_target*7700)
//                         {
//
//                            HashMap<String,String>params=new HashMap<>();
//                            params.put("winner",currentUser);
//                            params.put("status","completed");
//                           double kglost=calories/7700;
//                           String kglost_string= String.valueOf(kglost);
//                          //  params.put("totalSteps", kglost_string);
//                            databaseReference= database.getReference("groups");
//                            databaseReference.child(groupid) .child("winner").setValue(currentUser).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful())
//                                {
//                                     updateProfile();
//                                }
//                                else
//                                {
//                                    Toast.makeText(CompetitionDetailActivity.this, "Some thing went wrong,please try again.", Toast.LENGTH_SHORT).show();
//                                }
//                        }
//                    });
//                         }

                          if (calories< new_target*7700) //if the calories is less than the target total calories
                         {
                             double kglost=calories/7700;
                             String kglost_string= String.valueOf(kglost);
                             //openDialogWin("In Progress");
                             // fetch his old current weight,
                             if(currentUser.equals(first_user_id)) //identifies if the current user makes a action
                             {

                                 Double  firstcurrent_double=current_wei - kglost;
                                 String xx1=firstcurrent_double.toString();

                                 databaseReference= database.getReference("groups");
                                 databaseReference .child(groupid).child("Firstcurrent_weight").setValue(xx1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) { //messages to display once the calculation has happened
                                         if (task.isSuccessful())
                                         {
                                             getEntries();

                                             Toast.makeText(CompetitionDetailActivity.this, "First person's weight updated to: "+xx1, Toast.LENGTH_SHORT).show();
                                             if(Double.parseDouble(xx1)>Double.parseDouble(target))
                                             {
                                                 Toast.makeText(CompetitionDetailActivity.this, "you need to cut more weight "+String.valueOf(Double.parseDouble(xx1)-Double.parseDouble(target))+ "kg", Toast.LENGTH_SHORT).show();
                                             }
                                             if(Double.parseDouble(xx1)<=Double.parseDouble(target))
                                             {
                                                 Toast.makeText(CompetitionDetailActivity.this, "you have achieved your goal ", Toast.LENGTH_SHORT).show();
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
                                                 Toast.makeText(CompetitionDetailActivity.this, "you have achieved your goal/You won/ ", Toast.LENGTH_SHORT).show();
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
        Query q = databaseReference .child(groupid).child("weightdifference_first"); //I have to make a query in order to retrieve data from the firebase console in order to have the values to put into the graphs.
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
        Query q2 = databaseReference .child(groupid).child("weightdifference_second"); //second query
        q2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //creating the graphs using the query data I have
                 weightdiff_second = dataSnapshot.getValue(String.class); //
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

    public void openDialogWin(String status) { //the dialog message for winning

        androidx.appcompat.app.AlertDialog.Builder builder=new androidx.appcompat.app.AlertDialog.Builder(CompetitionDetailActivity.this,R.style.AlertDialog);
        View vw = getLayoutInflater().inflate(R.layout.layout_dialog_createcomp, null);

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
    public void openDialogloss(String status) { //the dialog message for losing

        androidx.appcompat.app.AlertDialog.Builder builder=new androidx.appcompat.app.AlertDialog.Builder(CompetitionDetailActivity.this,R.style.AlertDialog);
        View vw = getLayoutInflater().inflate(R.layout.layout_dialog_createcomp, null);

        CircleImageView circleImageClose=vw.findViewById(R.id.imgClose);
        TextView tvgroup=vw.findViewById(R.id.tvgroup);


        tvgroup.setText(status);

        Button btnCreate=vw.findViewById(R.id.dialog_like_bt);
        btnCreate.setVisibility(View.VISIBLE);
        builder.setView(vw);
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
        String  charity_payment[ ]= charity.split(","); //this is a regex expression because the amount and charity are stored together, therefore we have to seperate the amount and charity

        btnCreate.setText("£"+charity_payment[0]); //the message dialog will state the amount to pay and will be a button that will go to the Paypal API
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
    private void populate() { //function to populate the screen

        tv_name.setText(name);
        tv_current.setText("Current :"+ current);
        tv_target.setText("Target :"+target);
        tv_Secondname.setText(Secondname);
        tv_Secondcurrent.setText("Current :"+Secondcurrent);
        tv_Secondtarget.setText("Target :"+Secondtarget);

        ImageUtil.loadImage(GlideApp.with(this), String.valueOf(imageSecond), imageSecondUser);
        ImageUtil.loadImage(GlideApp.with(this), String.valueOf(imageFirst), imageUser);




    }

//SAMSUNG API function below
    private StepCountReporter.StepCountObserver mStepCountObserver = count -> {
        Log.d(APP_TAG, "Step reported : " + count);
        updateStepCountView(String.valueOf(count));
    };

    private void updateStepCountView(final String count) {
        runOnUiThread(() -> mStepCountTv.setText(count)


        );



    }
///////////////////////////////////
    private void updateProfile() { //updates the profile with the win
        HashMap<String,String>params=new HashMap<>();
        params.put("WINS","1");
        Preferences preferences=new Preferences(getApplicationContext());
        String value=preferences.getWINS();
        int res= Integer.parseInt(value );
       int v=res+1;
       String val= String.valueOf(v);

        databaseReference= database.getReference("profiles");
        databaseReference .child(currentUser).child("WINS").setValue(val).addOnCompleteListener(new OnCompleteListener<Void>() { //add a win on the user who has won
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(android.view.MenuItem item) {
//
//        if (item.getItemId() == R.id.connect) {
//            requestPermission();
//        }
//
//        return true;
//    }

    public void onBuyPressed() { //the button that will call the PayPal dialog in the app

        try {
            PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);
            Intent intent = new Intent(CompetitionDetailActivity.this, PaymentActivity.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
            startActivityForResult(intent, REQUEST_CODE_PAYMENT);
        }
        catch (Exception ex) //error handling in case the key was wrong.
        {
            Toast.makeText(this, "Check Paypal config key ", Toast.LENGTH_SHORT).show();
        }

    }
    private PayPalPayment getThingToBuy(String paymentIntent) { //the function to retrieve the name of the charity the user has taken
        return new PayPalPayment(new BigDecimal(charity), "GBP", charity,
                paymentIntent);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { //next function upon clicking to confirm the payment, this is all on the sandbox environment therefore you would not actually pay
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {

                    } catch (Exception e) {
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            }
        }
    }
}
