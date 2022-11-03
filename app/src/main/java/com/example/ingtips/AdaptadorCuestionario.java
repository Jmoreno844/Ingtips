package com.example.ingtips;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AdaptadorCuestionario extends RecyclerView.Adapter<AdaptadorCuestionario.ViewHolder>{

    ArrayList<Seleccionador> ListadoDatos;
    private OnItemClickListener onItemClickListener;

    public AdaptadorCuestionario(ArrayList<Seleccionador> listadoDatos){
        this.ListadoDatos= listadoDatos;
        this.onItemClickListener=null;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }





    @NonNull
    @Override
    public AdaptadorCuestionario.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_view,parent,false);

        return new ViewHolder(view,parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorCuestionario.ViewHolder holder, int position) {
        holder.cargarDatos(ListadoDatos.get(position));


    }

    @Override
    public int getItemCount() {
        return ListadoDatos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitulo;
        ImageView ivPrincipal;

        ViewGroup parent;



        public ViewHolder(@NonNull View itemView, ViewGroup parent) {
            super(itemView);
            this.parent=parent;

            tvTitulo = itemView.findViewById(R.id.tv_item_titulo);
            ivPrincipal = itemView.findViewById(R.id.iv_item_principal);




        }



        public void cargarDatos(Seleccionador preguntas) {

            tvTitulo.setText(preguntas.getSeleccion());
            Picasso.get().load(preguntas.getImagen()).resize(300,300).centerCrop().error(R.drawable.ic_launcher_background).into(ivPrincipal);




            if(onItemClickListener!=null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(preguntas,getAdapterPosition());


                    }
                });
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Seleccionador autos, int posicion);
    }
}

