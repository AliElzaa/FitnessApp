package com.regexbyte.councildata;

/**
 * Created by hp on 8/10/2018.
 */

public class GroupData {
    String downloadUrl;
    String groupname;
    String groupId;

    public GroupData(String groupname, String downloadUrl, String groupid) {
        this.downloadUrl = downloadUrl;
        this.groupname = groupname;
        this.groupId=groupid;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
