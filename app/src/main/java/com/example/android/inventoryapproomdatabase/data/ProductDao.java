package com.example.android.inventoryapproomdatabase.data;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;


@Dao
public interface ProductDao {

    @Insert
    void insert(Product product);

    @Query("SELECT * FROM products ORDER BY _id DESC")
    LiveData<List<Product>> getAllProducts();


    @Query("DELETE FROM products")
    void deleteAll();

    @Query("DELETE FROM products WHERE _ID = :id")
    void deleteById(long id);

    @Update
    void update(Product product);

    @Query("UPDATE products SET product_quantity = :updateQuantity WHERE _ID = :productId")
    void updateProductQuantity(int updateQuantity, int productId);



}
