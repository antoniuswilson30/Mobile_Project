package com.example.tubesp3b;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b.databinding.DaftarDokterBinding;
import com.example.tubesp3b.databinding.DaftarPertemuanBinding;

import java.util.Arrays;
import java.util.List;

public class PertemuanFragment extends Fragment {
    DaftarPertemuanBinding binding;
    MainPresenter presenter;
    PertemuanAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.binding = DaftarPertemuanBinding.inflate(inflater, container,false);
        this.adapter = new PertemuanAdapter(this.presenter);
        this.binding.daftarPertemuan.setAdapter(this.adapter);
        this.binding.tombolPertemuan.setOnClickListener(this::onClick);
        this.binding.tombolSearch.setOnClickListener(this::onFilter);
        setList();
        return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        this.presenter.loadDataPertemuan();
        this.binding.dropdownSearch.setText(this.presenter.getFilter());
        List<String> namaDoktor = Arrays.asList(presenter.getNamaSearch());
        ArrayAdapter adapter = new ArrayAdapter(this.getContext(), R.layout.dropdown_search, namaDoktor);
        binding.dropdownSearch.setAdapter(adapter);
    }

    private void onFilter(View view){
        presenter.setFilter(binding.dropdownSearch.getText().toString());
    }

    public void onClick(View view){
        presenter.changePage("tambahPertemuan");
    }

    public void setList(){
        this.adapter.updateList(this.presenter.getPertemuans());
    }

    public static Fragment newInstance(MainPresenter presenter){
        PertemuanFragment fragment = new PertemuanFragment();
        fragment.presenter = presenter;
        return fragment;
    }
}
