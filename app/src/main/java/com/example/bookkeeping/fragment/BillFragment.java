package com.example.bookkeeping.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bookkeeping.R;
import com.example.bookkeeping.adapter.BillListAdapter;
import com.example.bookkeeping.database.BillDBHelper;
import com.example.bookkeeping.entity.BillInfo;

import java.util.List;

public class BillFragment extends Fragment {



    public static BillFragment newInstance(String yearMonth) {
        BillFragment fragment = new BillFragment();
        Bundle args = new Bundle();
        args.putString("yearMonth",yearMonth);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        ListView lv_bill = view.findViewById(R.id.lv_bill);
//        查出这个集合放入lv_bill里面
        BillDBHelper mDBHelper = BillDBHelper.getInstance(getContext());
        Bundle arguments = getArguments();
//        拿到年和月份的时间
        String yearMonth = arguments.getString("yearMonth");
        List<BillInfo> billInfoList = mDBHelper.queryByMonth(yearMonth);
        BillListAdapter adapter = new BillListAdapter(getContext(),billInfoList);
//        给lv_bill设置适配器
        lv_bill.setAdapter(adapter);
        return view;
    }
}