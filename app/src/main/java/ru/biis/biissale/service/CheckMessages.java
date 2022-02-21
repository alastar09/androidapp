package ru.biis.biissale.service;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.biis.biissale.MainActivity;
import ru.biis.biissale.NotifCallsbiis;
import ru.biis.biissale.Notifbiis;
import ru.biis.biissale.R;
import ru.biis.biissale.SplashActivity;
import ru.biis.biissale.rest.TooltipsApi;
import ru.biis.biissale.rest.status;

public class CheckMessages extends Service {

    public static final int TWO_MINUTES = 6000;

    public static Boolean isRunning = false;
    ExecutorService es;
    final Handler handler = new Handler();

    private static final int NOTIFY_ID = 101;
    private static final int NOTIFY_ID2 = 102;

    // Идентификатор канала
    private static String CHANNEL_ID = "Biis sale channel";
    private static String CHANNEL_ID_CALLS = "Biis sale calls";
    public static boolean freeId;
    public static ArrayList<String> SendNotifID = new ArrayList<>();
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class apiRun implements Runnable {

        String userId;
        int startId;
        PendingIntent pi;

        public apiRun(String  userId, int startId, PendingIntent pi) {
            this.userId = userId;
            this.startId = startId;
            this.pi = pi;
        }

        public void run() {

            try {
                // сообщаем об старте задачи
                pi.send(MainActivity.STATUS_START);

                // начинаем выполнение задачи
                Log.d("SERVICE", "SECONDS");
                startListening(this.pi, this.userId);
                // сообщаем об окончании задачи
                Intent intent = new Intent().putExtra(MainActivity.PARAM_USERID, userId);
                pi.send(CheckMessages.this, MainActivity.STATUS_FINISH, intent);

            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
            handler.postDelayed(this, TWO_MINUTES);
            stop();
        }

        void stop() {
            Log.d("END", "MyRun#" + startId + " end, stopSelfResult("
                    + startId + ") = " + stopSelfResult(startId));
        }
    }

    class apiRunCalls implements Runnable {

        String userId;
        int startId;
        PendingIntent pi;

        public apiRunCalls(String  userId, int startId, PendingIntent pi) {
            this.userId = userId;
            this.startId = startId;
            this.pi = pi;
        }

        public void run() {

            try {
                // сообщаем об старте задачи
                pi.send(MainActivity.STATUS_START);

                // начинаем выполнение задачи
                Log.d("SERVICE2", "SECONDS2");
                startListeningCalls(this.pi, this.userId);
                // сообщаем об окончании задачи
                Intent intent = new Intent().putExtra(MainActivity.PARAM_USERID, userId);
                pi.send(CheckMessages.this, MainActivity.STATUS_FINISH, intent);

            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
            handler.postDelayed(this, TWO_MINUTES);
            stop();
        }

        void stop() {
            Log.d("END", "MyRun#" + startId + " end, stopSelfResult("
                    + startId + ") = " + stopSelfResult(startId));
        }
    }

    private void startListening(PendingIntent pi, String userId) throws PendingIntent.CanceledException {

        pi.send(MainActivity.STATUS_START);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.cache(null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bi-is.ru/wp-json/biis/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        TooltipsApi tolltipsApi = retrofit.create(TooltipsApi.class);

        Call<List<Notifbiis>> called = tolltipsApi.notification(userId);

        called.enqueue(new Callback<List<Notifbiis>>() {
            @Override
            public void onResponse(Call<List<Notifbiis>> call, Response<List<Notifbiis>> response) {
                if (response.isSuccessful()) {

                    Intent notificationIntent = new Intent(CheckMessages.this, MainActivity.class);

                    assert response.body() != null;
                    for (Notifbiis notifbiis : response.body()) {
                        String id = notifbiis.getUserid();
                        String parentid = notifbiis.getParentid();
                        String msg = notifbiis.getMsg();
                        String callname = notifbiis.getCallname();
                        String autoid = notifbiis.getAutoid();
                        String callid = notifbiis.getCallid();
                        //String ava = notifbiis.getAva();

                        if(!SendNotifID.contains(autoid)){
                        notificationIntent.putExtra("EXTRA_AUTO_ID", autoid);
                        //notificationIntent.putExtra("EXTRA_PARENT_ID", parentid);
                        PendingIntent contentIntent = PendingIntent.getActivity(CheckMessages.this,
                                Integer.parseInt(autoid), notificationIntent,
                                PendingIntent.FLAG_CANCEL_CURRENT);
                        Log.i("response notif", "autoid " + autoid);


                            SendNotifID.add(autoid);
                        NotificationManager notificationManagerCh =
                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel_ch = notificationManagerCh.getNotificationChannel(CHANNEL_ID);
                        if (channel_ch==null) {

                            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My channel",
                                    NotificationManager.IMPORTANCE_HIGH);
                            channel.setDescription("My channel description");
                            channel.enableLights(true);
                            channel.setLightColor(Color.RED);
                            channel.enableVibration(false);
                            notificationManagerCh.createNotificationChannel(channel);
                        }}

                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(CheckMessages.this, CHANNEL_ID)
                                        .setSmallIcon(R.mipmap.ic_launcher_biis_icon_orange)
                                        .setContentTitle(callname)
                                        .setContentText(msg)
                                        .setContentIntent(contentIntent)
                                        .setAutoCancel(true)
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        //Bitmap bmp = Picasso.with(getApplicationContext()).load(ava).into();
                        // builder.setLargeIcon(bmp);

                        NotificationManagerCompat notificationManager =
                                NotificationManagerCompat.from(CheckMessages.this);
                        notificationManager.notify(Integer.parseInt(callid), builder.build());
                    }}

                } else {
                    Log.i("response2 notif", "response code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Notifbiis>> call, Throwable t) {
                Log.i("failure notif", "" + t);
            }

        });

        //isRunning = true;
        if (freeId==false){
            if (userId.length()>0) {
                SendFreeID(userId);
            }
        }
    }
    void  SendFreeID(String userid) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bi-is.ru/wp-json/biis/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TooltipsApi tolltipsApi = retrofit.create(TooltipsApi.class);

        retrofit2.Call<status> called = tolltipsApi.status(userid, SplashActivity.token);
        called.enqueue(new Callback<status>() {
            @Override
            public void onResponse(Call<status> call, Response<status> response) {
                Log.i("SendFreeID response", "response code " + response.code());
                if (response.body()!=null) {Log.i("bodu  ",response.body().getStatus());}
                String st= response.body().getStatus();
                Log.i("st ------------  ",st);
                if ("OK".equals(st)){
                    freeId=true;
                }
            }

            @Override
            public void onFailure(Call<status> call, Throwable t) {
                Log.i("failure notif", "" + t);


            }
        });
    }
    private void startListeningCalls(PendingIntent pi, String userId) throws PendingIntent.CanceledException {

        pi.send(MainActivity.STATUS_START);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.cache(null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bi-is.ru/wp-json/biis/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        TooltipsApi tolltipsApi = retrofit.create(TooltipsApi.class);

        retrofit2.Call<List<NotifCallsbiis>> called = tolltipsApi.notificationCalls(userId);

        called.enqueue(new Callback<List<NotifCallsbiis>>() {
            @Override
            public void onResponse(retrofit2.Call<List<NotifCallsbiis>> call, Response<List<NotifCallsbiis>> response) {
                if (response.isSuccessful()) {

                    Intent notificationIntent = new Intent(CheckMessages.this, MainActivity.class);

                    assert response.body() != null;
                    for (NotifCallsbiis notifCallsbiis : response.body()) {
                        String id = notifCallsbiis.getUserid();
                        String title = notifCallsbiis.getTitle();
                        String msg = notifCallsbiis.getMsg();
                        String autoid = notifCallsbiis.getAutoid();
                        String callid = notifCallsbiis.getCallid();

                        if(!SendNotifID.contains(autoid)){
                        notificationIntent.putExtra("EXTRA_AUTO_ID", autoid);
                        //notificationIntent.putExtra("EXTRA_PARENT_ID", parentid);
                        PendingIntent contentIntent = PendingIntent.getActivity(CheckMessages.this,
                                Integer.parseInt(autoid), notificationIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        Log.i("response3 notif", "autoid " + autoid);
                            SendNotifID.add(autoid);
                            NotificationManager notificationManagerCh =
                                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
                                NotificationChannel channel_ch = notificationManagerCh.getNotificationChannel(CHANNEL_ID);
                            if (channel_ch==null) {
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My channel",
                                            NotificationManager.IMPORTANCE_HIGH);
                                    channel.setDescription("My channel description");
                                    channel.enableLights(true);
                                    channel.setLightColor(Color.RED);
                                    channel.enableVibration(false);
                                    notificationManagerCh.createNotificationChannel(channel);
                                }}}

                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(CheckMessages.this, CHANNEL_ID_CALLS)
                                        .setSmallIcon(R.mipmap.ic_launcher_biis_icon_orange)
                                        .setContentTitle(title)
                                        .setContentText(msg)
                                        .setContentIntent(contentIntent)
                                        .setAutoCancel(true)
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        //Bitmap bmp = Picasso.with(getApplicationContext()).load(ava).into();
                        // builder.setLargeIcon(bmp);

                        NotificationManagerCompat notificationManager =
                                NotificationManagerCompat.from(CheckMessages.this);
                        notificationManager.notify(Integer.parseInt(callid), builder.build());
                    }}

                } else {
                    Log.i("response3 notif", "response code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<NotifCallsbiis>> call, Throwable t) {
                Log.i("failure notif", "" + t);
            }

        });

        //isRunning = true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("SERVICE", "create2");
        es = Executors.newFixedThreadPool(2);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("SERVICE", "working");
        String userId = intent.getStringExtra(MainActivity.PARAM_USERID);
        PendingIntent pi = intent.getParcelableExtra(MainActivity.PARAM_PINTENT);
        Log.d("SERVICE", "userId "+userId);
        Log.d("SERVICE", "pi "+pi);
        apiRun mr = new apiRun(userId, startId, pi);
        apiRunCalls mrcalls = new apiRunCalls(userId, startId, pi);
        //es.execute(new apiRun(userId, startId, pi));
        //serviceTask(userId);
        Thread childTread = new Thread(mr);
        Thread childTreadCalls = new Thread(mrcalls);
        childTread.start();
        childTreadCalls.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
