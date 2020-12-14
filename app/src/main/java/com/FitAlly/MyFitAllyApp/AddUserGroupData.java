package com.FitAlly.MyFitAllyApp;

//get and setters for adding users to a group

public class AddUserGroupData {
    String downloadUrl;
    String Username;
    String userID;
    String currentWei;
    String wins,target;
    String losses,height,age;

    public AddUserGroupData(String Username, String downloadUrl, String userID, String currentWei, String wins, String losses,String target,String height,String age) { //function to add users to a group
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

    public String getTarget() {
        return target;
    }

    public String getCurrentWei() {
        return currentWei;
    }

    public String getWins() {
        return wins;
    }

    public String getLosses() {
        return losses;
    }

    public String getUserID() {
        return userID;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getUsername() {
        return Username;
    }



}
