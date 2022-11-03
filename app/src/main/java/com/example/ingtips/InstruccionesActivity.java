package com.example.ingtips;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InstruccionesActivity extends Dialog {

    private int puntosInstrucciones = 0;


    public InstruccionesActivity(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrucciones);

        final AppCompatButton continuarBtn = findViewById(R.id.continueBtn);
        final TextView instrucionesTV = findViewById(R.id.instructionsTV);

        setPuntosInstrucciones(instrucionesTV, "1. Tendras maximo 5 minutos para responder al quiz.");
        setPuntosInstrucciones(instrucionesTV, "2. Ganaras un punto por cada respuesta correcta.");

        continuarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void setPuntosInstrucciones(TextView instrucionesTV, String puntosInstrucciones){

        if(this.puntosInstrucciones == 0){
            instrucionesTV.setText(puntosInstrucciones);
        }
        else{
            instrucionesTV.setText(instrucionesTV.getText()+"\n\n"+puntosInstrucciones);
        }

        this.puntosInstrucciones++;
    }


}