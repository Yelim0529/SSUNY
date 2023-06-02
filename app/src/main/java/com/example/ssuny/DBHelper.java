package com.example.ssuny;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "alarm.db";

    // DBHelper 생성자
    public DBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    // Alarm Table 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Alarm(name TEXT, day INT, year INT, month INT, dayOfMonth INT, hour INT, minute INT, ampm TEXT, memo TEXT)");
    }

    // Alarm Table Upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("DROP TABLE IF EXISTS Alarm");
        onCreate(db);
    }

    // Alarm Table 데이터 입력
    public void insert(String name, int day, int year, int month, int dayOfMonth, int hour, int minute, String ampm, String memo) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Alarm VALUES('" + name + "', " + day + ", '" +
                year + "','" + month + "','" + dayOfMonth + "','" + hour + "','" + minute + "', '" + ampm +"','" + memo + "')");
        db.close();
    }

    // Alarm Table 데이터 수정
    public void Update(String name, int day, int year, int month, int dayOfMonth, int hour, int minute, String ampm, String memo) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Alarm SET day = " + day + ", year = " + year + ", month = " + month + ", dayOfMonth = " + dayOfMonth + ", hour = " + hour + ", minute = " + minute + ", smpm = " + ampm + ", memo = " + memo + " WHERE NAME = '" + name + "'");
        db.close();
    }

    // Alarm Table 데이터 삭제
    public void Delete(String name) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "NAME = ?";
        String[] whereArgs = { name };
        db.delete("Alarm", whereClause, whereArgs);
        db.close();
    }

    // Alarm Table 조회
    public ArrayList<Time> getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM Alarm", null);
        ArrayList<Time> al = new ArrayList<>();
        while (cursor.moveToNext()) {

            Time time = new Time();

            time.setName(cursor.getString(0));
            time.setReday(cursor.getInt(1));
            time.setYear(cursor.getInt(2));
            time.setMonth(cursor.getInt(3));
            time.setDay(cursor.getInt(4));
            time.setHour(cursor.getInt(5));
            time.setMinute(cursor.getInt(6));
            time.setAm_pm(cursor.getString(7));
            time.setMemo(cursor.getString(8));

            al.add(time);
        }

        return al;
    }
}

