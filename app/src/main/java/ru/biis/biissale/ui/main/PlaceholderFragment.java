package ru.biis.biissale.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.biis.biissale.Callbiis;
import ru.biis.biissale.R;
import ru.biis.biissale.adapter.BiisCalls;
import ru.biis.biissale.dialogs.dialogSignup;
import ru.biis.biissale.rest.TooltipsApi;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static ru.biis.biissale.Authbiis.isOnline;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private SharedPreferences sPref;

    final String SAVED_ID = "saved_id";
    final String SAVED_USERID = "saved_userid";
    final String SAVED_USERLOGIN = "saved_userlogin";

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private RecyclerView recyclerView;
    private TextView section_login;
    private Button buttonLogOut;
   // private Button fab1;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DialogFragment dlgAuth;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onStart() {
        super.onStart();
        refreshZayavki();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);

        //?????????????????? ???????? ???? ??????????????????????
        boolean flagAuth = loadAuth();

        if ( !flagAuth ) {
            dlgAuth = new dialogSignup();
            assert getFragmentManager() != null;
            dlgAuth.setCancelable(false);
            dlgAuth.show(getFragmentManager(), "dlgAuth");

        }

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
        View root = inflater.inflate(R.layout.fragment_main, container, false);


        section_login = (TextView) root.findViewById(R.id.section_login);
        BiisCalls biisCalls = new BiisCalls();
        /*
         * ?????????????? ????????????
         */
        final FragmentActivity c = getActivity();
        recyclerView = (RecyclerView) root.findViewById(R.id.rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(biisCalls);

        buttonLogOut = (Button) root.findViewById(R.id.buttonLogOut);
        /*
         * ???????????? ???? ???????? ???? ????????????????
         */
        boolean flagAuth = loadAuth();
        String authLogin = loadAuthLogin();

        if ( flagAuth ) {
            biisApiGetCallbacs();
            section_login.setText(authLogin);
        } else {
            Collection<Callbiis> calls = getCalls();
            BiisCalls.setItems((List<Callbiis>) calls);
        }


        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();

        /*
         * ?????????? ???? ????????????????
         */
        buttonLogOut.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                sPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString(SAVED_USERID, "");
                ed.putString(SAVED_USERLOGIN, "");
                ed.apply();
                //?????????? ???????????????????? ?????????????????? ????????????
                getActivity().recreate();
            }
        });

        /*
        FloatingActionButton fab1 = root.findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    sPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
                    String savedUserid = sPref.getString(SAVED_USERID, "");

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://bi-is.ru/wp-json/biis/v1/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    TooltipsApi tolltipsApi = retrofit.create(TooltipsApi.class);

                    retrofit2.Call<List<Callbiis>> called = tolltipsApi.callsPartner(savedUserid);

                    called.enqueue(new Callback<List<Callbiis>>() {
                        @Override
                        public void onResponse(retrofit2.Call<List<Callbiis>> call, Response<List<Callbiis>> response) {
                            if (response.isSuccessful()) {

                                assert response.body() != null;
                                loadCalls(response.body());
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
                    Snackbar.make(v, R.string.obnovil_spisok, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                       }catch (Exception e) {

                    }
            }
        });*/


        //recyclerView = (RecyclerView) root.findViewById(R.id.rv);
        mSwipeRefreshLayout = (SwipeRefreshLayout)root.findViewById(R.id.swipe_container);
        //mSwipeRefreshLayout.setOnRefreshListener(this);
        //mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {//???????????????????? ???? ????????????
                //
                if(isOnline(getContext())) {
                    try {

                        sPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
                        String savedUserid = sPref.getString(SAVED_USERID, "");
                        String savedid = sPref.getString(SAVED_ID, "");

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://bi-is.ru/wp-json/biis/v1/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        TooltipsApi tolltipsApi = retrofit.create(TooltipsApi.class);

                        retrofit2.Call<List<Callbiis>> called = tolltipsApi.callsPartnerGuid(savedid);

                        called.enqueue(new Callback<List<Callbiis>>() {
                            @Override
                            public void onResponse(retrofit2.Call<List<Callbiis>> call, Response<List<Callbiis>> response) {
                                if (response.isSuccessful()) {
                                    if (response.body()!=null){//???????????????? ?????????????? ???? ???????? ??????????????????
                                        @SuppressLint("ShowToast") Toast toast = Toast.makeText(getView().getContext(),
                                                "?????? ?? ",
                                                Toast.LENGTH_LONG);}

                                    //assert response.body() != null;//????????????????
                                    loadCalls(response.body());
                                    recyclerView.getAdapter().notifyDataSetChanged();
                                    Log.i("response list callback", "response " + response.body());
                                } else {
                                    Log.i("response2", "response code " + response.code());
                                }
                            }

                            @Override
                            public void onFailure(retrofit2.Call<List<Callbiis>> call, Throwable t) {
                                Log.i("failure list", "" + t);
                            }
                        });
                        Snackbar.make(getView(), R.string.obnovil_spisok, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    } catch (Exception e) {

                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // ???????????????? ???????????????? ????????????????????
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }, 1000);
                }

                else{@SuppressLint("ShowToast") Toast toast = Toast.makeText(getView().getContext(),
                        "?????? ???????????????? ???????????????????? ",
                        Toast.LENGTH_LONG);
                    View view = toast.getView();
                    //view.setBackgroundColor(Color.RED);//???????? ???????? ??????????????????
                    toast.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // ???????????????? ???????????????? ????????????????????
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }, 1000);
                }
            //
            }
        });

        return root;







    }

