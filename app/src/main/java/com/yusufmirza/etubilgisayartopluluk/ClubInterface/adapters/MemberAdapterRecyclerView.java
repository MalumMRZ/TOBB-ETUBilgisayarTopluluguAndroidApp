package com.yusufmirza.etubilgisayartopluluk.ClubInterface.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.yusufmirza.etubilgisayartopluluk.HelperClasses.Member;
import com.yusufmirza.etubilgisayartopluluk.databinding.MembersFragmentRecyclerRowBinding;

import java.util.ArrayList;


public class MemberAdapterRecyclerView extends RecyclerView.Adapter<MemberAdapterRecyclerView.MemberViewHolder> {

    ArrayList<Member> memberList;

    MembersFragmentRecyclerRowBinding membersFragmentRecyclerRowBinding;


    public MemberAdapterRecyclerView(ArrayList<Member> memberList){
        this.memberList= memberList;
    }



    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       membersFragmentRecyclerRowBinding = MembersFragmentRecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()));

        return new MemberViewHolder(membersFragmentRecyclerRowBinding);
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {

        Member member = memberList.get(position);

        holder.membersFragmentRecyclerRowBinding.memberLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = member.getLink();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.membersFragmentRecyclerRowBinding.memberName.setText(member.getName());
        holder.membersFragmentRecyclerRowBinding.memberInfo.setText(member.getInfo());

        Picasso.get().load(member.getImageUri()).fit().into(holder.membersFragmentRecyclerRowBinding.memberImage);

    }


    public static class MemberViewHolder extends RecyclerView.ViewHolder{

        MembersFragmentRecyclerRowBinding membersFragmentRecyclerRowBinding;
        public MemberViewHolder(MembersFragmentRecyclerRowBinding membersFragmentRecyclerRowBinding) {
            super(membersFragmentRecyclerRowBinding.getRoot());
            this.membersFragmentRecyclerRowBinding = membersFragmentRecyclerRowBinding;

        }
    }
}
