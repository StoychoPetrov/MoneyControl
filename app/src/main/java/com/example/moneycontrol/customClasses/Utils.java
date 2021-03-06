package com.example.moneycontrol.customClasses;

import java.text.DecimalFormat;

public class Utils {

    // INTENT EXTRAS
    public static final String INTENT_EXTRA_CATEGORY_NAME = "category_name";
    public static final String INTENT_EXTRA_SUBCATEGORY   = "choose_subcategory";

    // REQUEST CODES
    public static final int         REQUEST_CODE_CHOOSE_CATEGORY        = 0;
    public static final int         REQUEST_CODE_CHOOSE_SUBCATEGORY     = 1;
    public static final int         REQUEST_CODE_ADD_TRANSACTION        = 2;

    public static final String      INTENT_CATEGORY_ID                  = "category_id";

    public static String formatAmount(String amount){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        return decimalFormat.format(Double.parseDouble(amount));
    }
}
