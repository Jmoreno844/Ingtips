package com.example.ingtips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private ImageView ivPrincipal;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ingtips-default-rtdb.firebaseio.com/");


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.it_id:
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(MainActivity.this, GoogleActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

            case R.id.it_pf:
                Intent i = new Intent(MainActivity.this, PerfilActivity.class);
                startActivity(i);
                break;


        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivPrincipal= findViewById(R.id.imageView);



        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String url =snapshot.child("forum").getValue(String.class);



                Picasso.get().load(url).resize(250,300).centerCrop().error(R.drawable.ic_launcher_background).into(ivPrincipal);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to get data from Firebase", Toast.LENGTH_SHORT).show();
            }
        });




    }

    public void clickIngresar(View view){
        Intent intent= new Intent(MainActivity.this,VideosActivity.class);
        startActivity(intent);

    }

    public void clickCuestionario(View view){
        Intent intent= new Intent(MainActivity.this,SelecionadorActivity.class);
        startActivity(intent);

    }






    public void onClick(View view){
        switch (view.getId()){
            case R.id.imageView:


                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        String url =snapshot.child("cuestionario").getValue(String.class);

                        Intent intent= new Intent(MainActivity.this,WebviewActivity.class);

                        intent.putExtra("url",url);
                        startActivity(intent);

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MainActivity.this, "Failed to get data from Firebase", Toast.LENGTH_SHORT).show();
                    }
                });



                break;
            default:
                break;
        }
    }

}