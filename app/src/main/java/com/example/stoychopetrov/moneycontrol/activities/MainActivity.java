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
import com.example.stoychopetrov.moneycontrol.models.IncomeExpensesModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", new Locale("bg"));

                    try {
                        Date first  = simpleDateFormat.parse(o1.getDate());
                        Date second = simpleDateFormat.parse(o2.getDate());

                        return first.compareTo(second);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    return 0;
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
        }
    }
}
