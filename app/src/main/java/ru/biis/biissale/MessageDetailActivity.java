package ru.biis.biissale;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.biis.biissale.adapter.BiisMessage;
import ru.biis.biissale.rest.TooltipsApi;
import ru.biis.biissale.rest.postSellerSetResponse;
import ru.biis.biissale.rest.postAddChatMessage;

public class MessageDetailActivity extends AppCompatActivity {

    private SharedPreferences sPref;

    final String SAVED_RESPID = "saved_responseid";
    final String SAVED_CALLID = "saved_callid";
    final String SAVED_USERID = "saved_userid";

    private TextView section_label_dialog;
    private TextView section_label_date;
    private TextView msgText;
    private TextView setprice;
    private ImageView buttonSendMsg;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private String valueCallID;
    private String valuePespID;
    private String valueUserID;
    private String valueCallname;
    private String valueCalldate;
    private String valueParentID;

    public boolean kollmsg=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String commentid = getIntent().getStringExtra("EXTRA_COMMENT_ID");
        valueCallID = getIntent().getStringExtra("EXTRA_CALL_ID");
        valuePespID = getIntent().getStringExtra("EXTRA_RESP_ID");
        valueUserID = getIntent().getStringExtra("EXTRA_USER_ID");
        valueParentID = getIntent().getStringExtra("EXTRA_RESP_ID");
        valueCallname = getIntent().getStringExtra("EXTRA_CALL_NAME");
        valueCalldate = getIntent().getStringExtra("EXTRA_CALL_DATE");
        setContentView(R.layout.activity_message);
        buttonSendMsg = (ImageView) findViewById(R.id.buttonSendMsg);
        msgText = (TextView) findViewById(R.id.msgText);
        setprice = (TextView) findViewById(R.id.setprice);
        section_label_dialog = (TextView) findViewById(R.id.section_label_dialog);
        section_label_date = (TextView) findViewById(R.id.section_label_date);
        recyclerView = (RecyclerView) findViewById(R.id.rvmessage);


        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        section_label_dialog.setText(valueCallname);
        section_label_date.setText(valueCalldate);

        BiisMessage biisMessage = new BiisMessage();
        recyclerView.setAdapter(biisMessage);
        //Проверяем есть ли id запроса и ответа
        //String valueCallID = loadCallID();
        //String valuePespID = loadRespID();

        loadRetrofildList(commentid, valueCallID);


