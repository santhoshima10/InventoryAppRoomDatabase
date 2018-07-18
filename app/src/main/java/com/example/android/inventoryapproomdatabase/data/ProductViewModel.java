package com.example.android.inventoryapproomdatabase.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.database.Cursor;
import android.support.annotation.NonNull;

import java.util.List;

public class ProductViewModel extends AndroidViewModel{

    ProductRepository mProductRepository;
    LiveData<List<Product>> mAllProducts;


    public ProductViewModel(@NonNull Application application) {
        super(application);
       mProductRepository = new ProductRepository(application);
       mAllProducts = mProductRepository.getAllProducts();


    }

    public LiveData<List<Product>> getmAllProducts() {
        return mAllProducts;
    }


    public void insert(Product product){
         mProductRepository.insert(product);

    }

    public void deleteAll(){
        mProductRepository.deleteAll();
    }

    public void update(Product product){
        mProductRepository.update(product);
    }

    public void deleteById(long id){
        mProductRepository.deleteById(id);
    }

    public void updateProductQuantity(int updateQuantity,int productId){

     mProductRepository.updateProductQuantity(updateQuantity,productId);
    }

}
