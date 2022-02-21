package ru.biis.biissale;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;


@SuppressLint("Registered")
public class SplashActivity extends AppCompatActivity {
    public static String token;
    private static final String TAG = "firebase_token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        token = task.getResult().getToken();
                        // Log and toast
                        //String msg = getString(R.string.msg_token_fmt, token);//закоментировал
                        //Log.d(TAG, msg);//закоментировал
                        // Toast.makeText(, msg, Toast.LENGTH_SHORT).show();
                        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
                        // sendRegistrationToServer(token);
                        //
                    }
                });
        //
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}