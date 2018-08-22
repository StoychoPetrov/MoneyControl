package com.example.stoychopetrov.moneycontrol.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.stoychopetrov.moneycontrol.models.IncomeExpensesModel;

import java.util.Date;
import java.util.List;

@Dao
public interface IncomeExpensesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(IncomeExpensesModel incomeExpensesModel);

    @Delete
    void delete(IncomeExpensesModel incomeExpensesModel);

    @Query("SELECT * from income_expenses_table")
    List<IncomeExpensesModel> getAllIncomeExpenses();

    @Query("SELECT SUM(mAmount) FROM income_expenses_table WHERE mIsDebit = :isDebit AND mDate BETWEEN :from AND :to")
    double getIncomeExpensesTotal(int isDebit, Date from, Date to);
}
