package com.yusufmirza.etubilgisayartopluluk.adminpanel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yusufmirza.etubilgisayartopluluk.R;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminAuthLayoutBinding;

import java.util.ArrayList;

public class AdminAuth extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    FirebaseFirestore firestore;
    private GoogleSignInClient client;
    AdminAuthLayoutBinding adminAuthLayoutBinding;
    Context context;

    public AdminAuth(Context context){
        this.context = context;

    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        adminAuthLayoutBinding = AdminAuthLayoutBinding.inflate(inflater);

        return adminAuthLayoutBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();


        if(user!= null){

            DocumentReference adminList = firestore.collection("admins").document("admin_list");

            adminList.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    boolean control = false;
                    ArrayList<String> adminList = (ArrayList<String>) documentSnapshot.get("adminList");

                    if (adminList != null){

                        for (String admin : adminList){
                            if (admin.equals(user.getEmail())){
                                control = true;
                            }
                        }

                        if (control){
                            Intent intent = new Intent(context, AdminPanelEnterence.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(context, "Admin Paneline Erişmek İçin İzniniz Yok", Toast.LENGTH_LONG).show();
                        }


                    }



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "İnternetinizi kontrol edin", Toast.LENGTH_SHORT).show();
                }
            });






        }

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_app_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(context,options);
        adminAuthLayoutBinding.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = client.getSignInIntent();
                startActivityForResult(i,1234);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1234){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                firebaseAuth.signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    DocumentReference adminList = firestore.collection("admins").document("admin_list");

                                    adminList.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            boolean control = false;
                                            ArrayList<String> adminList = (ArrayList<String>) documentSnapshot.get("adminList");

                                            if (adminList != null){

                                                for (String admin : adminList){
                                                    if (admin.equals(user.getEmail())){
                                                        control = true;
                                                    }
                                                }

                                                if (control){
                                                    Intent intent = new Intent(context, AdminPanelEnterence.class);
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(context, "Admin Paneline Erişmek İçin İzniniz Yok", Toast.LENGTH_LONG).show();
                                                }


                                            }



                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, "İnternetinizi kontrol edin", Toast.LENGTH_SHORT).show();
                                        }
                                    });



                                }else {
                                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            } catch (ApiException e) {
                e.printStackTrace();
            }

        }

    }

}
