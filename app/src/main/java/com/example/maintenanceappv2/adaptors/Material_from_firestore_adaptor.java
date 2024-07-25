package com.example.maintenanceappv2.adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenanceappv2.DataModel.SapMaterialSearchModel;
import com.example.maintenanceappv2.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Material_from_firestore_adaptor extends FirestoreRecyclerAdapter<SapMaterialSearchModel,Material_from_firestore_adaptor.Viewholder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Material_from_firestore_adaptor(@NonNull FirestoreRecyclerOptions<SapMaterialSearchModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Viewholder holder, int position, @NonNull SapMaterialSearchModel model) {

        holder.itemView.findViewById(R.id.sapcode_card);

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        Material_from_firestore_adaptor.Viewholder mViewholder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.material_card, parent, false);
        mViewholder = new Viewholder(view);



        return null;
    }

    static class Viewholder extends RecyclerView.ViewHolder {

        TextView sapcode_card,stock_card,location_card,description_card,gen_description_card,used_in_card,station_card,vendor_card;

        public Viewholder(@NonNull View itemView) {
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