        buttonSendMsg.setOnClickListener(bSendMsgListener);
    }

    private final View.OnClickListener bSendMsgListener = new View.OnClickListener() {
        public void onClick(View v) {

            //String valueCallID = loadCallID();
            //String valuePespID = loadRespID();
            //String valueUserID = loadUserID();

            String valueMsg = msgText.getText().toString();
            String valuePrice = "1";//setprice.getText().toString();
            msgText.setText("");
            //kollmsg= 2;
            //if (response.body().size()==1)//проверка первое ли сообщение
            if ((valueMsg.length() > 1) /*|| (valuePrice.length() > 1)*/) {
                if (!kollmsg){
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
                Log.i("ALERTT", "metod " + "postSellerSetResponse");
                Log.i("ALERTT", "valuePespID " + valuePespID);
                Log.i("ALERTT", "valueCallID " + valueCallID);
                Log.i("ALERTT", "valueMsg " + valueMsg);
                Log.i("ALERTT", "valueUserID " + valueUserID);
                Log.i("ALERTT", "valuePrice " + valuePrice);

                //retrofit2.Call<List<Messagesbiis>> called = tolltipsApi.setmsg(valuePespID, valueCallID, valueMsg, valueUserID, valuePrice);
                postSellerSetResponse post = new postSellerSetResponse();
                post.setAppUserId(valueUserID);
                post.setContent(valueMsg);
                post.setCost(0);
                post.setUserRequestId(valueCallID.toString());

                retrofit2.Call<postSellerSetResponse> called = tolltipsApi.postSellerSetResponse(post);
                //   String commentid = getIntent().getStringExtra("EXTRA_COMMENT_ID");
                //   loadRetrofildList(commentid, valueCallID);
                called.enqueue(new Callback<postSellerSetResponse>() {
                    @Override
                    public void onResponse(retrofit2.Call<postSellerSetResponse> call, Response<postSellerSetResponse> response) {
                        Log.i("123", String.valueOf(response.code()));
                    }

                    @Override
                    public void onFailure(Call<postSellerSetResponse> call, Throwable t) {
                        Log.i("failure ", "" + t);

                    }
                });}
                else {
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
                    Log.i("ALERTT", "metod " + "postAddChatMessage");
                    Log.i("ALERTT", "valueParentID " + valueParentID);
                    Log.i("ALERTT", "valueCallID " + valueCallID);
                    Log.i("ALERTT", "valueMsg " + valueMsg);
                    Log.i("ALERTT", "valueUserID " + valueUserID);
                    //Log.i("ALERTT", "valuePrice " + valuePrice);

                    //retrofit2.Call<List<Messagesbiis>> called = tolltipsApi.setmsg(valuePespID, valueCallID, valueMsg, valueUserID, valuePrice);
                    postAddChatMessage postCM = new postAddChatMessage();
                    postCM.setAppUserId(valueUserID);
                    postCM.setContent(valueMsg);
                    postCM.setParentId(valueParentID);
                    //postCM.setCost(0);
                    postCM.setUserRequestId(valueCallID.toString());

                    retrofit2.Call<postAddChatMessage> called = tolltipsApi.postAddChatMessage(postCM);
                       String commentid = getIntent().getStringExtra("EXTRA_COMMENT_ID");
                       loadRetrofildList(commentid, valueCallID);

                    called.enqueue(new Callback<postAddChatMessage>() {
                        @Override
                        public void onResponse(retrofit2.Call<postAddChatMessage> call, Response<postAddChatMessage> response) {
                            String commentid = getIntent().getStringExtra("EXTRA_COMMENT_ID");
                            loadRetrofildList(commentid, valueCallID);
                            Log.i("123", String.valueOf(response.code()));
                        }

                        @Override
                        public void onFailure(Call<postAddChatMessage> call, Throwable t) {
                            String commentid = getIntent().getStringExtra("EXTRA_COMMENT_ID");
                            loadRetrofildList(commentid, valueCallID);
                            Log.i("failure ", "" + t);

                        }
                    });
                }
/**
 called.enqueue(new Callback<List<Messagesbiis>>() {
@Override public void onResponse(retrofit2.Call<List<Messagesbiis>> call, Response<List<Messagesbiis>> response) {
if (response.isSuccessful()) {

BiisMessage.addItem(response.body());
recyclerView.getAdapter().notifyDataSetChanged();
msgText.setText("");


Log.i("response list", "response " + response.body());
} else {
Log.i("response2", "response code " + response.code());
}
}

@Override public void onFailure(retrofit2.Call<List<Messagesbiis>> call, Throwable t) {
Log.i("failure list", "" + t);
}
});
 **/
                //Intent intent = new Intent(MessageDetailActivity.this, MainActivity.class);
                //startActivity(intent);
                //finish();
            } else {
                Snackbar.make(v, R.string.no_message1, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }


    };

    private String loadCallID() {
        sPref = getPreferences(Context.MODE_PRIVATE);
        return sPref.getString(SAVED_CALLID, "");
    }

    private String loadRespID() {
        sPref = getPreferences(Context.MODE_PRIVATE);
        return sPref.getString(SAVED_RESPID, "");
    }

    private String loadUserID() {
        sPref = getPreferences(Context.MODE_PRIVATE);
        return sPref.getString(SAVED_USERID, "");
    }

    private void loadRetrofildList(String valueCommentID, String valueCallID) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bi-is.ru/wp-json/biis/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        TooltipsApi tolltipsApi = retrofit.create(TooltipsApi.class);

        retrofit2.Call<List<Messagesbiis>> called = tolltipsApi.dialogPartner(valueUserID, valueCallID);

        called.enqueue(new Callback<List<Messagesbiis>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Messagesbiis>> call, Response<List<Messagesbiis>> response) {
                if (response.isSuccessful()) {

                    BiisMessage.setItems(response.body());
                    recyclerView.getAdapter().notifyDataSetChanged();
                    EditText editText1=(EditText) findViewById(R.id.msgText);
                    EditText editText2=(EditText) findViewById(R.id.setprice);
                    // про логин
                    /*if (response.isSuccessful()) {//крашится если нет сообщений
                        assert response.body() != null;
                        String usid = response.body().get(0).getAppUserId();//0
                        String ttt= UserInfo.getUserLogin(usid);
                        Log.i("ttttttt ","ttttttt "+ttt);
                    }*/

                    if (response.body().size()==0)//проверка первое ли сообщение
                    {
                        //editText1.setEnabled(false);
                        //editText2.setEnabled(false);
                        kollmsg=false;//не первое
                    }
                    else
                    {
                        editText1.setEnabled(true);
                        editText2.setEnabled(true);
                        kollmsg=true;//первое
                        if (recyclerView.getAdapter().getItemCount()>2) {//перемотка до последнего сообщения
                            recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount() - 1);}
                    };
                    Log.i("response msg", "response " + response.body().size() );
                } else {
                    Log.i("response2 msg", "response code " + response.code());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<Messagesbiis>> call, Throwable t) {
                Log.i("failure msg", "" + t);
            }
        });
    }


}