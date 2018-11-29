package com.example.zhousheng.memorandum;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.zhousheng.memorandum.DB_col.Words;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class WordsDB {
    private static final String TAG = "myTag";

    private static WordsDBHelper mDbHelper;

    private static WordsDB instance=new WordsDB();
    public static WordsDB getWordsDB(){
        return WordsDB.instance;
    }

    private WordsDB() {
        if (mDbHelper == null) {
            mDbHelper = new WordsDBHelper(WordsApplication.getContext());             //获取
        }
    }


    public void close() {         //关闭数据库
        if (mDbHelper != null)
            mDbHelper.close();
    }

    public Words.WordDescription getSingleWord(String id) {    //获得一个事件的全部数据
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String sql = "select * from words where _ID=?";
        Cursor cursor = db.rawQuery(sql, new String[]{id});
        if (cursor.moveToNext()) {
            ;
            Words.WordDescription item = new Words.WordDescription(cursor.getString(cursor.getColumnIndex(Words.Word._ID)),
                    cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_WORD)),
                    cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_MEANING)),
                    cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_SAMPLE)),
                    cursor.getString(cursor.getColumnIndex(Words.Word.COLUME_DATE)));
            return item;
        }
        return null;
    }

    //得到全部单词列表
    public ArrayList<Map<String, String>> getAllWords() {
        if (mDbHelper == null) {
            Log.v(TAG, "WordsDB::getAllWords()");
            return null;
        }
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                Words.Word._ID,
                Words.Word.COLUMN_NAME_WORD,
                Words.Word.COLUME_DATE
        };

        //排序
        String sortOrder = Words.Word.COLUME_DATE + " DESC";
        Cursor c = db.query(
                Words.Word.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        return ConvertCursor(c);
    }

    //将游标转化为事件列表
    private ArrayList<Map<String, String>> ConvertCursor(Cursor cursor) {
        ArrayList<Map<String, String>> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<>();
            map.put(Words.Word._ID, String.valueOf(cursor.getString(cursor.getColumnIndex(Words.Word._ID))));
            map.put(Words.Word.COLUMN_NAME_WORD, cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_WORD)));
            map.put(Words.Word.COLUME_DATE, cursor.getString(cursor.getColumnIndex(Words.Word.COLUME_DATE)));
            result.add(map);
        }
        return result;
    }

    public void Insert(String strWord, String strMeaning, String strSample) {
         String date_sql;
        SimpleDateFormat s_format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        s_format.setTimeZone(TimeZone.getTimeZone("GMT+08"));   //获取北京时间
        date_sql=s_format.format(new Date());
        System.out.println(date_sql);
        System.out.println("1111 "+new Date());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Words.Word.COLUMN_NAME_WORD, strWord);
        values.put(Words.Word.COLUMN_NAME_MEANING, strMeaning);
        values.put(Words.Word.COLUMN_NAME_SAMPLE, strSample);
        values.put(Words.Word.COLUME_DATE,date_sql);
        long newRowId;
        newRowId = db.insert(Words.Word.TABLE_NAME,null, values);
    }


    //使用Sql语句删除单词
    public void DeleteUseSql(String strId) {
        String sql = "delete from words where _id=='"+strId+"'";
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.execSQL(sql);
    }

    //删除单词
    public void Delete(String strId) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String selection = Words.Word._ID + " = ?";
        String[] selectionArgs = {strId};
        db.delete(Words.Word.TABLE_NAME, selection, selectionArgs);
    }


    //使用Sql语句更新单词
    public void UpdateUseSql(String strId, String strWord, String strMeaning, String strSample) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String date_sql;
        SimpleDateFormat s_format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        s_format.setTimeZone(TimeZone.getTimeZone("GMT+08"));   //获取北京时间
        date_sql=s_format.format(new Date());
        String sql = "update words set word=?,meaning=?,sample=?,date=? where _id=?";
        db.execSQL(sql, new String[]{strWord,strMeaning, strSample, date_sql,strId});
    }

    public void Update(String strId, String strWord, String strMeaning, String strSample) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Words.Word.COLUMN_NAME_WORD, strWord);
        values.put(Words.Word.COLUMN_NAME_MEANING, strMeaning);
        values.put(Words.Word.COLUMN_NAME_SAMPLE, strSample);

        String selection = Words.Word._ID + " = ?";
        String[] selectionArgs = {strId};

        int count = db.update(
                Words.Word.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


    //使用Sql语句查找
    public ArrayList<Map<String, String>> SearchUseSql(String strWordSearch) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String sql = "select * from words where word like ? order by word desc";
        Cursor c = db.rawQuery(sql, new String[]{"%" + strWordSearch + "%"});

        return ConvertCursor(c);
    }



}