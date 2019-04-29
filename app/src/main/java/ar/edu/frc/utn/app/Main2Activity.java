package ar.edu.frc.utn.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import ar.edu.frc.utn.app.Fragments.FragmentCsBasicas;
import ar.edu.frc.utn.app.Fragments.FragmentContacto;
import ar.edu.frc.utn.app.Fragments.FragmentCursos;
import ar.edu.frc.utn.app.Fragments.FragmentG2M;
import ar.edu.frc.utn.app.Fragments.FragmentHome;
import ar.edu.frc.utn.app.Fragments.FragmentEdVirtual;
import ar.edu.frc.utn.app.Fragments.FragmentMore2;
import ar.edu.frc.utn.app.Fragments.FragmentRadio;
import ar.edu.frc.utn.app.Fragments.FragmentCrono;
import ar.edu.frc.utn.app.Fragments.FragmentNoticias;
import ar.edu.frc.utn.app.Fragments.FragmentVideoConferencia;
import ar.edu.frc.utn.app.Fragments.MoreOptions;

public class Main2Activity extends AppCompatActivity implements FragmentMore2.OnListFragmentInteractionListener {
    private static final int INTERNET_REQUEST = 2222;
    private static final int NETWORKSTATE_REQUEST = 3333;
    private boolean doubleBackToExitPressedOnce;

    public static final String TAG_FRAGMENT_VIRTUAL = "fragment_inicio";
    public static final String TAG_FRAGMENT_NOTICIAS = "fragment_noticias";
    public static final String TAG_FRAGMENT_CURSOS = "fragment_cursos";
    public static final String TAG_FRAGMENT_CRONOGRAMA = "fragment_cronograma";
    public static final String TAG_FRAGMENT_GOTOMEETING = "fragment_gotomeeting";
    public static final String TAG_FRAGMENT_RADIO = "fragment_radio";
    public static final String TAG_FRAGMENT_CSBASICAS = "fragment_csbasicas";
    public static final String TAG_FRAGMENT_MORE = "fragment_more";
    public static final String TAG_FRAGMENT_CONTACTO = "fragment_contacto";
    public static final String TAG_FRAGMENT_VIDEOCONF = "fragment_video_conferencia";
    public static final String TAG_FRAGMENT_HOME = "fragment_home";

    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private String currentFragmentTag = "";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    openFragmentByTagName(TAG_FRAGMENT_HOME);
                    return true;
                case R.id.navigation_csbasicas:
                    openFragmentByTagName(TAG_FRAGMENT_CSBASICAS);
                    return true;
                case R.id.navigation_virtual:
                    openFragmentByTagName(TAG_FRAGMENT_VIRTUAL);
                    return true;
                case R.id.navigation_noticias:
                    openFragmentByTagName(TAG_FRAGMENT_NOTICIAS);
                    return true;
                case R.id.navigation_more:
                    openFragmentByTagName(TAG_FRAGMENT_MORE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.logoutn7);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();

        // loadFragment(new FragmentEdVirtual(), TAG_FRAGMENT_VIRTUAL);

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

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public boolean loadFragment(Fragment fragment, String tag) {
        if (fragment != null && !fragment.equals(currentFragment)) {
            if (fragmentManager.findFragmentByTag(tag) == null) {
                if (currentFragment != null) {
                    fragmentManager
                            .beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.fragment_container, fragment, tag)
                            .commit();
                } else {
                    fragmentManager
                            .beginTransaction()
                            .add(R.id.fragment_container, fragment, tag)
                            .commit();
                }
            } else {
                fragmentManager
                        .beginTransaction()
                        .hide(currentFragment)
                        .show(fragment)
                        .commit();
            }
            currentFragment = fragment;
            currentFragmentTag = tag;
            return true;
        }
        return false;
    }


    public void openFragment(View view) {
        openFragmentByTagName(view.getTag().toString());
    }

    public void openFragmentByTagName(String tag) {
        Fragment fragment;
        String fragmentTag;
        fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = createFragment(tag);
        }
        loadFragment(fragment, tag);
    }

    private Fragment createFragment(String tag) {
        switch (tag) {
            case TAG_FRAGMENT_GOTOMEETING: return new FragmentG2M();
            case TAG_FRAGMENT_RADIO: return new FragmentRadio();
            case TAG_FRAGMENT_CURSOS: return new FragmentCursos();
            case TAG_FRAGMENT_CONTACTO: return new FragmentContacto();
            case TAG_FRAGMENT_VIDEOCONF: return new FragmentVideoConferencia();
            case TAG_FRAGMENT_VIRTUAL: return new FragmentEdVirtual();
            case TAG_FRAGMENT_CRONOGRAMA: return new FragmentCrono();
            case TAG_FRAGMENT_NOTICIAS: return FragmentNoticias.newInstance();
            case TAG_FRAGMENT_CSBASICAS: return new FragmentCsBasicas();
            case TAG_FRAGMENT_MORE: return FragmentMore2.newInstance(1);
            case TAG_FRAGMENT_HOME: return new FragmentHome();
            default: return new FragmentHome();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!currentFragmentTag.isEmpty() && fragmentManager.findFragmentByTag(currentFragmentTag) != null)
            fragmentManager.findFragmentByTag(currentFragmentTag).setRetainInstance(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!currentFragmentTag.isEmpty() && fragmentManager.findFragmentByTag(currentFragmentTag) != null)
            fragmentManager.findFragmentByTag(currentFragmentTag).getRetainInstance();
        else {
            if (currentFragmentTag.isEmpty()) {
                openFragmentByTagName(TAG_FRAGMENT_HOME);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("currentFragmentTag", currentFragmentTag);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            currentFragmentTag = savedInstanceState.getString("currentFragmentTag", "");
            currentFragment = fragmentManager.findFragmentByTag(currentFragmentTag);
        }
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

    @Override
    public void onListFragmentInteraction(MoreOptions.MoreItem item) {
        //
        openFragmentByTagName(item.FragmentTag);
    }
}
