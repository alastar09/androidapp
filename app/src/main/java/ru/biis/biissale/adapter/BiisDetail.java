package ru.biis.biissale.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.biis.biissale.Detailsbiis;
import ru.biis.biissale.MessageDetailActivity;
import ru.biis.biissale.R;
import ru.biis.biissale.dialogs.dialogMessages;

public class BiisDetail extends RecyclerView.Adapter<BiisDetail.BiisViewHolder> {

    private SharedPreferences sPref;

    final String SAVED_RESPID = "saved_responseid";
    final String SAVED_CALLID = "saved_callid";
    String valueCallID;

    public static List<Detailsbiis> biisDetail = new ArrayList<>();
    private DialogFragment dlgMessage;

    public static void setItems(List<Detailsbiis> detailsbiis) {
        biisDetail = detailsbiis;
    }

    @NonNull
    @Override
    public BiisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.details, parent, false);

        sPref = PreferenceManager.getDefaultSharedPreferences(parent.getContext());
        valueCallID = sPref.getString(SAVED_CALLID, "0");
        return new BiisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BiisViewHolder holder, int position) {
        holder.bind(biisDetail.get(position));

    }

    class BiisViewHolder extends RecyclerView.ViewHolder {

        private TextView idDetail;
        private TextView costDetail;
        private TextView textDetail;
        private LinearLayout dialogItem;

        public BiisViewHolder(@NonNull View itemView) {
            super(itemView);

            idDetail = itemView.findViewById(R.id.idDetail);
            costDetail = itemView.findViewById(R.id.costDetail);
            textDetail = itemView.findViewById(R.id.textDetail);
            dialogItem = itemView.findViewById(R.id.dialogItem);

            dialogItem.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Log.i("CLICK MESSAGE", "OK "+ biisDetail.get(getAdapterPosition()).getId().toString());
                    //получаем id ответа и сохраняем
                    String respid = biisDetail.get(getAdapterPosition()).getId().toString();
                    String callid = biisDetail.get(getAdapterPosition()).getParent();
                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putString(SAVED_RESPID, biisDetail.get(getAdapterPosition()).getId().toString());
                    ed.apply();

                    Context context = v.getContext();
                    Intent intent = new Intent(context, MessageDetailActivity.class);
                    intent.putExtra("EXTRA_RESP_ID", respid);
                    intent.putExtra("EXTRA_CALL_ID", callid);
                    context.startActivity(intent);
                }
            });
        }

        public void bind(Detailsbiis biisDetail) {
            Log.i("итем", "" + biisDetail.getCompany());
            idDetail.setText(biisDetail.getCompany());
            costDetail.setText(biisDetail.getPrice());
            textDetail.setText(biisDetail.getMessage());
            //selectId.setText(biiscats.getCatid());

        }
    }

    @Override
    public int getItemCount() {
        return biisDetail.size();
    }
}
