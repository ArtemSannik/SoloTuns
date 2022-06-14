package com.example.solotune;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.solotune.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SingIn extends AppCompatActivity {

    private Button btnSingIn;
    private EditText editPhone, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        btnSingIn = findViewById(R.id.btnSingIn);
        editPhone = findViewById(R.id.editPhone);
        editPassword = findViewById(R.id.editPassword);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference table = db.getReference("User");

        btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(editPhone.getText().toString()).exists()){
                        User user = snapshot.child(editPhone.getText().toString()).getValue(User.class);
                        if(user.getPassword().equals(editPassword.getText().toString())){
                            Toast.makeText(SingIn.this, "Успешно Авторизован", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(SingIn.this, "Не успешно Авторизован", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(SingIn.this, "Такого пользователя нет", Toast.LENGTH_SHORT).show();
                    }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(SingIn.this, "Нет Интеренет Соединения", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}