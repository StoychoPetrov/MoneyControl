package com.example.stoychopetrov.moneycontrol.activities;

import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stoychopetrov.moneycontrol.MoneyControlDatabase;
import com.example.stoychopetrov.moneycontrol.R;
import com.example.stoychopetrov.moneycontrol.customClasses.CreateCategoryDialog;
import com.example.stoychopetrov.moneycontrol.interfaces.CategoryDao;
import com.example.stoychopetrov.moneycontrol.models.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageView               mBackArrowImg;
    private ListView                mCategoriesListView;
    private FloatingActionButton    mAddBtn;
    private TextView                mNoCategoriesTxt;

    private ArrayList<String>       mCategoriesTitlesArrayList = new ArrayList<>();
    private ArrayAdapter<String>    mCategoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        initUI();
        setListeners();

        setData();
    }

    private void initUI(){
        mBackArrowImg           = findViewById(R.id.left_img);
        mCategoriesListView     = findViewById(R.id.categories_listview);
        mAddBtn                 = findViewById(R.id.add_btn);
        mNoCategoriesTxt        = findViewById(R.id.no_categories_txt);
    }

    private void setListeners(){
        mBackArrowImg.setOnClickListener(this);
        mCategoriesListView.setOnItemClickListener(this);
        mAddBtn.setOnClickListener(this);
    }

    private void setData(){

        ((TextView) findViewById(R.id.title_txt)).setText(R.string.choose_category);

        CrudDatabase crudDatabase = new CrudDatabase();
        crudDatabase.execute();
    }

    private void setAdapter(){
        mCategoriesAdapter = new ArrayAdapter<String>(this, R.layout.item_category, R.id.category_name_txt, mCategoriesTitlesArrayList);
        mCategoriesListView.setAdapter(mCategoriesAdapter);

        mNoCategoriesTxt.setVisibility(mCategoriesTitlesArrayList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void showCreateCategoryDialog(){
        CreateCategoryDialog dialog = new CreateCategoryDialog(this, new CreateCategoryDialog.ButtonsClickedListener() {
            @Override
            public void onSaveClicked(String categoryName) {

            }
        });
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == mBackArrowImg.getId())
            onBackPressed();
        else if(view.getId() == mAddBtn.getId())
            showCreateCategoryDialog();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    class CrudDatabase extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            final MoneyControlDatabase database = MoneyControlDatabase.getDatabase(CategoriesActivity.this);

            CategoryDao categoryDao         = database.categoryDao();
            List<CategoryModel> categories  = categoryDao.getAllCategories();

            for (CategoryModel categoryModel : categories)
                mCategoriesTitlesArrayList.add(categoryModel.getCategoryName());

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            setAdapter();
        }
    }
}
