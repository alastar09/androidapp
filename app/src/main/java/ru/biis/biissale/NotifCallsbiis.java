package ru.biis.biissale;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotifCallsbiis {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("userid")
    @Expose
    private String userid;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("cats")
    @Expose
    private String cats;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("autoid")
    @Expose
    private String autoid;

    @SerializedName("call_id")
    @Expose
    private String call_id;

    public NotifCallsbiis(Long id, String userid, String title, String msg, String autoid, String call_id, String cats) {
        this.id = id;
        this.userid = userid;
        this.title = title;
        this.cats = cats;
        this.msg = msg;
        this.autoid = autoid;
        this.call_id = call_id;
    }

    public String getUserid() {
        return userid;
    }
    public String getMsg() {
        return msg;
    }
    public String getTitle() {
        return title;
    }
    public String getCats() {
        return cats;
    }
    public String getAutoid() {
        return autoid;
    }
    public String getCallid() {
        return call_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotifCallsbiis notifCallsbiis = (NotifCallsbiis) o;

        if (!msg.equals(notifCallsbiis.msg)) return false;
        if (!title.equals(notifCallsbiis.title)) return false;
        if (!cats.equals(notifCallsbiis.cats)) return false;
        return userid != null ? userid.equals(notifCallsbiis.userid) : notifCallsbiis.userid == null;
    }
}
