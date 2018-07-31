package com.example.stoychopetrov.moneycontrol.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "income_expenses_table")
public class IncomeExpensesModel {

    @PrimaryKey(autoGenerate = true)
    private long    mId;

    private double  mAmount;
    private String  mDate;
    private String  mDescription;
    private boolean mIsDebit;

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public double getAmount() {
        return mAmount;
    }

    public void setAmount(double mAmount) {
        this.mAmount = mAmount;
    }

    public boolean getIsDebit() {
        return mIsDebit;
    }

    public void setIsDebit(boolean mIsDebit) {
        this.mIsDebit = mIsDebit;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
