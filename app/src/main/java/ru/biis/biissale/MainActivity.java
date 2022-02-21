package ru.biis.biissale;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.biis.biissale.rest.TooltipsApi;
import ru.biis.biissale.service.CheckMessages;
import ru.biis.biissale.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sPref;
    final String SAVED_USERID = "saved_userid";
    final int TASK1_CODE = 1;
    public final static String PARAM_USERID = "pendingUserid";
    public final static String PARAM_PINTENT = "pendingIntent";
    public final static String CHANNEL_ID_Msg = "chanel_id_msg";

    public final static int STATUS_START = 100;
    public final static int STATUS_FINISH = 200;
    private String autiidCh=null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Ловим сообщения о старте задач

        if (data != null ) {
            String autoid = getIntent().getStringExtra("EXTRA_AUTO_ID");
            if (autoid != null  && autiidCh != autoid) {
                autiidCh=autoid;
                for (int i=0; i<CheckMessages.SendNotifID.size();i++) {
                    autoid=CheckMessages.SendNotifID.get(i);
                    Log.d("FROM SERVICE", "data = " + autoid);
                    Log.d("FROM SERVICE", "requestCode = " + requestCode);
                    Log.d("FROM SERVICE", "resultCode = " + resultCode);
                    Log.d("FROM SERVICE", "resultCode = " + resultCode);

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

                    retrofit2.Call<Removebiis> called = tolltipsApi.notificationRemove(autoid);
                called.enqueue(new Callback<Removebiis>() {
                    @Override
                    public void onResponse(retrofit2.Call<Removebiis> call, Response<Removebiis> response) {
                        if (response.isSuccessful()) {

                            NotificationManager mNotificationManager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                NotificationChannel channel = new NotificationChannel(CHANNEL_ID_Msg, "My channel",
                                        NotificationManager.IMPORTANCE_HIGH);
                                channel.setDescription("My channel description");
                                channel.enableLights(true);
                                channel.setLightColor(Color.RED);
                                channel.enableVibration(false);
                                mNotificationManager.createNotificationChannel(channel);
                                }
                            assert mNotificationManager != null;
                            assert response.body() != null;
                            String callid = response.body().getCallid();
                            Log.i("notif parentid", "parentid " + callid);
                            if (callid != "0") {
                                int parentidInt = Integer.parseInt(callid);
                                mNotificationManager.cancel(parentidInt);
                            }
                        } else {
                            Log.i("response2 notif", "response code " + response.code());
                        }
                    }


                    @Override
                    public void onFailure(Call<Removebiis> call, Throwable t) {
                        Log.i("failure notif", "" + t);
                    }
                });

            };
                for (int i=CheckMessages.SendNotifID.size()-1;i>=0;i--){
                    CheckMessages.SendNotifID.remove(i);};
        }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PendingIntent pi;
        Intent intent;

        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        //FloatingActionButton fab = findViewById(R.id.fab);



        sPref = getPreferences(Context.MODE_PRIVATE);
        String savedUserid = sPref.getString(SAVED_USERID, "");

        Log.d("ACTIVITY", "savedUserid = " + savedUserid);

        //сервис проверки уведомлений
        Intent resultIntent = new Intent(this, MainActivity.class);
        pi = createPendingResult(TASK1_CODE, resultIntent, 0);
        intent = new Intent(this, CheckMessages.class).putExtra(PARAM_USERID, savedUserid.toString())
                .putExtra(PARAM_PINTENT, pi);

        startService(intent);


      /*  fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   Snackbar.make(view, "Уведомлений нет", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */


    /*                Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://bi-is.ru/wp-json/biis/v1/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    TooltipsApi tolltipsApi = retrofit.create(TooltipsApi.class);
                sPref = getPreferences(Context.MODE_PRIVATE);
                String savedUserid = sPref.getString(SAVED_USERID, "");
                    retrofit2.Call<List<Callbiis>> called = tolltipsApi.callsPartner(savedUserid);

                    called.enqueue(new Callback<List<Callbiis>>() {
                        private void loadCalls(List<Callbiis> response) {
                            //Collection<Call> calls = getCalls();
                            BiisCalls.setItems(response);

                        }

                        private RecyclerView recyclerView;
                        @Override
                        public void onResponse(retrofit2.Call<List<Callbiis>> call, Response<List<Callbiis>> response) {
                            if (response.isSuccessful()) {

                                assert response.body() != null;

                                setContentView(R.layout.fragment_main);
                                RecyclerView RecyclerView = findViewById(R.id.rv);
                               ///recyclerView = (RecyclerView) recyclerView.findViewById(R.id.rv);

                                recyclerView.getAdapter().notifyDataSetChanged();
                                Log.i("response list callback", "response " + response.body() );
                            } else {
                                Log.i("response2", "response code " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<List<Callbiis>> call, Throwable t) {
                            Log.i("failure list", "" + t);
                        }
                    });


            }
        });*/


    }
    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            finish();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }
}