package com.magung.floating_app;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_container)
    RecyclerView rv;

    @BindView(R.id.et_nama)
    EditText met_nama;
    @BindView(R.id.cb_teknologi)
    CheckBox mcb_teknologi;
    @BindView(R.id.cb_kuliner) CheckBox mcb_kuliner;
    @BindView(R.id.cb_keuangan) CheckBox mcb_keuangan;
    @BindView(R.id.cb_ekonomi) CheckBox mcb_ekonomi;
    @BindView(R.id.cb_arsitektur) CheckBox mcb_arsitektur;
    @BindView(R.id.cb_lain) CheckBox mcb_lain;
    @BindView(R.id.sp_status)
    Spinner msp_status;
    @BindView(R.id.rg_kelas)
    RadioGroup mrg_kelas;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;

    private RadioButton mrb_kelas;

    String nama, kelas, status;
    boolean kesehatan, ekonomi, teknologi,
            arsitektur, kuliner, keuangan, lain;

    ArrayList<Anggota> anggotaArrayList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new AlertDialog.Builder(MainActivity.this);

                inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.form_input, null);

                final View diagView = view;

                ButterKnife.bind(this, view);

                dialog.setView(view);
                dialog.setCancelable(true);
                dialog.setIcon(R.mipmap.ic_launcher);
                dialog.setTitle("Form Biodata");

                dialog.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //pilih radio button yang ada di radio button group
                        int selectedId = mrg_kelas.getCheckedRadioButtonId();
                        mrb_kelas = (RadioButton) diagView.findViewById(selectedId);

                        nama    = met_nama.getText().toString();
                        kelas    = mrb_kelas.getText().toString();

                        teknologi = mcb_teknologi.isChecked();
                        kuliner = mcb_kuliner.isChecked();
                        ekonomi= mcb_ekonomi.isChecked();
                        keuangan = mcb_keuangan.isChecked();
                        arsitektur = mcb_arsitektur.isChecked();
                        lain = mcb_lain.isChecked();

                        status = msp_status.getSelectedItem().toString();

                        anggotaArrayList.add(
                                new Anggota(nama, kelas, status, teknologi,
                                        lain, kesehatan, kuliner,
                                        keuangan, ekonomi, arsitektur));

                        viewRecyclerView();


                        Toast.makeText(getBaseContext(),
                                nama + " berhasil disimpan",
                                Toast.LENGTH_SHORT).show();
                    }

                    private void viewRecyclerView() {
                    }
                });

                dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
