package com.FitAlly.MyFitAllyApp;

public class CompetitionData {

    String FirstuserID, Firstcurrent_weight, FirstTarget, Firstimage, FirstUserName,FirstUserHeight,Winner,Status, FirstUserAge,charity,
            SecondUserName,Groupid,  SecondImage, SecondTarget, SecondUserID,Groupname,SecondUserHeight,SecondAge,TotalSteps;

    public CompetitionData(String firstuserID, String firstcurrent_weight, String firstTarget, String firstimage, String firstUserName,String firstUserAge,String firstUserHeight, String charity, String secondUserName, String SecondCurrent_weight,
                           String secondImage, String secondTarget, String secondUserID,String groupname,String secondAge,String secondUserHeight,String totalSteps,String status,String winner,String groupid) {
        FirstuserID = firstuserID;
        Firstcurrent_weight = firstcurrent_weight;
        FirstTarget = firstTarget;
        Firstimage = firstimage;
        FirstUserName = firstUserName;
        this.charity = charity;
        SecondUserName = secondUserName;
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
//encapsulation applied here , pretty basic get-set methods.
    public String getGroupid() {
        return Groupid;
    }


    public String getTotalSteps() {
        return TotalSteps;
    }

    public String getWinner() {
        return Winner;
    }


    public String getStatus() {
        return Status;
    }


    public String getFirstUserHeight() {
        return FirstUserHeight;
    }


    public String getFirstUserAge() {
        return FirstUserAge;
    }


    public String getSecondUserHeight() {
        return SecondUserHeight;
    }


    public String getSecondAge() {
        return SecondAge;
    }


    public String getGroupname() {
        return Groupname;
    }


    public String getFirstuserID() {
        return FirstuserID;
    }


    public String getFirstcurrent_weight() {
        return Firstcurrent_weight;
    }


    public String getFirstTarget() {
        return FirstTarget;
    }


    public String getFirstimage() {
        return Firstimage;
    }


    public String getFirstUserName() {
        return FirstUserName;
    }


    public String getCharity() {
        return charity;
    }



    public String getSecondUserName() {
        return SecondUserName;
    }


    public String getSecondImage() {
        return SecondImage;
    }



    public String getSecondTarget() {
        return SecondTarget;
    }


    public String getSecondUserID() {
        return SecondUserID;
    }


}
