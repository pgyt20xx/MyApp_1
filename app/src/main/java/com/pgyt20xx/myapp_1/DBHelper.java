package com.pgyt20xx.myapp_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.pgyt20xx.myapp_1.model.CategoryBean;
import com.pgyt20xx.myapp_1.model.ContentsBean;

import java.io.File;

/**
 * Created by tanida on 2017/05/13.
 */

public class DBHelper {
    public static final String TAG = "DBHelper";
    public SQLiteDatabase sqLiteDatabase;
    private final DBOpenHelper dbOpenHelper;

    private static final String BLANK_STRING = "";
    private static final String TABLE_NAME_CATEGORY = "CATEGORY";
    private static final String TABLE_NAME_CONTENTS = "CONTENTS";


    public DBHelper(final Context context) {
        this.dbOpenHelper = new DBOpenHelper(context);
        establishDb();
    }

    private void establishDb(){
        if(this.sqLiteDatabase == null){
            this.sqLiteDatabase = this.dbOpenHelper.getWritableDatabase();
        }
    }

    /**
     * カテゴリーテーブルのインサート文
     * @param params
     */
    public void insertCategory(CategoryBean params){
        this.sqLiteDatabase.insert(TABLE_NAME_CATEGORY, BLANK_STRING, params.getParams());
    }

    /**
     * コンテンツテーブルのインサート文
     * @param params
     */
    public void insertContents(ContentsBean params){
        this.sqLiteDatabase.insert(TABLE_NAME_CONTENTS, BLANK_STRING, params.getParams());
    }

    public void cleanup(){
        if (this.sqLiteDatabase != null){
            this.sqLiteDatabase.close();
            this.sqLiteDatabase = null;
        }
    }

    /**
     * カテゴリーテーブルのセレクト文
     * @param param
     * @return
     */
    public Cursor selectCategory(String param){
        SQLiteDatabase readDb = dbOpenHelper.getReadableDatabase();
        Cursor cursor = null;
        if(param.isEmpty()){
            String sql = "SELECT id, category_name FROM CATEGORY;";
            cursor = readDb.rawQuery(sql, null);
        }else{
            String sql = "SELECT id, category_name FROM CATEGORY WHERE category_name = '" + param + "';";
            cursor = readDb.rawQuery(sql, new String[]{param});
        }
        return cursor;
    }

    public boolean isDatabaseDelete (final Context context){
        boolean result = false;

        if(this.sqLiteDatabase != null){
            File file = context.getDatabasePath(dbOpenHelper.getDatabaseName());

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                result = this.sqLiteDatabase.deleteDatabase(file);
            }
        }
        return result;
    }
}
