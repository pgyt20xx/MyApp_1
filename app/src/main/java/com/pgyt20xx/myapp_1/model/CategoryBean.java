package com.pgyt20xx.myapp_1.model;

import android.content.ContentValues;

/**
 * Created by tanida on 2017/05/13.
 * カテゴリーテーブルBean
 */
public class CategoryBean {

    /**
     * id
     */
    private int id;

    /**
     * カテゴリー名
     */
    private String category_name;

    /**
     * idを取得
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * idを設定
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * カテゴリー名を取得
     * @return
     */
    public String getCategory_name() {
        return category_name;
    }

    /**
     * カテゴリーを設定
     * @param category_name
     */
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    /**
     * ContentValuesに値を設定して返却
     * @return
     */
    public ContentValues getParams(){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", getId());
        contentValues.put("category_name", getCategory_name());
        return contentValues;
    }
}
