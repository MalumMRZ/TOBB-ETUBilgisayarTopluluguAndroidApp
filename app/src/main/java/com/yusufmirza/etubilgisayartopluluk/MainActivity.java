package com.yusufmirza.etubilgisayartopluluk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.yusufmirza.etubilgisayartopluluk.Fragments.FollowFragment;
import com.yusufmirza.etubilgisayartopluluk.Fragments.MainFragment;
import com.yusufmirza.etubilgisayartopluluk.Fragments.MembersFragment;
import com.yusufmirza.etubilgisayartopluluk.Fragments.PlanFragment;
import com.yusufmirza.etubilgisayartopluluk.adminpanel.AdminAuth;
import com.yusufmirza.etubilgisayartopluluk.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding activityMainBinding;

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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PlanFragment()).commit();
        }
        if (item.getItemId() == R.id.admin_panel){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AdminAuth(MainActivity.this)).commit();
        }
        if(item.getItemId()==R.id.nav_feedBack){ //Klasik email
            activityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + "yusufmrz111@gmail.com"));
            startActivity(intent);


        }
        if(item.getItemId()==R.id.nav_shareUs){ //Düzenlenmesi gereknlere dikkat
            activityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
            String Uri= "https://play.google.com/store/apps/details?id=com.yusufmirza.theyksproject";
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