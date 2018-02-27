package com.manju.BarterXchange;

import android.os.Bundle;

/**
 * Created by Manjunath on 10/29/2015.
 * Description : This class maintains the constants used across the package/App
 */
public class Constants {

    //Yahoo conversion URL to get the converstion rates
    public static String YAHOO_CONVERSION_URL = "http://finance.yahoo.com/d/quotes.csv?e=.csv&f=c4l1&s=%s=X";

    //Key returned by HTTP getter
    public static String RESULT_CODE = "RESULT_CODE";
    public static String RESULT_REASON = "RESULT_REASON";


    public static String XCHANGE_SERVICE_NAME = "XchangeWatchService";

    //Result description by HTTP getter
    public static int RESULT_CODE_NO_INTERNET = -1;
    public static String RESULT_CODE_NO_INTERNET_REASON = "No Internet connection";
    public static int RESULT_CODE_SUCCESS = 100;
    public static String RESULT_CODE_SUCCESS_REASON = "Download Success";

    //shared preference keys and values to store the data
    public static String DESTN_CUR = "DESTN_CUR";
    public static String DESTN_CUR_QUOTE = "DESTN_CUR_QUOTE";

    public static String SAVED_FROM_CUR = "FROM_CUR";
    public static String SAVED_TO_CUR = "TO_CUR";
    public static String SAVED_IS_ACTIVE = "ACTIVE";
    public static String SAVED_MAX_VALUE = "MAX_VALUE";
    public static String SAVED_MIN_VALUE = "MIN_VALUE";
    public static String SHARED_PRES_NAME = "XWATCH_SAVED_DATA";
    public static String SAVED_PREVIOUS_QUOTE = "PREVIOUS_QUOTE";



    //timer for remainder delay
    public static int MILLI_SECONDS = 1000;
    public static int DELAY_IN_MINUTES = 15 * (MILLI_SECONDS*60);
    //APP key used in remainder setting
    public static int APP_INTENT_KEY = 23091984;

    //resource ID marrpings for flags
    public static final int AED = 0;
    public static final int AUD = AED + 1;
    public static final int CAD = AUD + 1;
    public static final int CHF = CAD + 1;
    public static final int CNY = CHF + 1;
    public static final int EUR = CNY + 1;
    public static final int GBP = EUR + 1;
    public static final int INR = GBP + 1;
    public static final int JPY = INR + 1;
    public static final int MYR = JPY + 1;
    public static final int NZD = MYR + 1;
    public static final int QAR = NZD + 1;
    public static final int SAR = QAR + 1;
    public static final int SGD = SAR + 1;
    public static final int USD = SGD + 1;

    /**
     * Download notifier interface which will be implemented by caller of HTTP getter
     * returns bundle which contains the result code and reason and also conversation rates if success
     */
    public interface DownloadNotifier
    {
        public void quotesDownloadNotifier(Bundle aData);
    }

}
