package com.example.jsonexample.Product_Folder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jsonexample.MainActivity;
import com.example.jsonexample.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter {
    private ArrayList<Product> dataSet;
    Context mContext;
    int total_types;

    public ProductAdapter(ArrayList<Product> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = data.size();
    }

    public static class TextTypeViewHolder extends RecyclerView.ViewHolder {
        TextView titleType;
        TextView descrType;
        ImageView logoType;
        Button showMoreBtn;

        public TextTypeViewHolder(View itemView) {
            super(itemView);

            this.titleType = (TextView) itemView.findViewById(R.id.title_textView);
            this.descrType = (TextView) itemView.findViewById(R.id.description_textView);
            this.logoType = (ImageView) itemView.findViewById(R.id.logo_product);
            this.showMoreBtn = (Button) itemView.findViewById(R.id.showmorebnt);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);
        return new TextTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Product object = dataSet.get(position);
        if (object != null) {
            ((TextTypeViewHolder) holder).titleType.setText(object.getTitle());
            ((TextTypeViewHolder) holder).descrType.setText(object.getDescription());
            ((TextTypeViewHolder) holder).logoType.setImageBitmap(getBitmapFromURL(object.getThumbnail()));
            ((TextTypeViewHolder) holder).showMoreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mContext instanceof MainActivity) {
                        ((MainActivity)mContext).showSingleProduct(object);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
