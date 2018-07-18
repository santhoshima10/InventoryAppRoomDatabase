package com.example.android.inventoryapproomdatabase;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.android.inventoryapproomdatabase.data.Product;


import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> mProducts;

    private  LayoutInflater mInflater;

    Context mContext;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onSaleButtonClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public ProductAdapter(Context context){

         mInflater = LayoutInflater.from(context);
        mContext = context;

    }
    public void setData(List<Product>  list){
        mProducts = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = mInflater.inflate(R.layout.list_item,parent,false);
        return new ProductViewHolder(view,mListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {


         Product product = mProducts.get(position);


            holder.productName.setText(product.getProductName());
            holder.productUnitPrice.setText(String.valueOf(product.getProductUnitPrice()));
            holder.productQuantity.setText(String.valueOf(product.getProductQuantity()));
            Uri imageUri = Uri.parse(product.getProductImageUri());
            holder.productImg.setImageURI(imageUri);


    }

    @Override
    public int getItemCount() {



        if(mProducts == null){
            return 0;
        }
        else{
            return mProducts.size();

        }



    }




   static class ProductViewHolder extends RecyclerView.ViewHolder{

        ImageView productImg;
        TextView productName;
        TextView productUnitPrice;
        TextView productQuantity;
        ImageButton saleButton;
        TextView productStatus;



        private ProductViewHolder(View itemView, final OnItemClickListener listener) {

          super(itemView);

          productName = itemView.findViewById(R.id.product_name_id);
          productUnitPrice = itemView.findViewById(R.id.product_unit_price_id);
          productQuantity = itemView.findViewById(R.id.product_quantity_id);
          saleButton = itemView.findViewById(R.id.product_sale_btn_id);
          productStatus = itemView.findViewById(R.id.product_status_id);
          productImg =  itemView.findViewById(R.id.product_image_id);


         itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(listener != null){
                     int position = getAdapterPosition();
                     if(position != RecyclerView.NO_POSITION){
                         listener.onItemClick(position);
                     }
                 }

             }
         });


         saleButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if(listener != null){
                     int position = getAdapterPosition();
                     if(position != RecyclerView.NO_POSITION){
                     listener.onSaleButtonClick(position);
                     }
                 }



             }
         });

        }

     }


}
