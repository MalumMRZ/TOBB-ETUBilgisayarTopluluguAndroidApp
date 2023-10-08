package com.yusufmirza.etubilgisayartopluluk.ClubInterface.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.yusufmirza.etubilgisayartopluluk.ClubInterface.helperClasses.NewsClass;
import com.yusufmirza.etubilgisayartopluluk.R;

import java.util.ArrayList;



public class RecNewsViewAdapter extends RecyclerView.Adapter<RecNewsViewAdapter.NewsViewHolder>{

    ArrayList<NewsClass> newsClasses;

         public RecNewsViewAdapter (ArrayList<NewsClass> newsClasses){
              this.newsClasses= newsClasses;

         }


    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
           View view =   layoutInflater.inflate(R.layout.news_row,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        NewsClass newsClass = newsClasses.get(position);
        holder.insertData(newsClass);



    }

    @Override
    public int getItemCount() {
        return newsClasses.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{

          ImageButton goWeb;
          TextView textView;


        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            goWeb = itemView.findViewById(R.id.newsRowGoWeb);
            textView = itemView.findViewById(R.id.newsRowTextView);


        }

        public void insertData(NewsClass newsClass){
            textView.setText(newsClass.getName());

            goWeb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(newsClass.getLink()));
                    itemView.getContext().startActivity(intent);
                }
            });


        }

    }



}
