package com.example.ingtips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.snapshot.ChildrenNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PerfilActivity extends AppCompatActivity {

    private int intentos=0;
    private FirebaseAuth mAuth;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ingtips-default-rtdb.firebaseio.com/");

    private TextView nombre,numero;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        mAuth=FirebaseAuth.getInstance();

        nombre = findViewById(R.id.tv_perfil);
        numero = findViewById(R.id.tv_intentos);

        insertarDatos();





    }

    private void insertarDatos(){
        String id= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();


        databaseReference.child("Users").child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    String as =  String.valueOf(task.getResult().child("intentos").getValue(Integer.class));
                    if (task.getResult().child("name").exists()){
                        String nnombre = task.getResult().child("name").getValue(String.class);
                       nombre.setText(nnombre);
                    }
                    else {
                        nombre.setText("");
                    }
                    numero.setText(as);

                }
            }
        });
    }

    public void clickMain(View view){
        Intent intent= new Intent(PerfilActivity.this,MainActivity.class);
        startActivity(intent);

    }



    }
