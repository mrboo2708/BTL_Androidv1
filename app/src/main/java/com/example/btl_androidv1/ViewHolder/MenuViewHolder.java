package com.example.btl_androidv1.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_androidv1.Interface.ItemClick;
import com.example.btl_androidv1.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtMenuName;
    public ImageView imageView;
    private ItemClick itemClick;
    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);
        txtMenuName = (TextView)itemView.findViewById(R.id.menu_name);
        imageView = (ImageView) itemView.findViewById(R.id.menu_image);
        itemView.setOnClickListener(this);


    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    public void onClick(View view) {
        itemClick.onClick(view,getAbsoluteAdapterPosition(),false);
    }
}
