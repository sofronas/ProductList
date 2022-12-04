package com.example.jsonexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import com.example.jsonexample.Product_Folder.Product;
import com.example.jsonexample.Product_Folder.ProductAdapter;
import com.example.jsonexample.Product_Folder.SingleProductShown;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String jsonUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check permissions for internet access
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        jsonUrl = "https://dummyjson.com/products?limit=10";
        JSONObject jsonObject;
        try{
            jsonObject = getJSONObjectFromURL(jsonUrl);

            JSONArray productsAr = (JSONArray) jsonObject.get("products");

            ArrayList<Product> dataset = new ArrayList<Product>();

            for (int i = 0; i < productsAr.length(); i++) {
                dataset.add(new Product(productsAr.getJSONObject(i).getString("title"),
                        productsAr.getJSONObject(i).getString("description"),
                        productsAr.getJSONObject(i).getDouble("rating"),
                        productsAr.getJSONObject(i).getString("thumbnail")
                ));
                System.out.println(productsAr.getJSONObject(i).getString("title"));
            }

            System.out.println("dataset length: " + dataset.size());

            ProductAdapter adapter = new ProductAdapter(dataset,this);
            LinearLayoutManager layoutManager = new  LinearLayoutManager(this);

            RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.lid);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(adapter);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */ );
        urlConnection.setConnectTimeout(15000 /* milliseconds */ );
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        String jsonString = sb.toString();
        System.out.println("JSON: " + jsonString);

        return new JSONObject(jsonString);
    }

    public void showSingleProduct(Product object){
        Intent intent = new Intent(MainActivity.this, SingleProductShown.class);
        intent.putExtra("product_title", object.getTitle());
        intent.putExtra("product_description", object.getDescription());
        intent.putExtra("product_thumbnail", object.getThumbnail());
        intent.putExtra("product_rating", object.getRating());
        startActivity(intent);
        finish();
    }
}