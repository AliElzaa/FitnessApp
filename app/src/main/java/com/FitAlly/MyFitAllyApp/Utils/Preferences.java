package com.FitAlly.MyFitAllyApp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;



public class Preferences {
    public String FirstName="First.NAME";
    public String LASTName="LAST.NAME";
    public String EMAIL="EMAIL";
    public String PASSWORD="PASSWORD";
    public String WINS="wins";
    public String LOSS="loss";
    public String ImageUrl="imageUrl";
    public String imageUrlPath="imageUrlpath";
    public String IsfirstTime="IsFirstTime";
     SharedPreferences sharedPreferences;
Context context;

//preferences class is a java class that is used to store small data
    public Preferences(Context context) {
        this.context=context;
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setImage(String downloadUrl)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ImageUrl,downloadUrl);
        editor.apply();

    }
//    public void setImageUrl(String imageUrl)

//    }


    public void setWINS(String naame)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WINS,naame);
        editor.apply();

    }
    public String getWINS()
    {
        return sharedPreferences.getString(WINS,null);

    }
    public void setLOSS(String naame)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOSS,naame);
        editor.apply();

    }
    public String getLOSS()
    {
        return sharedPreferences.getString(LOSS,null);

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
