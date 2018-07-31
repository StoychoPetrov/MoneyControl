package com.example.stoychopetrov.moneycontrol.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stoychopetrov.moneycontrol.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView               mDrawerImg;
    private ImageView               mBackArrowImg;
    private FloatingActionButton    mAddFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        setListeners();
    }

    private void initUI(){
        mDrawerImg          = findViewById(R.id.left_img);
        mBackArrowImg       = findViewById(R.id.right_img);
        mAddFAB             = findViewById(R.id.add_fab);

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
}
