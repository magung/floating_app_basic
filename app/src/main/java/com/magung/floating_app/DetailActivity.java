package com.magung.floating_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.et_nama)
    EditText et_nama;
    @BindView(R.id.cb_teknologi)
    CheckBox cb_teknologi;
    @BindView(R.id.cb_kuliner) CheckBox cb_kuliner;
    @BindView(R.id.cb_keuangan) CheckBox cb_keuangan;
    @BindView(R.id.cb_ekonomi) CheckBox cb_ekonomi;
    @BindView(R.id.cb_arsitektur) CheckBox cb_arsitektur;
    @BindView(R.id.cb_lain) CheckBox cb_lain;
    @BindView(R.id.sp_status)
    Spinner sp_status;
    @BindView(R.id.rb_IK)
    RadioButton rb_ik;
    @BindView(R.id.rb_TI) RadioButton rb_ti;
    @BindView(R.id.bt_update)
    Button bt_update;
    @BindView(R.id.bt_hapus)
    Button bt_hapus;
    @BindView(R.id.rg_kelas)
    RadioGroup rg_kelas;
    ArrayList<Anggota> anggotaArrayList;

    Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        //tangkap bundle
        Bundle bundle = null;
        bundle = this.getIntent().getExtras();

        //letakkan isi bundle
        et_nama.setText(bundle.getString("b_nama"));

        //pada radio button
        if ("b_kelas" == "IK"){
            rb_ik.setChecked(true);
        }else{
            rb_ti.setChecked(true);
        }

        //pada checkbox
        cb_teknologi.setChecked(bundle.getBoolean("b_teknologi"));
        cb_arsitektur.setChecked(bundle.getBoolean("b_arsitektur"));
        cb_ekonomi.setChecked(bundle.getBoolean("b_ekonomi"));
        cb_keuangan.setChecked(bundle.getBoolean("b_keuangan"));
        cb_kuliner.setChecked(bundle.getBoolean("b_kuliner"));
        cb_lain.setChecked(bundle.getBoolean("b_lain"));

        //set data untuk spinner harus dikonversi dulu kedalam array
        String[] arrActive = getResources().getStringArray(R.array.status_bekerja);
        int idxActive = Arrays.asList(arrActive).indexOf(bundle.getString("b_status"));//find the index
        sp_status.setSelection(idxActive); //set spinner

        getSupportActionBar().setTitle("Data Peserta"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar

        Bundle finalBundle = bundle;
        bt_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anggotaArrayList.remove(finalBundle.getInt("posisi"));
                //simpan kedalam shared preferences
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(anggotaArrayList);
                editor.putString("sp_list_anggota", json);
                editor.apply();

                Intent intent = new Intent( v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                anggotaArrayList.remove(finalBundle.getInt("posisi"));
                RadioButton mrb_kelas;

                String nama, kelas, status;
                boolean kesehatan, ekonomi, teknologi,
                        arsitektur, kuliner, keuangan, lain;

                //pilih radio button yang ada di radio button group
                int selectedId = rg_kelas.getCheckedRadioButtonId();

                //khusus ini ga pake butter knife, karena nilai yang dimasukkan dinamis berdasarkan ID yang dipilih
                mrb_kelas = (RadioButton) findViewById(selectedId);

                nama = String.valueOf(et_nama.getText());
                kelas = mrb_kelas.getText().toString();
                teknologi = cb_teknologi.isChecked();
                kuliner = cb_kuliner.isChecked();
                ekonomi= cb_ekonomi.isChecked();
                keuangan = cb_keuangan.isChecked();
                arsitektur = cb_arsitektur.isChecked();
                lain = cb_lain.isChecked();

                status = sp_status.getSelectedItem().toString();

                //new MainActivity().anggotaArrayList.add( //panggil dari MainActivity
//                anggotaArrayList.add(
//                        new Anggota(nama, kelas, status, teknologi,
//                                lain, kuliner,
//                                keuangan, ekonomi, arsitektur)
//                );

                anggotaArrayList.set(finalBundle.getInt("posisi"), new Anggota(nama, kelas, status, teknologi,
                        lain, kuliner,
                        keuangan, ekonomi, arsitektur));

                //simpan kedalam shared preferences
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(anggotaArrayList);
                editor.putString("sp_list_anggota", json);
                editor.apply();

                Intent intent = new Intent( v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //load data dari shared preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = prefs.getString("sp_list_anggota", "");
        Type type = new TypeToken<ArrayList<Anggota>>() {}.getType();


        if (json != "") {
            anggotaArrayList =  gson.fromJson(json, type);
        }

    }

}