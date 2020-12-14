package com.regexbyte.councildata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.regexbyte.councildata.Utils.ApplicationHelper;
import com.regexbyte.councildata.Utils.DatabaseHelper;
import com.regexbyte.councildata.addUserToGroup.AddUsersActivity;
import com.regexbyte.councildata.ui.MainActivity;

import java.util.HashMap;

public class StartCompetitonActivity extends AppCompatActivity implements View.OnClickListener{
Button btn_next;
    private String charity;
    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private DatabaseHelper databaseHelper;
    private String currentUser;
    Spinner spinner;
    public String[] amountsarray = {"£2","£5","£10","£15"};



    LinearLayout layout_10,layout_20,layout_30,layout_40;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_competiton);
        mAuth= FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser().getUid();
        database= FirebaseDatabase.getInstance();
        databaseHelper = ApplicationHelper.getDatabaseHelper();
        btn_next=findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
                layout_10=findViewById(R.id.layout_10);
        layout_20=findViewById(R.id.layout_20);
        layout_30=findViewById(R.id.layout_30);
        layout_40=findViewById(R.id.layout_40);

        layout_40.setOnClickListener(this);
        layout_10.setOnClickListener(this);
        layout_20.setOnClickListener(this);
        layout_30.setOnClickListener(this);
        spinner= findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,amountsarray);
        //adapter.setDropDownViewResource(R.layout.text);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("selected", String.valueOf(i));
                if(i==0)
                {
           charity="2,";
                }
                if(i==1)
                {
           charity="5,";
                }
                if(i==2)
                {
                    charity="10,";

                }
                if(i==3)
                {
                    charity="15,";

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_next:


                if (TextUtils.isEmpty(charity) )
                {
                    Toast.makeText(this, "Select Charity", Toast.LENGTH_SHORT).show();
                }


                if (charity!=null    )
                {
                    HashMap<String,String> params=new HashMap<>();
                    params.put("charity",charity);
                    Intent intent=new Intent(StartCompetitonActivity.this, AddUsersActivity.class); // I changed this
                    intent.putExtra("charity",charity); //now charity contains the names of the charity,not the amount of penalty as before// comment by umar
                    startActivity(intent);
                }
                break;

            case R.id.layout_30:
                charity=charity+getString(R.string.lebanese);

                break;
            case R.id.layout_10:
                charity=charity+getString(R.string.syrian);
                break;
            case R.id.layout_20:
                charity=charity+getString(R.string.british_heart);
                break;
            case R.id.layout_40:
                charity=charity+getString(R.string.cancerUK);
                break;




        }
    }
}
