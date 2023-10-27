package com.yusufmirza.etubilgisayartopluluk;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.AdminPanelEnterence;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminAraPanelBinding;

import java.util.ArrayList;

public class AdminAraPanel extends AppCompatActivity {

    AdminAraPanelBinding adminAraPanelBinding;
    boolean control;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminAraPanelBinding = AdminAraPanelBinding.inflate(getLayoutInflater());
        setContentView(adminAraPanelBinding.getRoot());

        control = false;

        DocumentReference adminList = firestore.collection("admins").document("admin_list");

        adminList.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                ArrayList<String> adminList = (ArrayList<String>) documentSnapshot.get("adminList");

                if (adminList != null){

                    for (String admin : adminList){
                        if (admin.equals(user.getEmail())){
                            control = true;
                        }
                    }
                }

                if(control){

                    Intent intent = new Intent(AdminAraPanel.this, AdminPanelEnterence.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(AdminAraPanel.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminAraPanel.this, "İnternetinizi kontrol edin", Toast.LENGTH_SHORT).show();
            }
        });






    }
}
