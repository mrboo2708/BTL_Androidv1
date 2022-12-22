package com.example.btl_androidv1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl_androidv1.Interface.ItemClick;
import com.example.btl_androidv1.Model.Category;
import com.example.btl_androidv1.Stuff.Common;
import com.example.btl_androidv1.ViewHolder.CartAdater;
import com.example.btl_androidv1.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_androidv1.databinding.ActivityHomepageBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Homepage extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference category;
    TextView txtFullName;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomepageBinding binding;
    RecyclerView recyclerViewMenu;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHomepage.toolbar);
        binding.appBarHomepage.toolbar.setTitle("Menu");
        database =FirebaseDatabase.getInstance();
        category = database.getReference("Category");
        binding.appBarHomepage.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent cartIntent = new Intent(Homepage.this, Cart_Activity.class);
              startActivity(cartIntent);
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        final NavHostFragment navHostFragment =(NavHostFragment) getSupportFragmentManager().findFragmentById( R.id.nav_host_fragment_activity_main);
        final NavController navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id =   item.getItemId();
                if(id==R.id.nav_menu){

                }
                else if(id==R.id.nav_order){
                    Intent buyIntent = new Intent(Homepage.this,BuyStatus.class);
                    startActivity(buyIntent);
                }
                else if(id==R.id.nav_cart){
                    Intent cartIntent = new Intent(Homepage.this,Cart_Activity.class);
                    startActivity(cartIntent);
                }
                else if(id==R.id.nav_logout){
                    Intent signIn = new Intent(Homepage.this,SignInActivity.class);
                    signIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(signIn);
                }
                NavigationUI.onNavDestinationSelected(item,navController);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        View headerView =navigationView.getHeaderView(0);
        txtFullName = (TextView)headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentUser.getName());

        recyclerViewMenu = (RecyclerView) findViewById(R.id.recycleMenu);
        recyclerViewMenu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewMenu.setLayoutManager(layoutManager);

        loadMenu();
    }

    private void loadMenu() {

         adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolder menuViewHolder, Category category, int i) {
                menuViewHolder.txtMenuName.setText(category.getName());
                Picasso.get().load(category.getImage()).into(menuViewHolder.imageView);

                final Category clickItem = category;
                menuViewHolder.setItemClick(new ItemClick() {
                    @Override
                    public void onClick(View view, int pos, boolean isClick) {
                        Intent stuffList = new Intent(Homepage.this,StuffList.class);
                        stuffList.putExtra("CategoryId",adapter.getRef(pos).getKey());
                        startActivity(stuffList);
                    }
                });
            }
        };
        recyclerViewMenu.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homepage, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        final NavHostFragment navHostFragment =(NavHostFragment) getSupportFragmentManager().findFragmentById( R.id.nav_host_fragment_activity_main);
        final NavController navController = navHostFragment.getNavController();

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}