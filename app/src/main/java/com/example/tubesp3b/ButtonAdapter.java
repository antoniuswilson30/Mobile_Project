package com.example.tubesp3b;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.tubesp3b.databinding.DrawerButtonBinding;

import java.util.ArrayList;
import java.util.List;

public class ButtonAdapter extends BaseAdapter {
    private List<String> stringList;
    private DrawerButtonBinding binding;
    private FragmentManager fm;

    public ButtonAdapter(FragmentManager fragmentManager){
        stringList = new ArrayList<>();
        this.fm = fragmentManager;
        add("Home");
        add("Dokter");
        add("Pertemuan");
        add("Exit");
    }

    public void add(String title){
        stringList.add(title);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int i) {
        return stringList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater =LayoutInflater.from(viewGroup.getContext());
        binding = DrawerButtonBinding.inflate(inflater);
        view = binding.getRoot();
        ViewHolder viewHolder = new ViewHolder(binding, i);
        viewHolder.updateView();
        return view;
    }

    private class ViewHolder{
        DrawerButtonBinding binding;
        int tag;

        ViewHolder(DrawerButtonBinding binding, int i){
            this.binding = binding;
            this.tag = i;
        }

        public void updateView(){
            this.binding.tombol.setText(stringList.get(tag));
            this.binding.layoutTombol.setOnClickListener(this::onClick);
        }

        public void onClick(View view){
            if(tag == 0){
                Bundle bundle = new Bundle();
                bundle.putString("name", "home");
                fm.setFragmentResult("changePage", bundle);
            }else if(tag == 1){
                Bundle bundle = new Bundle();
                bundle.putString("name", "dokter");
                fm.setFragmentResult("changePage", bundle);
            }else if(tag == 2){
                Bundle bundle = new Bundle();
                bundle.putString("name", "pertemuan");
                fm.setFragmentResult("changePage", bundle);
            }else if(tag == 3){
                Bundle bundle = new Bundle();
                fm.setFragmentResult("closeApp", bundle);
            }
        }
    }
}
