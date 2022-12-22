package com.example.btl_androidv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.btl_androidv1.Interface.ItemClick;
import com.example.btl_androidv1.Model.Category;
import com.example.btl_androidv1.Model.Stuff;
import com.example.btl_androidv1.ViewHolder.MenuViewHolder;
import com.example.btl_androidv1.ViewHolder.StuffViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StuffList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference stuffList;
    String categoryId = "";
    FirebaseRecyclerAdapter<Stuff, StuffViewHolder> adapter;

    //search method
    FirebaseRecyclerAdapter<Stuff,StuffViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuff_list);

        database = FirebaseDatabase.getInstance();
        stuffList = database.getReference("Stuff");

        recyclerView = findViewById(R.id.recycleStuff);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (getIntent() != null) {
            categoryId = getIntent().getStringExtra("CategoryId");
        }
        if (!categoryId.isEmpty() && categoryId != null) {
            loadListStuff(categoryId);
        }
        materialSearchBar = (MaterialSearchBar) findViewById(R.id.search_bar);
        materialSearchBar.setHint("Enter name:");
        loadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> suggest = new ArrayList<String>();
                for(String search:suggestList){
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())) {
                        suggest.add(search);


                    }
                    else{

                    }



                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


    }

    private void startSearch(CharSequence text) {
        searchAdapter = new FirebaseRecyclerAdapter<Stuff, StuffViewHolder>(
                Stuff.class,R.layout.stuff_item,StuffViewHolder.class,stuffList.orderByChild("name").equalTo(text.toString())
        ) {
            @Override
            protected void populateViewHolder(StuffViewHolder stuffViewHolder, Stuff stuff, int i) {
                stuffViewHolder.stuffName.setText(stuff.getName());
                Picasso.get().load(stuff.getImage()).into(stuffViewHolder.stuffImage);

                final Stuff local = stuff;
                stuffViewHolder.setItemClick((view, pos, isClick) -> {
                    Intent stuffDetail = new Intent(StuffList.this,BuyDetail.class);
                    stuffDetail.putExtra("stuffId",searchAdapter.getRef(pos).getKey());
                    startActivity(stuffDetail);
                });
            }
        };
        recyclerView.setAdapter(searchAdapter);
    }

    private void loadSuggest() {
        stuffList.orderByChild("menuId").equalTo(categoryId)
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot postSnapShot : snapshot.getChildren()){
                        Stuff item = postSnapShot.getValue(Stuff.class);
                        suggestList.add(item.getName());

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    private void loadListStuff(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Stuff, StuffViewHolder>(Stuff.class, R.layout.stuff_item, StuffViewHolder.class, stuffList.orderByChild("menuId").equalTo(categoryId)) {


            @Override
            protected void populateViewHolder(StuffViewHolder stuffViewHolder, Stuff stuff, int i) {
                stuffViewHolder.stuffName.setText(stuff.getName());
                Picasso.get().load(stuff.getImage()).into(stuffViewHolder.stuffImage);

                final Stuff local = stuff;
                stuffViewHolder.setItemClick((view, pos, isClick) -> {
                    Intent stuffDetail = new Intent(StuffList.this,BuyDetail.class);
                    stuffDetail.putExtra("stuffId",adapter.getRef(pos).getKey());
                    startActivity(stuffDetail);
                });


            }
        };
        recyclerView.setAdapter(adapter);





























    }
}