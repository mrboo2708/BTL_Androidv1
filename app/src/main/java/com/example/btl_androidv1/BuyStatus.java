package com.example.btl_androidv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.btl_androidv1.Model.Order;
import com.example.btl_androidv1.Model.Request;
import com.example.btl_androidv1.Stuff.Common;
import com.example.btl_androidv1.ViewHolder.BuyViewOrder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BuyStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Request, BuyViewOrder> adapter;

    FirebaseDatabase database;
    DatabaseReference requests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_status);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Request");
        recyclerView = (RecyclerView) findViewById(R.id.listBuy);
        recyclerView.setHasFixedSize(true);
        layoutManager =  new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadBuyer(Common.currentUser.getPhone());


    }

    private void loadBuyer(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, BuyViewOrder>(
                Request.class,R.layout.buy_layout,BuyViewOrder.class,requests.orderByChild("phone").equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(BuyViewOrder buyViewOrder, Request request, int i) {
                buyViewOrder.txtBuyID.setText(adapter.getRef(i).getKey());
                buyViewOrder.txtBuyStatus.setText(convertCodeToStatus(request.getStatus()));
                buyViewOrder.txtBuyAddress.setText(request.getAddress());
                buyViewOrder.txtBuyPhone.setText(request.getPhone());
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private String convertCodeToStatus(String status) {
        if(status.equals("0")){
            return "Buyed";
        }
        else if(status.equals("1")){
            return  "Shipping";
        }
        else
            return "Shipped";
    }
}