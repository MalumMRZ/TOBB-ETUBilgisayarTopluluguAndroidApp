package com.yusufmirza.etubilgisayartopluluk.ClubInterface.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.squareup.picasso.Picasso;
import com.yusufmirza.etubilgisayartopluluk.ClubInterface.helperClasses.ActivityClass;
import com.yusufmirza.etubilgisayartopluluk.MainActivity;
import com.yusufmirza.etubilgisayartopluluk.R;


public class ActivityDetail extends AppCompatActivity {

    ImageButton imageBackButton,homeButton,imageButtonInstagram,imageButtonShare,imageButtonNotif;
    ImageView imageView;
    TextView toolbarDetailText;

    ActivityClass activityClass;
    private float startX;
    private int currentImageIndex=0;
    String keyWord;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = findViewById(R.id.imageView);
        imageBackButton = findViewById(R.id.imageBackButton);
        homeButton = findViewById(R.id.homeButton);
        imageButtonInstagram = findViewById(R.id.imageButtonInstagram);
        imageButtonShare = findViewById(R.id.imageButtonShare);
        imageButtonNotif = findViewById(R.id.imageButtonNotif);
        toolbarDetailText = findViewById(R.id.toolbarDetailText);

        activityClass = (ActivityClass) getIntent().getSerializableExtra("detail");

        keyWord = getIntent().getStringExtra("collect");


        toolbarDetailText.setText(activityClass.getActivityName()); //Kısaltılmış isim kullan.

        Picasso.get().load(activityClass.getImageList().get(0)).fit().into(imageView);





        imageView.setOnTouchListener(new View.OnTouchListener() {


            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        return true;
                    case MotionEvent.ACTION_UP:
                        float endX = event.getX();
                        if (endX > startX) {
                            // Kullanıcı sağa kaydırdı
                            currentImageIndex = (currentImageIndex + 1) % activityClass.getImageList().size();
                        } else if (endX < startX) {
                            // Kullanıcı sola kaydırdı
                            currentImageIndex = (currentImageIndex - 1 + activityClass.getImageList().size()) % activityClass.getImageList().size();
                        }


                        Picasso.get().load(activityClass.getImageList().get(currentImageIndex)).fit().into(imageView);

                        return true;
                }
                return false;
            }
        });





        imageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityDetail.this, MainActivityClub.class);
                intent.putExtra("collect",keyWord);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityDetail.this, MainActivity.class);
                startActivity(intent);
            }
        });

        imageButtonInstagram.setOnClickListener(new View.OnClickListener() {
            @Override //Sınıfa iki tane link eklenmeli
            public void onClick(View view) {

                    String url = activityClass.getActivityLink();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);



            }
        });



        imageButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String Uri= activityClass.getActivityLink();
                    Intent paylasIntent = new Intent(Intent.ACTION_SEND);
                    paylasIntent.setType("text/plain");
                    paylasIntent.putExtra(Intent.EXTRA_TEXT,Uri);
                    startActivity(Intent.createChooser(paylasIntent, "Paylaş"));

            }
        });

        imageButtonNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Work manager öğrendikten sonra konuşulacak
            }
        });

    }
}
