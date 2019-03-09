package ar.edu.frc.utn.app.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Usuario- on 20/10/2016.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs = 0;
    String[] tabtitles = {"1","2","3","4","5","6"};

    public PagerAdapter(FragmentManager fm, int NumTabs) {
        super(fm);
        this.mNumOfTabs = NumTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                //TabFragment1 tab1 = new TabFragment1();
                FragmentEdVirtual tab1 = new FragmentEdVirtual();
                return tab1;
            case 1:
                //return new Fragment();
                //FragmentCrono tab2 = new FragmentCrono();
                FragmentCursos tab2 = new FragmentCursos();
                return tab2;
            case 2:
                //return new Fragment();
                FragmentCrono tab3 = new FragmentCrono();
                return tab3;
            case 3:
                //return new Fragment();
                FragmentNoticias tab4 = new FragmentNoticias();
                //FragmentPrensa tab4 = new FragmentPrensa();
                return tab4;
            case 4:
                //return new Fragment();
                FragmentContacto tab5 = new FragmentContacto();
                return tab5;
            case 5:
                FragmentRadio tab6 = new FragmentRadio();
                return tab6;
            case 6:
                FragmentG2M tab7 = new FragmentG2M();
                return tab7;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.mNumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
}
