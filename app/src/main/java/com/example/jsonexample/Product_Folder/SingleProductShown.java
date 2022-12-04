package com.example.jsonexample.Product_Folder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.jsonexample.MainActivity;
import com.example.jsonexample.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SingleProductShown extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product_shown);



        Intent intent = getIntent();
        String product_title = intent.getStringExtra("product_title");
        setTitle(product_title);
        String product_description = intent.getStringExtra("product_description");
        String product_thumbnail = intent.getStringExtra("product_thumbnail");
        Double product_rating = intent.getDoubleExtra("product_rating",0);


        TextView pr_title = (TextView) findViewById(R.id.productShownTitle);
        pr_title.setText(product_title);

        TextView pr_description = (TextView) findViewById(R.id.productShownDescr);
        pr_description.setText(product_description);

        ImageView pr_thumb = (ImageView) findViewById(R.id.productShownImage);
        pr_thumb.setImageBitmap(getBitmapFromURL(product_thumbnail));

        RatingBar pr_rating = (RatingBar) findViewById(R.id.productShownRating);
        pr_rating.setRating(product_rating.floatValue());

    }

    private Bitmap getBitmapFromURL(String src) {
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

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}