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
import com.yusufmirza.etubilgisayartopluluk.ClubInterface.adapters.MemberAdapterRecyclerView;
import com.yusufmirza.etubilgisayartopluluk.ClubInterface.adapters.VideosRecyclerAdapter;
import com.yusufmirza.etubilgisayartopluluk.ClubInterface.helperClasses.VideosClass;
import com.yusufmirza.etubilgisayartopluluk.HelperClasses.Member;
import com.yusufmirza.etubilgisayartopluluk.MainActivity;
import com.yusufmirza.etubilgisayartopluluk.R;
import com.yusufmirza.etubilgisayartopluluk.databinding.VideoLayoutBinding;

import java.util.ArrayList;
import java.util.HashMap;


public class VideosActivity extends AppCompatActivity {

    VideoLayoutBinding videoLayoutBinding;

    ArrayList<VideosClass> videos= new ArrayList<>();

    String keyWord;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoLayoutBinding = VideoLayoutBinding.inflate(getLayoutInflater());
        setContentView(videoLayoutBinding.getRoot());

        keyWord = getIntent().getStringExtra("collect");

        videoLayoutBinding.imageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VideosActivity.this, MainActivityClub.class);
                intent.putExtra("col",keyWord);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        videoLayoutBinding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VideosActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        DocumentReference memberDocument = firestore.collection(keyWord).document("videos");

        memberDocument.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                HashMap<String, Object> videosMap = (HashMap<String, Object>) documentSnapshot.get("video_list");

                if (videosMap!=null){

                    for (String videoKey : videosMap.keySet()){

                        HashMap<String,Object> videoMap = (HashMap<String, Object>) documentSnapshot.get("video_list"+"."+videoKey);

                        if (videoMap !=null){

                            String name = (String) videoMap.get("name");
                            String link = (String) videoMap.get("link");
                            String accountType = (String) videoMap.get("accountType");
                            String imageUri = (String) videoMap.get("imageUri");

                            VideosClass video = new VideosClass(name,imageUri,accountType,link);
                            videos.add(video);

                        }

                    }


                    if (videos.size()>0){
                        videoLayoutBinding.videoRecyclerView.setAdapter(new VideosRecyclerAdapter(videos));
                        videoLayoutBinding.videoRecyclerView.setLayoutManager(new LinearLayoutManager(VideosActivity.this));
                    } else {
                        Toast.makeText(VideosActivity.this, "Bu topluluk henüz video yayınlamamış", Toast.LENGTH_SHORT).show();
                    }




                }




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VideosActivity.this, "Veriler Alınırken Hata Oluştu", Toast.LENGTH_SHORT).show();
            }
        });







    }
}
