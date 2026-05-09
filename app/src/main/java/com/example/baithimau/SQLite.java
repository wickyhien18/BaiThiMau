package com.example.baithimau;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class SQLite extends SQLiteOpenHelper{
    public static final String DbName = "SqlLite";
    public static final int DBVersion = 1;
    public static final String TABLE_NAME = "Contract";
    public static final String COL_ID = "Id";
    public static final String COL_Name = "Name";
    public static final String COL_PhoneNumber = "PhoneNumber";

    public SQLite(Context context) {
        super(context, DbName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_Name + " TEXT, " +
                COL_PhoneNumber + " TEXT)";
        db.execSQL(sql);

        for (int i = 1; i < 7; i++) {
            Contract contract = new Contract();
            contract.setId(i);
            contract.setName("Nguyễn Văn " + ('A' + i));
            contract.setPhoneNumber("098689925 "+ i);
            insert(contract);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public ArrayList<Contract> getAll() {
        ArrayList<Contract> contracts = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
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
        long result = db.insert(TABLE_NAME, null, value);
        db.close();
        return result;
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
}
