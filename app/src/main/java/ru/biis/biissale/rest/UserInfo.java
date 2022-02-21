package ru.biis.biissale.rest;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserInfo {

    @SerializedName("userid")
    @Expose
    private String userid;

    private String userLogin;

    public UserInfo(String userId){
        this.userid=userId;
    }
    private static ArrayList arId = new ArrayList();
    private static ArrayList arLogin = new ArrayList();
    private static ArrayList arAva = new ArrayList();
    private static String ret;

    public static String getUserLogin(final String userId) {
        //  x
        //int i=0;
       // String ret = "";
        ret="";
        for (int i = 0; i < arId.size(); i++) {
        if (arId.get(i).equals(userId)) ret = (String) arLogin.get(i);
        }
        if (ret==null){//.equals(""))  {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);
            httpClient.cache(null);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://bi-is.ru/wp-json/biis/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            TooltipsApi tolltipsApi = retrofit.create(TooltipsApi.class);

            retrofit2.Call<userinfoProfeller> called = tolltipsApi.userinforrof(userId);
            called.enqueue(new Callback<userinfoProfeller>() {
                @Override
                public void onResponse(Call<userinfoProfeller> call, Response<userinfoProfeller> response) {
                    if  (response.isSuccessful()){
                        assert response.body()!=null;
                        userinfoProfeller userinfoProfeller = response.body();
                        String bbbbbb=userinfoProfeller.getId();
                        String buserid = userinfoProfeller.getUserid();
                        String buserava = userinfoProfeller.getUserProfileAvatar();
                        String busername = userinfoProfeller.getUserName();
                        ret = busername;
                        arId.add(buserid);
                        arLogin.add(busername);
                        arAva.add(buserava);
                    } else {
                        //Log.i("userinfo response", "response code " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<userinfoProfeller> call, Throwable t) {
                    //Log.i("failure userinfo", "" + t);
                }
            });
/*            try {
                Response<userinfoProfeller> response = called.execute();
                if (response.isSuccessful()){
                assert response.body()!=null;
                    userinfoProfeller userinfoProfeller = (ru.biis.biissale.rest.userinfoProfeller) response.body();
                    String bbbbbb=userinfoProfeller.getId();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/





        }


        return ret;
    }


    public static String getUserAva(final String userId) {
        //  x
        //int i=0;
        // String ret = "";
        ret="";
        for (int i = 0; i < arId.size(); i++) {
            if (arId.get(i).equals(userId)) ret = (String) arAva.get(i);
        }
        if (ret.equals(""))  {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);
            httpClient.cache(null);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://bi-is.ru/wp-json/biis/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            TooltipsApi tolltipsApi = retrofit.create(TooltipsApi.class);

            retrofit2.Call<userinfoProfeller> called = tolltipsApi.userinforrof(userId);
            called.enqueue(new Callback<userinfoProfeller>() {
                @Override
                public void onResponse(Call<userinfoProfeller> call, Response<userinfoProfeller> response) {
                    if  (response.isSuccessful()){
                        assert response.body()!=null;
                        userinfoProfeller userinfoProfeller = response.body();
                        String bbbbbb=userinfoProfeller.getId();
                        String buserid = userinfoProfeller.getUserid();
                        String buserava = userinfoProfeller.getUserProfileAvatar();
                        String busername = userinfoProfeller.getUserName();
                        ret = busername;
                        arId.add(buserid);
                        arLogin.add(busername);
                        arAva.add(buserava);
                    } else {
                        //Log.i("userinfo response", "response code " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<userinfoProfeller> call, Throwable t) {
                    //Log.i("failure userinfo", "" + t);
                }
            });
/*            try {
                Response<userinfoProfeller> response = called.execute();
                if (response.isSuccessful()){
                assert response.body()!=null;
                    userinfoProfeller userinfoProfeller = (ru.biis.biissale.rest.userinfoProfeller) response.body();
                    String bbbbbb=userinfoProfeller.getId();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/





        }


        return ret;
    }


}
