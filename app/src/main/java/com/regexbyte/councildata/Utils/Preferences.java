package com.regexbyte.councildata.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;



public class Preferences {
    public String FirstName="First.NAME";
    public String LASTName="LAST.NAME";
    public String EMAIL="EMAIL";
    public String PASSWORD="PASSWORD";
    public String PHONE="PHONE";
    public String WINS="wins";
    public String LOSS="loss";
    public String IsfirstTime="IsFirstTime";
    public String ImageUrl="imageUrl";
    public String imageUrlPath="imageUrlpath";
     SharedPreferences sharedPreferences;
Context context;
    public Preferences(Context context) {
        this.context=context;
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
    }
    public  void setUserInfo(String first_name, String last_name, String user_phone, String user_password, String user_image) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FirstName,first_name);
        editor.putString(LASTName,last_name);
        editor.putString(PHONE,user_phone);
        editor.putString(PASSWORD,user_password);
        editor.putString(ImageUrl,user_image);
        editor.apply();
        //editor.putString(preferences.PHONE,phoneNo);
    }
    public void setImage(String downloadUrl)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ImageUrl,downloadUrl);
        editor.apply();

    }
    public void setImageUrl(String imageUrl)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(imageUrlPath,imageUrl);
        editor.apply();

    }
    public void setFirst(String first)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FirstName,first);
        editor.apply();

    }
    public void setLast(String last)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LASTName,last);
        editor.apply();

    }
    public void setPhone(String phone)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PHONE,phone);
        editor.apply();

    }
    public String getPhone()
    {
       return sharedPreferences.getString(PHONE,null);

    }
    public void setWINS(String phone)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WINS,phone);
        editor.apply();

    }
    public String getWINS()
    {
        return sharedPreferences.getString(WINS,null);

    }
    public void setLOSS(String phone)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOSS,phone);
        editor.apply();

    }
    public String getLOSS()
    {
        return sharedPreferences.getString(LOSS,null);

    }
    public Uri getImageUrl()
    {
        Uri imageUrl= Uri.parse(sharedPreferences.getString(imageUrlPath,null));
        return imageUrl;
    }
    public String getImage()
    {
        String imageUrl=sharedPreferences.getString(ImageUrl,null);
        return imageUrl;
    }
    public String getName()
    {
        String firstName=sharedPreferences.getString(FirstName,null);
        String LastName=sharedPreferences.getString(LASTName,null);
        String username=firstName +" "+LastName;
        return username;
    }

}
