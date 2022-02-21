package ru.biis.biissale.rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class status {
    @SerializedName("userid")
    @Expose
    private Long userid;

    @SerializedName("appid")
    @Expose
    private String appid;

    @SerializedName("status")
    @Expose
    private String status;

    public status(Long userid, String parentid) {
        this.userid = userid;
        this.appid = parentid;
    }
    public  String getStatus() {
        return status;
    }

    public String getCallid() {
        return appid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        status status = (status) o;

        if (!userid.equals(status.appid)) return false;
        return appid != null ? appid.equals(status.appid) : status.appid == null;
    }
}
