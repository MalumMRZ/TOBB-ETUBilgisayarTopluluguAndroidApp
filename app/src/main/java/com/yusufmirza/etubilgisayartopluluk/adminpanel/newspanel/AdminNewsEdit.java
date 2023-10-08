package com.yusufmirza.etubilgisayartopluluk.adminpanel.newspanel;

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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminNewsEditBinding;

import org.checkerframework.common.aliasing.qual.Unique;

import java.util.HashMap;
import java.util.UUID;

public class AdminNewsEdit extends AppCompatActivity {

    AdminNewsEditBinding adminNewsEditBinding;
    String keyWord,type,subMap,uniqueStr;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       adminNewsEditBinding =AdminNewsEditBinding.inflate(getLayoutInflater());
       setContentView(adminNewsEditBinding.getRoot());


        keyWord = getIntent().getStringExtra("key");
        type = getIntent().getStringExtra("type");
        subMap = getIntent().getStringExtra("subMap");



        if (subMap!=null){

            DocumentReference documentReference = firestore.collection(keyWord).document(type);

            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    HashMap<String,Object> hashMap = (HashMap<String, Object>) documentSnapshot.get("new_list."+subMap);

                     if (hashMap !=null){

                         adminNewsEditBinding.adminNewsLink.setText(hashMap.get("link").toString().trim());
                         adminNewsEditBinding.adminNewsName.setText(hashMap.get("name").toString().trim());

                     }




                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminNewsEdit.this, "Veri çekilemedi", Toast.LENGTH_SHORT).show();
                }
            });




        }


       adminNewsEditBinding.addNewNews.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               adminNewsEditBinding.addNewNews.setEnabled(false);

               String name = adminNewsEditBinding.adminNewsName.getText().toString().trim();
               String link = adminNewsEditBinding.adminNewsLink.getText().toString().trim();

               DocumentReference documentReference = firestore.collection(keyWord).document(type);



               if (subMap!=null){
                   uniqueStr = subMap;
               } else {
                   UUID unique = UUID.randomUUID();
                   uniqueStr = unique.toString();
               }



               if (!name.equals("") && !name.equals("")) {

                   HashMap<String, Object> myHash = new HashMap<>();
                   myHash.put("name",name);
                   myHash.put("link",link);
                   myHash.put("imageUri","YOK");



                   documentReference.update("new_list."+uniqueStr,myHash).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {
                           Toast.makeText(AdminNewsEdit.this, "Başarılı bir şekilde eklendi", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(AdminNewsEdit.this,AdminMainNews.class);
                           intent.putExtra("key",keyWord);
                           intent.putExtra("type",type);
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           startActivity(intent);

                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(AdminNewsEdit.this, "Ekleme başarısız oldu", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(AdminNewsEdit.this,AdminMainNews.class);
                           intent.putExtra("key",keyWord);
                           intent.putExtra("type",type);
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           startActivity(intent);
                       }
                   });


               }
           }
       });



    }
}
