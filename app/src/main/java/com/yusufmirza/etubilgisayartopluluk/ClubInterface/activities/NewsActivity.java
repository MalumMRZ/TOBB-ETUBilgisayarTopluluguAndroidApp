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
import com.yusufmirza.etubilgisayartopluluk.ClubInterface.adapters.RecNewsViewAdapter;
import com.yusufmirza.etubilgisayartopluluk.ClubInterface.helperClasses.NewsClass;
import com.yusufmirza.etubilgisayartopluluk.HelperClasses.Member;
import com.yusufmirza.etubilgisayartopluluk.MainActivity;
import com.yusufmirza.etubilgisayartopluluk.R;
import com.yusufmirza.etubilgisayartopluluk.databinding.NewsLayoutBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class NewsActivity extends AppCompatActivity {


    ArrayList<NewsClass> newsClasses = new ArrayList<>();
    NewsLayoutBinding newsLayoutBinding;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    String keyWord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsLayoutBinding = NewsLayoutBinding.inflate(getLayoutInflater());
        setContentView(newsLayoutBinding.getRoot());

        keyWord = getIntent().getStringExtra("collect");



        newsLayoutBinding.imageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewsActivity.this, MainActivityClub.class);
                intent.putExtra("col",keyWord);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        newsLayoutBinding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewsActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


         DocumentReference documentReference = firestore.collection(keyWord).document("news");


         documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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

                             NewsClass newsClass = new NewsClass(link, name, imageUri);
                             newsClasses.add(newsClass);


                         }

                     }

                     if (newsClasses.size() >0){
                         RecNewsViewAdapter recNewsViewAdapter = new RecNewsViewAdapter(newsClasses);

                         newsLayoutBinding.newsRecyclerView.setAdapter(recNewsViewAdapter);
                         newsLayoutBinding.newsRecyclerView.setLayoutManager(new LinearLayoutManager(NewsActivity.this));
                     } else {
                         Toast.makeText(NewsActivity.this, "Bu kulubümünüz henüz yazı yayınlamamış", Toast.LENGTH_SHORT).show();
                     }
                 }

             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(NewsActivity.this, "Veriler yüklenirken hata oluştu", Toast.LENGTH_SHORT).show();
             }
         });












    }
}
