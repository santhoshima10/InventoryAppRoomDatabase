package com.example.android.inventoryapproomdatabase.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = ProductContract.ProductEntry.TABLE_NAME)
public class Product  implements Serializable{


    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="_id")
    private  int productId;

    @NonNull
    public int getProductId() {
        return productId;
    }

    public void setProductId(@NonNull int productId) {
        this.productId = productId;
    }

    @NonNull
    public String getProductName() {
        return productName;
    }

    public void setProductName(@NonNull String productName) {
        this.productName = productName;
    }

    @NonNull
    public int getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(@NonNull int productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    @NonNull
    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(@NonNull int productQuantity) {
        this.productQuantity = productQuantity;
    }

    @NonNull
    public String getProductSupplierName() {
        return productSupplierName;
    }

    public void setProductSupplierName(@NonNull String productSupplierName) {
        this.productSupplierName = productSupplierName;
    }

    @NonNull
    public String getProductSupplierPhone() {
        return productSupplierPhone;
    }

    public void setProductSupplierPhone(@NonNull String productSupplierPhone) {
        this.productSupplierPhone = productSupplierPhone;
    }

    @NonNull
    public String getProductSupplierEmail() {
        return productSupplierEmail;
    }

    public void setProductSupplierEmail(@NonNull String productSupplierEmail) {
        this.productSupplierEmail = productSupplierEmail;
    }

    @NonNull
    public String getProductImageUri() {
        return productImageUri;
    }

    public void setProductImageUri(@NonNull String productImageUri) {
        this.productImageUri = productImageUri;
    }

    @NonNull
    @ColumnInfo(name = "product_name")
    private  String productName;

    @NonNull
    @ColumnInfo(name = "product_unit_price")
    private  int productUnitPrice;

    @NonNull
    @ColumnInfo(name = "product_quantity")
    private  int productQuantity;

    @NonNull
    @ColumnInfo(name = "product_supplier_name")
    private  String productSupplierName;

    @NonNull
    @ColumnInfo(name = "product_supplier_phone")
    private  String productSupplierPhone;

    @NonNull
    @ColumnInfo(name = "product_supplier_email")
    private  String productSupplierEmail;

    @NonNull
    @ColumnInfo(name = "product_image_uri")
    private  String productImageUri;




    public static Product fromContentValues(ContentValues values) {

        Product product = new Product();

        if (values.containsKey(ProductContract.ProductEntry._ID)) {
            product.productId = values.getAsInteger(ProductContract.ProductEntry._ID);
        }

        if (values.containsKey(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME)) {
            product.productName = values.getAsString(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME);
        }

        if (values.containsKey(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_UNIT_PRICE)) {
            product.productUnitPrice = values.getAsInteger(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_UNIT_PRICE);
        }

        if (values.containsKey(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY)) {
            product.productQuantity = values.getAsInteger(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY);
        }

        if (values.containsKey(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_NAME)) {
            product.productSupplierName = values.getAsString(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_NAME);
        }

        if (values.containsKey(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_PHONE)) {
            product.productSupplierPhone = values.getAsString(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_PHONE);
        }

        if (values.containsKey(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_EMAIL)) {
            product.productSupplierEmail = values.getAsString(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_EMAIL);
        }

        if (values.containsKey(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_IMAGE_URI)) {
            product.productImageUri = values.getAsString(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_IMAGE_URI);
        }



        return product;


    }



}
