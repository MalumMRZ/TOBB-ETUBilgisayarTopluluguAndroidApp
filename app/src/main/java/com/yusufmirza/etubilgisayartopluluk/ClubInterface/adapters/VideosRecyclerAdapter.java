package com.yusufmirza.etubilgisayartopluluk.ClubInterface.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yusufmirza.etubilgisayartopluluk.ClubInterface.helperClasses.VideosClass;
import com.yusufmirza.etubilgisayartopluluk.R;

import java.util.ArrayList;



public class VideosRecyclerAdapter extends RecyclerView.Adapter<VideosRecyclerAdapter.VideoViewHolder> {

    ArrayList<VideosClass> videosClassArrayList;

    public VideosRecyclerAdapter( ArrayList<VideosClass> videosClassArrayList){
        this.videosClassArrayList=videosClassArrayList;
    }


    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row,parent,false);

        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        VideosClass videosClass = videosClassArrayList.get(position);

        holder.insertData(videosClass);

        holder.videoRowImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = videosClass.getLink();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                holder.itemView.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return videosClassArrayList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        TextView videoRowTextView;
        ImageButton videoRowImageButton;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoRowTextView = itemView.findViewById(R.id.videoRowTextView);
            videoRowImageButton = itemView.findViewById(R.id.videoRowImageButton);


        }

        public void insertData(VideosClass videosClass){

            videoRowTextView.setText(videosClass.getName());

            if (videosClass.getAccountType().equals("instagram")){
                videoRowImageButton.setImageResource(R.drawable.instagram);
                videoRowImageButton.setBackgroundResource(R.drawable.my_shape_instagram);
            } else {
                videoRowImageButton.setImageResource(R.drawable.youtube);
                videoRowImageButton.setBackgroundResource(R.drawable.my_shape_youtube);
            }





        }



    }


}
