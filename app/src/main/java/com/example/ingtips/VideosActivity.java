package com.example.ingtips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VideosActivity extends AppCompatActivity {

    private RecyclerView rvVideos;
    //private ArrayList<Videos> listadoVideos = new ArrayList<>();
    private final  List<Videos> listadoVideos = new ArrayList<>();
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ingtips-default-rtdb.firebaseio.com/");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);



        rvVideos = findViewById(R.id.rv_listado);
        rvVideos.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                for(DataSnapshot videos : snapshot.child("videos").getChildren()){

                    String titulo = videos.child("titulo").getValue(String.class);
                    String urlImagen = videos.child("urlImagen").getValue(String.class);
                    String imagen = videos.child("imagen").getValue(String.class);

                    // creating questions list object and add details

                    Videos listadovid = new Videos(titulo,urlImagen,imagen);



                    listadoVideos.add(listadovid);
                }
                AdaptadorPersonalizado adaptador = new AdaptadorPersonalizado((ArrayList<Videos>) listadoVideos);
                rvVideos.setAdapter(adaptador);

                adaptador.setOnItemClickListener(new AdaptadorPersonalizado.OnItemClickListener() {
                    @Override
                    public void onItemClick(Videos videos, int posicion) {
                        Intent intent= new Intent(VideosActivity.this,WebviewActivity.class);
                        intent.putExtra("url",videos.getUrlImagen().toString());
                        startActivity(intent);

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VideosActivity.this, "Failed to get data from Firebase", Toast.LENGTH_SHORT).show();
            }
        });

    }




}