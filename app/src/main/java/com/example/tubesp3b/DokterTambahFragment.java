package com.example.tubesp3b;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.fragment.app.DialogFragment;

import com.example.tubesp3b.databinding.FragmentDokterbaruBinding;

public class DokterTambahFragment extends DialogFragment {
    FragmentDokterbaruBinding binding;
    MainPresenter presenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        binding = FragmentDokterbaruBinding.inflate(inflater,container, false);
        binding.tombolAddDokter.setOnClickListener(this::onClick);
        return binding.getRoot();
    }

    public void onClick(View view){
        if(!binding.namaDoktor.getText().toString().equals("") && !binding.spesialisDoktor.getText().toString().equals("") && !binding.noHPDoktor.getText().toString().equals("")) {
            this.presenter.updateDoctors(("dr. " + binding.namaDoktor.getText().toString()), binding.spesialisDoktor.getText().toString(), binding.noHPDoktor.getText().toString());
            this.presenter.closeDialog();
        }
    }

    public static DokterTambahFragment newInstance(MainPresenter presenter){
        DokterTambahFragment fragment = new DokterTambahFragment();
        fragment.presenter = presenter;
        return fragment;
    }
}
