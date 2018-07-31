package com.example.stoychopetrov.moneycontrol.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "categories_table")
public class CategoryModel {

    @PrimaryKey(autoGenerate = true)
    private long    mId;

    private String  mCategoryName;

    public CategoryModel(String mCategoryName) {
        this.mCategoryName = mCategoryName;
    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public void setCategoryName(String mCategoryName) {
        this.mCategoryName = mCategoryName;
    }
}
