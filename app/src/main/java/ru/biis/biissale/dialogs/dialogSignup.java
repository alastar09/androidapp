package ru.biis.biissale.dialogs;
//rade_pr1@gtrhrthtr.ru
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.biis.biissale.Authbiis;
import ru.biis.biissale.R;
import ru.biis.biissale.rest.TooltipsApi;

import static ru.biis.biissale.Authbiis.isOnline;

public class dialogSignup extends DialogFragment implements View.OnClickListener {

    private SharedPreferences sPref;

    final String SAVED_ID = "saved_id";
    final String SAVED_USERID = "saved_userid";
    final String SAVED_USERLOGIN = "saved_userlogin";
    final String SAVED_USERAVA   = "saved_userava";
    final String LOG_TAG = "dialogsLogs";

    private Button buttonAuth;
    private Button buttonReg;
    private Button buttonRegSend;
    private EditText nameAuth;
    private EditText passAuth;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {


        getDialog().setTitle("Авторизация");
        View v = inflater.inflate(R.layout.dialog_signin, null);
        v.findViewById(R.id.buttonAuth).setOnClickListener(this);

        buttonAuth = v.findViewById(R.id.buttonAuth);
        nameAuth   = v.findViewById(R.id.username);
        passAuth   = v.findViewById(R.id.password);
        buttonReg     = v.findViewById(R.id.buttonReg);
        buttonRegSend = v.findViewById(R.id.buttonRegSend);

        buttonReg.setOnClickListener( new View.OnClickListener(){

            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                passAuth.setVisibility(View.GONE);
                buttonAuth.setVisibility(View.GONE);
                buttonReg.setVisibility(View.GONE);
                buttonRegSend.setVisibility(View.VISIBLE);
                Dialog d = getDialog();
                if (d!=null){
                    int width = ViewGroup.LayoutParams.MATCH_PARENT;
                    d.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
                }

            }

        });


        buttonRegSend.setOnClickListener( new View.OnClickListener(){



            @Override
            public void onClick(View v) {
                String login = nameAuth.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://bi-is.ru/wp-json/biis/v1/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                TooltipsApi tolltipsApi = retrofit.create(TooltipsApi.class);//

                retrofit2.Call<Authbiis> called = tolltipsApi.reg(login);//

                if(isOnline(getContext())) {
                    called.enqueue(new Callback<Authbiis>() {
                        @Override
                        public void onResponse(retrofit2.Call<Authbiis> call, Response<Authbiis> response) {

                            if (response.isSuccessful()) {

                                Log.i("response list reg", "reg " + response.body());
                                assert response.body() != null;
                                if (!response.body().getId().equals("00000000-0000-0000-0000-000000000000")) {//if(!response.body().getUserid().equals("0"))
                                    saveAuth(response.body().getId(), response.body().getUserid(), response.body().getUserLogin(), response.body().getUserAva());
                                } else {
                                    @SuppressLint("ShowToast") Toast toast = Toast.makeText(getView().getContext(),
                                            "не допустимая почта ",
                                            Toast.LENGTH_LONG);
                                    toast.show();
                                }

                            } else {
                                Log.i("response reg", "response code " + response.code());
                            }


                        }

                        @Override
                        public void onFailure(Call<Authbiis> call, Throwable t) {
                            @SuppressLint("ShowToast") Toast toast = Toast.makeText(getView().getContext(),
                                    "Неверный логин или пароль 666",
                                    Toast.LENGTH_LONG);
                            toast.show();
                            Log.i("failure list", "" + t);
                        }

                    });
                }
                else{@SuppressLint("ShowToast") Toast toast = Toast.makeText(getView().getContext(),
                        "нет интернет соединения ",
                        Toast.LENGTH_LONG);
                    toast.show();}
            }
        });

        return v;
    }

    /**
     *
     */

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d!=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            //int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new Dialog(getActivity(), getTheme()){
            @Override
            public void onBackPressed() {
              String asd=LOG_TAG;
                if (buttonReg.getVisibility()==View.VISIBLE) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                    // this.finish();
                } else {
                    passAuth.setVisibility(View.VISIBLE);
                    buttonAuth.setVisibility(View.VISIBLE);
                    buttonReg.setVisibility(View.VISIBLE);
                    buttonRegSend.setVisibility(View.GONE);
                }
                // On backpress, do your stuff here.
            }
        };
    }
    @Override
    public void onClick(View v) {
        Log.d(LOG_TAG, "Dialog 1: "+ nameAuth.getText().toString());
        Log.d(LOG_TAG, "Dialog 1: "+ passAuth.getText().toString());

        String login = nameAuth.getText().toString();
        String pass = passAuth.getText().toString();

        //HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        //logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        //httpClient.addInterceptor(logging);
        //httpClient.cache(null);

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl("https://bi-is.ru/wp-json/biis/v1/")
                .addConverterFactory(GsonConverterFactory.create())
          //      .client(httpClient.build())
                .build();

        TooltipsApi tolltipsApi = retrofit.create(TooltipsApi.class);

  //      retrofit2.Call<Authbiis> called = tolltipsApi.auth(login, pass);
        retrofit2.Call<Authbiis> called=tolltipsApi.authV1(login, pass);

        if(isOnline(getContext())) {
            called.enqueue(new Callback<Authbiis>() {
                @Override
                public void onResponse(retrofit2.Call<Authbiis> call, Response<Authbiis> response) {
                    if (response.isSuccessful()) {

                        Log.i("response list", "response " + response.body());
                        assert response.body() != null;
                        String login = response.body().getUserLogin();

                        if (!login.equals("client000") && !login.equals("0")) {//
                            saveAuth(response.body().getId(), response.body().getUserid(), response.body().getUserLogin(), response.body().getUserAva());
                        } else {
                            @SuppressLint("ShowToast") Toast toast = Toast.makeText(getView().getContext(),
                                    "Авторизуйтесь под партнером",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        }
                    } else {
                        Log.i("response2", "response code " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Authbiis> call, Throwable t) {
                    @SuppressLint("ShowToast") Toast toast = Toast.makeText(getView().getContext(),
                            "Неверный логин или пароль",
                            Toast.LENGTH_LONG);
                    toast.show();
                    Log.i("failure list", "" + t);
                }

            });
        }
        else{@SuppressLint("ShowToast") Toast toast = Toast.makeText(getView().getContext(),
                "нет интернет соединения ",
                Toast.LENGTH_LONG);
            toast.show();}
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(LOG_TAG, "Dialog 1: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 1: onCancel");
    }

    private void saveAuth(String id, String userid, String userlogin, String userava) {
        Log.d(LOG_TAG, "Saved: " + userlogin);
        sPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_ID, id);
        ed.putString(SAVED_USERID, userid);
        ed.putString(SAVED_USERLOGIN, userlogin);
        ed.putString(SAVED_USERAVA, userava);
        ed.apply();
        //после сохранения закрываем диалог
        Objects.requireNonNull(getDialog()).dismiss();
        getActivity().recreate();

    }


}
