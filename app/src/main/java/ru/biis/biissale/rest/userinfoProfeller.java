package ru.biis.biissale.rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class userinfoProfeller {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userProfileAvatar")
    @Expose
    private String userProfileAvatar;

    public userinfoProfeller(String userId, String userName, String userProfileAvatar, String id) {
        this.userId = userId;
        this.userName = userName;
        this.userProfileAvatar = userProfileAvatar;
        this.id=id;
    }
    public String getId(){
        return id;
    }
    public String getUserid() {
        return userId;
    }

    public String getUserProfileAvatar() {
        return userProfileAvatar;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        userinfoProfeller userinfoProfeller= (userinfoProfeller) o;

        if (!id.equals(userinfoProfeller.id)) return false;
        return id != null ? id.equals(userinfoProfeller) : userinfoProfeller.id == null;
    }
}