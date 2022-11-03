package com.example.ingtips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelecionadorActivity extends AppCompatActivity {


    private RecyclerView rvVideos;
    //private ArrayList<Videos> listadoVideos = new ArrayList<>();
    private final List<Seleccionador> listadoVideos = new ArrayList<>();
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ingtips-default-rtdb.firebaseio.com/");

    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);



        tv = findViewById(R.id.textView2);
        rvVideos = findViewById(R.id.rv_listado);
        rvVideos.setLayoutManager(new LinearLayoutManager(this));
        tv.setText("quiz");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                for(DataSnapshot seleccionador : snapshot.child("seleccionador").getChildren()){

                    String titulo = seleccionador.child("uno").getValue(String.class);
                    String url = seleccionador.child("imagen").getValue(String.class);



                    Seleccionador listadovid = new Seleccionador(titulo,url);

                    listadoVideos.add(listadovid);
                }
                AdaptadorCuestionario adaptador = new AdaptadorCuestionario((ArrayList<Seleccionador>) listadoVideos);
                rvVideos.setAdapter(adaptador);

                adaptador.setOnItemClickListener(new AdaptadorCuestionario.OnItemClickListener() {
                    @Override
                    public void onItemClick(Seleccionador videos, int posicion) {
                        Intent intent= new Intent(SelecionadorActivity.this,CuestionarioActivity.class);
                        intent.putExtra("url",videos.getSeleccion().toString());
                        startActivity(intent);

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SelecionadorActivity.this, "Failed to get data from Firebase", Toast.LENGTH_SHORT).show();
            }
        });

    }

}