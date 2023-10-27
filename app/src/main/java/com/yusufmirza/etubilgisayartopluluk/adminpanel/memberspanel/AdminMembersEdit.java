package com.yusufmirza.etubilgisayartopluluk.adminpanel.memberspanel;

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
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminMembersEditBinding;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminNewsEditBinding;

import java.util.HashMap;
import java.util.UUID;

public class AdminMembersEdit extends AppCompatActivity {

    AdminMembersEditBinding adminMembersEditBinding;
    String keyWord,type,subMap,uniqueStr;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminMembersEditBinding =AdminMembersEditBinding.inflate(getLayoutInflater());
       setContentView(adminMembersEditBinding.getRoot());


        keyWord = getIntent().getStringExtra("key");
        type = getIntent().getStringExtra("type");
        subMap = getIntent().getStringExtra("subMap");

        switch (keyWord){

            case "BlokZincir":
                adminMembersEditBinding.toolbarDetailText.setText("Blok Zincir-Üyeler");
                break;

            case "YapayZeka":
                adminMembersEditBinding.toolbarDetailText.setText("Yapay Zeka-Üyeler");
                break;

            case "OyunGelistirme":
                adminMembersEditBinding.toolbarDetailText.setText("Oyun Geliştirme-Üyeler");
                break;

            case "UygulamaGelistirme":
                adminMembersEditBinding.toolbarDetailText.setText("Uygulama Geliştirme-Üyeler");
                break;

            case "SiberGuvenlik":
                adminMembersEditBinding.toolbarDetailText.setText("Siber Güvenlik-Üyeler");
                break;

        }


        adminMembersEditBinding.imageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        adminMembersEditBinding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMembersEdit.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        if (subMap!=null){

            DocumentReference documentReference = firestore.collection(keyWord).document(type);

            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    HashMap<String,Object> hashMap = (HashMap<String, Object>) documentSnapshot.get("member_list."+subMap);

                     if (hashMap !=null){

                         adminMembersEditBinding.adminMembersLink.setText(hashMap.get("link").toString().trim());
                         adminMembersEditBinding.adminMembersName.setText(hashMap.get("name").toString().trim());
                         adminMembersEditBinding.adminMembersInfo.setText(hashMap.get("info").toString().trim());

                     }




                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminMembersEdit.this, "Veri çekilemedi", Toast.LENGTH_SHORT).show();
                }
            });




        }


        adminMembersEditBinding.addNewMembers.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {



               String name = adminMembersEditBinding.adminMembersName.getText().toString().trim();
               String link = adminMembersEditBinding.adminMembersLink.getText().toString().trim();
               String info = adminMembersEditBinding.adminMembersInfo.getText().toString().trim();

               DocumentReference documentReference = firestore.collection(keyWord).document(type);



               if (subMap!=null){
                   uniqueStr = subMap;
               } else {
                   UUID unique = UUID.randomUUID();
                   uniqueStr = unique.toString();
               }



               if (!name.equals("") && !link.equals("") && !info.equals("")) {

                   HashMap<String, Object> myHash = new HashMap<>();
                   myHash.put("name",name);
                   myHash.put("link",link);
                   myHash.put("info",info);
                   myHash.put("imageUri","YOK");



                   documentReference.update("member_list."+uniqueStr,myHash).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {
                           adminMembersEditBinding.addNewMembers.setEnabled(false);
                           Toast.makeText(AdminMembersEdit.this, "Başarılı bir şekilde eklendi", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(AdminMembersEdit.this,AdminMainMembers.class );
                           intent.putExtra("key",keyWord);
                           intent.putExtra("type",type);
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           startActivity(intent);

                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           adminMembersEditBinding.addNewMembers.setEnabled(false);
                           Toast.makeText(AdminMembersEdit.this, "Ekleme başarısız oldu", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(AdminMembersEdit.this,AdminMainMembers.class);
                           intent.putExtra("key",keyWord);
                           intent.putExtra("type",type);
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           startActivity(intent);
                       }
                   });


               } else {

                   Toast.makeText(AdminMembersEdit.this, "Boşlukları doldurun", Toast.LENGTH_SHORT).show();

               }
           }
       });



    }
}
