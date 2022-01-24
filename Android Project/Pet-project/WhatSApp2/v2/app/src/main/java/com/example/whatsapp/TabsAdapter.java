package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsapp.Fragment.CharFragment1;
import com.example.whatsapp.Fragment.CharFragment2;
import com.example.whatsapp.Fragment.CharFragment3;

public class TabsAdapter extends FragmentPagerAdapter {

    public TabsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    // переключение фрагментов по нажатию на табы
    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                CharFragment1 fragment1 = new CharFragment1();
                return fragment1;
            case 1:
                CharFragment2 fragment2 = new CharFragment2();
                return fragment2;
            case 2:
                CharFragment3 fragment3 = new CharFragment3();
                return fragment3;
            default:
                return null;
        }

    }

    // кол-во табов
    @Override
    public int getCount() {
        return 3;
    }


    // titiles табов
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "Чаты";
            case 1:
                return "Контакты";
            case 2:
                CharFragment3 fragment3 = new CharFragment3();
                return "Группы";
            default:
                return null;
        }

    }
}
