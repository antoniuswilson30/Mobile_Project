package com.example.tubesp3b;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.tubesp3b.databinding.ItemListDokterBinding;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class DoctorsAdapter extends BaseAdapter {
    public List<Doctor> list;
    public MainPresenter presenter;

    public DoctorsAdapter(MainPresenter presenter){
        this.list = new ArrayList<>();
        this.presenter = presenter;
    }

    public void updateList(List<Doctor> getDoctor){
        this.list.clear();
        this.list.addAll(getDoctor);
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
            ItemListDokterBinding binding = ItemListDokterBinding.inflate(inflater);
            view = binding.getRoot();
            viewHolder = new ViewHolder(binding, this.presenter, viewGroup.getContext());
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.updateView(i);
        return view;
    }

    private class ViewHolder{
        protected ItemListDokterBinding binding;
        protected int tag;
        private MainPresenter presenter;
        private Context context;
        public ViewHolder(ItemListDokterBinding binding, MainPresenter presenter, Context context){
            this.context = context;
            this.binding = binding;
            this.presenter = presenter;
        }

        public void updateView(int tag){
            Doctor temp = list.get(tag);
            this.tag = tag;
            binding.listTitle.setText(temp.nama);
            binding.listDetails.setText(temp.spesialis);
            binding.listNoHP.setText(temp.nomor);
            binding.tombolDelete.setOnClickListener(this::deleteClick);
            binding.full.setOnClickListener(this::editDokter);
            binding.tombolCall.setOnClickListener(this::call);
        }

        private void call(View view){
            Uri uri = Uri.parse("tel:" + binding.listNoHP.getText().toString());
            Intent intent = new Intent(Intent.ACTION_DIAL,uri);
            context.startActivity(intent);
        }

        private void editDokter(View view) {
            this.presenter.dialogDoktorEdit(tag);
        }

        private void deleteClick(View view){
            AlertDialog.Builder builder = new AlertDialog.Builder(this.binding.getRoot().getContext());

            // Set the message show for the Alert time
            builder.setMessage("Hapus informasi dokter ?");

            // Set Alert Title
            builder.setTitle("Peringatan!");

            // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
            builder.setCancelable(false);

            // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
            builder.setPositiveButton("Yes",  (dialog, which) -> {
                // When the user click yes button then app will close
                presenter.deleteDoctor(tag);
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

class Doctor{
    String nama;
    String spesialis;
    String nomor;
    int id;

    Doctor(int id, String nama, String spesialis, String nomor){
        this.id = id;
        this.nama = nama;
        this.nomor = nomor;
        this.spesialis = spesialis;
    }

    public String getNama() {
        return nama;
    }

    public String getSpesialis() {
        return spesialis;
    }
}


