package com.yusufmirza.etubilgisayartopluluk.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yusufmirza.etubilgisayartopluluk.databinding.PlanFragmentBinding;

public class PlanFragment extends Fragment {

    PlanFragmentBinding planFragmentBinding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          planFragmentBinding = PlanFragmentBinding.inflate(inflater);
          return planFragmentBinding.getRoot();
    }

}
