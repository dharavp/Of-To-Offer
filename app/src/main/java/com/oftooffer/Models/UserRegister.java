package com.oftooffer.Models;

/**
 * Created by dsk-221 on 30/3/17.
 */

public class UserRegister {
    private String userEmail;
    private String userName;
    private String userMono;
    private String userPass;
    private String userTitle;

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMono() {
        return userMono;
    }

    public void setUserMono(String userMono) {
        this.userMono = userMono;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
}
