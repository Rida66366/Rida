package com.example.cts;
import java.io.Serializable;
public class UserModel implements Serializable {


    public String name;
    public String email;
    public  String address;
    public String mobileNo;
    public String role;
    public boolean isAccepted;
    public  boolean isAlloted;


    public  UserModel()
    {

    }

    public UserModel(String name,String email,String address, String mobileNo, String role, boolean isAccepted, boolean isAlloted) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.mobileNo = mobileNo;
        this.role = role;
        this.isAccepted = isAccepted;
        this.isAlloted = isAlloted;
        ;
    }

}

















