package com.yusufmirza.etubilgisayartopluluk.ClubInterface.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yusufmirza.etubilgisayartopluluk.ArrayListSorter;
import com.yusufmirza.etubilgisayartopluluk.ClubInterface.adapters.ActivityClassRecAdapter;
import com.yusufmirza.etubilgisayartopluluk.ClubInterface.adapters.RecNewsViewAdapter;
import com.yusufmirza.etubilgisayartopluluk.ClubInterface.helperClasses.ActivityClass;
import com.yusufmirza.etubilgisayartopluluk.ClubInterface.helperClasses.NewsClass;
import com.yusufmirza.etubilgisayartopluluk.MainActivity;
import com.yusufmirza.etubilgisayartopluluk.R;
import com.yusufmirza.etubilgisayartopluluk.databinding.ActivityLayoutBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class EtkinlikActivity extends AppCompatActivity {


    ArrayList<ActivityClass> activityClasses = new ArrayList<>();

    String keyWord;


    ActivityLayoutBinding activityLayoutBinding;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLayoutBinding = ActivityLayoutBinding.inflate(getLayoutInflater());
       setContentView(activityLayoutBinding.getRoot());



        keyWord = getIntent().getStringExtra("collect");

        activityLayoutBinding.imageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EtkinlikActivity.this, MainActivityClub.class);
                intent.putExtra("col",keyWord);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        activityLayoutBinding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EtkinlikActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        DocumentReference documentReference = firestore.collection(keyWord).document("activitys");


        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                HashMap<String, Object> activitiesMap = (HashMap<String, Object>) documentSnapshot.get("activity_list");

                if (activitiesMap != null) {

                    for (String activityKey : activitiesMap.keySet()) {

                        HashMap<String, Object> activityMap = (HashMap<String, Object>) documentSnapshot.get("activity_list" + "." + activityKey);

                        if (activityMap != null) {

                            String speaker = (String) activityMap.get("speaker");
                            String place = (String) activityMap.get("place");
                            String time = (String) activityMap.get("time");
                            String name = (String) activityMap.get("name");
                            String date = (String) activityMap.get("date");
                            String imageUri = (String) activityMap.get("imageUri");
                            String activityLink = (String) activityMap.get("link");




                            ActivityClass activityClass = new ActivityClass(imageUri,activityLink,name,date,time,place,speaker);

                            activityClasses.add(activityClass);

                        }

                    }

                    if (activityClasses.size() >0){

                        ArrayListSorter<ActivityClass> newsClassArrayListSorter = new ArrayListSorter<>();
                        ArrayList<ActivityClass> adapterClasses = newsClassArrayListSorter.sortedArray(activityClasses);

                        ActivityClassRecAdapter activityClassRecAdapter = new ActivityClassRecAdapter(adapterClasses,keyWord);
                        activityLayoutBinding.activityRecyclerView.setAdapter(activityClassRecAdapter);
                        activityLayoutBinding.activityRecyclerView.setLayoutManager(new LinearLayoutManager(EtkinlikActivity.this));
                    } else {
                        Toast.makeText(EtkinlikActivity.this, "Bu kulubümünüz henüz etkinlik yapmamış", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EtkinlikActivity.this, "Veriler yüklenirken hata oluştu", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
