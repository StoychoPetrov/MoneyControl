package com.example.stoychopetrov.moneycontrol.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stoychopetrov.moneycontrol.MoneyControlDatabase;
import com.example.stoychopetrov.moneycontrol.R;
import com.example.stoychopetrov.moneycontrol.TransactionsAdapter;
import com.example.stoychopetrov.moneycontrol.customClasses.Utils;
import com.example.stoychopetrov.moneycontrol.interfaces.IncomeExpensesDao;
import com.example.stoychopetrov.moneycontrol.models.IncomeExpensesModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView               mDrawerImg;
    private ImageView               mBackArrowImg;
    private FloatingActionButton    mAddFAB;
    private ListView                mTransactionsListView;

    private double                         mDebit;
    private double                         mCredit;
    private double                         mTotal;

    private TransactionsAdapter            mAdapter;
    private ArrayList<IncomeExpensesModel> mTransactionsArrayList   = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        setListeners();
        setAdapter();
        selectTransactions();
    }

    private void initUI(){
        mDrawerImg              = findViewById(R.id.left_img);
        mBackArrowImg           = findViewById(R.id.right_img);
        mAddFAB                 = findViewById(R.id.add_fab);
        mTransactionsListView   = findViewById(R.id.transactions_listview);

        TextView mTitleTxt  = findViewById(R.id.title_txt);

        mTitleTxt.setText(R.string.app_name);
        mDrawerImg.setImageResource(R.drawable.ic_menu);
        mBackArrowImg.setImageResource(R.drawable.ic_search);
    }

    private void setListeners(){
        mDrawerImg.setOnClickListener(this);
        mBackArrowImg.setOnClickListener(this);
        mAddFAB.setOnClickListener(this);
    }

    private void setAdapter(){
        mAdapter = new TransactionsAdapter(this, mTransactionsArrayList);
        mTransactionsListView.setAdapter(mAdapter);
    }

    private void selectTransactions(){
        DatabaseQuery query = new DatabaseQuery();
        query.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Utils.REQUEST_CODE_ADD_TRANSACTION && resultCode == RESULT_OK){
            selectTransactions();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == mBackArrowImg.getId()){

        }
        else if(view.getId() == mDrawerImg.getId()){

        }
        else if(view.getId() == mAddFAB.getId()){
            Intent intent = new Intent(this, AddTransactionActivity.class);
            startActivityForResult(intent, Utils.REQUEST_CODE_ADD_TRANSACTION);
        }
    }

    private class DatabaseQuery extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            selectTransactions();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Collections.sort(mTransactionsArrayList, new Comparator<IncomeExpensesModel>() {
                @Override
                public int compare(IncomeExpensesModel o1, IncomeExpensesModel o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });

            Collections.reverse(mTransactionsArrayList);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                }
            });
        }

        private void selectTransactions(){
            mTransactionsArrayList.clear();
            MoneyControlDatabase database = MoneyControlDatabase.getDatabase(MainActivity.this);
            mTransactionsArrayList.addAll(database.incomeExpensesDao().getAllIncomeExpenses());

            Calendar start = Calendar.getInstance();
            Calendar end   = Calendar.getInstance();

            Date minDate = new Date(Long.MIN_VALUE);

            start.set(Calendar.DAY_OF_MONTH, 1);

            IncomeExpensesDao dao = database.incomeExpensesDao();

            mDebit  = dao.getIncomeExpensesTotal(1, start.getTime(), end.getTime());
            mCredit = dao.getIncomeExpensesTotal(0, start.getTime(), end.getTime());
            mTotal  = dao.getIncomeExpensesTotal(0, minDate, end.getTime()) - dao.getIncomeExpensesTotal(1, minDate, end.getTime());;

            mAdapter.setmDebit(mDebit);
            mAdapter.setmCredit(mCredit);
            mAdapter.setmTotal(mTotal);
        }
    }
}