package com.example.recyclerviewalertdialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.recyclerviewalertdialog.Adapters.TodoAdapter;
import com.example.recyclerviewalertdialog.Modelos.ToDo;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;

import com.example.recyclerviewalertdialog.databinding.ActivityMainBinding;

import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<ToDo> todoList;
    private TodoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        todoList = new ArrayList<>();
         //crearToDos();

         adapter = new TodoAdapter(todoList, R.layout.todo_view_model , MainActivity.this);
         binding.contentMain.contenedor.setAdapter(adapter);
         // layoutManager = new LinearLayoutManager(MainActivity.this); //Sin columnas
        layoutManager = new GridLayoutManager(MainActivity.this,2); //Con columnas
         binding.contentMain.contenedor.setLayoutManager(layoutManager);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createtoDo("Nueva Tarea").show();
            }
        });
    }

    private AlertDialog createtoDo(String titulo){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(titulo);

        View contenido = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_todo_alert_dialog,null);
        EditText txtTitulo = contenido.findViewById(R.id.txtTituloToDo);
        EditText txtContenido = contenido.findViewById(R.id.txtContenidoAddTodo);

        builder.setView(contenido);

        builder.setNegativeButton("CANCELAR",null);
        builder.setPositiveButton("AGREGAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ToDo toDo = new ToDo(txtTitulo.getText().toString(),txtContenido.getText().toString());
                todoList.add(toDo);
                adapter.notifyDataSetChanged();
            }
        });

        return builder.create();
    }

    private void crearToDos() {
        for (int i = 0; i < 1000000; i++) {
            todoList.add(new ToDo("Titulo "+i, "Contenido "+i));
        }
    }
}