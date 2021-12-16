package com.nagaki.vuhoang.androidintern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nagaki.vuhoang.androidintern.Model.User;

public class LoginActivity extends AppCompatActivity {

    protected EditText txt_id,txt_pass;
    protected MaterialButton btn_login, btn_register;
    String id = "", pass="";

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getId();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        btn_login.setOnClickListener(v -> {

            id = txt_id.getText().toString();
            pass = txt_pass.getText().toString();

            myRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        User u = snapshot.getValue(User.class);
                        assert u != null;
                        // showToast(v.getContext(), u.getPassword());
                        if(u.getPassword().equals(pass)){
                            showToast(v.getContext(),"Login success");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            finish();
                            startActivity(intent);
                        }else{
                            showToast(v.getContext(), "Password is not correct");
                        }

                    }else{
                        showToast(v.getContext(), "ID user not exit");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    throw error.toException();
                }
            });
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void showToast(Context context, String mess){
        Toast t = Toast.makeText(context,mess, Toast.LENGTH_LONG);
        t.show();
    }

    public void getId(){
        txt_id = (EditText) findViewById(R.id.id);
        txt_pass = (EditText) findViewById(R.id.password);
        btn_login = (MaterialButton) findViewById(R.id.btn_login);
        btn_register = (MaterialButton) findViewById(R.id.btn_register);
    }
}