package com.andrefrancisco.olxclone.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andrefrancisco.olxclone.R;
import com.andrefrancisco.olxclone.model.Anuncio;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnuncioAdapter extends RecyclerView.Adapter<AnuncioAdapter.MyViewHolder> {

    private final List<Anuncio> anuncioList;

    public AnuncioAdapter(List<Anuncio> anuncioList, OnClickListener onClickListener) {
        this.anuncioList = anuncioList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anuncio_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Anuncio anuncio = anuncioList.get(position);
        Picasso.get().load(String.valueOf(anuncio.getUrlImagens().get(0))).into(holder.img_anuncio);
        holder.text_titulo.setText(anuncio.getTitulo());
        //holder.text_valor.setText(anuncio.getValor());
        //holder.text_local.setText(anuncio.getLocal());

    }

    @Override
    public int getItemCount() {
        return anuncioList.size();
    }

    public interface OnClickListener {
        void onClick(Anuncio anuncio);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView img_anuncio;
        TextView text_titulo;
        TextView text_valor;
        TextView text_local;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_anuncio.findViewById(R.id.img_anuncio);
            text_titulo.findViewById(R.id.text_titulo);
            text_valor.findViewById(R.id.text_valor);
            text_local.findViewById(R.id.text_local);

        }
    }
}
