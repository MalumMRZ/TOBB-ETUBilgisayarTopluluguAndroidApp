package com.yusufmirza.etubilgisayartopluluk.adminpanel.memberspanel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.newspanel.AdminNewsEdit;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.newspanel.AdminNewsHelper;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminMembersRowBinding;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminNewsRowBinding;

import java.util.ArrayList;

public class AdminMembersRecyclerAdapter extends RecyclerView.Adapter<AdminMembersRecyclerAdapter.AdminNewsViewHolder> {

    ArrayList<AdminMembersHelper> adminMembersHelpers;

    AdminMembersRowBinding adminMembersRowBinding;

    AlertDialog.Builder alertDelete;
    Context context;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String keyWord,type;


    public AdminMembersRecyclerAdapter(ArrayList<AdminMembersHelper> adminMembersHelpers, Context context, String keyWord, String type){
        this.adminMembersHelpers = adminMembersHelpers;
        this.context = context;
        this.keyWord = keyWord;
        this.type = type;


    }


    @NonNull
    @Override
    public AdminNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        adminMembersRowBinding = AdminMembersRowBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new AdminNewsViewHolder(adminMembersRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminNewsViewHolder holder, int position) {

        AdminMembersHelper adminMemberHelper = adminMembersHelpers.get(holder.getAdapterPosition());
        holder.adminMembersRowBinding.membersNameText.setText(adminMemberHelper.getName());


        //Editleme butonu ile yeni bir sınıf açılmalı
        holder.adminMembersRowBinding.membersEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, AdminMembersEdit.class);
                intent.putExtra("key",keyWord);
                intent.putExtra("type",type);
                intent.putExtra("subMap",adminMemberHelper.getSubMap());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);

            }
        });

        //Silme işlemi
        holder.adminMembersRowBinding.membersDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDeleteAlertDialog(adminMemberHelper.getSubMap(),adminMemberHelper, holder.getAdapterPosition());
                alertDelete.create();
                alertDelete.show();





            }
        });



    }

    @Override
    public int getItemCount() {
        return adminMembersHelpers.size();
    }

    public static class AdminNewsViewHolder extends RecyclerView.ViewHolder{


        AdminMembersRowBinding adminMembersRowBinding;
        public AdminNewsViewHolder(@NonNull AdminMembersRowBinding adminMembersRowBinding) {
            super(adminMembersRowBinding.getRoot());
            this.adminMembersRowBinding = adminMembersRowBinding;
        }
    }


    public void setDeleteAlertDialog(String subMap,AdminMembersHelper adminMembersHelper, int position){

        alertDelete = new AlertDialog.Builder(context);
        TextView textView = new TextView(context);
        textView.setText("Bu haberi silmek istediğinize emin misiniz");


        alertDelete.setView(textView);


        alertDelete.setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
             //Silme işlemi yap

                DocumentReference documentReference = firestore.collection(keyWord).document(type);

                documentReference.update("member_list."+subMap, FieldValue.delete()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Silme işlemi başarılı", Toast.LENGTH_SHORT).show();
                        adminMembersHelpers.remove(adminMembersHelper);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, adminMembersHelpers.size());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Silme işlemi başarısız", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        }).setNegativeButton("İptal Et", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Silme işlemi iptal edildi.", Toast.LENGTH_SHORT).show();
            }
        });



    }


}





