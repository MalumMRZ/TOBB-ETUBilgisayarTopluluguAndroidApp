package com.yusufmirza.etubilgisayartopluluk.adminpanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yusufmirza.etubilgisayartopluluk.MainActivity;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.activitypanel.AdminMainActivity;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.memberspanel.AdminMainMembers;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.newspanel.AdminMainNews;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.videospanel.AdminMainVideos;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.videospanel.AdminVideosEdit;
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


        switch (keyWord){

            case "BlokZincir":
                adminClubActivitiesBinding.toolbarDetailText.setText("Blok Zincir Kulübü");
                break;

            case "YapayZeka":
                adminClubActivitiesBinding.toolbarDetailText.setText("Yapay Zeka Kulübü");
                break;

            case "OyunGelistirme":
                adminClubActivitiesBinding.toolbarDetailText.setText("Oyun Geliştirme Kulübü");
                break;

            case "UygulamaGelistirme":
                adminClubActivitiesBinding.toolbarDetailText.setText("Uygulama Geliştirme Kulübü");
                break;

            case "SiberGuvenlik":
                adminClubActivitiesBinding.toolbarDetailText.setText("Siber Güvenlik Kulübü");
                break;

        }

        adminClubActivitiesBinding.imageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        adminClubActivitiesBinding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminClubActivities.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        adminClubActivitiesBinding.activitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminClubActivities.this, AdminMainActivity.class);
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
                Intent intent = new Intent(AdminClubActivities.this, AdminMainMembers.class);
                intent.putExtra("key",keyWord);
                intent.putExtra("type","members");
                startActivity(intent);
            }
        });

        adminClubActivitiesBinding.videosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminClubActivities.this, AdminMainVideos.class);
                intent.putExtra("key",keyWord);
                intent.putExtra("type","videos");
                startActivity(intent);
            }
        });






    }
}
