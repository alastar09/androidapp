package ru.biis.biissale.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.biis.biissale.Callbiis;
import ru.biis.biissale.MessageDetailActivity;
import ru.biis.biissale.R;



public class BiisCalls extends RecyclerView.Adapter<BiisCalls.BiisViewHolder> {

    private SharedPreferences sPref;

    final String SAVED_CALLID = "saved_callid";

    public static List<Callbiis> biisList = new ArrayList<>();

    public BiisCalls() {
    }

    public static void setItems(List<Callbiis> callbiis) {
        //biisList.addAll(Calls);
        biisList = callbiis;
    }

    public void clearItems() {
        biisList.clear();
        notifyDataSetChanged();
    }

    public void reinitList() {
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public BiisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards, parent, false);
        return new BiisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BiisViewHolder holder, int position) {
        holder.bind(biisList.get(position));
    }

    class BiisViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView textViewDate;
        private TextView textViewCount;
       // private TextView textViewClient;
        private LinearLayout callitem;
        private LinearLayout mainViewInfo;

        public BiisViewHolder(@NonNull final View itemView) {
            super(itemView);

            textViewName    = itemView.findViewById(R.id.textViewName);
            textViewDate    = itemView.findViewById(R.id.textViewDate);
            textViewCount   = itemView.findViewById(R.id.textViewCount);
            callitem        = itemView.findViewById(R.id.callitem);
            mainViewInfo    = itemView.findViewById(R.id.mainViewInfo);

            callitem.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    //проверяем есть ли ответы
                    String countCall = biisList.get(getAdapterPosition()).getCount().toString();

                    //if (!countCall.equals("0")){
                        //получаем из списка id запроса и сохраняем
                        sPref = Objects.requireNonNull(((AppCompatActivity) v.getContext())).getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed = sPref.edit();
                        String callforsave = biisList.get(getAdapterPosition()).getId().toString();
                        String userid = biisList.get(getAdapterPosition()).getClient();
                        String callname = biisList.get(getAdapterPosition()).getText();
                        String calldate = biisList.get(getAdapterPosition()).ParsDate2();//"abso fuckin lutly";//biisList.get(getAdapterPosition()).getDate();//parsdate
                        ed.putString(SAVED_CALLID, callforsave);
                        ed.apply();

                        //после сохранения id запроса открываем диалог
                        Context context = v.getContext();
                        Intent intent = new Intent(context, MessageDetailActivity.class);

                        String commentid = biisList.get(getAdapterPosition()).getCommentid().toString();

                        if (!commentid.isEmpty()) {
                            Log.i("ALERT", "commentid: " + commentid);
                            intent.putExtra("EXTRA_COMMENT_ID", commentid);
                            intent.putExtra("EXTRA_CALL_ID", callforsave);
                            intent.putExtra("EXTRA_RESP_ID", commentid);
                            intent.putExtra("EXTRA_USER_ID", userid);
                            intent.putExtra("EXTRA_CALL_NAME", callname);
                            intent.putExtra("EXTRA_CALL_DATE", calldate);
                            context.startActivity(intent);
                        } else {
                            Log.i("ALERT", "commentid: " + commentid);
                        }
                    //} else {
                        /*@SuppressLint("ShowToast") Toast toast = Toast.makeText(v.getContext(),
                                "Ответов еще нет",
                                Toast.LENGTH_LONG );
                        toast.show();
                    }*/
                }
            });
        }

        public void bind(Callbiis callbiis) {

            textViewName.setText(callbiis.getText());
            textViewDate.setText(callbiis.ParsDate2());//textViewDate.setText(callbiis.getDate());
            textViewCount.setText(callbiis.getCount());

        }

    }

    @Override
    public int getItemCount() {
        return (biisList==null)?0:biisList.size();
    }


}
