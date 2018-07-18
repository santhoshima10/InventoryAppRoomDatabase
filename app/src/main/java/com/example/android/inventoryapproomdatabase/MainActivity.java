package com.example.android.inventoryapproomdatabase;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.android.inventoryapproomdatabase.data.Product;
import com.example.android.inventoryapproomdatabase.data.ProductViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity  {

    ProductAdapter adapter;
    List<Product> mProducts;

    public static final int EDITOR_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDITOR_ACTIVITY_UPD_REQUEST_CODE = 1;

    public static final String NEW_PRODUCT = "NEW_PRODUCT";
    public static final String UPDATE_PRODUCT = "UPDATE_PRODUCT";
    public static final String DELETE_PRODUCT = "DELETE_PRODUCT";


    private ProductViewModel mProductViewModel;
    private RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton fab = findViewById(R.id.add_product_id);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EditorActivity.class);
                startActivityForResult(intent,EDITOR_ACTIVITY_REQUEST_CODE);
            }
        });

        recyclerView = findViewById(R.id.recycler_view_id);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new ProductAdapter(this);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent(MainActivity.this,EditorActivity.class);
                intent.putExtra(UPDATE_PRODUCT,mProducts.get(position));
                startActivityForResult(intent,EDITOR_ACTIVITY_UPD_REQUEST_CODE);

            }

            @Override
            public void onSaleButtonClick(int position) {
                int currrentQty = mProducts.get(position).getProductQuantity();

                if (currrentQty > 0) {

                    mProductViewModel.updateProductQuantity(currrentQty - 1, mProducts.get(position).getProductId());
                }

            }


        });



        mProductViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        mProductViewModel.getmAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {

                mProducts = products;
                adapter.setData(products);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EDITOR_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){

            if(data.getSerializableExtra(NEW_PRODUCT) != null){
                Log.d("MainActivity","Inside request code for insert  ");

                Product product = (Product)data.getSerializableExtra(NEW_PRODUCT);
                mProductViewModel.insert(product);

            }
            else if(data.getSerializableExtra(UPDATE_PRODUCT) != null){
                Log.d("MainActivity","Inside request code for update  ");
                Product product = (Product)data.getSerializableExtra(UPDATE_PRODUCT);
                mProductViewModel.update(product);


            }

            else if(data.getStringExtra(DELETE_PRODUCT) != null){
                Log.d("MainActivity","Inside request code for delete  ");

                long productId = Long.valueOf(data.getStringExtra(DELETE_PRODUCT));
                mProductViewModel.deleteById(productId);


            }


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.insert_test_data:
                mInsertTestData();
                return true;

            case R.id.delete_all_data:
                mProductViewModel.deleteAll();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }




    private void mInsertTestData(){

        Log.d("MainActivity","Inside the mInsertTestData ");

        ContentValues values;
        Uri imageUri;



        values = new ContentValues();
        imageUri = getUriToDrawable(this,R.drawable.carrot);


        values.put("product_name","Carrot");
        values.put("product_unit_price","15");
        values.put("product_quantity","10");
        values.put("product_supplier_name","Richard");
        values.put("product_supplier_phone","123345678");
        values.put("product_supplier_email","richard@grofers.com");
        values.put("product_image_uri",imageUri.toString());


        mProductViewModel.insert(Product.fromContentValues(values));





    }

    public static final Uri getUriToDrawable(@NonNull Context context,
                                             @AnyRes int drawableId) {


        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
        return imageUri;



    }




}
