package com.yusufmirza.etubilgisayartopluluk.adminpanel.videospanel;

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
import com.yusufmirza.etubilgisayartopluluk.R;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.newspanel.AdminNewsEdit;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.newspanel.AdminNewsHelper;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminNewsRowBinding;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminVideosRowBinding;

import java.util.ArrayList;

public class AdminVideosRecyclerAdapter extends RecyclerView.Adapter<AdminVideosRecyclerAdapter.AdminNewsViewHolder> {

    ArrayList<AdminVideosHelper> adminVideosHelpers;
    AdminVideosRowBinding adminVideosRowBinding;

    AlertDialog.Builder alertDelete;
    Context context;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String keyWord,type;


    public AdminVideosRecyclerAdapter(ArrayList<AdminVideosHelper> adminVideosHelpers, Context context, String keyWord, String type){
        this.adminVideosHelpers = adminVideosHelpers;
        this.context = context;
        this.keyWord = keyWord;
        this.type = type;


    }


    @NonNull
    @Override
    public AdminNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        adminVideosRowBinding = AdminVideosRowBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new AdminNewsViewHolder(adminVideosRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminNewsViewHolder holder, int position) {
        AdminVideosHelper adminVideosHelper = adminVideosHelpers.get(holder.getAdapterPosition());
        holder.adminNewsRowBinding.videosNameText.setText(adminVideosHelper.getName());

        if (adminVideosHelper.getAccountType().equals("instagram")){
            holder.adminNewsRowBinding.imageAccountType.setImageResource(R.drawable.insta);
        } else {
            holder.adminNewsRowBinding.imageAccountType.setImageResource(R.drawable.youtube);
        }



        //Editleme butonu ile yeni bir sınıf açılmalı
        holder.adminNewsRowBinding.videosEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, AdminVideosEdit.class);
                intent.putExtra("key",keyWord);
                intent.putExtra("type",type);
                intent.putExtra("subMap",adminVideosHelper.getSubMap());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);

            }
        });

        //Silme işlemi
        holder.adminNewsRowBinding.videosDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDeleteAlertDialog(adminVideosHelper.getSubMap(),adminVideosHelper,holder.getAdapterPosition());
                alertDelete.create();
                alertDelete.show();





            }
        });



    }

    @Override
    public int getItemCount() {
        return adminVideosHelpers.size();
    }

    public static class AdminNewsViewHolder extends RecyclerView.ViewHolder{


        AdminVideosRowBinding adminNewsRowBinding;
        public AdminNewsViewHolder(@NonNull AdminVideosRowBinding adminVideosRowBinding) {
            super(adminVideosRowBinding.getRoot());
            this.adminNewsRowBinding = adminVideosRowBinding;
        }
    }


    public void setDeleteAlertDialog(String subMap,AdminVideosHelper adminVideos, int position){

        alertDelete = new AlertDialog.Builder(context);
        TextView textView = new TextView(context);
        textView.setText("Bu haberi silmek istediğinize emin misiniz");


        alertDelete.setView(textView);


        alertDelete.setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
             //Silme işlemi yap

                DocumentReference documentReference = firestore.collection(keyWord).document(type);

                documentReference.update("video_list."+subMap, FieldValue.delete()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Silme işlemi başarılı", Toast.LENGTH_SHORT).show();
                        adminVideosHelpers.remove(adminVideos);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, adminVideosHelpers.size());
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





