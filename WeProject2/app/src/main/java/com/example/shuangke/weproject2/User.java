package com.example.shuangke.weproject2;

/**
 * Created by XinZhang on 4/10/17.
 */

public class User {

    public String first;
    public String lastname;
    public String numcounts;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String first, String last,String numcounts) {
        this.first = first;
        this.lastname = last;
        this.numcounts = numcounts;
    }

}