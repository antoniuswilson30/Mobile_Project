package com.example.tubesp3b;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.tubesp3b.databinding.DropdownSearchBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter  extends BaseAdapter {
    List<String> arr;
    MainPresenter presenter;

    public SearchAdapter(MainPresenter presenter){
        arr = new ArrayList<>();
        this.presenter = presenter;
    }
    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int i) {
        return arr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        LayoutInflater inflater =LayoutInflater.from(viewGroup.getContext());
        if(view == null){
            DropdownSearchBinding binding = DropdownSearchBinding.inflate(inflater);
            view = binding.getRoot();
            viewHolder = new ViewHolder(binding);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.updateView(i);
        return view;
    }

    private class ViewHolder{
        protected DropdownSearchBinding binding;

        ViewHolder(DropdownSearchBinding binding){
            this.binding = binding;
        }

        public void updateView(int tag){
            binding.listSearch.setText(arr.get(tag));
        }


    }
}
