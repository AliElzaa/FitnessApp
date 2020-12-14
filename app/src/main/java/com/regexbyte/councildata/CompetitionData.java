package com.regexbyte.councildata;

public class CompetitionData {

    String FirstuserID, Firstcurrent_weight, FirstTarget, Firstimage, FirstUserName,FirstUserHeight,Winner,Status, FirstUserAge,charity,
            SecondUserName,Groupid,  SecondImage, SecondTarget, SecondUserID,Groupname,SecondUserHeight,SecondAge,TotalSteps;
    String SecondCurrent_weight;

    public CompetitionData(String firstuserID, String firstcurrent_weight, String firstTarget, String firstimage, String firstUserName,String firstUserAge,String firstUserHeight, String charity, String secondUserName, String SecondCurrent_weight,
                           String secondImage, String secondTarget, String secondUserID,String groupname,String secondAge,String secondUserHeight,String totalSteps,String status,String winner,String groupid) {
        FirstuserID = firstuserID;
        Firstcurrent_weight = firstcurrent_weight;
        FirstTarget = firstTarget;
        Firstimage = firstimage;
        FirstUserName = firstUserName;
        this.charity = charity;
        SecondUserName = secondUserName;
        SecondCurrent_weight = SecondCurrent_weight;
        SecondImage = secondImage;
        SecondTarget = secondTarget;
        SecondUserID = secondUserID;
        Groupname=groupname;
        FirstUserAge=firstUserAge;
        FirstUserHeight=firstUserHeight;
        SecondAge=secondAge;
        SecondUserHeight=secondUserHeight;
        TotalSteps=totalSteps;
        Status=status;
        Winner=winner;
        Groupid=groupid;
    }

    public String getGroupid() {
        return Groupid;
    }

    public void setGroupid(String groupid) {
        Groupid = groupid;
    }

    public String getTotalSteps() {
        return TotalSteps;
    }

    public String getWinner() {
        return Winner;
    }

    public void setWinner(String winner) {
        Winner = winner;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setTotalSteps(String totalSteps) {
        TotalSteps = totalSteps;
    }

    public String getFirstUserHeight() {
        return FirstUserHeight;
    }

    public void setFirstUserHeight(String firstUserHeight) {
        FirstUserHeight = firstUserHeight;
    }

    public String getFirstUserAge() {
        return FirstUserAge;
    }

    public void setFirstUserAge(String firstUserAge) {
        FirstUserAge = firstUserAge;
    }

    public String getSecondUserHeight() {
        return SecondUserHeight;
    }

    public void setSecondUserHeight(String secondUserHeight) {
        SecondUserHeight = secondUserHeight;
    }

    public String getSecondAge() {
        return SecondAge;
    }

    public void setSecondAge(String secondAge) {
        SecondAge = secondAge;
    }

    public String getGroupname() {
        return Groupname;
    }

    public void setGroupname(String groupname) {
        Groupname = groupname;
    }

    public String getFirstuserID() {
        return FirstuserID;
    }

    public void setFirstuserID(String firstuserID) {
        FirstuserID = firstuserID;
    }

    public String getFirstcurrent_weight() {
        return Firstcurrent_weight;
    }

    public void setFirstcurrent_weight(String firstcurrent_weight) {
        Firstcurrent_weight = firstcurrent_weight;
    }

    public String getFirstTarget() {
        return FirstTarget;
    }

    public void setFirstTarget(String firstTarget) {
        FirstTarget = firstTarget;
    }

    public String getFirstimage() {
        return Firstimage;
    }

    public void setFirstimage(String firstimage) {
        Firstimage = firstimage;
    }

    public String getFirstUserName() {
        return FirstUserName;
    }

    public void setFirstUserName(String firstUserName) {
        FirstUserName = firstUserName;
    }

    public String getCharity() {
        return charity;
    }

    public void setCharity(String charity) {
        this.charity = charity;
    }

    public String getSecondUserName() {
        return SecondUserName;
    }

    public void setSecondUserName(String secondUserName) {
        SecondUserName = secondUserName;
    }

    public String getSecondCurrent_weight() {
        return SecondCurrent_weight;
    }

    public void setSecondCurrent_weight(String secondCurrent_weight) {
        SecondCurrent_weight = secondCurrent_weight;
    }

    public String getSecondImage() {
        return SecondImage;
    }

    public void setSecondImage(String secondImage) {
        SecondImage = secondImage;
    }

    public String getSecondTarget() {
        return SecondTarget;
    }

    public void setSecondTarget(String secondTarget) {
        SecondTarget = secondTarget;
    }

    public String getSecondUserID() {
        return SecondUserID;
    }

    public void setSecondUserID(String secondUserID) {
        SecondUserID = secondUserID;
    }
}
