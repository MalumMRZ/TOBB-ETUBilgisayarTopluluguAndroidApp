package com.yusufmirza.etubilgisayartopluluk.adminpanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yusufmirza.etubilgisayartopluluk.adminpanel.newspanel.AdminMainNews;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminClubActivitiesBinding;

public class AdminClubActivities extends AppCompatActivity {


    String keyWord;
  AdminClubActivitiesBinding adminClubActivitiesBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminClubActivitiesBinding = AdminClubActivitiesBinding.inflate(getLayoutInflater());
        setContentView(adminClubActivitiesBinding.getRoot());

        keyWord = getIntent().getStringExtra("col");

        adminClubActivitiesBinding.activitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminClubActivities.this, AdminMainNews.class);
                intent.putExtra("key",keyWord);
                intent.putExtra("type","activitys");
                startActivity(intent);
            }
        });

        adminClubActivitiesBinding.newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminClubActivities.this, AdminMainNews.class);
                intent.putExtra("key",keyWord);
                intent.putExtra("type","news");
                startActivity(intent);
            }
        });

        adminClubActivitiesBinding.membersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminClubActivities.this, AdminMainNews.class);
                intent.putExtra("key",keyWord);
                intent.putExtra("type","members");
                startActivity(intent);
            }
        });

        adminClubActivitiesBinding.videosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminClubActivities.this, AdminMainNews.class);
                intent.putExtra("key",keyWord);
                intent.putExtra("type","videos");
                startActivity(intent);
            }
        });






    }
}
