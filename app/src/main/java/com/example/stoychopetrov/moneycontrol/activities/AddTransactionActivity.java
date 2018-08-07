package com.example.stoychopetrov.moneycontrol.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stoychopetrov.moneycontrol.MoneyControlDatabase;
import com.example.stoychopetrov.moneycontrol.R;
import com.example.stoychopetrov.moneycontrol.customClasses.Utils;
import com.example.stoychopetrov.moneycontrol.models.CategoryModel;
import com.example.stoychopetrov.moneycontrol.models.IncomeExpensesModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView       mBackArrowImg;
    private EditText        mDateEdt;
    private EditText        mCategoryEdt;
    private EditText        mSubCategoryEdt;
    private EditText        mAmountEdt;
    private EditText        mDescriptionEdt;
    private Button          mSaveBtn;

    private CategoryModel   mSelectedCategoryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        initUI();
        setListeners();
    }

    private void initUI(){

        mBackArrowImg   = findViewById(R.id.left_img);
        mDateEdt        = findViewById(R.id.date_edt);
        mCategoryEdt    = findViewById(R.id.category_edt);
        mSubCategoryEdt = findViewById(R.id.subcategory_edt);
        mAmountEdt      = findViewById(R.id.amount_edt);
        mDescriptionEdt = findViewById(R.id.description_edt);
        mSaveBtn        = findViewById(R.id.save_btn);

        TextView titleTxt = findViewById(R.id.title_txt);
        titleTxt.setText(R.string.add_transaction);

        findViewById(R.id.right_img).setVisibility(View.GONE);

        setDate();
    }

    private void setDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", new Locale("bg"));
        String dateNow = simpleDateFormat.format(Calendar.getInstance().getTime());

        mDateEdt.setText(dateNow);
    }

    private void setListeners(){
        mBackArrowImg.setOnClickListener(this);
        mDateEdt.setOnClickListener(this);
        mCategoryEdt.setOnClickListener(this);
        mSaveBtn.setOnClickListener(this);
    }

    private void showDatePicker(){

        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                mDateEdt.setText(sdf.format(calendar.getTime()));
            }
        };

        new DatePickerDialog(this, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void startCategoryActivity(int requestCode){
        Intent intent = new Intent(this, CategoriesActivity.class);
        if(requestCode == Utils.REQUEST_CODE_CHOOSE_SUBCATEGORY)
            intent.putExtra(Utils.INTENT_EXTRA_SUBCATEGORY, true);
        startActivityForResult(intent, requestCode);
    }

    private void onSave(){
        if(mDateEdt.getText().toString().isEmpty()){

            return;
        }

        if(mCategoryEdt.getText().toString().isEmpty()){

            return;
        }

        if(mAmountEdt.getText().toString().isEmpty()){
            return;
        }

        if(mDescriptionEdt.getText().toString().isEmpty()){

        }

        CrudDatabase saveInDatabase = new CrudDatabase();
        saveInDatabase.execute();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == mBackArrowImg.getId())
            onBackPressed();
        else if(v.getId() == mDateEdt.getId())
            showDatePicker();
        else if(v.getId() == mCategoryEdt.getId())
            startCategoryActivity(Utils.REQUEST_CODE_CHOOSE_CATEGORY);
        else if(v.getId() == mSubCategoryEdt.getId())
            startCategoryActivity(Utils.REQUEST_CODE_CHOOSE_SUBCATEGORY);
        else if(v.getId() == mSaveBtn.getId())
            onSave();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Utils.REQUEST_CODE_CHOOSE_CATEGORY && resultCode == RESULT_OK){
            mSelectedCategoryModel  = data.getParcelableExtra(Utils.INTENT_EXTRA_CATEGORY_NAME);

            mCategoryEdt.setText(mSelectedCategoryModel.getCategoryName());
        }
    }

    class CrudDatabase extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            final MoneyControlDatabase mDatabase = MoneyControlDatabase.getDatabase(AddTransactionActivity.this);

            IncomeExpensesModel incomeExpensesModel = new IncomeExpensesModel();
            incomeExpensesModel.setDate(mDateEdt.getText().toString());
            incomeExpensesModel.setCategoryId(mSelectedCategoryModel.getId());
            incomeExpensesModel.setAmount(Double.parseDouble(mAmountEdt.getText().toString()));
            incomeExpensesModel.setDescription(mDescriptionEdt.getText().toString());

            mDatabase.incomeExpensesDao().insert(incomeExpensesModel);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(AddTransactionActivity.this, "Успешно е добавено ново движение", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
