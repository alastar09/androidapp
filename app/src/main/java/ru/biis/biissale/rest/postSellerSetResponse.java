package ru.biis.biissale.rest;

public class postSellerSetResponse {
private String Content;
private int Cost;
private String UserRequestId;
private String AppUserId;


    public void setContent(String content) {
        Content = content;
    }

    public void setCost(int cost) {
        this.Cost = cost;
    }

    public void setAppUserId(String appUserId) {
        this.AppUserId = appUserId;
    }

    public void setUserRequestId(String userRequestId) {
        UserRequestId = userRequestId;
    }

    @Override
    public String toString() {
        return "postSellerSetResponse{" +
                "Content:" + Content +
                "Cost:" + Cost +
                "UserRequestId:" + UserRequestId +
                "AppUserId:" + AppUserId +
                '}';
    }

}
