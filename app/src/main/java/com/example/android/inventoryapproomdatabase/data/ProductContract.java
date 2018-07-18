package com.example.android.inventoryapproomdatabase.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class ProductContract {

    private ProductContract(){

    }

    public static class ProductEntry implements BaseColumns {



        public static final String TABLE_NAME = "products";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME_PRODUCT_NAME = "product_name";
        public static final String COLUMN_NAME_PRODUCT_UNIT_PRICE = "product_unit_price";
        public static final String COLUMN_NAME_PRODUCT_QUANTITY = "product_quantity";
        public static final String COLUMN_NAME_PRODUCT_SUPPLIER_NAME = "product_supplier_name";
        public static final String COLUMN_NAME_PRODUCT_SUPPLIER_PHONE = "product_supplier_phone";
        public static final String COLUMN_NAME_PRODUCT_SUPPLIER_EMAIL = "product_supplier_email";
        public static final String COLUMN_NAME_PRODUCT_IMAGE_URI = "product_image_uri";





    }


}