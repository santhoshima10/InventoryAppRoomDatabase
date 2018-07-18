package com.example.android.inventoryapproomdatabase.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Query;
import android.os.AsyncTask;

import java.util.List;

public class ProductRepository {

    private ProductDao mProductDao;
    private LiveData<List<Product>> mListProductData;



    ProductRepository(Application application) {
        ProductRoomDatabase db = ProductRoomDatabase.getDatabase(application);
        this.mProductDao = db.productDao();
        this.mListProductData = mProductDao.getAllProducts();
    }


    LiveData<List<Product>> getAllProducts(){
       return mListProductData;
    };


    void insert(Product product){
        new insertProductDataTask(mProductDao).execute(product);

    }

    void deleteAll(){
        new deleteProductDataTask(mProductDao).execute();
    }


   void update(Product product){
        new updateProductDataTask(mProductDao).execute(product);
    }

    void deleteById(long id){

        new deleteProductByIdTask(mProductDao).execute(id);

    }

    void updateProductQuantity(int updateQuantity,int productId){

        new updateProductQuantityTask(mProductDao).execute(updateQuantity,productId);

    }




    private  class insertProductDataTask extends AsyncTask<Product, Void, Void> {

        ProductDao asyncProductDao;

        public insertProductDataTask(ProductDao productDao) {
            this.asyncProductDao = productDao;
        }


        @Override
        protected Void doInBackground(Product... products) {
            asyncProductDao.insert(products[0]);
            return null;

        }
    }


    private class deleteProductDataTask extends AsyncTask<Void,Void,Void> {

        ProductDao asyncProductDao;
        int id;

        public deleteProductDataTask(ProductDao productDao) {
            this.asyncProductDao = productDao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            asyncProductDao.deleteAll();
            return null;
        }




    }



    private class deleteProductByIdTask extends AsyncTask<Long,Void,Void> {

        ProductDao asyncProductDao;
        int id;

        public deleteProductByIdTask(ProductDao productDao) {
            this.asyncProductDao = productDao;
        }


        @Override
        protected Void doInBackground(Long... longs) {
            asyncProductDao.deleteById(longs[0]);
            return null;
        }
    }

   private  class updateProductDataTask extends AsyncTask<Product, Void, Void> {

        ProductDao asyncProductDao;

        public updateProductDataTask(ProductDao productDao) {
            this.asyncProductDao = productDao;
        }


        @Override
        protected Void doInBackground(Product... products) {
            asyncProductDao.update(products[0]);
            return null;
        }
    }

    private class updateProductQuantityTask extends AsyncTask<Integer,Void,Void> {

        ProductDao asyncProductDao;
        int id;

        public updateProductQuantityTask(ProductDao productDao) {
            this.asyncProductDao = productDao;
        }


        @Override
        protected Void doInBackground(Integer... integers) {
            asyncProductDao.updateProductQuantity(integers[0],integers[1]);
            return null;
        }
    }





}
