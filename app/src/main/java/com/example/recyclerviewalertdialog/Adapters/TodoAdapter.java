package com.example.recyclerviewalertdialog.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewalertdialog.Modelos.ToDo;
import com.example.recyclerviewalertdialog.R;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ToDoVH> {

    private List<ToDo> objects;
    private int resources;
    private Context context;

    public TodoAdapter(List<ToDo> objects, int resources, Context context) {
        this.objects = objects;
        this.resources = resources;
        this.context = context;
    }

    /**
     * ALGO NO ME IMPORTA QUE. Llamará a este método para crear una nueva FILA
     * @param parent
     * @param viewType
     * @return un ObjetoViewHolder
     */
    @NonNull
    @Override
    public ToDoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View todoView = LayoutInflater.from(context).inflate(resources,null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        todoView.setLayoutParams(lp);
        return new ToDoVH(todoView);
    }

    /**
     * A partir de un ViewHolder -> Asignar valores a los elementos
     * @param holder -> Fila a configurar
     * @param position -> Elemento a la vista a mostrar
     */

    @Override
    public void onBindViewHolder(@NonNull ToDoVH holder, int position) {
        ToDo todo = objects.get(position);
        holder.lblTitulo.setText(todo.getTitulo());
        holder.lblContenido.setText(todo.getContenido());
        holder.lblFecha.setText(todo.getFecha().toString());
        if (todo.isCompletado())
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_on_background);
        else
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_off_background);

        holder.btnCompletado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmaCambioEstado("¿ Estas seguro de cambiar el estado ?",todo).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    private AlertDialog confirmaCambioEstado(String mensaje,ToDo todo){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(mensaje);
        builder.setCancelable(false);

        builder.setNegativeButton("NO",null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                todo.setCompletado(!todo.isCompletado());
                notifyDataSetChanged();
            }
        });

        return builder.create();
    }

    public class ToDoVH extends  RecyclerView.ViewHolder{
        TextView lblTitulo,lblContenido,lblFecha;
        ImageButton btnCompletado;

        public ToDoVH(@NonNull View itemView) {
            super(itemView);
            lblTitulo = itemView.findViewById(R.id.lblTituloToDoModelView);
            lblContenido = itemView.findViewById(R.id.lblContenidoToDoModelView);
            lblFecha = itemView.findViewById(R.id.lblFechaToDoModelView);
            btnCompletado = itemView.findViewById(R.id.btnCompletadoToDoModelView);
        }
    }
}
