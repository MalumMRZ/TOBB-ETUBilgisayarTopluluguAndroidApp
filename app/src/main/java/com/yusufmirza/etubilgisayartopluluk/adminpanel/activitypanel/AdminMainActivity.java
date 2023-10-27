package com.yusufmirza.etubilgisayartopluluk.adminpanel.activitypanel;

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
import com.yusufmirza.etubilgisayartopluluk.adminpanel.memberspanel.AdminMembersEdit;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.memberspanel.AdminMembersHelper;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.memberspanel.AdminMembersRecyclerAdapter;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminMainActivitiesBinding;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminMainMembersBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminMainActivity extends AppCompatActivity {

   AdminMainActivitiesBinding adminMainActivitiesBinding;

   ArrayList<AdminActivityHelper> adminActivityHelpers = new ArrayList<>();
   String keyWord;
   String type;

   DocumentReference document;

   FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminMainActivitiesBinding = AdminMainActivitiesBinding.inflate(getLayoutInflater());

         setContentView(adminMainActivitiesBinding.getRoot());

         keyWord = getIntent().getStringExtra("key");
         type = getIntent().getStringExtra("type");

        switch (keyWord){

            case "BlokZincir":
                adminMainActivitiesBinding.toolbarDetailText.setText("Blok Zincir-Etkinlik");
                break;

            case "YapayZeka":
                adminMainActivitiesBinding.toolbarDetailText.setText("Yapay Zeka-Etkinlik");
                break;

            case "OyunGelistirme":
                adminMainActivitiesBinding.toolbarDetailText.setText("Oyun Geliştirme-Etkinlik");
                break;

            case "UygulamaGelistirme":
                adminMainActivitiesBinding.toolbarDetailText.setText("Uygulama Geliştirme-Etkinlik");
                break;

            case "SiberGuvenlik":
                adminMainActivitiesBinding.toolbarDetailText.setText("Siber Güvenlik-Etkinlik");
                break;

        }




        adminMainActivitiesBinding.imageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        adminMainActivitiesBinding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

         document = firestore.collection(keyWord).document(type);


        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                HashMap<String, Object> newsMap = (HashMap<String, Object>) documentSnapshot.get("activity_list");

                if (newsMap != null) {

                    for (String newsKey : newsMap.keySet()) {

                        HashMap<String, Object> newMap = (HashMap<String, Object>) documentSnapshot.get("activity_list" + "." + newsKey);

                        if (newMap != null) {

                            String speaker = (String) newMap.get("speaker");
                            String place = (String) newMap.get("place");
                            String time = (String) newMap.get("time");
                            String name = (String) newMap.get("name");
                            String date = (String) newMap.get("date");
                            String activityLink = (String) newMap.get("activityLink");

                            AdminActivityHelper adminActivityHelper = new AdminActivityHelper(name,activityLink,place,date,time,speaker,newsKey);
                            adminActivityHelpers.add(adminActivityHelper);


                        }

                    }

                    if (adminActivityHelpers.size() >0){
                        ArrayListSorter<AdminActivityHelper> newsClassArrayListSorter = new ArrayListSorter<>();
                        ArrayList<AdminActivityHelper> adapterClasses = newsClassArrayListSorter.sortedArray(adminActivityHelpers);

                        AdminActivityRecyclerAdapter adminMembersRecyclerAdapter = new AdminActivityRecyclerAdapter(adapterClasses, AdminMainActivity.this,keyWord,type);

                        adminMainActivitiesBinding.activitysAdminRecycle.setAdapter(adminMembersRecyclerAdapter);
                        adminMainActivitiesBinding.activitysAdminRecycle.setLayoutManager(new LinearLayoutManager(AdminMainActivity.this));
                    }
                }




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminMainActivity.this, "İnternetinizi kontrol edin", Toast.LENGTH_SHORT).show();
            }
        });





        adminMainActivitiesBinding.addActivitysButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(AdminMainActivity.this, AdminActivityEdit.class);
                 intent.putExtra("key",keyWord);
                 intent.putExtra("type",type);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 startActivity(intent);
             }
         });






    }


}