public  void refreshZayavki()
{
    sPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
    String savedUserid = sPref.getString(SAVED_USERID, "");
    String savedid = sPref.getString(SAVED_ID, "");

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://bi-is.ru/wp-json/biis/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    TooltipsApi tolltipsApi = retrofit.create(TooltipsApi.class);

    retrofit2.Call<List<Callbiis>> called = tolltipsApi.callsPartnerGuid(savedid);

    called.enqueue(new Callback<List<Callbiis>>() {
        @Override
        public void onResponse(retrofit2.Call<List<Callbiis>> call, Response<List<Callbiis>> response) {
            if (response.isSuccessful()) {
                if (response.body()==null){//???????????????? ?????????????? ???? ???????? ??????????????????
                    @SuppressLint("ShowToast") Toast toast = Toast.makeText(getView().getContext(),
                        "?????? ?? ",
                        Toast.LENGTH_LONG);}

                //assert response.body() != null;
                loadCalls(response.body());
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
    Snackbar.make(super.getView(), R.string.obnovil_spisok, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();

}
    /**
     * ???????????? ???????????? ???? ???????? ?????????? retrofit
     * ???????????????? json ?? ??????????????:
     * id, title, date
     *
     * ?????????????????? ?? ???????????? ?????????? loadCalls()
     * ?????????????????? ??????????????
     */
    private void biisApiGetCallbacs() {
        sPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        String savedUserid = sPref.getString(SAVED_USERID, "");
        String savedid = sPref.getString(SAVED_ID, "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bi-is.ru/wp-json/biis/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TooltipsApi tolltipsApi = retrofit.create(TooltipsApi.class);

        retrofit2.Call<List<Callbiis>> called = tolltipsApi.callsPartnerGuid(savedid);

        called.enqueue(new Callback<List<Callbiis>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Callbiis>> call, Response<List<Callbiis>> response) {
                if (response.isSuccessful()) {
                    if (response.body()!=null){
                        @SuppressLint("ShowToast") Toast toast = Toast.makeText(getView().getContext(),
                                "?????? ?? ",
                                Toast.LENGTH_LONG);}

                    //assert response.body() != null;
                    loadCalls(response.body());
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

    /**
     * ?????????????????? ???????????? ?? ????????????????
     * @param response json ?? ?????????????? ?? ??????????????:
     *      id, title, date
     */
    private void loadCalls(List<Callbiis> response) {
        //Collection<Call> calls = getCalls();
        BiisCalls.setItems(response);
    }

    /**
     * ?????????????????? ???????? ???? ?????????????????????? ??????????
     *
     * @return
     */
    private boolean loadAuth() {
        sPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        String savedUserid = sPref.getString(SAVED_USERID, "");
        Log.i("AUTH", "" + savedUserid.length());
        return savedUserid.length() > 0;
    }

    /**
     * ???????????????? ??????????
     *
     * @return
     */
    private String loadAuthLogin() {
        sPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        String savedUserLogin = sPref.getString(SAVED_USERLOGIN, "Rade_cl2");
        Log.i("LOGIN", "" + savedUserLogin.length());
        return savedUserLogin;
    }

    /**
     * ???????????????????? ?????? ???????????????? ????????????
     * @return
     */
    private Collection<Callbiis> getCalls() {
        return Collections.singletonList(

                new Callbiis("", "?? ?????? ?????? ?????? ????????????",
                        "--.--.----", "", "", "")
        );
    }
}