package ru.biis.biissale.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.biis.biissale.Callbiis;
import ru.biis.biissale.Messagesbiis;
import ru.biis.biissale.R;
import ru.biis.biissale.rest.UserInfo;

public class BiisMessage extends RecyclerView.Adapter<BiisMessage.BiisViewHolder> {

    public static List<Messagesbiis> biismessage = new ArrayList<>();//biismessage.isEmpty();

    public static void setItems(List<Messagesbiis> messagesbiis) {
        biismessage = messagesbiis;
    }

    public static void addItem(List<Messagesbiis> messagesbiis) {
        biismessage.addAll(messagesbiis);
    }

    @NonNull
    @Override
    public BiisMessage.BiisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //if(!biismessage.isEmpty()){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message, parent, false);

        return new BiisMessage.BiisViewHolder(view);
    /*}else{
            return new BiisMessage.BiisViewHolder(0);;
        }*/
    }

    @Override
    public void onBindViewHolder(@NonNull BiisViewHolder holder, int position) {
        holder.bind(biismessage.get(position));
    }

    class BiisViewHolder extends RecyclerView.ViewHolder {

        private TextView loginMsg;
        private TextView messageMsg;
        private TextView dateMsg;
        private TextView datePrice;
        private TextView dateStock;
        private ImageView avaProfile;

        public BiisViewHolder(@NonNull View itemView) {
            super(itemView);
            loginMsg = itemView.findViewById(R.id.loginMsg);
            dateMsg = itemView.findViewById(R.id.dateMsg);
            messageMsg = itemView.findViewById(R.id.messageMsg);
            avaProfile = itemView.findViewById(R.id.avaProfile);
            datePrice = itemView.findViewById(R.id.datePrice);
            dateStock = itemView.findViewById(R.id.dateStock);
        }

        public void bind(Messagesbiis messagesbiis) {
            //String stock = messagesbiis.getStock();
            //String price = messagesbiis.getPrice();
            // UserInfo UserInfo = null;
            //loginMsg.setText(messagesbiis.getLogin());
            String usid=messagesbiis.getAppUserId();
            String ttt= UserInfo.getUserLogin(usid);
            String av=UserInfo.getUserAva(usid);
            loginMsg.setText(ttt);

            dateMsg.setText(Callbiis.ParsDate3(messagesbiis.getDate()));
            messageMsg.setText(messagesbiis.getMessage());
            //messageMsg.setText(av);

            // if (!stock.equals("")) {
            // dateStock.setText(stock);
            //dateStock.setVisibility(View.VISIBLE);
            //}
            //  if (!price.equals("")) {
            //      datePrice.setText(price);
            //      datePrice.setVisibility(View.VISIBLE);
            //  }
            //Picasso.with(itemView.getContext()).load(messagesbiis.getAva()).fit().into(avaProfile);//аватарка
            Picasso.with(itemView.getContext()).load(Messagesbiis.checkAva(av)).fit().into(avaProfile);//аватарка
            Log.i("avatar", "" + Messagesbiis.checkAva(av));
            //Picasso.with(itemView.getContext()).load("https://sun9-5.userapi.com/VIe1t6glyxeeuFRZ4nYTcAqBAYTVhEuldagqeg/h4r3Qk8lvoU.jpg?ava=1").into(avaProfile);//котик

        }
    }

    @Override
    public int getItemCount() {
        return biismessage.size();
    }
}
