package com.FitAlly.MyFitAllyApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.FitAlly.MyFitAllyApp.Utils.ApplicationHelper;
import com.FitAlly.MyFitAllyApp.Utils.DatabaseHelper;
import com.FitAlly.MyFitAllyApp.Utils.Preferences;
import com.FitAlly.MyFitAllyApp.editProfile.EditProfileActivity;
import com.FitAlly.MyFitAllyApp.ui.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener  {


    Button btn_start;
   TextView tv_arena,tv_stats,tv_settings;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private DatabaseHelper databaseHelper;
    private String currentUser;

    private String charity;
    Uri downloadUrl,UriFromFirebase;
    LinearLayout   layout_10,layout_20,layout_30,layout_40,layout_view;
    CircleImageView imageUser;
    TextView tv_name,tv_current,tv_wins,tv_losses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth= FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser().getUid();
        database= FirebaseDatabase.getInstance();
        databaseHelper = ApplicationHelper.getDatabaseHelper();
        imageUser=findViewById(R.id.imageUser);
        tv_name=findViewById(R.id.tv_name);
        tv_current=findViewById(R.id.tv_current);
        tv_wins=findViewById(R.id.tv_wins);
        tv_losses=findViewById(R.id.tv_losses);
        tv_settings=findViewById(R.id.tv_settings);
        tv_arena=findViewById(R.id.tv_arena);
        tv_stats=findViewById(R.id.tv_stats);
        btn_start=findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
        tv_stats.setOnClickListener(this);
        tv_arena.setOnClickListener(this);
        tv_settings.setOnClickListener(this);





    }

    @Override
    protected void onStart() {
        super.onStart();
        RetreiveUserInfo();
    }

    private void RetreiveUserInfo() { //retrieve the user information
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("profiles").child(currentUser).addValueEventListener(new ValueEventListener() {
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

                    UriFromFirebase= Uri.parse(String.valueOf(dataSnapshot.child("imageUri").getValue())) ;
                    tv_current.setText("Current Weight : "+current_weight);
                    tv_losses.setText("Losses : "+losses);
                    tv_wins.setText("Wins : "+wins);
                    tv_name.setText(username);

                    Preferences preferences;
                    preferences=new Preferences(getApplicationContext());
                    preferences.setWINS(wins);
                    preferences.setLOSS(losses);

                  //  Picasso.LoadedFrom(getApplicationContext()).load(image).into(img);

                }
                else if ((dataSnapshot.exists())&&(dataSnapshot.hasChild("imageUri"))) //if no image is uploaded take the default image and upload
                {
                    UriFromFirebase= Uri.parse(String.valueOf(dataSnapshot.child("imageUri").getValue())) ;
                }
                else
                {

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    @Override
    public void onClick(View view) { //once the user clicks the drop down list
        switch (view.getId())
        {
            case R.id.btn_start:

                    Intent   intent=new Intent(HomeActivity.this, StartCompetitonActivity.class);

                    startActivity(intent);


                break;

            case R.id.layout_30:
                charity="5";
                break;
            case R.id.layout_10:
                charity="1";
                break;
            case R.id.layout_20:
                charity="3";
                break;
            case R.id.layout_40:
                charity="10";
                break;
            case R.id.tv_stats:
            Intent intentStats=new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intentStats);
          finish();
                break;
            case R.id.tv_arena:
                Intent intentArena=new Intent(HomeActivity.this, ArenaActivity.class);
                startActivity(intentArena);
                break;
            case R.id.tv_settings:
                Intent intentProfile=new Intent(HomeActivity.this, EditProfileActivity.class);
                startActivity(intentProfile);
                break;



        }
    }


}
