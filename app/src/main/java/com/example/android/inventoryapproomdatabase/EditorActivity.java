package com.example.android.inventoryapproomdatabase;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.android.inventoryapproomdatabase.data.Product;
import com.example.android.inventoryapproomdatabase.data.ProductContract;


import java.io.FileNotFoundException;


public class EditorActivity extends AppCompatActivity {


    private EditText mProductName;
    private EditText mProductUnitPrice;
    private EditText mProductQuantity;
    private EditText mProductSupplierName;
    private EditText mProductSupplierPhone;
    private EditText mProductSupplierEmail;
    private Button mProductAddImageBtn;
    private ImageView mProductImage;
    private Button mIncrementBtn;
    private Button mDecrementBtn;
    private int item_qty = 0;
    private String imageURI="";

    private boolean mProductHasChanged = false;

    public static final String NEW_PRODUCT = "NEW_PRODUCT";
    public static final String UPDATE_PRODUCT = "UPDATE_PRODUCT";
    public static final String DELETE_PRODUCT = "DELETE_PRODUCT";

    private String mEditorActivityStatus= "";
    private int mProductId=0;



    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mProductHasChanged = true;
            return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);


        mProductName = (EditText)findViewById(R.id.item_name_id) ;
        mProductUnitPrice = (EditText)findViewById(R.id.unit_item_price_id) ;
        mProductQuantity = (EditText)findViewById(R.id.item_quantity_id) ;
        mIncrementBtn = (Button)findViewById(R.id.increment_qty_btn_id) ;
        mDecrementBtn = (Button)findViewById(R.id.decrement_qty_btn_id) ;
        mProductSupplierName = (EditText)findViewById(R.id.supplier_name_id) ;
        mProductSupplierPhone = (EditText)findViewById(R.id.supplier_phone_id) ;
        mProductSupplierEmail = (EditText)findViewById(R.id.supplier_email_id) ;
        mProductAddImageBtn = (Button) findViewById(R.id.upload_image_btn_id) ;
        mProductImage = (ImageView) findViewById(R.id.item_image_id);



        mProductName.setOnTouchListener(mTouchListener);
        mProductUnitPrice.setOnTouchListener(mTouchListener);
        mProductQuantity.setOnTouchListener(mTouchListener) ;
        mIncrementBtn.setOnTouchListener(mTouchListener) ;
        mDecrementBtn.setOnTouchListener(mTouchListener) ;
        mProductSupplierName.setOnTouchListener(mTouchListener) ;
        mProductSupplierPhone.setOnTouchListener(mTouchListener) ;
        mProductSupplierEmail.setOnTouchListener(mTouchListener);
        mProductAddImageBtn.setOnTouchListener(mTouchListener) ;




        item_qty = Integer.parseInt(mProductQuantity.getText().toString());

        mIncrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIncrementQty();

            }
        });

        mDecrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDecrementQty();
            }
        });

        mProductAddImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();


            }
        });

        if(getIntent().getSerializableExtra(UPDATE_PRODUCT) != null){

            getSupportActionBar().setTitle("Edit Product");
            mEditorActivityStatus = "UPDATE";

            Product product = (Product)getIntent().getSerializableExtra(UPDATE_PRODUCT);

            mProductName.setText(product.getProductName());
            mProductUnitPrice.setText(String.valueOf(product.getProductUnitPrice())); ;
            mProductQuantity.setText(String.valueOf(product.getProductQuantity()));
            mProductSupplierName.setText(product.getProductSupplierName());
            mProductSupplierPhone.setText(product.getProductSupplierPhone());
            mProductSupplierEmail.setText(product.getProductSupplierEmail());
            Uri imageUriPath = Uri.parse(product.getProductImageUri());
            mProductImage.setImageURI(imageUriPath);
            mProductId = product.getProductId();
            imageURI = product.getProductImageUri();
            Log.d("EditorActivity",String.valueOf(product.getProductId()));






        }
        else{
            mEditorActivityStatus="INSERT";
            getSupportActionBar().setTitle("Add New Product");
            invalidateOptionsMenu();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if(mEditorActivityStatus == "INSERT"){
            MenuItem menuItem = menu.findItem(R.id.delete_product_id);
            menuItem.setVisible(false);
        }
        return true;
    }

    private void selectImage(){

        Intent intent = new Intent();


        if (Build.VERSION.SDK_INT > 19) {

            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addCategory(Intent.CATEGORY_OPENABLE);

        } else {

            intent = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }

        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),0);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            Uri targetURI = data.getData();
            Bitmap bitmap;

            try{
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetURI));
                mProductImage.setImageBitmap(bitmap);
                imageURI = targetURI.toString();



            }
            catch (FileNotFoundException e){

                e.printStackTrace();

            }

        }
    }

    private void mIncrementQty(){

        item_qty = item_qty+1;
        mProductQuantity.setText(Integer.toString(item_qty));


    }

    private void mDecrementQty(){
        if(item_qty > 0){
            item_qty = item_qty -1;
        }
        mProductQuantity.setText(Integer.toString(item_qty));
    }

    private void mInsertProductData(){


        String productName = mProductName.getText().toString().trim();
        int productUnitPrice = Integer.parseInt(mProductUnitPrice.getText().toString());
        int qty = Integer.parseInt(mProductQuantity.getText().toString());
        String SupplierName = mProductSupplierName.getText().toString().trim();
        String SupplierPhone = mProductSupplierPhone.getText().toString().trim();
        String SupplierEmail = mProductSupplierEmail.getText().toString().trim();
        String productImageUri = imageURI;



        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME,productName);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_UNIT_PRICE,productUnitPrice);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY,qty);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_NAME,SupplierName);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_EMAIL,SupplierEmail);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_PHONE,SupplierPhone);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_IMAGE_URI,productImageUri);


        Product product = Product.fromContentValues(values);

        Intent replyIntent = new Intent();
        replyIntent.putExtra(NEW_PRODUCT,product);
        setResult(RESULT_OK,replyIntent);
        finish();





    }


    private void mUpdateProductData() {


        Log.d("EditorActivity","Product Id :"+String.valueOf(mProductId));

        String productName = mProductName.getText().toString().trim();
        int productUnitPrice = Integer.parseInt(mProductUnitPrice.getText().toString());
        int qty = Integer.parseInt(mProductQuantity.getText().toString());
        String SupplierName = mProductSupplierName.getText().toString().trim();
        String SupplierPhone = mProductSupplierPhone.getText().toString().trim();
        String SupplierEmail = mProductSupplierEmail.getText().toString().trim();
        String productImageUri = imageURI;


        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry._ID,mProductId);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME, productName);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_UNIT_PRICE, productUnitPrice);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY, qty);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_NAME, SupplierName);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_EMAIL, SupplierEmail);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_PHONE, SupplierPhone);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_IMAGE_URI, productImageUri);


        Product productUpd = Product.fromContentValues(values);

        Intent replyIntent = new Intent();
        replyIntent.putExtra(UPDATE_PRODUCT,productUpd);
        setResult(RESULT_OK,replyIntent);
        finish();



    }

    private void mDeleteProductData(){


        Intent replyIntent = new Intent();
        replyIntent.putExtra(DELETE_PRODUCT,String.valueOf(mProductId));
        setResult(RESULT_OK,replyIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editor_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                if(!mProductHasChanged){
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }
                else{
                    showAlertDialogForHomeButton();
                    return true;
                }


            case R.id.save_new_product_id:
                if( mEditorActivityStatus== "UPDATE"){

                    if(validateUserInputs()){
                       mUpdateProductData();
                    }


                }
                else{

                    if(validateUserInputs()){
                        mInsertProductData();

                    }

                }


                return true;

            case R.id.delete_product_id:
                showAlertDialogForDeleteProduct();
                return true;

            case R.id.order_more_id:
                showAlertDialogForOrderMoreProducts();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    private boolean validateUserInputs(){

        boolean flag = true;

        if(mProductName.getText().toString().trim().length() ==0){
            mProductName.setError("Enter Product Name");
            flag= false;
        }

        if(mProductUnitPrice.getText().toString().length() ==0){
            mProductUnitPrice.setError("Enter Valid Price");
            flag = false;
        }

        if(mProductQuantity.getText().toString().length() == 0){
            mProductQuantity.setError("Enter Valid Quantity");
            flag =false;
        }

        if(mProductSupplierName.getText().toString().trim().length() ==0){
            mProductSupplierName.setError("Enter Valid Supplier Name");
            flag= false;
        }

        if(mProductSupplierPhone.getText().toString().trim().length() ==0){
            mProductSupplierPhone.setError("Enter Valid Supplier Phone");
            flag= false;
        }

        if(mProductSupplierEmail.getText().toString().trim().length() ==0){
            mProductSupplierEmail.setError("Enter Valid Supplier Email");
            flag= false;
        }


        if(mProductImage.getDrawable() == null)
        {
            mProductAddImageBtn.setError("Upload Product Image");
            flag = false;
        }

        System.out.println("The value of the validate function"+flag);

        if(flag){
            return true;
        }
        else{
            return false;
        }




    }

    @Override
    public void onBackPressed() {

        if(!mProductHasChanged){
            super.onBackPressed();
            return;
        }
        else{
            showAlertDialogForBackButton();
        }


    }

    private void showAlertDialogForBackButton(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Discard your changes and quit editing?");
        builder.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Keep Editing", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }


    private void showAlertDialogForHomeButton(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Discard your changes and quit editing?");
        builder.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NavUtils.navigateUpFromSameTask(EditorActivity.this);
            }
        });
        builder.setNegativeButton("Keep Editing", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }


    private void showAlertDialogForDeleteProduct(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do You Want to Delete the Product ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mDeleteProductData();


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }

    private void showAlertDialogForOrderMoreProducts(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Order Item By Phone or Email");
        builder.setNegativeButton("Phone", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+mProductSupplierPhone.getText().toString()));
                startActivity(intent);


            }
        }).setPositiveButton("Email", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String email = mProductSupplierEmail.getText().toString();
                String subject = "Order of Product "+mProductName.getText().toString();


                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


}
