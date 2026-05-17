package com.example.baithimau;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class SQLite extends SQLiteOpenHelper{
    public static final String DbName = "SqlLite";
    public static final int DBVersion = 3;
    public static final String TABLE_NAME = "Contract";
    public static final String COL_ID = "Id";
    public static final String COL_Name = "Name";
    public static final String COL_PhoneNumber = "PhoneNumber";
    public SQLiteDatabase myDB;


    public SQLite(@Nullable Context context) {
        super(context, DbName, null, DBVersion);
    }

    public SQLite(@Nullable Context context,@Nullable String name,@Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_Name + " TEXT, " +
                COL_PhoneNumber + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public void OpenDB() {
        if (myDB == null || !myDB.isOpen()) {
            myDB = this.getWritableDatabase();
        }
    }

    public void CloseDB() {
        if (myDB != null && myDB.isOpen()) {
            myDB.close();
        }
    }

    public ArrayList<Contract> getAll() {
        ArrayList<Contract> contracts = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_Name;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String phoneNumber = cursor.getString(2);
                Contract contract = new Contract(id, name, phoneNumber);
                contracts.add(contract);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return contracts;
    }

    public long insert(Contract contract) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(COL_Name, contract.getName());
        value.put(COL_PhoneNumber, contract.getPhoneNumber());
        long id = db.insert(TABLE_NAME, null, value);
        if (id != -1) {
            contract.setId((int) id);
        }
        db.close();
        return id;
    }


    public long update(Contract contract) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(COL_Name, contract.getName());
        value.put(COL_PhoneNumber, contract.getPhoneNumber());
        long result = db.update(TABLE_NAME, value, COL_ID + "=?", new String[]{String.valueOf(contract.getId())});
        db.close();
        return result;
    }

    public long delete(Contract contract) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(contract.getId())});
        db.close();
        return result;
    }

    public ArrayList<Contract> search(String keyword) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_Name + " LIKE ? OR " + COL_PhoneNumber + " LIKE ?" + "ORDER BY " + COL_Name;
        String[] args = {"%" + keyword + "%", "%" + keyword + "%"};
        Cursor cursor = db.rawQuery(sql, args);
        ArrayList<Contract> contracts = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String phoneNumber = cursor.getString(2);
                Contract contract = new Contract(id, name, phoneNumber);
                contracts.add(contract);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contracts;
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }
}
