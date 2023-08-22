package com.example.bookkeeping;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookkeeping.Util.DateUtil;
import com.example.bookkeeping.Util.ToastUtil;
import com.example.bookkeeping.database.BillDBHelper;
import com.example.bookkeeping.entity.BillInfo;

import java.util.Calendar;

public class BillAddActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private TextView tv_date;
    private Calendar calendar;
    private RadioGroup rg_type;
    private EditText et_amount;
    private EditText et_remark;
    private BillDBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_add);


        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_option = findViewById(R.id.tv_option);
        tv_title.setText("请填写账单");
        tv_option.setText("账单列表");

        tv_date = findViewById(R.id.tv_date); //日期
        rg_type = findViewById(R.id.rg_type); //账单类型
        et_amount = findViewById(R.id.et_amount); //金额
        et_remark = findViewById(R.id.et_remark);
//        保存按钮设置监听事件
        findViewById(R.id.btn_save).setOnClickListener(this);

        //        设置跳转监听
        tv_option.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);

//        显示当前的日期
        calendar = Calendar.getInstance();
        tv_date.setText(DateUtil.getDate(calendar));
//        点击当前时间会显示出一个dialog弹窗。
        tv_date.setOnClickListener(this);

//        拿到dbHelper实例
        mDBHelper = BillDBHelper.getInstance(this);
//        打开读写
        mDBHelper.openReadLink();
        mDBHelper.openWriteLink();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_date:
//                弹出日期对话框
//                listener:从监听器拿到选择的日期
                DatePickerDialog dialog = new DatePickerDialog(this,
                        this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                break;
            case R.id.btn_save:
//                保存订单信息
//                调用BillInfo
                BillInfo bill = new BillInfo();
                bill.date = tv_date.getText().toString();
                bill.type = rg_type.getCheckedRadioButtonId() == R.id.rb_income ?
                        BillInfo.BILL_TYPE_INCOME : BillInfo.BILL_TYPE_COST;
                bill.remark = et_remark.getText().toString();
                bill.amount = Double.parseDouble(et_amount.getText().toString());
//                如果返回的id大于，那么就添加成功
                if (mDBHelper.save(bill) > 0) {
                    ToastUtil.show(this, "添加账单成功");
                }
                break;
            case R.id.tv_option:
//                跳转到添加账单页面
                Intent intent = new Intent(this,BillPagerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
                case R.id.iv_back:
//                    关闭当前页面
                    finish();
                    break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        设置显示文本
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        tv_date.setText(DateUtil.getDate(calendar));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        关闭数据库的连接
        mDBHelper.closeLink();
    }
}