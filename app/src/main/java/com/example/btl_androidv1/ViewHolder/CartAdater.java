package com.example.btl_androidv1.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.btl_androidv1.Cart_Activity;
import com.example.btl_androidv1.Interface.ItemClick;
import com.example.btl_androidv1.Model.Order;
import com.example.btl_androidv1.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class CartViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtCardName,txtCardPrice;
    public ImageView img_cart;

    private ItemClick itemClick;

    public void setTxtCardName(TextView txtCardName) {
        this.txtCardName = txtCardName;
    }

    public CartViewHolder(View view){
        super(view);
        txtCardName = (TextView) view.findViewById(R.id.cart_item_name);
        txtCardPrice = (TextView) view.findViewById(R.id.cart_item_price);
        img_cart = (ImageView) view.findViewById(R.id.cart_item_count);
    }
    @Override
    public void onClick(View view) {

    }
}

public class    CartAdater extends RecyclerView.Adapter<CartViewHolder>{

    private List<Order> listData = new ArrayList<>();
    private Context context;

    public CartAdater(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout,parent,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        TextDrawable drawable = TextDrawable.builder().buildRound(""+listData.get(position).getQuantity(), Color.RED);
        holder.img_cart.setImageDrawable(drawable);

        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));
        holder.txtCardPrice.setText(fmt.format(price));
        holder.txtCardName.setText(listData.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
