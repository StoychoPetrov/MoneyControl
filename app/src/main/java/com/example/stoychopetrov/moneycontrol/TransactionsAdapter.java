package com.example.stoychopetrov.moneycontrol;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.stoychopetrov.moneycontrol.models.IncomeExpensesModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TransactionsAdapter extends ArrayAdapter<IncomeExpensesModel> {

    private Context                         mContext;
    private ArrayList<IncomeExpensesModel>  mTransactionsArrayList  = new ArrayList<>();

    public TransactionsAdapter(@NonNull Context context, @NonNull ArrayList<IncomeExpensesModel> objects) {
        super(context, R.layout.item_transaction, objects);

        mContext                = context;
        mTransactionsArrayList  = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null){

            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_transaction, null, false);
            holder      = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();

        IncomeExpensesModel incomeExpensesModel = mTransactionsArrayList.get(position);

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        String amount = incomeExpensesModel.getIsDebit() ? "-" : "" + decimalFormat.format(incomeExpensesModel.getAmount()) + " лв.";
        holder.mAmountTxt.setText(amount);

        holder.mAmountTxt.setTextColor(ContextCompat.getColor(mContext, incomeExpensesModel.getIsDebit() ? android.R.color.holo_red_light : android.R.color.holo_green_light));

        holder.mDescrTxt.setText(incomeExpensesModel.getDescription());

        holder.mDateTxt.setText(incomeExpensesModel.getDate());

        return convertView;
    }

    class ViewHolder {

        public TextView mDescrTxt;
        public TextView mAmountTxt;
        public TextView mDateTxt;

        private ViewHolder(View itemView){
            mDescrTxt       = itemView.findViewById(R.id.descr_txt);
            mAmountTxt      = itemView.findViewById(R.id.amount_txt);
            mDateTxt        = itemView.findViewById(R.id.date_txt);
        }
    }
}
