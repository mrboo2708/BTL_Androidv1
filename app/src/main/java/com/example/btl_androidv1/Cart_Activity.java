package com.example.btl_androidv1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl_androidv1.Model.Order;
import com.example.btl_androidv1.Model.Request;
import com.example.btl_androidv1.Stuff.Common;
import com.example.btl_androidv1.ViewHolder.CartAdater;
import com.example.btl_androidv1.database.Database;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference requests;

    List<Order> cart = new ArrayList<>();
    CartAdater adater;

    TextView txtTotalPrice;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Request");

        recyclerView = (RecyclerView) findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView) findViewById(R.id.total);
        button = (Button) findViewById(R.id.btnAddCart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              showDialog();
            }
        });

        loadListBuyer();

    }

    private void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Cart_Activity.this);
        alert.setTitle("Fill complete:");
        alert.setMessage("Address: ");

        final EditText editText = new EditText(Cart_Activity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        editText.setLayoutParams(lp);
        alert.setView(editText);
        alert.setIcon(R.drawable.ic_baseline_shopping_cart_24);
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Request request = new Request(Common.currentUser.getPhone(),
                        Common.currentUser.getName(),editText.getText().toString(),
                        txtTotalPrice.getText().toString(),cart);
                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart_Activity.this,"Thank you very much",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }

    private void loadListBuyer() {
        cart = new Database(this).getCart();
        adater = new CartAdater(cart,this);
        recyclerView.setAdapter(adater);

        int total = 0;
        for(Order order:cart){
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        }
        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txtTotalPrice.setText(fmt.format(total));


    }
}