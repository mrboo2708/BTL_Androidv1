package com.example.btl_androidv1.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_androidv1.Interface.ItemClick;
import com.example.btl_androidv1.R;

public class BuyViewOrder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtBuyID,txtBuyStatus,txtBuyPhone,txtBuyAddress;

    private ItemClick itemClick;
    public BuyViewOrder(View view){
            super(view);
            txtBuyID = (TextView) view.findViewById(R.id.buy_id);
            txtBuyPhone = (TextView) view.findViewById(R.id.buy_phone);
            txtBuyAddress = (TextView) view.findViewById(R.id.buy_address);
            txtBuyStatus = (TextView) view.findViewById(R.id.buy_status);

            view.setOnClickListener(this);
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @Override

    public void onClick(View view) {
        itemClick.onClick(view,getAbsoluteAdapterPosition(),false);
    }
}
