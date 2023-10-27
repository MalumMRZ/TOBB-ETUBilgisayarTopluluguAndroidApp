package com.yusufmirza.etubilgisayartopluluk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yusufmirza.etubilgisayartopluluk.Fragments.FollowFragment;
import com.yusufmirza.etubilgisayartopluluk.Fragments.MainFragment;
import com.yusufmirza.etubilgisayartopluluk.Fragments.MembersFragment;
import com.yusufmirza.etubilgisayartopluluk.Fragments.PlanFragment;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.AdminPanelEnterence;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.activitypanel.AdminActivityEdit;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.activitypanel.AdminMainActivity;
import com.yusufmirza.etubilgisayartopluluk.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding activityMainBinding;
     FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    boolean control = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());



            setSupportActionBar(activityMainBinding.toolbar); //Action bar olarak toolbarımız ayarlandı.

            activityMainBinding.navView.setNavigationItemSelectedListener(MainActivity.this);

            ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,activityMainBinding.drawerLayout,activityMainBinding.toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
            activityMainBinding.drawerLayout.addDrawerListener(toogle);
            toogle.syncState();

            if(savedInstanceState==null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment(MainActivity.this)).commit();
                activityMainBinding.navView.setCheckedItem(R.id.nav_mainScreen);
            }

            if (user!=null){

                user.reload();
                // NavigationView'ı ve başlık görünümünü alın
                NavigationView navigationView = activityMainBinding.navView;
                View headerView = navigationView.getHeaderView(0);

                // Başlık içindeki TextView'lere erişin ve metinleri değiştirin
                TextView textView1 = headerView.findViewById(R.id.yourEmail);
                TextView textView2 = headerView.findViewById(R.id.yourVerification);
                textView1.setText("Emailiniz : "+ user.getEmail());
                textView2.setText("Doğrulama : "+ (user.isEmailVerified() ?  "Evet" : "Hayır"));
            }





    }

    @Override
    public void onBackPressed() {
        if(activityMainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            activityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.nav_mainScreen){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MainFragment(MainActivity.this)).commit();
        }
        if(item.getItemId()==R.id.nav_ourMembers){ //Üyeleri göster
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MembersFragment(MainActivity.this)).commit();
        }
        if(item.getItemId()==R.id.nav_ourPlans){ //Şimdilik boş bir layout gösterilsin
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PlanFragment(MainActivity.this,MainActivity.this)).commit();
        }

        if (item.getItemId() == R.id.admin_panel&& user !=null && user.isEmailVerified())
        {
            Intent intent = new Intent(MainActivity.this, AdminAraPanel.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.open_account){
            Intent intent = new Intent(MainActivity.this,AdminAuth.class);
            startActivity(intent);

        }

        if(item.getItemId()==R.id.nav_feedBack){ //Klasik email
            activityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + "yusufmrz111@gmail.com"));
            startActivity(intent);


        }
        if(item.getItemId()==R.id.nav_shareUs){ //Düzenlenmesi gereknlere dikkat
            activityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
            String Uri= "https://play.google.com/store/apps/details?id=com.yusufmirza.etubilgisayartopluluk";
            Intent paylasIntent = new Intent(Intent.ACTION_SEND);
            paylasIntent.setType("text/plain");
            paylasIntent.putExtra(Intent.EXTRA_TEXT,Uri);
            startActivity(Intent.createChooser(paylasIntent, "Paylaş"));
        }
        if(item.getItemId()==R.id.nav_followUs){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FollowFragment()).commit();
        }



        activityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }





}