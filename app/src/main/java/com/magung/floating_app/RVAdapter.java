package com.magung.floating_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

class RvAdapter extends RecyclerView.Adapter<RvAdapter.CardViewHolder> {
    private Context context;
    ArrayList<Anggota> anggotaArrayList;

    public RvAdapter(Context context, ArrayList<Anggota> anggotaArrayList) {
        this.context = context;
        this.anggotaArrayList = anggotaArrayList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
        CardViewHolder viewHolder = new CardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        final String
                nama = anggotaArrayList.get(position).getNama(),
                kelas = anggotaArrayList.get(position).getKelas(),
                status = anggotaArrayList.get(position).getStatus();

        final boolean
                teknologi = anggotaArrayList.get(position).isTeknologi(),
                arsitektur = anggotaArrayList.get(position).isArsitektur(),
                ekonomi = anggotaArrayList.get(position).isEkonomi(),
                keuangan = anggotaArrayList.get(position).isKeuangan(),
                kuliner = anggotaArrayList.get(position).isKuliner(),
                lain = anggotaArrayList.get(position).isLain();

        holder.tvNama.setText(nama);
        holder.tvKelas.setText(kelas);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //simpan array list yang ada pada MainActivity
                //new MainActivity().saveArrayList(context, anggotaArrayList, "list_anggota");

                Bundle b = new Bundle();
                b.putString("b_nama", nama);
                b.putString("b_kelas", kelas);
                b.putString("b_status", status);
                b.putBoolean("b_teknologi", teknologi);
                b.putBoolean("b_arsitektur", arsitektur);
                b.putBoolean("b_ekonomi", ekonomi);
                b.putBoolean("b_keuangan", keuangan);
                b.putBoolean("b_kuliner", kuliner);
                b.putBoolean("b_lain", lain);

                //buka detail activity
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtras(b);
                view.getContext().startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return anggotaArrayList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_nama)
        TextView tvNama;
        @BindView(R.id.tv_kelas) TextView tvKelas;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}