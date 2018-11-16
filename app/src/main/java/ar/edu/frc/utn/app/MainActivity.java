package ar.edu.frc.utn.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int INTERNET_REQUEST = 2222;
    private static final int NETWORKSTATE_REQUEST = 3333;
    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_b);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.logoutn5);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.INTERNET}, INTERNET_REQUEST);
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, NETWORKSTATE_REQUEST);
            }
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getText(R.string.label_tab1)).setIcon(getDrawable(R.drawable.home)));
        tabLayout.addTab(tabLayout.newTab().setText(getText(R.string.label_tab2)).setIcon(getDrawable(R.drawable.curso)));
        tabLayout.addTab(tabLayout.newTab().setText(getText(R.string.label_tab7)).setIcon(getDrawable(R.drawable.crono)));
        tabLayout.addTab(tabLayout.newTab().setText(getText(R.string.label_tab3)).setIcon(getDrawable(R.drawable.noticias2)));
        tabLayout.addTab(tabLayout.newTab().setText(getText(R.string.label_tab4)).setIcon(getDrawable(R.drawable.contacto)));
        tabLayout.addTab(tabLayout.newTab().setText(getText(R.string.label_tab5)).setIcon(getDrawable(R.drawable.radio)));
        tabLayout.addTab(tabLayout.newTab().setText(getText(R.string.label_tab6)).setIcon(getDrawable(R.drawable.g2m)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setOffscreenPageLimit(tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onBackPressed(){
        if (doubleBackToExitPressedOnce  || getSupportFragmentManager().getBackStackEntryCount() != 0) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.doubleback), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 3000);
    }

    public void acercaDe(View view) {
        Intent intent = new Intent(this, AcercaDeActivity.class);
        startActivity(intent);
    }

    public void salir(View view) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_acerca_de: {
                acercaDe(null);
                return true;
            }
            case R.id.menu_salir: {
                finish();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
}
