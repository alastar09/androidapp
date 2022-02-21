package ru.biis.biissale;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;
import android.app.Activity;
import android.os.Bundle;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Surface;
import android.view.Display;
import android.view.WindowManager;
import java.io.IOException;
import android.app.Activity;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;


public class Authbiis {

 //   private Long id;
    private String id;
    private String userid;
    private String userlogin;
    private String userava;


    public Authbiis(String id, String userid, String userlogin, String userava) {
        this.id     = id;
        this.userid = userid;
        this.userlogin = userlogin;
        this.userava   = userava;
    }
    public  String getId() {
        return  id;
    }

    public String getUserid() {
        return userid;
    }

    public String getUserLogin() {
        return userlogin;
    }

    public String getUserAva() {
        return userava;
    }

    public static boolean isOnline(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authbiis authbiis = (Authbiis) o;

        if (!id.equals(authbiis.id)) return false;
        if (!userlogin.equals(authbiis.userlogin)) return false;
        if (!userava.equals(authbiis.userava)) return false;
        return userid != null ? userid.equals(authbiis.userid) : authbiis.userid == null;
    }
}