package com.example.tubesp3b;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.tubesp3b.databinding.HalamanUtamaBinding;

import java.util.List;

public class MainFragment extends Fragment{
    HalamanUtamaBinding binding;
    public MainPresenter presenter;
    private MainFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = HalamanUtamaBinding.inflate(inflater, container,false);
        binding.tombolJanjiHome.setOnClickListener(this::onClick);
        return binding.getRoot();
    }

    public void onClick(View view){
        Bundle bundle = new Bundle();
        //nama fragment tambah pertemuan
        bundle.putString("name", "pertemuan");
        this.getParentFragmentManager().setFragmentResult("changePage", bundle);
    }

    public static MainFragment newInstance(MainPresenter presenter){
        MainFragment fragment = new MainFragment();
        fragment.presenter = presenter;
        return fragment;
    }
}
