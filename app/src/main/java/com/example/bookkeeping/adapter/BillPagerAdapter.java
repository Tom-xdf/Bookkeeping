package com.example.bookkeeping.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.bookkeeping.fragment.BillFragment;

public class BillPagerAdapter extends FragmentPagerAdapter {

    private final int mYear;

    public BillPagerAdapter(@NonNull FragmentManager fm, int year) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mYear = year;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
//        如是是9前面就要加一个0，10包括10就不用加
//        9 -> 09 10->10
        int month = position + 1;
        String zeroMonth = month < 10 ? "0" + month : String.valueOf(month);
        String yearMonth = mYear + "-" + zeroMonth;
        Log.d("ning",yearMonth);

        return BillFragment.newInstance(yearMonth);
    }

    @Override
    public int getCount() {
        return 12;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//        position是从0到11的值，一共有十二个月，所以加1
        return (position + 1) + "月份";
    }
}
