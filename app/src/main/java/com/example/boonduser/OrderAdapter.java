package com.example.boonduser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class OrderAdapter extends FirebaseRecyclerAdapter<Order, OrderAdapter.OrderViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    public OrderAdapter(@NonNull FirebaseRecyclerOptions<Order> options, Context context) {

        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Order model) {


        holder.tvOrderPrice.setText(model.getPrice());
        holder.tvOrderQuantity.setText(model.getQuantity());
        holder.tvOrderSize.setText(model.getSize());
        holder.tvOrderStatus.setText(model.getStatus());
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderlist, parent, false);
        return new OrderViewHolder(view);
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{

        TextView tvOrderPrice, tvOrderQuantity, tvOrderSize, tvOrderStatus;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOrderPrice = itemView.findViewById(R.id.tvOrderPrice);
            tvOrderQuantity = itemView.findViewById(R.id.tvOrderQuantity);
            tvOrderSize = itemView.findViewById(R.id.tvOrderSize);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);

        }
    }

}
