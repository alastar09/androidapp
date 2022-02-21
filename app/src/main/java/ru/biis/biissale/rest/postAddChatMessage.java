package ru.biis.biissale.rest;

public class postAddChatMessage {
    private String Content;
    private String ParentId;
    private String UserRequestId;
    private String AppUserId;


    public void setContent(String content) {
        Content = content;
    }

    public void setParentId(String parentId) {
        this.ParentId = parentId;
    }

    public void setAppUserId(String appUserId) {
        this.AppUserId = appUserId;
    }

    public void setUserRequestId(String userRequestId) {
        UserRequestId = userRequestId;
    }

    @Override
    public String toString() {
        return "postAddChatMessage{" +
                "Content:" + Content +
                "ParentId:" + ParentId +
                "UserRequestId:" + UserRequestId +
                "AppUserId:" + AppUserId +
                '}';
    }

}
