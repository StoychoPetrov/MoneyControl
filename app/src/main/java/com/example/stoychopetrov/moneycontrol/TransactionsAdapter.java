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

import com.example.stoychopetrov.moneycontrol.customClasses.Utils;
import com.example.stoychopetrov.moneycontrol.models.IncomeExpensesModel;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

            if(position != 0)
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_transaction, null, false);
            else
                convertView = LayoutInflater.from(mContext).inflate(R.layout.transactions_header, null, false);

            holder      = new ViewHolder(convertView, position);
            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();

        if(position == 0){

        }
        else {
            IncomeExpensesModel incomeExpensesModel = mTransactionsArrayList.get(position);

            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", new Locale("bg"));
            SimpleDateFormat secondFormat = new SimpleDateFormat("dd.MM.yyyy", new Locale("bg"));


            try {
                Date date = format.parse(incomeExpensesModel.getDate());

                Calendar now = Calendar.getInstance();
                Calendar dateCalendar = Calendar.getInstance();
                dateCalendar.setTime(date);

                if (now.get(Calendar.DATE) == dateCalendar.get(Calendar.DATE))
                    holder.mDateTxt.setText(R.string.today);
                else if (now.get(Calendar.DATE) - dateCalendar.get(Calendar.DATE) == 1)
                    holder.mDateTxt.setText(R.string.yesterday);
                else
                    holder.mDateTxt.setText(secondFormat.format(date));

                if (position != 1 && mTransactionsArrayList.get(position - 1).getDate().equalsIgnoreCase(incomeExpensesModel.getDate()))
                    holder.mDateTxt.setVisibility(View.GONE);
                else
                    holder.mDateTxt.setVisibility(View.VISIBLE);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            String amount = (incomeExpensesModel.getIsDebit() ? "-" : "+") + Utils.formatAmount(String.valueOf(incomeExpensesModel.getAmount())) + " лв.";
            holder.mAmountTxt.setText(amount);

            holder.mAmountTxt.setTextColor(ContextCompat.getColor(mContext, incomeExpensesModel.getIsDebit() ? android.R.color.holo_red_dark : android.R.color.holo_green_dark));

            holder.mDescrTxt.setText(incomeExpensesModel.getDescription());
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return mTransactionsArrayList.size() + 1;
    }

    class ViewHolder {

        TextView mDescrTxt;
        TextView mAmountTxt;
        TextView mDateTxt;
        TextView mTotalTxt;
        TextView mDebitTxt;
        TextView mCreditTxt;

        private ViewHolder(View itemView, int position) {
            if (position != 0) {
                mDescrTxt = itemView.findViewById(R.id.descr_txt);
                mAmountTxt = itemView.findViewById(R.id.amount_txt);
                mDateTxt = itemView.findViewById(R.id.date_txt);
            } else {
                mTotalTxt = itemView.findViewById(R.id.total_txt);
                mCreditTxt = itemView.findViewById(R.id.montly_credit_txt);
                mDebitTxt = itemView.findViewById(R.id.montly_debit_txt);
            }
        }
    }
}
