package com.example.tubesp3b;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.fragment.app.DialogFragment;

import com.example.tubesp3b.databinding.FragmentDokterbaruBinding;
import com.example.tubesp3b.databinding.FragmentDoktereditBinding;

public class DokterEditFragment extends DialogFragment {
    FragmentDoktereditBinding binding;
    MainPresenter presenter;
    Doctor temp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        binding = FragmentDoktereditBinding.inflate(inflater,container, false);
        binding.namaDoktor.setText(temp.nama.substring(4));
        binding.spesialisDoktor.setText(temp.spesialis);
        binding.noHPDoktor.setText(temp.nomor);
        binding.tombolAddDokter.setOnClickListener(this::onClick);
        return binding.getRoot();
    }

    public void onClick(View view){
        if(!binding.namaDoktor.getText().toString().equals("") && !binding.spesialisDoktor.getText().toString().equals("") && !binding.noHPDoktor.getText().toString().equals("")) {
            this.presenter.editDoctor(("dr. " + binding.namaDoktor.getText().toString()), binding.spesialisDoktor.getText().toString(), binding.noHPDoktor.getText().toString(), temp.id);
            this.presenter.closeDialogEdit();
        }
    }

    public static DokterEditFragment newInstance(MainPresenter presenter, Doctor temp){
        DokterEditFragment fragment = new DokterEditFragment();
        fragment.temp = temp;
        fragment.presenter = presenter;
        return fragment;
    }
}
