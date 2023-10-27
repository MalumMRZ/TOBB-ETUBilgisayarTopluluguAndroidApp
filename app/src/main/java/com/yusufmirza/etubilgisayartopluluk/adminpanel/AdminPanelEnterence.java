package com.yusufmirza.etubilgisayartopluluk.adminpanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yusufmirza.etubilgisayartopluluk.ClubInterface.activities.EtkinlikActivity;
import com.yusufmirza.etubilgisayartopluluk.ClubInterface.activities.MainActivityClub;
import com.yusufmirza.etubilgisayartopluluk.MainActivity;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminPanelEnterenceBinding;

public class AdminPanelEnterence extends AppCompatActivity {

    AdminPanelEnterenceBinding adminPanelEnterenceBinding;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminPanelEnterenceBinding = AdminPanelEnterenceBinding.inflate(getLayoutInflater());
        setContentView(adminPanelEnterenceBinding.getRoot());

        adminPanelEnterenceBinding.toolbarDetailText.setText("Admin Paneli");

        adminPanelEnterenceBinding.imageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        adminPanelEnterenceBinding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanelEnterence.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        adminPanelEnterenceBinding.aiClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminPanelEnterence.this, AdminClubActivities.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("col","YapayZeka");
                AdminPanelEnterence.this.startActivity(intent);

            }
        });

        adminPanelEnterenceBinding.cyberSecClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanelEnterence.this, AdminClubActivities.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("col","SiberGuvenlik");
                AdminPanelEnterence.this.startActivity(intent);

            }
        });

        adminPanelEnterenceBinding.appDevClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanelEnterence.this, AdminClubActivities.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("col","UygulamaGelistirme");
                AdminPanelEnterence.this.startActivity(intent);

            }
        });

        adminPanelEnterenceBinding.gameDevClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanelEnterence.this, AdminClubActivities.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("col","OyunGelistirme");
                AdminPanelEnterence.this.startActivity(intent);

            }
        });

        adminPanelEnterenceBinding.blockChainClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanelEnterence.this, AdminClubActivities.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("col","BlokZincir");
                AdminPanelEnterence.this.startActivity(intent);

            }
        });



    }
}
