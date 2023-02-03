package com.andrefrancisco.olxclone.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andrefrancisco.olxclone.R;
import com.andrefrancisco.olxclone.model.Categoria;


import java.util.List;

public class AdapterCategoria extends RecyclerView.Adapter<AdapterCategoria.MyViewHolder> {
    private List<Categoria> categoriaList;
    private OnClick onClick;

    public AdapterCategoria(List<Categoria> anuncioList, OnClick onClick) {
        this.categoriaList = anuncioList;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoria_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Categoria categoria = categoriaList.get(position);

        holder.img_categoria.setImageResource(categoria.getCaminho());
        holder.text_categoria.setText(categoria.getNome());


        holder.itemView.setOnClickListener(v -> onClick.OnClickListener(categoria));
    }

    @Override
    public int getItemCount() {
        return categoriaList.size();
    }

    public interface OnClick {
        void OnClick(Categoria categoria);

        public void OnClickListener(Categoria categoria);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_categoria;
        TextView text_categoria;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_categoria = itemView.findViewById(R.id.img_categoria);
            text_categoria = itemView.findViewById(R.id.text_categoria);


        }
    }
}
