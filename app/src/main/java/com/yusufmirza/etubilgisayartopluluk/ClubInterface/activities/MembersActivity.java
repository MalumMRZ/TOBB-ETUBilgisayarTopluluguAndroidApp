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
import com.yusufmirza.etubilgisayartopluluk.Adapters.MemberAdapter;
import com.yusufmirza.etubilgisayartopluluk.ArrayListSorter;
import com.yusufmirza.etubilgisayartopluluk.ClubInterface.adapters.MemberAdapterRecyclerView;
import com.yusufmirza.etubilgisayartopluluk.ClubInterface.helperClasses.NewsClass;
import com.yusufmirza.etubilgisayartopluluk.HelperClasses.Member;
import com.yusufmirza.etubilgisayartopluluk.MainActivity;
import com.yusufmirza.etubilgisayartopluluk.R;
import com.yusufmirza.etubilgisayartopluluk.databinding.MemberLayoutBinding;

import java.util.ArrayList;
import java.util.HashMap;


public class MembersActivity extends AppCompatActivity {


    ArrayList<Member> memberList = new ArrayList<>();

   MemberLayoutBinding memberLayoutBinding;

   FirebaseFirestore firestore = FirebaseFirestore.getInstance();

   String keyWord;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memberLayoutBinding = MemberLayoutBinding.inflate(getLayoutInflater());
        setContentView(memberLayoutBinding.getRoot());

        keyWord = getIntent().getStringExtra("collect");

        memberLayoutBinding.imageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MembersActivity.this, MainActivityClub.class);
                intent.putExtra("col",keyWord);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        memberLayoutBinding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MembersActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        DocumentReference memberDocument = firestore.collection(keyWord).document("members");

        memberDocument.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                HashMap<String, Object> membersMap = (HashMap<String, Object>) documentSnapshot.get("member_list");

                if (membersMap!=null){

                    for (String memberKey : membersMap.keySet()){

                        HashMap<String,Object> memberMap = (HashMap<String, Object>) documentSnapshot.get("member_list"+"."+memberKey);

                        if (memberMap !=null){

                            String name = (String) memberMap.get("name");
                            String link = (String) memberMap.get("link");
                            String info = (String) memberMap.get("info");
                            String imageUri = (String) memberMap.get("imageUri");

                            Member member = new Member(name,info,imageUri,link);
                            memberList.add(member);

                        }

                    }

                    if (memberList.size() > 0){
                        ArrayListSorter<Member> newsClassArrayListSorter = new ArrayListSorter<>();
                        ArrayList<Member> adapterClasses = newsClassArrayListSorter.sortedArray(memberList);

                        memberLayoutBinding.memberRecyclerView.setAdapter(new MemberAdapterRecyclerView(adapterClasses));
                        memberLayoutBinding.memberRecyclerView.setLayoutManager(new LinearLayoutManager(MembersActivity.this));
                    } else {
                        Toast.makeText(MembersActivity.this, "Bu kulubümünüz henüz üyelerini girmemiş", Toast.LENGTH_SHORT).show();
                    }





                }




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MembersActivity.this, "Veriler Alınırken Hata Oluştu", Toast.LENGTH_SHORT).show();
            }
        });








    }
}
