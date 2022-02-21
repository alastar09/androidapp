package ru.biis.biissale;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notifbiis {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("userid")
    @Expose
    private String userid;

    @SerializedName("comment_id")
    @Expose
    private String comment_id;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("parent_id")
    @Expose
    private String parent_id;

    @SerializedName("call_name")
    @Expose
    private String call_name;

    @SerializedName("ava")
    @Expose
    private String ava;

    @SerializedName("autoid")
    @Expose
    private String autoid;

    @SerializedName("call_id")
    @Expose
    private String call_id;

    public Notifbiis(Long id, String userid, String comment_id, String msg, String parent_id, String call_name, String ava, String autoid, String call_id) {
        this.id = id;
        this.userid = userid;
        this.comment_id = comment_id;
        this.msg = msg;
        this.parent_id = parent_id;
        this.call_name = call_name;
        this.ava = ava;
        this.autoid = autoid;
        this.call_id = call_id;
    }

    public String getUserid() {
        return userid;
    }
    public String getMsg() {
        return msg;
    }
    public String getCommentid() {
        return comment_id;
    }
    public String getAva() {
        return ava;
    }
    public String getCallname() {
        return call_name;
    }
    public String getAutoid() {
        return autoid;
    }
    public String getParentid() {
        return parent_id;
    }
    public String getCallid() {
        return call_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notifbiis notifbiis = (Notifbiis) o;

        if (!id.equals(notifbiis.id)) return false;
        if (!msg.equals(notifbiis.msg)) return false;
        if (!comment_id.equals(notifbiis.comment_id)) return false;
        if (!call_id.equals(notifbiis.call_id)) return false;
        return userid != null ? userid.equals(notifbiis.userid) : notifbiis.userid == null;
    }
}
