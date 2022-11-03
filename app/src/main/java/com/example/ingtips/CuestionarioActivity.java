package com.example.ingtips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CuestionarioActivity extends AppCompatActivity {

    private final List<ListaPreguntas> listaPreguntas = new ArrayList<>();

    private TextView contador;

    private RelativeLayout option1Layout, option2Layout, option3Layout, option4Layout;
    private TextView option1TV, option2TV, option3TV, option4TV;
    private ImageView option1Icon, option2Icon, option3Icon, option4Icon;

    private  TextView questionTV;

    private TextView totalQuestionTV;
    private TextView currentQuestion;

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ingtips-default-rtdb.firebaseio.com/");

    private CountDownTimer countDownTimer;

    private int posicionPregunta = 0;

    private int opcionSeleccionada = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionario);

        contador = findViewById(R.id.quizTimer);

        option1Layout = findViewById(R.id.option1Layout);
        option2Layout = findViewById(R.id.option2Layout);
        option3Layout = findViewById(R.id.option3Layout);
        option4Layout = findViewById(R.id.option4Layout);

        option1TV = findViewById(R.id.option1TV);
        option2TV = findViewById(R.id.option2TV);
        option3TV = findViewById(R.id.option3TV);
        option4TV = findViewById(R.id.option4TV);

        option1Icon = findViewById(R.id.option1Icon);
        option2Icon = findViewById(R.id.option2Icon);
        option3Icon = findViewById(R.id.option3Icon);
        option4Icon = findViewById(R.id.option4Icon);

        questionTV = findViewById(R.id.questionTV);

        totalQuestionTV = findViewById(R.id.totalQuestionsTV);
        currentQuestion = findViewById(R.id.currentQuestionTV);

        final AppCompatButton nextBtn = findViewById(R.id.nextQuestionBtn);

        String url = getIntent().getStringExtra("url");


        InstruccionesActivity instrucciones = new InstruccionesActivity(CuestionarioActivity.this);
        instrucciones.setCancelable(false);
        instrucciones.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        instrucciones.show();



        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                final int tiempoQuiz = Integer.parseInt(snapshot.child("time").getValue(String.class));

                switch(url){
                    case "quiz 1":

                        for(DataSnapshot questions : snapshot.child("questions").getChildren()){

                            String getQuestion = questions.child("question").getValue(String.class);
                            String getOption1 = questions.child("option1").getValue(String.class);
                            String getOption2 = questions.child("option2").getValue(String.class);
                            String getOption3 = questions.child("option3").getValue(String.class);
                            String getOption4 = questions.child("option4").getValue(String.class);
                            int getAnswer = Integer.parseInt(questions.child("answer").getValue(String.class));


                            ListaPreguntas listaPreguntas = new ListaPreguntas(getQuestion, getOption1, getOption2, getOption3, getOption4, getAnswer);


                            CuestionarioActivity.this.listaPreguntas.add(listaPreguntas);
                        }

                        break;
                    case "quiz 2":
                        for(DataSnapshot questions2 : snapshot.child("questions2").getChildren()){

                            String getQuestion = questions2.child("question").getValue(String.class);
                            String getOption1 = questions2.child("option1").getValue(String.class);
                            String getOption2 = questions2.child("option2").getValue(String.class);
                            String getOption3 = questions2.child("option3").getValue(String.class);
                            String getOption4 = questions2.child("option4").getValue(String.class);
                            int getAnswer = Integer.parseInt(questions2.child("answer").getValue(String.class));


                            ListaPreguntas listaPreguntas = new ListaPreguntas(getQuestion, getOption1, getOption2, getOption3, getOption4, getAnswer);


                            CuestionarioActivity.this.listaPreguntas.add(listaPreguntas);
                        }

                        break;

                }




                totalQuestionTV.setText("/"+ listaPreguntas.size());


                startQuizTimer(tiempoQuiz);


                seleccionarPregunta(posicionPregunta);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CuestionarioActivity.this, "Failed to get data from Firebase", Toast.LENGTH_SHORT).show();
            }
        });



        option1Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                opcionSeleccionada = 1;


                selectOpciones(option1Layout, option1Icon);
            }
        });
        option2Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                opcionSeleccionada = 2;


                selectOpciones(option2Layout, option2Icon);
            }
        });
        option3Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                opcionSeleccionada = 3;


                selectOpciones(option3Layout, option3Icon);
            }
        });
        option4Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                opcionSeleccionada = 4;


                selectOpciones(option4Layout, option4Icon);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(opcionSeleccionada != 0){


                    listaPreguntas.get(posicionPregunta).setRespuestaSeleccionada(opcionSeleccionada);


                    opcionSeleccionada = 0;
                    posicionPregunta++;


                    if(posicionPregunta < listaPreguntas.size()){
                        seleccionarPregunta(posicionPregunta);
                    }
                    else{


                        countDownTimer.cancel();
                        finishQuiz();
                    }
                }
                else{
                    Toast.makeText(CuestionarioActivity.this, "Escoge una opcion", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


    private void finishQuiz(){


        Intent intent = new Intent(CuestionarioActivity.this, ResultadoActivity.class);


        Bundle bundle = new Bundle();
        bundle.putSerializable("questions", (Serializable) listaPreguntas);


        intent.putExtras(bundle);


        startActivity(intent);


        finish();
    }

    private void startQuizTimer(int maximoTiempoSegundos){

        countDownTimer = new CountDownTimer(maximoTiempoSegundos * 1000, 1000) {
            @Override
            public void onTick(long millisMientrasAcaba) {

                long getHour = TimeUnit.MILLISECONDS.toHours(millisMientrasAcaba);
                long getMinute = TimeUnit.MILLISECONDS.toMinutes(millisMientrasAcaba);
                long getSecond = TimeUnit.MILLISECONDS.toSeconds(millisMientrasAcaba);

                String generateTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", getHour,
                        getMinute - TimeUnit.HOURS.toMinutes(getHour),
                        getSecond - TimeUnit.MINUTES.toSeconds(getMinute));

                contador.setText(generateTime);
            }

            @Override
            public void onFinish() {


                finishQuiz();
            }
        };


        countDownTimer.start();
    }

    private void seleccionarPregunta(int preguntaPosicionLista){


        resetOpciones();


        questionTV.setText(listaPreguntas.get(preguntaPosicionLista).getPregunta());
        option1TV.setText(listaPreguntas.get(preguntaPosicionLista).getOpcion1());
        option2TV.setText(listaPreguntas.get(preguntaPosicionLista).getOpcion2());
        option3TV.setText(listaPreguntas.get(preguntaPosicionLista).getOpcion3());
        option4TV.setText(listaPreguntas.get(preguntaPosicionLista).getOpcion4());


        currentQuestion.setText("Pregunta "+(preguntaPosicionLista+1));
    }

    private void resetOpciones(){

        option1Layout.setBackgroundResource(R.drawable.round_back_white50_10);
        option2Layout.setBackgroundResource(R.drawable.round_back_white50_10);
        option3Layout.setBackgroundResource(R.drawable.round_back_white50_10);
        option4Layout.setBackgroundResource(R.drawable.round_back_white50_10);

        option1Icon.setImageResource(R.drawable.round_back_white50_100);
        option2Icon.setImageResource(R.drawable.round_back_white50_100);
        option3Icon.setImageResource(R.drawable.round_back_white50_100);
        option4Icon.setImageResource(R.drawable.round_back_white50_100);
    }

    private void selectOpciones(RelativeLayout seleccionarOpcionesLayout, ImageView seleccionarOpcionesIcon){


        resetOpciones();

        seleccionarOpcionesIcon.setImageResource(R.drawable.check_icon);
        seleccionarOpcionesLayout.setBackgroundResource(R.drawable.round_back_selected_option);
    }
}
