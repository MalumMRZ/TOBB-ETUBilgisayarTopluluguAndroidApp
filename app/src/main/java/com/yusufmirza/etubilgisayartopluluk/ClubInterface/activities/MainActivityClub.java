package com.yusufmirza.etubilgisayartopluluk.ClubInterface.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yusufmirza.etubilgisayartopluluk.MainActivity;
import com.yusufmirza.etubilgisayartopluluk.databinding.ActivityMainClubBinding;


public class MainActivityClub extends AppCompatActivity {



    ActivityMainClubBinding activityMainClubBinding;

    String keyWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainClubBinding = ActivityMainClubBinding.inflate(getLayoutInflater());
        setContentView(activityMainClubBinding.getRoot());


        keyWord = getIntent().getStringExtra("col");




        activityMainClubBinding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityClub.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });




        switch (keyWord){

            case "BlokZincir":
                activityMainClubBinding.toolbarDetailText.setText("Blok Zincir Kulübü");
                break;

            case "YapayZeka":
                activityMainClubBinding.toolbarDetailText.setText("Yapay Zeka Kulübü");
                break;

            case "OyunGelistirme":
                activityMainClubBinding.toolbarDetailText.setText("Oyun Geliştirme Kulübü");
                break;

            case "UygulamaGelistirme":
                activityMainClubBinding.toolbarDetailText.setText("Uygulama Geliştirme Kulübü");
                break;

            case "SiberGuvenlik":
                activityMainClubBinding.toolbarDetailText.setText("Siber Güvenlik Kulübü");
                break;

        }








        activityMainClubBinding.newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityClub.this, NewsActivity.class);
                intent.putExtra("collect",keyWord);
                startActivity(intent);
            }
        });

        activityMainClubBinding.activitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityClub.this, EtkinlikActivity.class);
                intent.putExtra("collect",keyWord);
                startActivity(intent);
            }
        });

        activityMainClubBinding.videosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityClub.this, VideosActivity.class);
                intent.putExtra("collect",keyWord);
                startActivity(intent);
            }
        });

        activityMainClubBinding.membersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityClub.this, MembersActivity.class);
                intent.putExtra("collect",keyWord);
                startActivity(intent);
            }
        });

        activityMainClubBinding.accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityClub.this, AccountActivity.class);
                intent.putExtra("collect",keyWord);
                startActivity(intent);
            }
        });


    }
}