package com.yusufmirza.etubilgisayartopluluk.ClubInterface.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yusufmirza.etubilgisayartopluluk.MainActivity;
import com.yusufmirza.etubilgisayartopluluk.R;
import com.yusufmirza.etubilgisayartopluluk.databinding.AccountLayoutBinding;

public class AccountActivity extends AppCompatActivity {


    AccountLayoutBinding accountLayoutBinding;
    String keyWord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        keyWord = getIntent().getStringExtra("collect");






        switch (keyWord){

            case "BlokZincir" :
                accountLayoutBinding = AccountLayoutBinding.inflate(getLayoutInflater());
                setContentView(accountLayoutBinding.getRoot());

                accountLayoutBinding.instagram.setText("tobb_blockchain_kulübü");
                accountLayoutBinding.linkedin.setText("TOBB ETÜ Blokzincir Kulübü");
                accountLayoutBinding.medium.setText("TOBB ETU Blockchain"); //Burada X niyetine kullanılacak


                accountLayoutBinding.medium.setCompoundDrawablesWithIntrinsicBounds(R.drawable.twitter,0,0,0);

                setOnButtonListeners("https://www.instagram.com/tobb_blockchain_kulubu/","https://www.linkedin.com/company/tobb-etu-blockchain-club/","https://twitter.com/tobbetuchain");

                break;

            case "YapayZeka" :
                accountLayoutBinding = AccountLayoutBinding.inflate(getLayoutInflater());
                setContentView(accountLayoutBinding.getRoot());

                accountLayoutBinding.instagram.setText("tobb_yapayzeka_kulubu");
                accountLayoutBinding.linkedin.setText("TOBB ETU Yapay Zeka Kulübü");
                accountLayoutBinding.medium.setText("TOBB ETU Yapay Zeka Kulübü");

                setOnButtonListeners("https://www.instagram.com/tobb_yapayzeka_kulubu/","https://www.linkedin.com/company/tobb-etu-ai-club/?viewAsMember=true","https://medium.com/@tobb_yapayzeka_kulubu");

                break;

            case "OyunGelistirme" :
                accountLayoutBinding = AccountLayoutBinding.inflate(getLayoutInflater());
                setContentView(accountLayoutBinding.getRoot());

                accountLayoutBinding.instagram.setText("tobb_oyun_kulubu");
                accountLayoutBinding.linkedin.setText("TOBB ETÜ Oyun Geliştirme Kulübü");
                accountLayoutBinding.medium.setText("Tobb Oyun Klubü");

                setOnButtonListeners("https://www.instagram.com/tobb_oyun_kulubu/","https://www.linkedin.com/company/tobb-oyun-kulubu/", "https://medium.com/@tobb_oyun_kulubu");

                break;

            case "UygulamaGelistirme" :
                accountLayoutBinding = AccountLayoutBinding.inflate(getLayoutInflater());
                setContentView(accountLayoutBinding.getRoot());

                accountLayoutBinding.instagram.setText("tobb_uygulama_kulubu");
                accountLayoutBinding.linkedin.setText("TOBB ETU Uygulama Geliştirme Kulübü");
                accountLayoutBinding.medium.setText("TOBB ETÜ Uygulama Geliştirme Kulübü");

                setOnButtonListeners("https://www.instagram.com/tobb_uygulama_kulubu/","https://www.linkedin.com/showcase/tobb-etu-uygulama-geli%C5%9Ftirme-kul%C3%BCb%C3%BC/","https://medium.com/@tobb_uygulama_kulubu");

                break;

            case "SiberGuvenlik" :
                accountLayoutBinding = AccountLayoutBinding.inflate(getLayoutInflater());
                setContentView(accountLayoutBinding.getRoot());

                accountLayoutBinding.instagram.setText("tobb_siberguvenlik_kulubu");
                accountLayoutBinding.linkedin.setText("TOBB ETU Siber Güvenlik Kulübü");
                accountLayoutBinding.medium.setText("TOBB ETU Siber Güvenlik Kulübü");

                setOnButtonListeners("https://www.instagram.com/tobb_siberguvenlik_kulubu/","https://www.linkedin.com/company/tobbsiberguvenlik/","https://medium.com/@etucybercommunity");

                break;


        }









    }

    public void setOnButtonListeners(String instagram ,String linkedin, String medium){

        accountLayoutBinding.instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = instagram;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });





        accountLayoutBinding.linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = linkedin;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        accountLayoutBinding.medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = medium;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        accountLayoutBinding.imageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, MainActivityClub.class);
                intent.putExtra("col",keyWord);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        accountLayoutBinding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

}
