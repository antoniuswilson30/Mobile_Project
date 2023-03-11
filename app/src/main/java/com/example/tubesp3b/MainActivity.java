package com.example.tubesp3b;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.example.tubesp3b.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements IMainActivity{
    ActivityMainBinding binding;
    FragmentManager fragmentManager;
    Toolbar toolbar;
    HashMap<String, Fragment> fragments = new HashMap<>();
    MainPresenter presenter;
    List<Doctor> doctors;
    List<Pertemuan> pertemuans;
    DokterTambahFragment fragmentDialog;
    DokterEditFragment fragmentDialogEdit;
    DialogPertemuan pertemuanDialog;
    FeedReaderDbHelper dbHelper;
    FeedReaderDbHelperPertemuan dbHelperPertemuan;
    PenyimpanFilter filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        this.toolbar = binding.toolbar;
        DrawerLayout drawer = binding.drawerLayout;
        this.setSupportActionBar(toolbar);
        ActionBarDrawerToggle adbt = new ActionBarDrawerToggle(this, drawer,toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawer.addDrawerListener(adbt);
        adbt.syncState();
        this.doctors = new ArrayList<>();
        this.pertemuans = new ArrayList<>();
        this.fragments = new HashMap<>();
        this.dbHelper = new FeedReaderDbHelper(this);
        this.dbHelperPertemuan = new FeedReaderDbHelperPertemuan(this);
        this.filter = new PenyimpanFilter(this);
        this.presenter = new MainPresenter(this, this.dbHelper, this.dbHelperPertemuan, this.filter);
        fragments.put("home", MainFragment.newInstance(this.presenter));
        fragments.put("dokter", DoktorFragment.newInstance(this.presenter));
        fragments.put("pertemuan", PertemuanFragment.newInstance(this.presenter));
        fragments.put("tambahPertemuan",PertemuanTambahFragment.newInstance(this.presenter));
        this.fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction ft =this.fragmentManager.beginTransaction();
        ft.add(binding.fragmentContainer.getId(), fragments.get("home")).commit();
        fragmentManager.setFragmentResultListener("changePage", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String name = result.getString("name");
                changePage(name);
                binding.drawerLayout.closeDrawers();
            }
        });
        fragmentManager.setFragmentResultListener("closeApp", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                closeApp();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        this.presenter.loadData();
        this.presenter.loadDataPertemuan();
    }

    @Override
    public void changePage(String fragment){
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        ft.replace(binding.fragmentContainer.getId(), this.fragments.get(fragment)).commit();
    }

    @Override
    public void closeApp() {
        this.moveTaskToBack(true);
        this.finish();
    }

    @Override
    public void updateDoctor(List<Doctor> doctorList) {
        DoktorFragment df = (DoktorFragment) this.fragments.get("dokter");
        this.doctors.clear();
        this.doctors.addAll(doctorList);
        df.setList();
    }

    @Override
    public void updatePertemuans(List<Pertemuan> pertemuanList) {
        PertemuanFragment pf = (PertemuanFragment) this.fragments.get("pertemuan");
        this.pertemuans.clear();
        this.pertemuans.addAll(pertemuanList);
        pf.setList();
    }

    @Override
    public void dialogDoktor(){
        this.fragmentDialog= DokterTambahFragment.newInstance(this.presenter);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        fragmentDialog.show(ft, "tag");
    }
    @Override
    public void dialogDoktorEdit(Doctor temp){
        this.fragmentDialogEdit= DokterEditFragment.newInstance(this.presenter, temp);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        fragmentDialogEdit.show(ft, "tag");
    }

    @Override
    public void dialogPertemuan(Pertemuan temp){
        this.pertemuanDialog = DialogPertemuan.newInstance(this.presenter, temp);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        pertemuanDialog.show(ft, "tag");
    }

    @Override
    public void closeDialog() {
        this.fragmentDialog.dismiss();
    }

    @Override
    public void closeDialogPertemuan() {
        this.pertemuanDialog.dismiss();
    }

    @Override
    public void closeDialogEdit() {
        this.fragmentDialogEdit.dismiss();
    }

    @Override
    public void loadDoctor(List<Doctor> doctorList) {
        this.doctors.clear();
        this.doctors.addAll(doctorList);
    }

    @Override
    public void loadPertemuans(List<Pertemuan> pertemuanList) {
        this.pertemuans.clear();
        this.pertemuans.addAll(pertemuanList);
    }

}