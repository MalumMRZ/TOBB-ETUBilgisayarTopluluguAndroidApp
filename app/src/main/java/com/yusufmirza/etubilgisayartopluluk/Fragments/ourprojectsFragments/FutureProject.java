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
import com.yusufmirza.etubilgisayartopluluk.Adapters.CommunityProjectsAdapter;
import com.yusufmirza.etubilgisayartopluluk.ArrayListSorter;
import com.yusufmirza.etubilgisayartopluluk.HelperClasses.CommunityActivities;
import com.yusufmirza.etubilgisayartopluluk.HelperClasses.CommunityProjects;
import com.yusufmirza.etubilgisayartopluluk.databinding.OurProjectsPlansBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class FutureProject extends Fragment {

        OurProjectsPlansBinding ourProjectsPlansBinding;

    String keyWord;

    ArrayList<CommunityProjects> communityProjects = new ArrayList<>();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    Context context;

    public FutureProject(Context context){

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

            keyWord ="TOBBBilgisayar";

        DocumentReference documentReference = firestore.collection(keyWord).document("projects");


        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                HashMap<String, Object> activitiesMap = (HashMap<String, Object>) documentSnapshot.get("project_list");

                if (activitiesMap != null) {

                    for (String activityKey : activitiesMap.keySet()) {

                        HashMap<String, Object> activityMap = (HashMap<String, Object>) documentSnapshot.get("project_list" + "." + activityKey);

                        if (activityMap != null) {


                            String place = (String) activityMap.get("place");
                            String time = (String) activityMap.get("time");
                            String name = (String) activityMap.get("name");
                            String date = (String) activityMap.get("date");
                            String imageUri = (String) activityMap.get("imageUri");
                            String activityLink = (String) activityMap.get("link");
                            String expired = (String) activityMap.get("expired");
                            String slogan = (String) activityMap.get("slogan");
                            String application = (String) activityMap.get("application");


                            if(expired!= null && expired.equals("no")){
                                CommunityProjects communityProject = new CommunityProjects(imageUri,activityLink,name,date,time,place,slogan,application,expired);
                                communityProjects.add(communityProject);
                            }



                        }

                    }

                    if (communityProjects.size() >0){

                        ArrayListSorter<CommunityProjects> arrayListSorter = new ArrayListSorter<>();
                        ArrayList<CommunityProjects> adapterClasses = arrayListSorter.sortedArray(communityProjects);

                        CommunityProjectsAdapter communityProjectsAdapter = new CommunityProjectsAdapter(adapterClasses);
                        ourProjectsPlansBinding.ourProjectsAndPlans.setAdapter(communityProjectsAdapter);
                        ourProjectsPlansBinding.ourProjectsAndPlans.setLayoutManager(new LinearLayoutManager(context));
                    } else {
                        Toast.makeText(context, "Topluluğumuzun şimdilik gelecek proje planı yok", Toast.LENGTH_LONG).show();
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
