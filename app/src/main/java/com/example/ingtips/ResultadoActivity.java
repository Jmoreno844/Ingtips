package com.example.ingtips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ResultadoActivity extends AppCompatActivity {

    private  List<ListaPreguntas> listaPreguntas = new ArrayList<>();
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ingtips-default-rtdb.firebaseio.com/");
    private int intentos=100;


    private FirebaseAuth mAuth;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        final TextView scoreTV = findViewById(R.id.scoreTV);
        final TextView totalScoreTV = findViewById(R.id.totalScoreTV);
        final TextView correctTV = findViewById(R.id.correctTV);
        final TextView incorrectTV = findViewById(R.id.inCorrectTV);
        final AppCompatButton reTakeBtn = findViewById(R.id.reTakeQuizBtn);

        mAuth=FirebaseAuth.getInstance();
        sumarPuntos();



        listaPreguntas = (List<ListaPreguntas>) getIntent().getSerializableExtra("questions");

        totalScoreTV.setText("/"+ listaPreguntas.size());
        scoreTV.setText(getRespuestaCorrecta() +"");
        correctTV.setText(getRespuestaCorrecta() + "");
        incorrectTV.setText(String.valueOf(listaPreguntas.size() - getRespuestaCorrecta()));





        reTakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(ResultadoActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private int getRespuestaCorrecta(){

        int respuestaCorrecta = 0;

        for(int i = 0; i < listaPreguntas.size(); i++){

            int getUserSelectedOption = listaPreguntas.get(i).getRespuestaSeleccionada();
            int getQuestionAnswer = listaPreguntas.get(i).getRespuesta();


            if(getQuestionAnswer == getUserSelectedOption){
                respuestaCorrecta++;
            }
        }

        return respuestaCorrecta;
    }

    private void sumarPuntos(){
        String id= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        Map<String, Object> map=new HashMap<>();

        databaseReference.child("Users").child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Integer as =  task.getResult().child("intentos").getValue(Integer.class) + 1;
                    map.put("intentos",as);
                    map.putAll(map);
                    databaseReference.child("Users").child(id).updateChildren(map).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(ResultadoActivity.this, "Se actualizo el perfil", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}