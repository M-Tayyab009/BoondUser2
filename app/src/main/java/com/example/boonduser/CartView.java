package com.example.boonduser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class CartView extends AppCompatActivity {

    RecyclerView recyclerView;
    SizeAdaptor adapter;
    String userNumber, shopNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);

        userNumber = getIntent().getStringExtra("User_Number");
        shopNumber = getIntent().getStringExtra("Shop_Number");

        recyclerView = findViewById(R.id.rvSizeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(CartView.this));

        FirebaseRecyclerOptions<Size> options =
                new FirebaseRecyclerOptions.Builder<Size>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Size").orderByChild("Number").equalTo(shopNumber), Size.class)
                        .build();
        adapter = new SizeAdaptor(options, CartView.this, userNumber);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}