package com.example.tubesp3b;


import java.util.List;

public interface IMainActivity {
    public void updateDoctor(List<Doctor> doctorList);
    public void updatePertemuans(List<Pertemuan> pertemuanList);
    public void changePage(String title);
    public void closeApp();
    public void dialogDoktor();
    public void dialogDoktorEdit(Doctor temp);
    public void dialogPertemuan(Pertemuan temp);
    public void closeDialog();
    public void closeDialogPertemuan();
    public void closeDialogEdit();
    public void loadDoctor(List<Doctor> doctorList);
    public void loadPertemuans(List<Pertemuan> pertemuanList);
}
