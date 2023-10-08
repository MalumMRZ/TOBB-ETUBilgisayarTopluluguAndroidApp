package com.yusufmirza.etubilgisayartopluluk.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yusufmirza.etubilgisayartopluluk.HelperClasses.Member;
import com.yusufmirza.etubilgisayartopluluk.databinding.MembersFragmentRecyclerRowBinding;

import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberRowHolder> {

    ArrayList<Member> memberArrayList;
    MembersFragmentRecyclerRowBinding membersFragmentRecyclerRowBinding;

    Context context;

    public MemberAdapter(ArrayList<Member> memberArrayList, Context context){
        this.memberArrayList=memberArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public MemberRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        membersFragmentRecyclerRowBinding = MembersFragmentRecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()));


        return new MemberRowHolder(membersFragmentRecyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberRowHolder holder, int position) {

           int pos = holder.getAdapterPosition();

           holder.membersFragmentRecyclerRowBinding.memberName.setText(memberArrayList.get(pos).getName());
           holder.membersFragmentRecyclerRowBinding.memberInfo.setText(memberArrayList.get(pos).getInfo());
           holder.membersFragmentRecyclerRowBinding.memberLink.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   String url = memberArrayList.get(pos).getLink();
                   Intent intent = new Intent(Intent.ACTION_VIEW);
                   intent.setData(Uri.parse(url));
                   context.startActivity(intent);
               }
           });

           String imageUri = memberArrayList.get(pos).getImageUri();

        Picasso.get().load(imageUri).fit().
                into(membersFragmentRecyclerRowBinding.memberImage);


    }

    @Override
    public int getItemCount() {
        return memberArrayList.size();
    }

    public static class  MemberRowHolder extends RecyclerView.ViewHolder{

        MembersFragmentRecyclerRowBinding membersFragmentRecyclerRowBinding;
        public MemberRowHolder(MembersFragmentRecyclerRowBinding membersFragmentRecyclerRowBinding) {
            super(membersFragmentRecyclerRowBinding.getRoot());
            this.membersFragmentRecyclerRowBinding = membersFragmentRecyclerRowBinding;

        }





    }








}
