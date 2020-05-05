package com.magung.floating_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.rv_container)
    RecyclerView rv;

    ArrayList<Anggota> anggotaArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                CreateDialog alertDialog = new CreateDialog(rv, MainActivity.this, anggotaArrayList);
                alertDialog.show(fm, "fragment_alert");

            }
        });



    }

    public void viewRecyclerView(RecyclerView rv, ArrayList<Anggota> anggotaArrayList) {
        //definisi
        LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
        rv.setLayoutManager(llm);

        RvAdapter rvAdapter = new RvAdapter(getBaseContext(), MainActivity.this, anggotaArrayList);
        rv.setAdapter(rvAdapter);
    }

    //otomatis menyimpan ke shared preferences saat pindah ke Detail Activity
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        //simpan kedalam shared preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(anggotaArrayList);
        editor.putString("sp_list_anggota", json);
        editor.apply();
    }

    //otomatis menyimpan ke shared preferences saat pindah ke Detail Activity

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
            if ( anggotaArrayList.size() > 0 ) viewRecyclerView(rv, anggotaArrayList);
        }

    }


}