package com.yusufmirza.etubilgisayartopluluk.adminpanel.memberspanel;

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
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminMainMembersBinding;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminMainNewsBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminMainMembers extends AppCompatActivity {

   AdminMainMembersBinding adminMainMembersBinding;

   ArrayList<AdminMembersHelper> adminMembersHelpers = new ArrayList<>();
   String keyWord;
   String type;

   DocumentReference document;

   FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminMainMembersBinding = AdminMainMembersBinding.inflate(getLayoutInflater());

         setContentView(adminMainMembersBinding.getRoot());

         keyWord = getIntent().getStringExtra("key");
         type = getIntent().getStringExtra("type");

        switch (keyWord){

            case "BlokZincir":
                adminMainMembersBinding.toolbarDetailText.setText("Blok Zincir-Üyeler");
                break;

            case "YapayZeka":
                adminMainMembersBinding.toolbarDetailText.setText("Yapay Zeka-Üyeler");
                break;

            case "OyunGelistirme":
                adminMainMembersBinding.toolbarDetailText.setText("Oyun Geliştirme-Üyeler");
                break;

            case "UygulamaGelistirme":
                adminMainMembersBinding.toolbarDetailText.setText("Uygulama Geliştirme-Üyeler");
                break;

            case "SiberGuvenlik":
                adminMainMembersBinding.toolbarDetailText.setText("Siber Güvenlik-Üyeler");
                break;

        }

        adminMainMembersBinding.imageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        adminMainMembersBinding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainMembers.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

         document = firestore.collection(keyWord).document(type);


        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                HashMap<String, Object> newsMap = (HashMap<String, Object>) documentSnapshot.get("member_list");

                if (newsMap != null) {

                    for (String newsKey : newsMap.keySet()) {

                        HashMap<String, Object> newMap = (HashMap<String, Object>) documentSnapshot.get("member_list" + "." + newsKey);

                        if (newMap != null) {

                            String name = (String) newMap.get("name");
                            String link = (String) newMap.get("link");
                            String imageUri = (String) newMap.get("imageUri");
                            String info = (String) newMap.get("info");

                            AdminMembersHelper adminMembersHelper = new AdminMembersHelper(name, link, imageUri,newsKey,info);
                            adminMembersHelpers.add(adminMembersHelper);


                        }

                    }

                    if (adminMembersHelpers.size() >0){
                        ArrayListSorter<AdminMembersHelper> newsClassArrayListSorter = new ArrayListSorter<>();
                        ArrayList<AdminMembersHelper> adapterClasses = newsClassArrayListSorter.sortedArray(adminMembersHelpers);

                        AdminMembersRecyclerAdapter adminMembersRecyclerAdapter = new AdminMembersRecyclerAdapter(adapterClasses, AdminMainMembers.this,keyWord,type);

                        adminMainMembersBinding.membersAdminRecycle.setAdapter(adminMembersRecyclerAdapter);
                        adminMainMembersBinding.membersAdminRecycle.setLayoutManager(new LinearLayoutManager(AdminMainMembers.this));
                    }
                }




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminMainMembers.this, "İnternetinizi kontrol edin", Toast.LENGTH_SHORT).show();
            }
        });





        adminMainMembersBinding.addMembersButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(AdminMainMembers.this, AdminMembersEdit.class);
                 intent.putExtra("key",keyWord);
                 intent.putExtra("type",type);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 startActivity(intent);
             }
         });






    }


}
