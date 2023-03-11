package com.example.tubesp3b;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b.databinding.PertemuanBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PertemuanTambahFragment extends Fragment {
    PertemuanBinding binding;
    MainPresenter presenter;
    SimpleDateFormat dateFormatter;
    String tanggal;
    private TimePickerDialog timePickerDialog;;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = PertemuanBinding.inflate(inflater, container,false);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        binding.tombolTanggal.setOnClickListener(this::showDateDialog);
        binding.tombolAddPertemuan.setOnClickListener(this::onClick);
        binding.tombolWaktu.setOnClickListener(this::showTimeDialog);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        String[] namaDoktor = presenter.getNamaDoctor();
        ArrayAdapter adapter = new ArrayAdapter(this.getContext(), R.layout.dropdown_dokter, namaDoktor);
        binding.dropdownDoktor.setAdapter(adapter );
    }

    private void onClick(View view){
        if(!binding.dropdownDoktor.getText().toString().equals("") && !binding.tvWaktu.getText().toString().equals("") && !binding.tvTanggal.getText().toString().equals("") && !binding.tombolKeluhan.getText().toString().equals("")) {
            this.presenter.updatePertemuans(this.presenter.getIdDoctor(binding.dropdownDoktor.getText().toString()), binding.dropdownDoktor.getText().toString(), binding.tombolKeluhan.getText().toString(), binding.tvWaktu.getText().toString(), binding.tvTanggal.getText().toString(), "Belum Konsultasi");
            this.binding.dropdownDoktor.setText("");
            this.binding.tombolKeluhan.setText("");
            this.presenter.changePage("pertemuan");
        }
    }

    private void showTimeDialog(View view) {
        binding.tombolKeluhan.onEditorAction(EditorInfo.IME_ACTION_DONE);
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(this.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                binding.tvWaktu.setText(hourOfDay + " : " + minute);
            }
        },
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this.getContext()));
        timePickerDialog.show();
    }

    private void showDateDialog(View view){
        binding.tombolKeluhan.onEditorAction(EditorInfo.IME_ACTION_DONE);
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                tanggal = dateFormatter.format(newDate.getTime());
                binding.tvTanggal.setText(tanggal);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();


    }

    public PertemuanTambahFragment(){
    }

    public static Fragment newInstance(MainPresenter presenter){
        PertemuanTambahFragment fragment = new PertemuanTambahFragment();
        fragment.presenter = presenter;
        return fragment;
    }
}
