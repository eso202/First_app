package com.example.laptophome.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class sqllite extends SQLiteOpenHelper {
    public static final String dbname = "users.db";
    public static final int dbversion = 1;


    public sqllite(Context context) {
        super(context, dbname, null, dbversion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS users(id INTEGER PRIMARY KEY,title TEXT,category TEXT,description TEXT,price INTEGER,image blob)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table IF EXISTS users");
        onCreate(db);

    }


    public ArrayList getrecords() {
        ArrayList l1 = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from users", null);
        laptop_item laptop_item;
        if (cursor.moveToFirst())

        {

            do {

                    int price=cursor.getInt(cursor.getColumnIndex("price"));
                    String title=cursor.getString(cursor.getColumnIndex("title"));
                    String category=cursor.getString(cursor.getColumnIndex("category"));
                    String description=cursor.getString(cursor.getColumnIndex("description"));
                    byte[]image=cursor.getBlob(cursor.getColumnIndex("image"));
                    laptop_item=new laptop_item(title,category,description,image,price);

                        l1.add(laptop_item);

            }
            while (cursor.moveToNext());
        }
        return l1;
    }


    public void addpost(laptop_item laptop_item)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",laptop_item.getTitle());
        contentValues.put("category",laptop_item.getCategory());
        contentValues.put("description",laptop_item.getDescprtion());
        contentValues.put("price",laptop_item.getPrice());
        contentValues.put("image",laptop_item.getImage());
        db.insert("users",null,contentValues);

    }

    public void deletContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from users where id="+Integer.toString(id));

    }

    public void deletalldata() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from users ");

    }
    public void deletabytitle(String title) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from users where title="+title);

}
}