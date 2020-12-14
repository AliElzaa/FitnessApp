package com.regexbyte.councildata;

/**
 * Created by hp on 8/10/2018.
 */

public class AddUserGroupData {
    String downloadUrl;
    String Username;
    String userID;
    String currentWei;
    String wins,target;
    String losses,height,age;

    public AddUserGroupData(String Username, String downloadUrl, String userID, String currentWei, String wins, String losses,String target,String height,String age) {
        this.downloadUrl = downloadUrl;
        this.Username = Username;
        this.userID=userID;
        this.currentWei=currentWei;
        this.wins=wins;
        this.losses=losses;
        this.target=target;
        this.age=age;
        this.height=height;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getCurrentWei() {
        return currentWei;
    }

    public void setCurrentWei(String currentWei) {
        this.currentWei = currentWei;
    }

    public String getWins() {
        return wins;
    }

    public void setWins(String wins) {
        this.wins = wins;
    }

    public String getLosses() {
        return losses;
    }

    public void setLosses(String losses) {
        this.losses = losses;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }



    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }


}
