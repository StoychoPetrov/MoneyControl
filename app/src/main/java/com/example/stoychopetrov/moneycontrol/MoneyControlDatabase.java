package com.example.stoychopetrov.moneycontrol;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.stoychopetrov.moneycontrol.interfaces.CategoryDao;
import com.example.stoychopetrov.moneycontrol.interfaces.IncomeExpensesDao;
import com.example.stoychopetrov.moneycontrol.models.CategoryModel;
import com.example.stoychopetrov.moneycontrol.models.IncomeExpensesModel;

@Database(entities = {IncomeExpensesModel.class, CategoryModel.class}, version = 2)
public abstract class MoneyControlDatabase extends RoomDatabase {

    public abstract IncomeExpensesDao   incomeExpensesDao();
    public abstract CategoryDao         categoryDao();

    private static MoneyControlDatabase INSTANCE;

    public static MoneyControlDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MoneyControlDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MoneyControlDatabase.class, "money_control_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
