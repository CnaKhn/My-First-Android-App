package com.cnakhn.faradarscompletion.DataModel.Product;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ProductDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "ProductDBHelper";
    private static final String DB_NAME = "product.db";
    private static final int DB_VERSION = 1;
    private static final String TB_PRODUCTS_NAME = "products";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS '" + TB_PRODUCTS_NAME + "' ('" +
            Product.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '" +
            Product.KEY_NAME + "' TEXT, '" +
            Product.KEY_CATEGORY + "' TEXT, '" +
            Product.KEY_INSTRUCTIONS + "' TEXT, '" +
            Product.KEY_PRICE + "' NUMERIC, '" +
            Product.KEY_PHOTO + "' TEXT)";
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////


    public ProductDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.i(TAG, "onCreate: Table " + TB_PRODUCTS_NAME + " Created Successfully!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_PRODUCTS_NAME);
        Log.i(TAG, "onUpgrade: Table " + TB_PRODUCTS_NAME + " Dropped Successfully!");
    }

    public void insertProducts(Product product) {
        ContentValues values = new ContentValues();
        values.put(Product.KEY_ID, product.getId());
        values.put(Product.KEY_NAME, product.getName());
        values.put(Product.KEY_CATEGORY, product.getCategory());
        values.put(Product.KEY_INSTRUCTIONS, product.getInstructions());
        values.put(Product.KEY_PRICE, product.getPrice());
        values.put(Product.KEY_PHOTO, product.getPhoto());

        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(TB_PRODUCTS_NAME, null, values);
        if (id == -1) Log.i(TAG, "insertProducts: Product " + product.toString() + " inserted failed with id " + id);
        else Log.i(TAG, "insertProducts: Product inserted with id: " + id);

        if (db.isOpen()) db.close();
    }

    public List<Product> getProducts() {
        SQLiteDatabase db = getReadableDatabase();
        List<Product> productList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM '" + TB_PRODUCTS_NAME + "'", null);
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getLong(cursor.getColumnIndex(Product.KEY_ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(Product.KEY_NAME)));
                product.setCategory(cursor.getString(cursor.getColumnIndex(Product.KEY_CATEGORY)));
                product.setInstructions(cursor.getString(cursor.getColumnIndex(Product.KEY_INSTRUCTIONS)));
                product.setPrice(cursor.getDouble(cursor.getColumnIndex(Product.KEY_PRICE)));
                product.setPhoto(cursor.getString(cursor.getColumnIndex(Product.KEY_PHOTO)));
                productList.add(product);
            } while (cursor.moveToNext());
        }

        if (db.isOpen()) db.close();
        cursor.close();
        return productList;
    }

    public void deleteProduct(long productId) {
        SQLiteDatabase db = getWritableDatabase();
        int count = db.delete(TB_PRODUCTS_NAME, Product.KEY_ID + " = ?", new String[] {String.valueOf(productId)});
        Log.i(TAG, "deleteProduct: " + count + " Rows Deleted!");
        if (db.isOpen()) db.close();

    }
}
