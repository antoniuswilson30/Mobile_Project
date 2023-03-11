package com.example.tubesp3b;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.tubesp3b.databinding.DaftarDokterBinding;
import com.example.tubesp3b.databinding.HalamanUtamaBinding;

import java.util.List;

public class DoktorFragment extends Fragment{
    DaftarDokterBinding binding;
    MainPresenter presenter;
    DoctorsAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.binding = DaftarDokterBinding.inflate(inflater, container,false);
        this.adapter = new DoctorsAdapter(this.presenter);
        this.binding.daftarDokter.setAdapter(this.adapter);
        this.binding.tambahDokter.setOnClickListener(this::onClick);
        setList();
        return binding.getRoot();
    }

    public void onClick(View view){
        this.presenter.dialogDoktor();
    }

    public void setList(){
        this.adapter.updateList(this.presenter.getDoctors());
    }

    public static Fragment newInstance(MainPresenter presenter){
        DoktorFragment fragment = new DoktorFragment();
        fragment.presenter =presenter;
        return fragment;
    }

}
