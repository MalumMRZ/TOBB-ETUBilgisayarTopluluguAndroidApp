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
import com.yusufmirza.etubilgisayartopluluk.HelperClasses.CommunityProjects;
import com.yusufmirza.etubilgisayartopluluk.R;

import java.util.ArrayList;


public class CommunityProjectsAdapter extends RecyclerView.Adapter<CommunityProjectsAdapter.ProjectViewHolder> {

    ArrayList<CommunityProjects> communityProjects;






    public CommunityProjectsAdapter(ArrayList<CommunityProjects> communityProjects){
        this.communityProjects= communityProjects;

    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_rcyc_row,parent,false);

            return new ProjectViewHolder(view);



    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        CommunityProjects communityProject = communityProjects.get(position);
        holder.insertData(communityProject);

        holder.imageViewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = communityProject.getProjectLink();

                if (!url.equals("")){
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
        return communityProjects.size();
    }



    public static class ProjectViewHolder extends RecyclerView.ViewHolder{

        TextView actvName,actvSlogan,actvDate,actvTime,actvPlace,actvApplication;
        ImageView imageViewActivity;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            actvName = itemView.findViewById(R.id.actvName);
            actvSlogan = itemView.findViewById(R.id.actvSlogan);
            actvDate = itemView.findViewById(R.id.actvDate);
            actvTime = itemView.findViewById(R.id.actvTime);
            actvApplication = itemView.findViewById(R.id.actvApplication);
            actvPlace = itemView.findViewById(R.id.actvPlace);
            imageViewActivity = itemView.findViewById(R.id.imageViewActivity);


        }

        public  void insertData(CommunityProjects communityProjects) {

           if (!communityProjects.getImageUri().equals("")){
               Picasso.get().load(communityProjects.getImageUri()).fit().into(imageViewActivity);
           }


            actvName.setText(communityProjects.getName());
            actvApplication.setText(communityProjects.getProjectApplication());
            actvSlogan.setText(communityProjects.getProjectSlogan());
            actvDate.setText(communityProjects.getProjectDate());
            actvTime.setText(communityProjects.getProjectTime());
            actvPlace.setText(communityProjects.getProjectPlace());

        }

    }

}
