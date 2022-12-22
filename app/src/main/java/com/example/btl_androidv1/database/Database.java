package com.example.btl_androidv1.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.btl_androidv1.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class Database extends SQLiteAssetHelper {

        private static final String DB_Name = "BuySellDB.db";
        private static final int DB_ver = 1;


    public Database(Context context) {
        super(context, DB_Name, null, DB_ver);
    }

    public List<Order> getCart(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb =  new SQLiteQueryBuilder();

        String[] sqlSelect = {"StuffId","StuffName","Amount","Price","Discount"};
        String sqlTable = "OrderDetail";
        qb.setTables(sqlTable);
        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Order> result = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                result.add(new Order(cursor.getString(cursor.getColumnIndexOrThrow("StuffId")),
                        cursor.getString(cursor.getColumnIndexOrThrow("StuffName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Amount")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Price")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Discount"))

                ));
            }
            while(cursor.moveToNext());
        }
        return result;
    }

    public void AddCart(Order order){
        SQLiteDatabase db =getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(StuffId,StuffName,Amount,Price,Discount) VALUES ('%s','%s','%s','%s','%s');",
                order.getOrderId(),order.getProductName(),order.getQuantity(),order.getPrice(),order.getDiscount());
        db.execSQL(query);
    }
    public void cleanCart(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }
}
