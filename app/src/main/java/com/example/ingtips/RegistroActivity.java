package com.example.ingtips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistroActivity<DatabaseReference> extends AppCompatActivity {

    private EditText tv1,tv2,tv3;
    private Button btn1;

    private    String name="";
    private  String email="";
    private  String password="";
    private int intentos=0;

    FirebaseAuth mAuth;
    com.google.firebase.database.DatabaseReference mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth=FirebaseAuth.getInstance();
        mdatabase=FirebaseDatabase.getInstance().getReferenceFromUrl("https://ingtips-default-rtdb.firebaseio.com/");
        tv1=(EditText)findViewById(R.id.tv1);
        tv2=(EditText)findViewById(R.id.tv2);
        tv3=(EditText)findViewById(R.id.tv3);

        btn1=(Button) findViewById(R.id.bttt);




        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=tv1.getText().toString();
                email=tv2.getText().toString();
                password=tv3.getText().toString();



                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                    registerUser();
                }
                else {
                    // StyleableToast.makeText(regitrate.this,"debe completar los campos",R.style.exampleToast).show();
                    Toast.makeText(RegistroActivity.this, "debe completar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private   void  registerUser( ){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Map<String, Object> map=new HashMap<>();
                    map.put( "name",name);
                    map.put("email",email);
                    map.put("password",password);
                    map.put("intentos",intentos);

                    String id= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                    mdatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {

                            if (task2.isSuccessful()){
                                startActivity(new Intent(RegistroActivity.this,GoogleActivity.class));
                                finish();
                                // StyleableToast.makeText(regitrate.this,"Registro correctamente",R.style.exampleToast).show();
                                Toast.makeText(RegistroActivity.this, "Registro correctamente", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //StyleableToast.makeText(regitrate.this,"no se pudo crear datos correctamete",R.style.exampleToast).show();
                                Toast.makeText(RegistroActivity.this, "no se pudo crear datos ", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
                else {
                    //  StyleableToast.makeText(regitrate.this,"no se pudo crear datos correctamente",R.style.exampleToast).show();
                    Toast.makeText(RegistroActivity.this, "no se pudo crear datos correctamente", Toast.LENGTH_SHORT).show();
                }


            }

        });

    }

    public void clickLogin(View view){
        Intent intent= new Intent(RegistroActivity.this,GoogleActivity.class);
        startActivity(intent);

    }
}