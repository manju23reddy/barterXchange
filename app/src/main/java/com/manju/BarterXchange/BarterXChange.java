package com.manju.BarterXchange;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


public class BarterXChange extends ActionBarActivity implements View.OnClickListener, Constants.DownloadNotifier {

    //currency spinner
    Spinner mFrom_Cur_Spinner = null;
    Spinner mTo_Cur_Spinner = null;

    //currency text of country
    TextView mFrom_Cur_TextView = null;
    TextView mTo_Cur_TextView = null;


    //Max widget
    ImageButton mMaxMinusButton = null;
    ImageButton mMaxPlusButton = null;
    EditText mMaxEditText = null;

    //min widget
    ImageButton mMinMinusButton = null;
    ImageButton mMinPlusButton = null;
    EditText mMinEditText = null;

    //action buttons
    Button mStartNotificationButton = null;
    Button mStopNotificationButton = null;

    ImageView mConversionValue_from = null;
    ImageView mConversionValue_to = null;
    TextView mConversionValue_result = null;

    ArrayList<String> mCountryArray = null;
    ArrayList<String> mCurrencyArray = null;

    Button mGetQuotesBtn  = null;

    XchangeSharedPrefsHelper mSavedDate = null;

    Switch mStartStopNotifiationSwitch = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xchange_watch_main);

        //get widgets
        mFrom_Cur_Spinner = (Spinner)findViewById(R.id.from_cur_spinner);
        mTo_Cur_Spinner = (Spinner)findViewById(R.id.to_cur_spinner);

        mFrom_Cur_TextView = (TextView)findViewById(R.id.from_cur_detail);
        mTo_Cur_TextView = (TextView)findViewById(R.id.to_cur_details);

        mMaxMinusButton = (ImageButton)findViewById(R.id.notifyMaxMinus);
        mMaxMinusButton.setOnClickListener(this);
        mMaxPlusButton = (ImageButton)findViewById(R.id.notifyMaxPlus);
        mMaxPlusButton.setOnClickListener(this);
        mMaxEditText = (EditText)findViewById(R.id.notifyMax);


        mMinMinusButton = (ImageButton)findViewById(R.id.notifyMinMinus);
        mMinMinusButton.setOnClickListener(this);
        mMinPlusButton = (ImageButton)findViewById(R.id.notifyMinPlus);
        mMinPlusButton.setOnClickListener(this);
        mMinEditText = (EditText)findViewById(R.id.notifyMin);

        mStartNotificationButton = (Button)findViewById(R.id.start_notify_button);
        mStartNotificationButton.setOnClickListener(this);
        mStopNotificationButton = (Button)findViewById(R.id.stop_notify_button);
        mStopNotificationButton.setOnClickListener(this);

        mConversionValue_from = (ImageView)findViewById(R.id.conversion_value_from);
        mConversionValue_to = (ImageView)findViewById(R.id.conversion_value_to);
        mConversionValue_result = (TextView)findViewById(R.id.conversion_value_result);

        mCurrencyArray = new ArrayList<>();
        mCountryArray = new ArrayList<>();

        Collections.addAll(mCurrencyArray, getResources().getStringArray(R.array.currency));
        Collections.addAll(mCountryArray, getResources().getStringArray(R.array.country));



        mGetQuotesBtn = (Button)findViewById(R.id.get_quotes_button);
        mGetQuotesBtn.setOnClickListener(this);

        mSavedDate = new XchangeSharedPrefsHelper(getApplicationContext());



        mFrom_Cur_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mFrom_Cur_TextView.setText(mCountryArray.get(position));
                //mConversionValue_from.setText(mCurrencyArray.get(position));

                //mConversionValue_from.setImageResource(resource_id);
                mConversionValue_from.setImageDrawable(getResources().getDrawable(BarterXChange.getResId(position)));
                getCurrentConversionValue();
                validateForNotification();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        mTo_Cur_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTo_Cur_TextView.setText(mCountryArray.get(position));
               // mConversionValue_to.setText(mCurrencyArray.get(position));
                mConversionValue_to.setImageDrawable(getResources().getDrawable(BarterXChange.getResId(position)));
                getCurrentConversionValue();
                validateForNotification();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }

    public void getCurrentConversionValue()
    {
        XchangeWatchHttpGetter downloader = new XchangeWatchHttpGetter(getApplicationContext(), this);
        downloader.getQuotes(String.format(Constants.YAHOO_CONVERSION_URL, mFrom_Cur_Spinner.getSelectedItem().toString()+mTo_Cur_Spinner.getSelectedItem().toString()));
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(R.id.notifyMaxPlus == id){
            float cur_text = Float.parseFloat(mMaxEditText.getText().toString());
            cur_text += 0.1;
            mMaxEditText.setText(Float.toString(cur_text));
        }
        else if(R.id.notifyMaxMinus == id){
            float cur_text = Float.parseFloat(mMaxEditText.getText().toString());
            float cur_min_text = Float.parseFloat(mMinEditText.getText().toString());
            cur_text -= 0.1;
            if (cur_text < cur_min_text){
                showToast("Max cannot be lesser or equal to Min");
                return;
            }
            mMaxEditText.setText(Float.toString(cur_text));
        }
        else if(R.id.notifyMinMinus == id){
            float cur_text = Float.parseFloat(mMinEditText.getText().toString());
            cur_text -= 0.1;
            if (cur_text <= 0)
            {
                showToast("Min cannot be Zero or Lesser");
                return;
            }
            mMinEditText.setText(Float.toString(cur_text));

        }
        else if(R.id.notifyMinPlus == id){
            float cur_text = Float.parseFloat(mMinEditText.getText().toString());
            float cur_max_text = Float.parseFloat(mMaxEditText.getText().toString());
            cur_text += 0.1;
            if (cur_text > cur_max_text)
            {
                showToast("Min cannot be equal or more to Min");
                return;
            }
            mMinEditText.setText(Float.toString(cur_text));

        }
        else if (R.id.start_notify_button == id){
            if (mSavedDate.updateSharedPrefs(new XchangeSavedDataHolder(mFrom_Cur_Spinner.getSelectedItem().toString(), mTo_Cur_Spinner.getSelectedItem().toString(),
                    Float.parseFloat(mMaxEditText.getText().toString()), Float.parseFloat(mMinEditText.getText().toString()), true))){
                showToast("Notification started");
                mStartNotificationButton.setText(R.string.update_notification);
                mStartNotificationButton.setEnabled(false);
                mStopNotificationButton.setEnabled(true);
                if (false == isMyServiceRunning()) {
                    XchangeWatchAlarmManager alramManager = new XchangeWatchAlarmManager(this);
                    alramManager.setAlarm();
                }
                else{

                }

            }
            else{
                showToast("Failed to start Notification");
            }
        }
        else if(R.id.stop_notify_button == id){
            if (mSavedDate.updateSharedPrefs(new XchangeSavedDataHolder(mFrom_Cur_Spinner.getSelectedItem().toString(), mTo_Cur_Spinner.getSelectedItem().toString(),
                    Float.parseFloat(mMaxEditText.getText().toString()), Float.parseFloat(mMinEditText.getText().toString()), false))){
                showToast("Notification stopped");
                mStopNotificationButton.setEnabled(false);
                XchangeWatchAlarmManager alramManager = new XchangeWatchAlarmManager(this);
                alramManager.stopAlarm();
            }
            else{
                showToast("Failed to stop Notification");
            }

        }

    }

    public void validateForNotification()
    {
        String from = mFrom_Cur_Spinner.getSelectedItem().toString();
        String to = mTo_Cur_Spinner.getSelectedItem().toString();

        if (from.equalsIgnoreCase(to)){
            mStartNotificationButton.setEnabled(false);
        }
        else
        {
            mStartNotificationButton.setEnabled(true);
        }

    }

    @Override
    public void quotesDownloadNotifier(Bundle aData) {
        if (null != aData){
            int result_code = aData.getInt(Constants.RESULT_CODE);
            if (result_code == Constants.RESULT_CODE_SUCCESS){
                String CUR = aData.getString(Constants.DESTN_CUR);
                String Quotes = aData.getString(Constants.DESTN_CUR_QUOTE);
                mConversionValue_result.setText(Quotes+" "+CUR);
                if (mSavedDate.getSharedPrefsData() == null) {
                    mMaxEditText.setText(Quotes);
                    mMinEditText.setText(Quotes);
                }
                if (null != mSavedDate.getSharedPrefsData()) {

                    if (!CUR.equalsIgnoreCase(mSavedDate.getSharedPrefsData().getTo()) || !mFrom_Cur_Spinner.getSelectedItem().toString().equalsIgnoreCase(mSavedDate.getSharedPrefsData().getFrom())) {
                        mMaxEditText.setText(Quotes);
                        mMinEditText.setText(Quotes);
                    }
                    else{
                        mMaxEditText.setText(Float.toString(mSavedDate.getSharedPrefsData().getMax()));
                        mMinEditText.setText(Float.toString(mSavedDate.getSharedPrefsData().getMin()));
                    }

                }

            }
            else
            {
                showToast(aData.getString(Constants.RESULT_REASON));
            }
        }

    }

    public void showToast(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        setSavedDateToUI();
    }

    public void setSavedDateToUI()
    {
        XchangeSavedDataHolder curData = mSavedDate.getSharedPrefsData();
        if (null != curData){

            int index = mCurrencyArray.indexOf(curData.getFrom());

            mFrom_Cur_Spinner.setSelection(index);
            mFrom_Cur_TextView.setText(mCountryArray.get(index));

            index = mCurrencyArray.indexOf(curData.getTo());
            mTo_Cur_Spinner.setSelection(index);
            mTo_Cur_TextView.setText(mCountryArray.get(index));

            mMaxEditText.setText(Float.toString(curData.getMax()));
            mMinEditText.setText(Float.toString(curData.getMin()));

            mStartNotificationButton.setText(R.string.update_notification);
            mStartNotificationButton.setEnabled(true);
            mStopNotificationButton.setEnabled(curData.getActive());
        }
        else
        {
            mStartNotificationButton.setText(R.string.Start_notification);

        }

    }

    private boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (Constants.XCHANGE_SERVICE_NAME.equals(service.service.getClassName()))
            {
                return true;
            }

        }
        return false;
    }

    public static int getResId(int position) {

        switch (position)
        {
            case Constants.AUD :
                return R.drawable.aud;
            case Constants.AED :
                return  R.drawable.aed;
            case Constants.CAD :
                return R.drawable.cad;
            case Constants.CHF :
                return R.drawable.chf;
            case Constants.CNY:
                return R.drawable.cny;
            case Constants.EUR:
                return R.drawable.euro;
            case Constants.GBP:
                return  R.drawable.gbp;
            case Constants.JPY:
                return  R.drawable.jpy;
            case Constants.INR:
                return R.drawable.inr;
            case Constants.MYR:
                return R.drawable.myr;
            case Constants.NZD:
                return R.drawable.nzd;
            case Constants.QAR:
                return R.drawable.qar;
            case Constants.SAR:
                return R.drawable.sar;
            case Constants.SGD:
                return R.drawable.sgd;
            case Constants.USD:
                return R.drawable.usd;
            default:
                return R.drawable.minus32;
        }
    }
}
