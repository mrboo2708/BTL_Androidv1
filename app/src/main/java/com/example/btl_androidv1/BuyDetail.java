package com.example.btl_androidv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.btl_androidv1.Model.Order;
import com.example.btl_androidv1.Model.Stuff;
import com.example.btl_androidv1.database.Database;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BuyDetail extends AppCompatActivity {

    TextView stuff_name,stuff_price,stuff_description;
    ImageView stuff_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton floatingActionButton;
    ElegantNumberButton elegantNumberButton;

    String stuffId= "";
    FirebaseDatabase database;
    DatabaseReference stuff;

    Stuff currentStuff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_detail);
        database =FirebaseDatabase.getInstance();
        stuff = database.getReference("Stuff");
        elegantNumberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.Cart_btn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(getBaseContext()).AddCart(new Order(stuffId,currentStuff.getName(),
                        elegantNumberButton.getNumber(),currentStuff.getPrice(),currentStuff.getDiscount()));

                Toast.makeText(BuyDetail.this,"Added to Cart",Toast.LENGTH_SHORT).show();
            }
        });

        stuff_description = (TextView) findViewById(R.id.stuff_descript);
        stuff_name = (TextView) findViewById(R.id.stuff_name_txt);
        stuff_price = (TextView) findViewById(R.id.stuff_price_txt);
        stuff_image = (ImageView) findViewById(R.id.img_stuff);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolBar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandeAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapseAppbar);

        if(getIntent()!= null){
            stuffId = getIntent().getStringExtra("stuffId");

        }
        if(! stuffId.isEmpty()){
            getDetailStuff(stuffId);
        }

    }

    private void getDetailStuff(String stuffId) {
        stuff.child(stuffId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentStuff = snapshot.getValue(Stuff.class);
                Picasso.get().load(currentStuff.getImage()).into(stuff_image);
                collapsingToolbarLayout.setTitle(currentStuff.getName());
                stuff_price.setText(currentStuff.getPrice());
                stuff_name.setText(currentStuff.getName());
                stuff_description.setText(currentStuff.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}