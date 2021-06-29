package com.example.newsfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<NewsClass> newsList;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsList = new ArrayList<>();
        context = this;
        getNews();
        Log.d("mytag",String.valueOf(newsList.size()));


    }
    void getNews()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://newsdata.io/api/1/archive?apikey=pub_353f4ca7a14a3c2b819c0a48d2194c41fb6&q=ipl";

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        try {
//                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray arr = response.getJSONArray("results");
//                            int size = arr.length();
                            if(arr.length()>0)
                            {
                                Log.d("mytag","arr populated");
                            }
                            for(int i=0;i<arr.length();i++)
                            {
                                String news = arr.getJSONObject(i).getString("title");
                                Log.d("mytag",news);
                                String image = arr.getJSONObject(i).getString("image_url");
                                Log.d("mytag", String.valueOf(image.length()));
                                String newsLink = arr.getJSONObject(i).getString("link");
                                newsList.add(new NewsClass(image,news,newsLink));

                            }
                            Log.d("mytag",String.valueOf(newsList.size()));
                            recyclerView = findViewById(R.id.recyclerView);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView.setHasFixedSize(true);
                            RecyclerViewAdapter adapter = new RecyclerViewAdapter(newsList,context);
                            recyclerView.setAdapter(adapter);
//                            RecyclerViewAdapter adapter = new RecyclerViewAdapter(newsList,context);
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mytag","some error occured!");


            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}