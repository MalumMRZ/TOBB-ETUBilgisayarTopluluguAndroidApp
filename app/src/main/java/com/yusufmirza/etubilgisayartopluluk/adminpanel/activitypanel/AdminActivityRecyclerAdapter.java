package com.yusufmirza.etubilgisayartopluluk.adminpanel.activitypanel;

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
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminActivitiesRowBinding;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminNewsRowBinding;

import java.util.ArrayList;

public class AdminActivityRecyclerAdapter extends RecyclerView.Adapter<AdminActivityRecyclerAdapter.AdminNewsViewHolder> {

    ArrayList<AdminActivityHelper> adminActivityHelpers;
    AdminActivitiesRowBinding adminActivitiesRowBinding;

    AlertDialog.Builder alertDelete;
    Context context;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String keyWord,type;


    public AdminActivityRecyclerAdapter(ArrayList<AdminActivityHelper> adminActivityHelpers, Context context, String keyWord, String type){
        this.adminActivityHelpers = adminActivityHelpers;
        this.context = context;
        this.keyWord = keyWord;
        this.type = type;


    }


    @NonNull
    @Override
    public AdminNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        adminActivitiesRowBinding = AdminActivitiesRowBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new AdminNewsViewHolder(adminActivitiesRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminNewsViewHolder holder, int position) {
        AdminActivityHelper adminActivityHelper = adminActivityHelpers.get(holder.getAdapterPosition());
        holder.adminActivitiesRowBinding.activitiesNameText.setText(adminActivityHelper.getName());


        //Editleme butonu ile yeni bir sınıf açılmalı
        holder.adminActivitiesRowBinding.activitiesEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, AdminActivityEdit.class);
                intent.putExtra("key",keyWord);
                intent.putExtra("type",type);
                intent.putExtra("subMap",adminActivityHelper.getSubMap());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);

            }
        });

        //Silme işlemi
        holder.adminActivitiesRowBinding.activitiesDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDeleteAlertDialog(adminActivityHelper.getSubMap(),adminActivityHelper,holder.getAdapterPosition());
                alertDelete.create();
                alertDelete.show();





            }
        });



    }

    @Override
    public int getItemCount() {
        return adminActivityHelpers.size();
    }

    public static class AdminNewsViewHolder extends RecyclerView.ViewHolder{


        AdminActivitiesRowBinding adminActivitiesRowBinding;
        public AdminNewsViewHolder(@NonNull AdminActivitiesRowBinding adminActivitiesRowBinding) {
            super(adminActivitiesRowBinding.getRoot());
            this.adminActivitiesRowBinding = adminActivitiesRowBinding;
        }
    }


    public void setDeleteAlertDialog(String subMap,AdminActivityHelper adminActivityHelper, int position){

        alertDelete = new AlertDialog.Builder(context);
        TextView textView = new TextView(context);
        textView.setText("Bu haberi silmek istediğinize emin misiniz");


        alertDelete.setView(textView);


        alertDelete.setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
             //Silme işlemi yap

                DocumentReference documentReference = firestore.collection(keyWord).document(type);

                documentReference.update("activity_list."+subMap, FieldValue.delete()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Silme işlemi başarılı", Toast.LENGTH_SHORT).show();
                        adminActivityHelpers.remove(adminActivityHelper);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, adminActivityHelpers.size());
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





