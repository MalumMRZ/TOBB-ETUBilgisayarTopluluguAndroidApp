package com.yusufmirza.etubilgisayartopluluk.adminpanel.videospanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yusufmirza.etubilgisayartopluluk.MainActivity;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.AdminClubActivities;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.newspanel.AdminMainNews;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.newspanel.AdminNewsEdit;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminNewsEditBinding;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminVideosEditBinding;

import java.util.HashMap;
import java.util.UUID;

public class AdminVideosEdit extends AppCompatActivity {

    AdminVideosEditBinding adminVideosEditBinding;
    String keyWord,type,subMap,uniqueStr;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminVideosEditBinding =AdminVideosEditBinding.inflate(getLayoutInflater());
       setContentView(adminVideosEditBinding.getRoot());


        keyWord = getIntent().getStringExtra("key");
        type = getIntent().getStringExtra("type");
        subMap = getIntent().getStringExtra("subMap");

        switch (keyWord){

            case "BlokZincir":
                adminVideosEditBinding.toolbarDetailText.setText("Blok Zincir-Video");
                break;

            case "YapayZeka":
                adminVideosEditBinding.toolbarDetailText.setText("Yapay Zeka-Video");
                break;

            case "OyunGelistirme":
                adminVideosEditBinding.toolbarDetailText.setText("Oyun Geliştirme-Video");
                break;

            case "UygulamaGelistirme":
                adminVideosEditBinding.toolbarDetailText.setText("Uygulama Geliştirme-Video");
                break;

            case "SiberGuvenlik":
                adminVideosEditBinding.toolbarDetailText.setText("Siber Güvenlik-Video");
                break;

        }

        adminVideosEditBinding.imageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        adminVideosEditBinding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminVideosEdit.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        if (subMap!=null){

            DocumentReference documentReference = firestore.collection(keyWord).document(type);

            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    HashMap<String,Object> hashMap = (HashMap<String, Object>) documentSnapshot.get("video_list."+subMap);

                     if (hashMap !=null){

                         adminVideosEditBinding.adminVideosLink.setText(hashMap.get("link").toString().trim());
                         adminVideosEditBinding.adminVideosName.setText(hashMap.get("name").toString().trim());
                         adminVideosEditBinding.adminVideosAccountType.setText(hashMap.get("accountType").toString().trim());
                     }




                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminVideosEdit.this, "Veri çekilemedi", Toast.LENGTH_SHORT).show();
                }
            });




        }


        adminVideosEditBinding.addNewVideos.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {



               String name = adminVideosEditBinding.adminVideosName.getText().toString().trim();
               String link = adminVideosEditBinding.adminVideosLink.getText().toString().trim();
               String accountType = adminVideosEditBinding.adminVideosAccountType.getText().toString().trim();

               DocumentReference documentReference = firestore.collection(keyWord).document(type);



               if (subMap!=null){
                   uniqueStr = subMap;
               } else {
                   UUID unique = UUID.randomUUID();
                   uniqueStr = unique.toString();
               }



               if (!name.equals("") && !link.equals("") && !accountType.equals("")) {

                   HashMap<String, Object> myHash = new HashMap<>();
                   myHash.put("name",name);
                   myHash.put("link",link);
                   myHash.put("accountType",accountType);
                   myHash.put("imageUri","YOK");



                   documentReference.update("video_list."+uniqueStr,myHash).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {
                           adminVideosEditBinding.addNewVideos.setEnabled(false);
                           Toast.makeText(AdminVideosEdit.this, "Başarılı bir şekilde eklendi", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(AdminVideosEdit.this, AdminMainVideos.class);
                           intent.putExtra("key",keyWord);
                           intent.putExtra("type",type);
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           startActivity(intent);

                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           adminVideosEditBinding.addNewVideos.setEnabled(false);
                           Toast.makeText(AdminVideosEdit.this, "Ekleme başarısız oldu", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(AdminVideosEdit.this,AdminMainVideos.class);
                           intent.putExtra("key",keyWord);
                           intent.putExtra("type",type);
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           startActivity(intent);
                       }
                   });


               } else {

                   Toast.makeText(AdminVideosEdit.this, "Boşlukları doldurun", Toast.LENGTH_SHORT).show();

               }
           }
       });



    }
}
