package com.yusufmirza.etubilgisayartopluluk.adminpanel.newspanel;

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
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminNewsRowBinding;

import java.util.ArrayList;

public class AdminNewsRecyclerAdapter extends RecyclerView.Adapter<AdminNewsRecyclerAdapter.AdminNewsViewHolder> {

    ArrayList<AdminNewsHelper> adminNewsHelper;
    AdminNewsRowBinding adminNewsRowBinding;

    AlertDialog.Builder alertDelete;
    Context context;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String keyWord,type;


    public AdminNewsRecyclerAdapter(ArrayList<AdminNewsHelper> adminNewsHelper,Context context,String keyWord,String type){
        this.adminNewsHelper = adminNewsHelper;
        this.context = context;
        this.keyWord = keyWord;
        this.type = type;


    }


    @NonNull
    @Override
    public AdminNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        adminNewsRowBinding = AdminNewsRowBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new AdminNewsViewHolder(adminNewsRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminNewsViewHolder holder, int position) {
        AdminNewsHelper adminNewsClass = adminNewsHelper.get(position);
        holder.adminNewsRowBinding.newsNameText.setText(adminNewsClass.getName());


        //Editleme butonu ile yeni bir sınıf açılmalı
        holder.adminNewsRowBinding.newsEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,AdminNewsEdit.class);
                intent.putExtra("key",keyWord);
                intent.putExtra("type",type);
                intent.putExtra("subMap",adminNewsClass.getSubMap());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);

            }
        });

        //Silme işlemi
        holder.adminNewsRowBinding.newsDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDeleteAlertDialog(adminNewsClass.getSubMap(),adminNewsClass,position);
                alertDelete.create();
                alertDelete.show();





            }
        });



    }

    @Override
    public int getItemCount() {
        return adminNewsHelper.size();
    }

    public static class AdminNewsViewHolder extends RecyclerView.ViewHolder{


        AdminNewsRowBinding adminNewsRowBinding;
        public AdminNewsViewHolder(@NonNull AdminNewsRowBinding adminNewsRowBinding) {
            super(adminNewsRowBinding.getRoot());
            this.adminNewsRowBinding = adminNewsRowBinding;
        }
    }


    public void setDeleteAlertDialog(String subMap,AdminNewsHelper adminNews, int position){

        alertDelete = new AlertDialog.Builder(context);
        TextView textView = new TextView(context);
        textView.setText("Bu haberi silmek istediğinize emin misiniz");


        alertDelete.setView(textView);


        alertDelete.setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
             //Silme işlemi yap

                DocumentReference documentReference = firestore.collection(keyWord).document(type);

                documentReference.update("new_list."+subMap, FieldValue.delete()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Silme işlemi başarılı", Toast.LENGTH_SHORT).show();
                        adminNewsHelper.remove(adminNews);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, adminNewsHelper.size());
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





