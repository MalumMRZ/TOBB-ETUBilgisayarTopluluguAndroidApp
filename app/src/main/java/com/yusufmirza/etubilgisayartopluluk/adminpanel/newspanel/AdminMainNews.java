package com.yusufmirza.etubilgisayartopluluk.adminpanel.newspanel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.yusufmirza.etubilgisayartopluluk.ClubInterface.activities.NewsActivity;
import com.yusufmirza.etubilgisayartopluluk.ClubInterface.adapters.RecNewsViewAdapter;
import com.yusufmirza.etubilgisayartopluluk.ClubInterface.helperClasses.NewsClass;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminClubActivitiesBinding;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminMainNewsBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminMainNews extends AppCompatActivity {

   AdminMainNewsBinding adminMainNewsBinding;

   ArrayList<AdminNewsHelper> adminNewsHelpers = new ArrayList<>();
   String keyWord;
   String type;

   DocumentReference document;

   FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         adminMainNewsBinding = AdminMainNewsBinding.inflate(getLayoutInflater());

         setContentView(adminMainNewsBinding.getRoot());

         keyWord = getIntent().getStringExtra("key");
         type = getIntent().getStringExtra("type");

         document = firestore.collection(keyWord).document(type);


        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                HashMap<String, Object> newsMap = (HashMap<String, Object>) documentSnapshot.get("new_list");

                if (newsMap != null) {

                    for (String newsKey : newsMap.keySet()) {

                        HashMap<String, Object> newMap = (HashMap<String, Object>) documentSnapshot.get("new_list" + "." + newsKey);

                        if (newMap != null) {

                            String name = (String) newMap.get("name");
                            String link = (String) newMap.get("link");
                            String imageUri = (String) newMap.get("imageUri");

                            AdminNewsHelper adminNewsHelper = new AdminNewsHelper(name, link, imageUri,newsKey);
                            adminNewsHelpers.add(adminNewsHelper);


                        }

                    }

                    if (adminNewsHelpers.size() >0){
                        AdminNewsRecyclerAdapter adminNewsRecyclerAdapter = new AdminNewsRecyclerAdapter(adminNewsHelpers,AdminMainNews.this,keyWord,type);

                        adminMainNewsBinding.newsAdminRecycle.setAdapter(adminNewsRecyclerAdapter);
                        adminMainNewsBinding.newsAdminRecycle.setLayoutManager(new LinearLayoutManager(AdminMainNews.this));
                    }
                }




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminMainNews.this, "İnternetinizi kontrol edin", Toast.LENGTH_SHORT).show();
            }
        });





         adminMainNewsBinding.addNewsButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(AdminMainNews.this,AdminNewsEdit.class);
                 intent.putExtra("key",keyWord);
                 intent.putExtra("type",type);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 startActivity(intent);
             }
         });






    }


}
