package com.yusufmirza.etubilgisayartopluluk.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class VPAdaptor extends FragmentStateAdapter {

    ArrayList<Fragment> fragmentArrayList;



    public VPAdaptor(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> fragmentArrayList) {
        super(fragmentActivity);
        this.fragmentArrayList= fragmentArrayList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = fragmentArrayList.get(position);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return fragmentArrayList.size();
    }
}
