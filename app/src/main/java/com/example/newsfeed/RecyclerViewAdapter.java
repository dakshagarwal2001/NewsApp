package com.example.newsfeed;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    ArrayList<NewsClass> newsList;
    Context context;
    Context parContext;
    NewsClass obj;

    public RecyclerViewAdapter(ArrayList<NewsClass> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_layout,parent,false);
        parContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull RecyclerViewAdapter.ViewHolder holder, int position) {
        obj = newsList.get(position);
        if(obj.img.length()>4)
        {
            Glide.with(parContext).load(obj.img).into(holder.newsImage);
        }
        else holder.newsImage.setImageResource(R.drawable.news);
        holder.headline.setText(obj.headline);

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView newsImage;
        TextView headline;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            newsImage = itemView.findViewById(R.id.newsImage);
            headline = itemView.findViewById(R.id.headline);
        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(v.getContext(), "clicked : " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            String url = obj.newsLink;
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(context, Uri.parse(url));
//
        }
    }
}
