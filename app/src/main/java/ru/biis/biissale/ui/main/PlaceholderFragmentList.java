package ru.biis.biissale.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.PrimitiveIterator;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.biis.biissale.Catsbiis;
import ru.biis.biissale.Infobiis;
import ru.biis.biissale.Messagesbiis;
import ru.biis.biissale.R;
import ru.biis.biissale.rest.TooltipsApi;

public class PlaceholderFragmentList extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private SharedPreferences sPref;
    final String SAVED_USERID = "saved_userid";
    final String SAVED_ID = "saved_id";

    private PageViewModel pageViewModel;
    private RecyclerView recyclerView;
    private Button buttonSend;
    private ImageButton buttonfeedback;
    private Spinner staticSpinner;
    private EditText zapros;
    private EditText email;
    private EditText phone;
    private EditText address;
    public static List<Catsbiis> biiscatsSelect = new ArrayList<>();


    public static PlaceholderFragmentList newInstance(int index) {
        PlaceholderFragmentList fragment = new PlaceholderFragmentList();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_partner_profile, container, false);


        final TextView textView = root.findViewById(R.id.section_label);
        buttonSend = root.findViewById(R.id.sendzapros);
        zapros = root.findViewById(R.id.zapros);
        email = root.findViewById(R.id.email);
        phone = root.findViewById(R.id.phone);
        address = root.findViewById(R.id.address);
        buttonfeedback = root.findViewById(R.id.feedback);
        staticSpinner = (Spinner)  root.findViewById(R.id.spinnerCat);

        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.brew_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);

        Spinner dynamicSpinner = (Spinner) root.findViewById(R.id.spinnerCat);

        String[] items = new String[] { "Chai Latte", "Green Tea", "Black Tea" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, items);

        dynamicSpinner.setAdapter(adapter);

        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        buttonfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String toDial="tel:88002348585";
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(toDial)));
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View v) {

                //////////////////////////
                gotoUrl("http://www.bi-is.ru/directlink/userprofile?userid="+sPref.getString(SAVED_ID, ""));
                //////////////////////////
                /*


                String getName = zapros.getText().toString();
                String getEmail = email.getText().toString();
                String getPhone = phone.getText().toString();
                String getAddress = address.getText().toString();
                sPref =  Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
                String getuserid = sPref.getString(SAVED_USERID, "");

                //sPref =  Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
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

                retrofit2.Call<List<Messagesbiis>> called = tolltipsApi.setinfo(getName, getEmail, getPhone, getAddress, getuserid);

                called.enqueue(new Callback<List<Messagesbiis>>() {
                    @Override
                    public void onResponse(retrofit2.Call<List<Messagesbiis>> call, Response<List<Messagesbiis>> response) {
                        String ss="sdas";
                        //public void onResponse(Call<List<Messagesbiis>> call, Response<List<Messagesbiis>> response) {
                        if (response.isSuccessful()){
                            Log.i("response  Refresh", "response " + response.body() );
                        } else {
                            Log.i("response2 Refresh", "response code " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Messagesbiis>> call, Throwable t) {

                    }
                });*/
            }
        });

        sPref =  Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        // String savedUserid = sPref.getString(SAVED_USERID, "");
        String savedUserid = sPref.getString(SAVED_ID, "");
        Log.i("savedUseri", "savedUserid " + savedUserid);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bi-is.ru/wp-json/biis/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TooltipsApi tolltipsApi = retrofit.create(TooltipsApi.class);

        retrofit2.Call<Infobiis> called = tolltipsApi.infoPartner(savedUserid);

        called.enqueue(new Callback<Infobiis>() {
            @Override
            public void onResponse(retrofit2.Call<Infobiis> call, Response<Infobiis> response) {
                if (response.isSuccessful()) {

                    assert response.body() != null;
                    zapros.setText(response.body().getName());
                    email.setText(response.body().getEmail());
                    phone.setText(response.body().getPhone());
                    address.setText(response.body().getAddress());
                    Log.i("response INFO", "response " + response.body() );
                } else {
                    Log.i("response2 INFO", "response code " + response.code());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Infobiis> call, Throwable t) {
                Log.i("failure INFO", "" + t);
            }
        });

        return root;
    }

    //////////////////////////
    private void gotoUrl(String s){//метод для перехода по ссылке
        Uri uri= Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
    //////////////////////////


}
