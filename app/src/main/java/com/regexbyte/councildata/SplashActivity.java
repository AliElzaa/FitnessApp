package com.regexbyte.councildata;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.regexbyte.councildata.editProfile.EditProfileActivity;
import com.regexbyte.councildata.ui.MainActivity;

import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 100;   ///change it to 2500
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
//                if(datee[0].equals("Sun") && datee[2].equals("23"))
//                {
                    Intent intent=new Intent(SplashActivity.this, LoginActivity.class); //Login
                    startActivity(intent);
                    finish();
//                }
//                else {
//
//                }

                //need to change this,after completing app

            }
        }, SPLASH_TIME_OUT);

        ////////////////////////////////////////////


//        Thread timer=new Thread()
//        {
//            public void run()
//            {
//                try
//                {
//                    sleep(2000);
//
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                finally {
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 7011);
//                        // requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
//                    }
//                }
//            }
//        };
//        timer.start();

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
