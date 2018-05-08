package edu.tacoma.uw.css.uwtchaingang.chaingang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Member implements Serializable {
    public static final String FIRSTNAME = "fName";
    public static final String LASTNAME = "lName";
    public static final String EMAIL = "email";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "pass";

    private String mFirstName;
    private String mLastName;

    private String mEmail;
    private String mLogin;
    private String mPassword;
    private String mConfirmPassword;

    public Member(String mFirstName, String mLastName, String mEmail, String mLogin, String mPassword, String mConfirmPassword) {
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mEmail = mEmail;
        this.mLogin = mLogin;
        this.mPassword = mPassword;
        this.mConfirmPassword = mConfirmPassword;
    }



    public String getmFirstName() {
        return mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmLogin() {
        return mLogin;
    }

    public String getmPassword() {
        return mPassword;
    }

    public String getmConfirmPassword() {
        return mConfirmPassword;
    }


    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public void setmLogin(String mLogin) {
        this.mLogin = mLogin;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public void setmConfirmPassword(String mConfirmPassword) {
        this.mConfirmPassword = mConfirmPassword;
    }

}
