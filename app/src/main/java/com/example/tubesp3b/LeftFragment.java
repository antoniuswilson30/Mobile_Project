package com.example.tubesp3b;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b.databinding.FragmentLeftBinding;

import java.util.List;

public class LeftFragment extends Fragment {
    private FragmentLeftBinding binding;
    private ButtonAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = FragmentLeftBinding.inflate(inflater, container, false);
        this.adapter = new ButtonAdapter(this.getParentFragmentManager());
        this.binding.listBtn.setAdapter(this.adapter);
        return binding.getRoot();
    }

}
