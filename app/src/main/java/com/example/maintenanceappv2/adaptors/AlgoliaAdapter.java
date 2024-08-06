package com.example.maintenanceappv2.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceappv2.DataModel.SapMaterialSearchModel;
import com.example.maintenanceappv2.R;

import java.util.List;

public class AlgoliaAdapter extends RecyclerView.Adapter<AlgoliaAdapter.ViewHolder> {
    private List<SapMaterialSearchModel> itemList;
    private final Context context;

    public AlgoliaAdapter(List<SapMaterialSearchModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SapMaterialSearchModel item = itemList.get(position);
        holder.sapcode_card.setText(String.valueOf(item.getSap_code()));
        holder.description_card.setText(item.getDescription());
        holder.stock_card.setText(String.valueOf(item.getStock()));
        holder.location_card.setText(item.getLocation());
        holder.gen_description_card.setText(item.getGen_desc());
        holder.used_in_card.setText(item.getUsed_in_1());
        holder.station_card.setText(item.getStation());
        holder.vendor_card.setText(item.getVendor());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateList(List<SapMaterialSearchModel> newList) {
        itemList = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sapcode_card,stock_card,location_card,description_card,gen_description_card,used_in_card,station_card,vendor_card;

        public ViewHolder(View itemView) {
            super(itemView);

            sapcode_card=itemView.findViewById(R.id.sapcode_card);
            stock_card=itemView.findViewById(R.id.stock_card);
            location_card=itemView.findViewById(R.id.location_card);
            description_card=itemView.findViewById(R.id.description_card);
            gen_description_card=itemView.findViewById(R.id.gen_description_card);
            used_in_card=itemView.findViewById(R.id.used_in_card);
            station_card=itemView.findViewById(R.id.station_card);
            vendor_card=itemView.findViewById(R.id.vendor_card);

        }
    }
}
