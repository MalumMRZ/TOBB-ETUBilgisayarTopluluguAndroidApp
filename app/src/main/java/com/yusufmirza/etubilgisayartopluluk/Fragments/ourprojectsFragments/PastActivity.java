package com.yusufmirza.etubilgisayartopluluk.Fragments.ourprojectsFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yusufmirza.etubilgisayartopluluk.Adapters.CommunityActivitiesAdapter;
import com.yusufmirza.etubilgisayartopluluk.ArrayListSorter;
import com.yusufmirza.etubilgisayartopluluk.HelperClasses.CommunityActivities;
import com.yusufmirza.etubilgisayartopluluk.databinding.OurProjectsPlansBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class PastActivity extends Fragment {

    OurProjectsPlansBinding ourProjectsPlansBinding;

    String keyWord,keyType;

    Context context;

    ArrayList<CommunityActivities> communityActivities = new ArrayList<>();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public PastActivity(Context context){

        this.context=context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ourProjectsPlansBinding = OurProjectsPlansBinding.inflate(getLayoutInflater());
        return ourProjectsPlansBinding.getRoot();

    }

    @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

        //ortak kısım ismi
        keyWord ="TOBBBilgisayar";
        keyType = "activity";

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
                            String expired = (String) activityMap.get("expired");


                            if(expired ==null ){
                                expired = "yes";
                                CommunityActivities communityActivity = new CommunityActivities(imageUri,activityLink,name,date,time,place,speaker,expired);
                                communityActivities.add(communityActivity);
                            } else if (expired.equals("yes")){
                                CommunityActivities communityActivity = new CommunityActivities(imageUri,activityLink,name,date,time,place,speaker,expired);
                                communityActivities.add(communityActivity);
                            }



                        }

                    }

                    if (communityActivities.size() >0){

                        ArrayListSorter<CommunityActivities> activitiesArrayListSorter = new ArrayListSorter<>();
                        ArrayList<CommunityActivities> adapterClasses = activitiesArrayListSorter.sortedArray(communityActivities);

                        CommunityActivitiesAdapter community_activitiesAdapter = new CommunityActivitiesAdapter(adapterClasses);
                        ourProjectsPlansBinding.ourProjectsAndPlans.setAdapter(community_activitiesAdapter);
                        ourProjectsPlansBinding.ourProjectsAndPlans.setLayoutManager(new LinearLayoutManager(context));
                    } else {
                     //   Toast.makeText(context, "Bu kulubümünüz henüz etkinlik yapmamış", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Veriler yüklenirken hata oluştu", Toast.LENGTH_SHORT).show();
            }
        });

        }
    }
