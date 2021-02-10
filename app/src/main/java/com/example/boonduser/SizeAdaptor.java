package com.example.boonduser;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class SizeAdaptor extends FirebaseRecyclerAdapter<Size, SizeAdaptor.SizeViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    String userNumber;
    public SizeAdaptor(@NonNull FirebaseRecyclerOptions<Size> options, Context context, String userNumber) {
        super(options);
        this.context = context;
        this.userNumber = userNumber;
    }

    @Override
    protected void onBindViewHolder(@NonNull SizeViewHolder holder, int position, @NonNull Size model) {

        holder.tvListSize.setText(model.getSize());
        holder.tvListPrice.setText(model.getPrice());

        holder.ivAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialog = DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.addquantity))
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                dialog.show();

                View dialogView = dialog.getHolderView();
                Button btnAddOrder;
                EditText etQuantity;

                btnAddOrder = dialogView.findViewById(R.id.btnAddOrder);
                etQuantity = dialogView.findViewById(R.id.etQuantity);

                btnAddOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(etQuantity.getText().toString().isEmpty())
                        {
                            etQuantity.setError("Please Enter Quantity...");
                            etQuantity.requestFocus();
                        }else {
                            HashMap<String, String> data = new HashMap<>();
                            data.put("Quantity",etQuantity.getText().toString().trim());
                            data.put("Price",holder.tvListPrice.getText().toString().trim());
                            data.put("Size",holder.tvListSize.getText().toString().trim());
                            data.put("Number",userNumber);
                            data.put("Status","Pending");

                            FirebaseDatabase.getInstance().getReference()
                                    .child("Order")
                                    .push()
                                    .setValue(data)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(context, "Order successfully placed.", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            dialog.dismiss();
                        }
                    }

                });
            }
        });

    }

    @NonNull
    @Override
    public SizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sizelist, parent, false);
        return new SizeViewHolder(view);
    }

    public class SizeViewHolder extends RecyclerView.ViewHolder{

        TextView tvListSize, tvListPrice;
        ImageView ivAddToCart;

        public SizeViewHolder(@NonNull View itemView) {
            super(itemView);

            tvListPrice = itemView.findViewById(R.id.tvListPrice);
            tvListSize = itemView.findViewById(R.id.tvListSize);
            ivAddToCart = itemView.findViewById(R.id.ivAddToCart);

        }
    }

}
