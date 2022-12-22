package com.example.btl_androidv1.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_androidv1.Interface.ItemClick;
import com.example.btl_androidv1.R;

public class StuffViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView stuffName;
    public ImageView stuffImage;
    private ItemClick itemClick;

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public StuffViewHolder(@NonNull View itemView) {
        super(itemView);
        stuffName = (TextView) itemView.findViewById(R.id.Stuff_name);
        stuffImage= (ImageView) itemView.findViewById(R.id.Stuff_image);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClick.onClick(view,getAbsoluteAdapterPosition(),false) ;
    }
}
