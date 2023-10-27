package com.yusufmirza.etubilgisayartopluluk.adminpanel.activitypanel;

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
import com.yusufmirza.etubilgisayartopluluk.adminpanel.memberspanel.AdminMainMembers;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminActivitiesEditBinding;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminMembersEditBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class AdminActivityEdit extends AppCompatActivity {

    AdminActivitiesEditBinding adminActivitiesEditBinding;
    String keyWord,type,subMap,uniqueStr;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminActivitiesEditBinding =AdminActivitiesEditBinding.inflate(getLayoutInflater());
       setContentView(adminActivitiesEditBinding.getRoot());


        keyWord = getIntent().getStringExtra("key");
        type = getIntent().getStringExtra("type");
        subMap = getIntent().getStringExtra("subMap");

        switch (keyWord){

            case "BlokZincir":
                adminActivitiesEditBinding.toolbarDetailText.setText("Blok Zincir-Etkinlik");
                break;

            case "YapayZeka":
                adminActivitiesEditBinding.toolbarDetailText.setText("Yapay Zeka-Etkinlik");
                break;

            case "OyunGelistirme":
                adminActivitiesEditBinding.toolbarDetailText.setText("Oyun Geliştirme-Etkinlik");
                break;

            case "UygulamaGelistirme":
                adminActivitiesEditBinding.toolbarDetailText.setText("Uygulama Geliştirme-Etkinlik");
                break;

            case "SiberGuvenlik":
                adminActivitiesEditBinding.toolbarDetailText.setText("Siber Güvenlik-Etkinlik");
                break;

        }

        adminActivitiesEditBinding.imageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        adminActivitiesEditBinding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivityEdit.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        if (subMap!=null){

            DocumentReference documentReference = firestore.collection(keyWord).document(type);

            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    HashMap<String,Object> hashMap = (HashMap<String, Object>) documentSnapshot.get("activity_list."+subMap);

                     if (hashMap !=null){

                         adminActivitiesEditBinding.adminActivityLink.setText(hashMap.get("link").toString().trim());
                         adminActivitiesEditBinding.adminActivitiesName.setText(hashMap.get("name").toString().trim());
                         adminActivitiesEditBinding.adminActivityDate.setText(hashMap.get("date").toString().trim());
                         adminActivitiesEditBinding.adminActivityPlace.setText(hashMap.get("place").toString().trim());
                         adminActivitiesEditBinding.adminActivitySpeaker.setText(hashMap.get("speaker").toString().trim());
                         adminActivitiesEditBinding.adminActivityTime.setText(hashMap.get("time").toString().trim());
                     }




                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminActivityEdit.this, "Veri çekilemedi", Toast.LENGTH_SHORT).show();
                }
            });




        }




        adminActivitiesEditBinding.addNewMembers.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {



               String name = adminActivitiesEditBinding.adminActivitiesName.getText().toString().trim();
               String link = adminActivitiesEditBinding.adminActivityLink.getText().toString().trim();
               String date = adminActivitiesEditBinding.adminActivityDate.getText().toString().trim();
               String place = adminActivitiesEditBinding.adminActivityPlace.getText().toString().trim();
               String speaker = adminActivitiesEditBinding.adminActivitySpeaker.getText().toString().trim();
               String time = adminActivitiesEditBinding.adminActivityTime.getText().toString().trim();

               DocumentReference documentReference = firestore.collection(keyWord).document(type);



               if (subMap!=null){
                   uniqueStr = subMap;
               } else {
                   UUID unique = UUID.randomUUID();
                   uniqueStr = unique.toString();
               }



               if (!name.equals("") && !link.equals("") && !date.equals("") && !place.equals("") && !speaker.equals("") && !time.equals("") ) {

                   HashMap<String, Object> myHash = new HashMap<>();
                   ArrayList<String> imageArrayList = new ArrayList<>();
                   myHash.put("name",name);
                   myHash.put("link",link);
                   myHash.put("date",date);
                   myHash.put("place",place);
                   myHash.put("speaker",speaker);
                   myHash.put("time",time);
                   myHash.put("imageList",imageArrayList);
                   myHash.put("imageUri","YOK");




                   documentReference.update("activity_list."+uniqueStr,myHash).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {
                           adminActivitiesEditBinding.addNewMembers.setEnabled(false);
                           Toast.makeText(AdminActivityEdit.this, "Başarılı bir şekilde eklendi", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(AdminActivityEdit.this, AdminMainActivity.class );
                           intent.putExtra("key",keyWord);
                           intent.putExtra("type",type);
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           startActivity(intent);

                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           adminActivitiesEditBinding.addNewMembers.setEnabled(false);
                           Toast.makeText(AdminActivityEdit.this, "Ekleme başarısız oldu", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(AdminActivityEdit.this,AdminMainActivity.class);
                           intent.putExtra("key",keyWord);
                           intent.putExtra("type",type);
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           startActivity(intent);
                       }
                   });


               } else {

                   Toast.makeText(AdminActivityEdit.this, "Boşlukları doldurunuz !", Toast.LENGTH_SHORT).show();

               }
           }
       });



    }
}
