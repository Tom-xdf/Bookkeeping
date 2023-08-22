package com.example.bookkeeping.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bookkeeping.entity.BillInfo;

import java.util.ArrayList;
import java.util.List;

public class BillDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Bill.db";//数据库名字
    //    商品信息表
    private static final String TABLE_BILLS_INFO = "bill_info";//数据库表名
    private static final int DB_VERSION = 1;//版本名
    private static BillDBHelper mHelper = null;
    private SQLiteDatabase mRDB = null;//SQLiteDatabase,读的实例
    private SQLiteDatabase mWDB = null;//SQLiteDAtabase,写的实例

    private BillDBHelper(Context context) {
        super(context,
                DB_NAME,
                null,
                DB_VERSION);
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static BillDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new BillDBHelper(context);
        }
        return mHelper;
    }

    // 打开数据库的读连接
    public SQLiteDatabase openReadLink() {
        if (mRDB == null || !mRDB.isOpen()) {
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    // 打开数据库的写连接
    public SQLiteDatabase openWriteLink() {
        if (mWDB == null || !mWDB.isOpen()) {
            mWDB = mHelper.getWritableDatabase();
        }
        return mWDB;
    }

    // 关闭数据库连接
    public void closeLink() {
        if (mRDB != null && mRDB.isOpen()) {
            mRDB.close();
            mRDB = null;
        }

        if (mWDB != null && mWDB.isOpen()) {
            mWDB.close();
            mWDB = null;
        }
    }

    // 创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建账单信息表
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_BILLS_INFO +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " date VARCHAR NOT NULL," +
                " type INTEGER NOT NULL," +
                " amount DOUBLE NOT NULL," +
                " remark VARCHAR NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
//    保存一条订单记录
    public long save(BillInfo bill){
        ContentValues cv = new ContentValues();
        cv.put("date",bill.date);
        cv.put("type",bill.type);
        cv.put("amount",bill.amount);
        cv.put("remark",bill.remark);
//        保存订单记录
        return mWDB.insert(TABLE_BILLS_INFO,null,cv);
    }

//    生成一个从数据库里面查出 fragment的数据方法，查询账单
    @SuppressLint("Range")
    public List<BillInfo> queryByMonth(String yearMonth){
        List<BillInfo> list = new ArrayList<>();
//        查询出指定年份的所有数据比如：2035-09-12
//        where date like:模糊查询
//        select * from bill_info where date like '2035-09%'
        String sql = "select * from "+TABLE_BILLS_INFO+" where date like '"+yearMonth+"%'";
        Log.d("ning",sql);
//        查询这个数据库里面的数据
        Cursor cursor = mRDB.rawQuery(sql, null);
        while (cursor.moveToNext()){
            BillInfo bill = new BillInfo();
//            根据id来查询数据库存储的数据
            bill.id = cursor.getInt(cursor.getColumnIndex("_id"));
            bill.date = cursor.getString(cursor.getColumnIndex("date"));
            bill.type = cursor.getInt(cursor.getColumnIndex("type"));
            bill.amount = cursor.getDouble(cursor.getColumnIndex("amount"));
            bill.remark = cursor.getString(cursor.getColumnIndex("remark"));
            list.add(bill);
        }
        return list;
    }
}
