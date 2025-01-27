package com.example.liam.li_bel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    MaterialEditText username, email, password;
    Button BTNRegister;

    FirebaseAuth auth;
    DatabaseReference reference;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        BTNRegister=findViewById(R.id.btn_register);

        auth=FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        BTNRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_username=username.getText().toString();
                String txt_email=email.getText().toString();
                String txt_password=password.getText().toString();

            if(TextUtils.isEmpty(txt_username) ||TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_password)){
                Toast.makeText(RegistrationActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            }else if(txt_password.length()<8){
                Toast.makeText(RegistrationActivity.this, "Password has to be at least 8 characters", Toast.LENGTH_SHORT).show();
            }else if(!isValid(txt_email)){
                Toast.makeText(getApplicationContext(),"Please enter a valid email address", Toast.LENGTH_LONG).show();
            }else{
                progressDialog.setMessage("Please wait.....Registering");
                progressDialog.show();
                register(txt_email, txt_password, txt_username);
            }
            }
        });

    }

    static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    private void register(String email, String password, final String username){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser=auth.getCurrentUser();
                    assert firebaseUser!=null;

                    String userid=firebaseUser.getUid();

                    reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);

                    HashMap<String, String> hashMap=new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("username", username);
                    hashMap.put("imageURL", "default");
                    hashMap.put("status", "offline");
                    hashMap.put("search", username.toLowerCase());


                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                Intent intent=new Intent(RegistrationActivity.this, StartActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                                finish();
                            }
                        }
                    });

                }else{
                    Toast.makeText(RegistrationActivity.this, "You cannot register with this email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
