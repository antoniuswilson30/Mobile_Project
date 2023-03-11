package com.example.tubesp3b;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.example.tubesp3b.databinding.FragmentDetailpertemuanBinding;

public class DialogPertemuan extends DialogFragment {
    FragmentDetailpertemuanBinding binding;
    MainPresenter presenter;
    Pertemuan temp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        binding = FragmentDetailpertemuanBinding.inflate(inflater,container, false);
        binding.detailDokter.setText(temp.doctor);
        binding.detailKeluhan.setText(temp.keluhan);
        binding.detailTanggal.setText(temp.tanggal);
        binding.detailWaktu.setText(temp.waktu);
        binding.tombolBelum.setOnClickListener(this::undoneStatus);
        binding.tombolSudah.setOnClickListener(this::doneStatus);
        return binding.getRoot();
    }

    public void doneStatus(View view){
        this.presenter.doneStatus(temp.id);
        this.presenter.closeDialogPertemuan();
    }

    public void undoneStatus(View view){
        this.presenter.undoneStatus(temp.id);
        this.presenter.closeDialogPertemuan();
    }
    public static DialogPertemuan newInstance(MainPresenter presenter, Pertemuan temp){
        DialogPertemuan fragment = new DialogPertemuan();
        fragment.presenter = presenter;
        fragment.temp = temp;
        return fragment;
    }
}
