package com.yusufmirza.etubilgisayartopluluk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.AdminPanelEnterence;
import com.yusufmirza.etubilgisayartopluluk.databinding.AdminAuthLayoutBinding;

public class AdminAuth extends AppCompatActivity {


AdminAuthLayoutBinding adminAuthLayoutBinding;
String email,password,rePass;
FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

FirebaseFirestore firestore = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminAuthLayoutBinding =AdminAuthLayoutBinding.inflate(getLayoutInflater());

        setContentView(adminAuthLayoutBinding.getRoot());

        adminAuthLayoutBinding.imageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        adminAuthLayoutBinding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAuth.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        adminAuthLayoutBinding.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    email = adminAuthLayoutBinding.emailText.getText().toString();
                    password = adminAuthLayoutBinding.passwordText.getText().toString().trim();
                    rePass = adminAuthLayoutBinding.passwordRepeatText.getText().toString().trim();

                    if(!email.equals("") && password.equals(rePass)){

                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {


                            if (firebaseUser != null) {

                                Toast.makeText(AdminAuth.this, "E-postanıza doğrulama emaili gönderildi", Toast.LENGTH_SHORT).show();

                                firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()){
                                            Toast.makeText(AdminAuth.this, firebaseUser.getEmail()+" adresine email gönderildi", Toast.LENGTH_LONG).show();

                                            Intent intent = new Intent(AdminAuth.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);



                                        }else {
                                            Toast.makeText(AdminAuth.this, "Email gönderilemedi", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                            } else {

                                Toast.makeText(AdminAuth.this, "Zaten bu hesapla kayıt oldunuz", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AdminAuth.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminAuth.this, "Kayıt başarısız", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        adminAuthLayoutBinding.resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = adminAuthLayoutBinding.emailText.getText().toString();

                if (!email.equals("")){
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AdminAuth.this, "Mesaj yollandı", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AdminAuth.this, "Mesaj yolla başarısız", Toast.LENGTH_SHORT).show();
                        }
                    }

                });



            }
        }});

        adminAuthLayoutBinding.guestIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminAuth.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });


        adminAuthLayoutBinding.logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                    email = adminAuthLayoutBinding.emailText.getText().toString();
                    password = adminAuthLayoutBinding.passwordText.getText().toString();

                if (!email.equals("")&& !password.equals("") ) {

                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(AdminAuth.this, "Giriş başarılı", Toast.LENGTH_SHORT).show();


                                    Intent intent = new Intent(AdminAuth.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);


                            } else {
                                Toast.makeText(AdminAuth.this, "Şifreniz yanlış ya da internetiniz yok", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
            }
        });

       adminAuthLayoutBinding.sendVerification.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


               firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {

                       if (task.isSuccessful()){
                           Toast.makeText(AdminAuth.this, email+" adresine email gönderildi", Toast.LENGTH_LONG).show();

                           Intent intent = new Intent(AdminAuth.this, MainActivity.class);
                           startActivity(intent);

                       }else {
                           Toast.makeText(AdminAuth.this, "Email gönderilemedi", Toast.LENGTH_SHORT).show();
                       }
                   }
               });




           }
       });

    }
}
