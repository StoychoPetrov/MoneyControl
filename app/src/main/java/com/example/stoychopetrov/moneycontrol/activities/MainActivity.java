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
import com.example.stoychopetrov.moneycontrol.models.IncomeExpensesModel;

import java.util.ArrayList;
import java.util.List;

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
    public void onClick(View view) {
        if(view.getId() == mBackArrowImg.getId()){

        }
        else if(view.getId() == mDrawerImg.getId()){

        }
        else if(view.getId() == mAddFAB.getId()){
            Intent intent = new Intent(this, AddTransactionActivity.class);
            startActivity(intent);
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
