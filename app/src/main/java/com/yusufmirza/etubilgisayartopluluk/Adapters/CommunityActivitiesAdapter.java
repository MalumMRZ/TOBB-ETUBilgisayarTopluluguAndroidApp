package com.yusufmirza.etubilgisayartopluluk.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yusufmirza.etubilgisayartopluluk.HelperClasses.CommunityActivities;
import com.yusufmirza.etubilgisayartopluluk.R;

import java.util.ArrayList;


public class CommunityActivitiesAdapter extends RecyclerView.Adapter<CommunityActivitiesAdapter.ActivityViewHolder> {

    ArrayList<CommunityActivities> communityActivities;


    public CommunityActivitiesAdapter(ArrayList<CommunityActivities> communityActivities) {
        this.communityActivities = communityActivities;

    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rcyc_row, parent, false);

        return new ActivityViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        CommunityActivities communityActivity = communityActivities.get(position);
        holder.insertData(communityActivity);

        holder.imageViewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String url = communityActivity.getActivityLink();

                if (!url.equals("")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    holder.itemView.getContext().startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(), "Bu etkinliğin linki yok", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return communityActivities.size();
    }

    public static class ActivityViewHolder extends RecyclerView.ViewHolder {

        TextView actvName, actvSpeker, actvDate, actvTime, actvPlace;
        ImageView imageViewActivity;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            actvName = itemView.findViewById(R.id.actvName);
            actvSpeker = itemView.findViewById(R.id.actvSpeker);
            actvDate = itemView.findViewById(R.id.actvDate);
            actvTime = itemView.findViewById(R.id.actvTime);
            actvPlace = itemView.findViewById(R.id.actvPlace);
            imageViewActivity = itemView.findViewById(R.id.imageViewActivity);


        }

        public void insertData(CommunityActivities communityActivity) {
            if (!communityActivity.getImageUri().equals("")) {
                Picasso.get().load(communityActivity.getImageUri()).fit().into(imageViewActivity);
            }


            actvName.setText(communityActivity.getName());
            actvSpeker.setText(communityActivity.getActivitySpeaker());
            actvDate.setText(communityActivity.getActivityDate());
            actvTime.setText(communityActivity.getActivityTime());
            actvPlace.setText(communityActivity.getActivityPlace());

        }

    }


}
