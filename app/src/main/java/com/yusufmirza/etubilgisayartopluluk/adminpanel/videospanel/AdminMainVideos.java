package com.yusufmirza.etubilgisayartopluluk.adminpanel.videospanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yusufmirza.etubilgisayartopluluk.ArrayListSorter;
import com.yusufmirza.etubilgisayartopluluk.ClubInterface.helperClasses.NewsClass;
import com.yusufmirza.etubilgisayartopluluk.MainActivity;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.AdminClubActivities;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.newspanel.AdminNewsEdit;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.newspanel.AdminNewsHelper;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.newspanel.AdminNewsRecyclerAdapter;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminMainNewsBinding;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminMainVideosBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminMainVideos extends AppCompatActivity {

   AdminMainVideosBinding adminMainVideosBinding;

   ArrayList<AdminVideosHelper> adminVideosHelpers = new ArrayList<>();
   String keyWord;
   String type;

   DocumentReference document;

   FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminMainVideosBinding = AdminMainVideosBinding.inflate(getLayoutInflater());

         setContentView(adminMainVideosBinding.getRoot());

         keyWord = getIntent().getStringExtra("key");
         type = getIntent().getStringExtra("type");

        switch (keyWord){

            case "BlokZincir":
                adminMainVideosBinding.toolbarDetailText.setText("Blok Zincir-Video");
                break;

            case "YapayZeka":
                adminMainVideosBinding.toolbarDetailText.setText("Yapay Zeka-Video");
                break;

            case "OyunGelistirme":
                adminMainVideosBinding.toolbarDetailText.setText("Oyun Geliştirme-Video");
                break;

            case "UygulamaGelistirme":
                adminMainVideosBinding.toolbarDetailText.setText("Uygulama Geliştirme-Video");
                break;

            case "SiberGuvenlik":
                adminMainVideosBinding.toolbarDetailText.setText("Siber Güvenlik-Video");
                break;

        }


        adminMainVideosBinding.imageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        adminMainVideosBinding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainVideos.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


         document = firestore.collection(keyWord).document(type);


        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                HashMap<String, Object> newsMap = (HashMap<String, Object>) documentSnapshot.get("video_list");

                if (newsMap != null) {

                    for (String newsKey : newsMap.keySet()) {

                        HashMap<String, Object> newMap = (HashMap<String, Object>) documentSnapshot.get("video_list" + "." + newsKey);

                        if (newMap != null) {

                            String name = (String) newMap.get("name");
                            String link = (String) newMap.get("link");
                            String imageUri = (String) newMap.get("imageUri");
                            String accountType = (String) newMap.get("accountType");

                            AdminVideosHelper adminVideosHelper = new AdminVideosHelper(name, link, imageUri,newsKey,accountType);
                            adminVideosHelpers.add(adminVideosHelper);


                        }

                    }

                    if (adminVideosHelpers.size() >0){
                        ArrayListSorter<AdminVideosHelper> newsClassArrayListSorter = new ArrayListSorter<>();
                        ArrayList<AdminVideosHelper> adapterClasses = newsClassArrayListSorter.sortedArray(adminVideosHelpers);

                        AdminVideosRecyclerAdapter adminVideosRecyclerAdapter = new AdminVideosRecyclerAdapter(adapterClasses, AdminMainVideos.this,keyWord,type);
                        adminMainVideosBinding.videosAdminRecycle.setAdapter(adminVideosRecyclerAdapter);
                        adminMainVideosBinding.videosAdminRecycle.setLayoutManager(new LinearLayoutManager(AdminMainVideos.this));
                    }
                }




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminMainVideos.this, "İnternetinizi kontrol edin", Toast.LENGTH_SHORT).show();
            }
        });





        adminMainVideosBinding.addVideosButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(AdminMainVideos.this, AdminVideosEdit.class);
                 intent.putExtra("key",keyWord);
                 intent.putExtra("type",type);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 startActivity(intent);
             }
         });






    }


}
