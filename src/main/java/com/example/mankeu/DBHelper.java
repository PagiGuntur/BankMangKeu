package com.example.mankeu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper  extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "mangkeu.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE transaksi(_id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, inout TEXT, label TEXT, value INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS transaksi");
        onCreate(DB);
    }

    public Boolean insertTransaksi(String date, String inout, String label, int value){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("inout", inout);
        contentValues.put("label", label);
        contentValues.put("value", value);
        long result = DB.insert("transaksi", null, contentValues);
        if (result==-1){
            return false;
        }else  {
            return true;
        }
    }

    public Boolean updateTransaksi(String id, String date, String label){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("label", label);
        Cursor cursor = DB.rawQuery("SELECT * FROM transaksi where _id = ?", new String[] {id});
        if (cursor.getCount()>0){
            long result = DB.update("transaksi", contentValues, "_id=?", new String[] {id});
            if (result==-1){
                return false;
            }else  {
                return true;
            }
        }else{
            return false;
        }
    }
    public Boolean deleteTransaksi(String id){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM transaksi where _id = ?", new String[] {id});
        if (cursor.getCount()>0){
            long result = DB.delete("transaksi", "_id=?", new String[] {id});
            if (result==-1){
                return false;
            }else  {
                return true;
            }
        }else{
            return false;
        }
    }
    public Cursor getPengeluaranByLabel(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = null;
        if (DB != null){
            cursor = DB.rawQuery("SELECT inout, label, SUM(value) AS total FROM transaksi GROUP BY label", null);
        }
        return cursor;
    }
    public Cursor getData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = null;
        if (DB != null){
            cursor = DB.rawQuery("SELECT * FROM transaksi ORDER BY _id DESC", null);
        }
        return cursor;
    }
}
