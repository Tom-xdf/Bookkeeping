package com.example.bookkeeping.entity;

public class BillInfo {
    public int id;
    public String date;
    public int type; //订单类型
    public double amount; //金额
    public String remark;  //说明


//    账单收入 ， 0收入 ，1支出
    public static final int BILL_TYPE_INCOME = 0;
    public static final int BILL_TYPE_COST = 1;

    @Override
    public String toString() {
        return "BillInfo{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", remark='" + remark + '\'' +
                '}';
    }
}
