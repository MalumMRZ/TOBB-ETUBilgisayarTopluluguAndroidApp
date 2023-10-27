package com.yusufmirza.etubilgisayartopluluk.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.yusufmirza.etubilgisayartopluluk.Adapters.VPAdaptor;
import com.yusufmirza.etubilgisayartopluluk.Fragments.ourprojectsFragments.FutureActivity;
import com.yusufmirza.etubilgisayartopluluk.Fragments.ourprojectsFragments.FutureProject;
import com.yusufmirza.etubilgisayartopluluk.Fragments.ourprojectsFragments.PastActivity;
import com.yusufmirza.etubilgisayartopluluk.Fragments.ourprojectsFragments.PastProject;
import com.yusufmirza.etubilgisayartopluluk.R;


import java.util.ArrayList;

public class PlanFragment extends Fragment { //View Binding hatası


    ArrayList<Fragment> fragmentArrayList;
    FragmentActivity fragmentActivity;
    ViewPager2 viewPager2;
    TabLayout tabLayout;

    Context context;


    public PlanFragment(FragmentActivity fragmentActivity,Context context){

        this.fragmentActivity=fragmentActivity;
        this.context = context;


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          View view = LayoutInflater.from(container.getContext()).inflate(R.layout.plan_fragment,container,false);
          return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager2 = view.findViewById(R.id.viewPager2);
        tabLayout = view.findViewById(R.id.tabLayout);

        fragmentArrayList = new ArrayList<Fragment>();

        fragmentArrayList.add(new PastActivity(context));
        fragmentArrayList.add(new FutureActivity(context));
        fragmentArrayList.add(new PastProject(context));
        fragmentArrayList.add(new FutureProject(context));


        VPAdaptor adaptor =  new VPAdaptor(fragmentActivity,fragmentArrayList);

        viewPager2.setAdapter(adaptor);

        TabLayoutMediator.TabConfigurationStrategy tabConfigurationStrategy = new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position==0) {
                    tab.setText("Geçmiş Etkinlikler");
                } else if(position==1) {
                    tab.setText("Gelecek Etkinlikler");
                } else if (position==2){
                    tab.setText("Geçmiş Projeler");
                } else if (position==3){
                    tab.setText("Gelecek Projeler");
                }
            }
        };

        new TabLayoutMediator(tabLayout,viewPager2,tabConfigurationStrategy).attach();

    }
}
