package com.example.boonduser;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ShopsAdapter extends FirebaseRecyclerAdapter<Shop, ShopsAdapter.ShopsViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    String userNumber;
    public ShopsAdapter(@NonNull FirebaseRecyclerOptions<Shop> options, Context context, String number) {
        super(options);
        this.context = context;
        userNumber = number;
    }

    @Override
    protected void onBindViewHolder(@NonNull ShopsViewHolder holder, int position, @NonNull Shop model) {
        holder.tvShopName.setText(model.getShop_Name());
        holder.tvName.setText(model.getName());

        holder.ivShopView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CartView.class);
                intent.putExtra("Shop_Number", model.getNumber());
                intent.putExtra("User_Number", userNumber);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public ShopsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopslist, parent, false);
        return new ShopsViewHolder(view);
    }

    public class ShopsViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvShopName;
        ImageView ivShopView;

        public ShopsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvOwnerName);
            tvShopName = itemView.findViewById(R.id.tvShopName);
            ivShopView = itemView.findViewById(R.id.ivShopView);
        }
    }

}
