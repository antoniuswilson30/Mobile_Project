package com.example.tubesp3b;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.tubesp3b.databinding.ItemListDokterBinding;
import com.example.tubesp3b.databinding.ItemListPertemuanBinding;

import java.util.ArrayList;
import java.util.List;

public class PertemuanAdapter extends BaseAdapter {
    public List<Pertemuan> list;
    public MainPresenter presenter;

    public PertemuanAdapter(MainPresenter presenter){
        this.list = new ArrayList<>();
        this.presenter = presenter;
    }

    public void updateList(List<Pertemuan> getPertemuan){
        this.list.clear();
        this.list.addAll(getPertemuan);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
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
            ItemListPertemuanBinding binding = ItemListPertemuanBinding.inflate(inflater);
            view = binding.getRoot();
            viewHolder = new ViewHolder(binding, this.presenter);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.updateView(i);
        return view;
    }

    private class ViewHolder{
        protected ItemListPertemuanBinding binding;
        protected int tag;
        private MainPresenter presenter;
        public ViewHolder(ItemListPertemuanBinding binding, MainPresenter presenter){
            this.binding = binding;
            this.presenter = presenter;
        }

        public void updateView(int tag){
            Pertemuan temp = list.get(tag);
            this.tag = tag;
            binding.listDokter.setText(temp.doctor);
            binding.listTanggal.setText(temp.tanggal);
            binding.listWaktu.setText(temp.waktu);
            binding.tombolDelete.setOnClickListener(this::deleteClick);
            binding.listStatus.setText(temp.status);
            binding.full.setOnClickListener(this::showDialog);
        }

        public void showDialog(View view){
            this.presenter.dialogPertemuan(tag);
        }

        private void deleteClick(View view){
            AlertDialog.Builder builder = new AlertDialog.Builder(this.binding.getRoot().getContext());

            // Set the message show for the Alert time
            builder.setMessage("Hapus pertemuan? ");

            // Set Alert Title
            builder.setTitle("Peringatan! ");

            // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
            builder.setCancelable(false);

            // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
            builder.setPositiveButton("Yes",  (dialog, which) -> {
                // When the user click yes button then app will close
                presenter.deletePertemuan(tag);
                notifyDataSetChanged();
            });

            // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
            builder.setNegativeButton("No", (dialog, which) -> {
                // If user click no then dialog box is canceled.
                dialog.cancel();
            });

            // Create the Alert dialog
            AlertDialog alertDialog = builder.create();
            // Show the Alert Dialog box
            alertDialog.show();
        }
    }
}

class Pertemuan{
    String doctor;
    String keluhan;
    String tanggal;
    String waktu;
    String status;
    int id;
    String idDokter;

    Pertemuan(int id, String idDokter, String doctor, String keluhan, String tanggal, String waktu, String status){
        this.doctor = doctor;
        this.idDokter = idDokter;
        this.keluhan = keluhan;
        this.tanggal =tanggal;
        this.waktu= waktu;
        this.status = status;
        this.id = id;
    }
}
