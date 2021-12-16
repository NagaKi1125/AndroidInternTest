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
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nagaki.vuhoang.androidintern.Model.User;

public class RegisterActivity extends AppCompatActivity {

    protected EditText txt_id,txt_pass,txt_re_pass, txt_email, txt_birthday;
    protected MaterialButton btn_login, btn_register, btn_checkID;
    String id = "", pass ="", re_pass="", email ="", birthday ="";
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getId();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();


        btn_login.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
        btn_register.setEnabled(false);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getText();
                if(pass.equals(re_pass)){
                    User user = new User(pass, birthday, email);
                    myRef.child(id).setValue(user);
                    showToast(v.getContext(),"Sign up success");
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    finish();
                    startActivity(intent);
                }else{
                    showToast(v.getContext(),"Re-password not match");
                }

            }
        });

        btn_checkID.setOnClickListener(v->{
            id = txt_id.getText().toString().trim();
            myRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        showToast(v.getContext(), "The ID already exits. Please choose another ones");
                    }else{
                        showToast(v.getContext(), "You can use this ID");
                        btn_register.setEnabled(true);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    throw error.toException();
                }
            });
        });

    }

    public void showToast(Context context, String mess){
        Toast t = Toast.makeText(context,mess, Toast.LENGTH_LONG);
        t.show();
    }

    public void getText(){
        id = txt_id.getText().toString();
        email = txt_email.getText().toString();
        pass = txt_pass.getText().toString();
        re_pass = txt_re_pass.getText().toString();
        birthday = txt_birthday.getText().toString();
    }

    public void getId(){
        txt_id = (EditText) findViewById(R.id.id);
        txt_pass = (EditText) findViewById(R.id.pass);
        txt_re_pass = (EditText) findViewById(R.id.re_pass);
        txt_email = (EditText) findViewById(R.id.email);
        txt_birthday = (EditText) findViewById(R.id.birthday);
        btn_login = (MaterialButton) findViewById(R.id.btn_login);
        btn_register = (MaterialButton) findViewById(R.id.btn_register);
        btn_checkID = (MaterialButton) findViewById(R.id.btn_checkID);
    }
}