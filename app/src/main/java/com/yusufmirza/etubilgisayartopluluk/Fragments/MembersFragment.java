package com.yusufmirza.etubilgisayartopluluk.Fragments;

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
import com.yusufmirza.etubilgisayartopluluk.Adapters.MemberAdapter;
import com.yusufmirza.etubilgisayartopluluk.HelperClasses.Member;
import com.yusufmirza.etubilgisayartopluluk.databinding.MembersFragmentBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MembersFragment extends Fragment {

   Context context;
   ArrayList<Member> memberArrayList = new ArrayList<>();

    public MembersFragment(Context context){
        this.context = context;
    }

MembersFragmentBinding membersFragmentBinding;
FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        membersFragmentBinding = MembersFragmentBinding.inflate(inflater);
        View view = membersFragmentBinding.getRoot();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DocumentReference memberDocument = firestore.collection("members").document("member_list");

        memberDocument.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                HashMap<String, Object> membersMap = (HashMap<String, Object>) documentSnapshot.get("memberss");

                if (membersMap!=null){

                    for (String memberKey : membersMap.keySet()){

                        HashMap<String,Object> memberMap = (HashMap<String, Object>) documentSnapshot.get("memberss"+"."+memberKey);

                        if (memberMap !=null){

                            String name = (String) memberMap.get("name");
                            String link = (String) memberMap.get("link");
                            String info = (String) memberMap.get("info");
                            String imageUri = (String) memberMap.get("imageUri");

                            Member member = new Member(name,info,imageUri,link);
                            memberArrayList.add(member);

                        }

                    }


                    membersFragmentBinding.memberRecyclerView.setAdapter(new MemberAdapter(memberArrayList,context));
                    membersFragmentBinding.memberRecyclerView.setLayoutManager(new LinearLayoutManager(context));


                }




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Veriler Alınırken Hata Oluştu", Toast.LENGTH_SHORT).show();
            }
        });




    }
}
