package com.FitAlly.MyFitAllyApp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2500;   ///timer to display the splash screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {


                Date date=new Date();

                String datee[]= String.valueOf(date).split(" ");

                Log.e("date ", String.valueOf(date));
                Log.e("day", datee[1]);
                Log.e("date", datee[2]);
//
//                {
                    Intent intent=new Intent(SplashActivity.this, LoginActivity.class); //Login
                    startActivity(intent);
                    finish();
//                }
//                else {
//
//                }



            }
        }, SPLASH_TIME_OUT); //the function which starts the splash screen activity

        ////////////////////////////////////////////




    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 7011) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
