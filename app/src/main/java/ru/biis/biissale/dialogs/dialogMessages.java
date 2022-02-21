package ru.biis.biissale.dialogs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.biis.biissale.Messagesbiis;
import ru.biis.biissale.R;
import ru.biis.biissale.adapter.BiisMessage;
import ru.biis.biissale.rest.TooltipsApi;

public class dialogMessages extends DialogFragment {

    private RecyclerView recyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Сообщения");
        View v = inflater.inflate(R.layout.dialog_message, null);

        BiisMessage biisMessage = new BiisMessage();

        final FragmentActivity c = getActivity();
        recyclerView = (RecyclerView) v.findViewById(R.id.rvmessage);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(biisMessage);

        /*
        Получаем ответы на запрос
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bi-is.ru/wp-json/biis/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TooltipsApi tolltipsApi = retrofit.create(TooltipsApi.class);

        retrofit2.Call<List<Messagesbiis>> called = tolltipsApi.dialogPartner("526", "709");

        called.enqueue(new Callback<List<Messagesbiis>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Messagesbiis>> call, Response<List<Messagesbiis>> response) {
                if (response.isSuccessful()) {

                    BiisMessage.setItems(response.body());
                    recyclerView.getAdapter().notifyDataSetChanged();
                    Log.i("response list", "response " + response.body() );
                } else {
                    Log.i("response2", "response code " + response.code());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<Messagesbiis>> call, Throwable t) {
                Log.i("failure detail", "" + t);
            }
        });

        return v;
    }
}
