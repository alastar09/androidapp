package ru.biis.biissale;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Removebiis {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("callid")
    @Expose
    private String callid;

    public Removebiis(Long id, String parentid) {
        this.id = id;
        this.callid = parentid;
    }

    public String getCallid() {
        return callid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Removebiis removebiis = (Removebiis) o;

        if (!id.equals(removebiis.id)) return false;
        return callid != null ? callid.equals(removebiis.callid) : removebiis.callid == null;
    }
}
