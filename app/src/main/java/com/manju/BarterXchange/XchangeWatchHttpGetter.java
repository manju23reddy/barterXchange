package com.manju.BarterXchange;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Created by mkodandx on 10/29/2015.
 */
public class XchangeWatchHttpGetter implements Constants.DownloadNotifier {
    private static XchangeWatchHttpGetter INSTANCE = null;
    Context mContext = null;
    Constants.DownloadNotifier mNotifierInstance = null;

    public XchangeWatchHttpGetter(Context aContext, Constants.DownloadNotifier aNotifier)
    {
        mNotifierInstance = aNotifier;
        mContext = aContext;
    }




   public void getQuotes(String aUrl )
   {
       Bundle quotes = new Bundle();

       ConnectivityManager connMgr = (ConnectivityManager)
               this.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
       if (networkInfo != null && networkInfo.isConnected()) {
          // fetch data
           getQuotesTask tasker = new getQuotesTask(this);
           tasker.execute(aUrl);
       } else {
           // display error
           quotes.putInt(Constants.RESULT_CODE, Constants.RESULT_CODE_NO_INTERNET);
           quotes.putString(Constants.RESULT_REASON, Constants.RESULT_CODE_NO_INTERNET_REASON);
           mNotifierInstance.quotesDownloadNotifier(quotes);
       }

   }

    @Override
    public void quotesDownloadNotifier(Bundle aData) {
        aData.putInt(Constants.RESULT_CODE, Constants.RESULT_CODE_SUCCESS);
        aData.putString(Constants.RESULT_REASON, Constants.RESULT_CODE_SUCCESS_REASON);
        mNotifierInstance.quotesDownloadNotifier(aData);
    }


    private class getQuotesTask extends AsyncTask<String, Void, Bundle> {

        Constants.DownloadNotifier mNotifierListener = null;

        public getQuotesTask(Constants.DownloadNotifier aNotifier)
        {
            mNotifierListener = aNotifier;
        }

        @Override
        protected Bundle doInBackground(String... urls) {
            Bundle result_data = null;


            // params comes from the execute() call: params[0] is the url.
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpContext localContext = new BasicHttpContext();
                HttpGet httpGet = new HttpGet(urls[0]);
                HttpResponse response = httpClient.execute(httpGet, localContext);


                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                response.getEntity().getContent()
                        )
                );

                CSVReader csv_reader = new CSVReader(reader);
                String [] nextLine;
                if (null != csv_reader) {
                    while ((nextLine = csv_reader.readNext()) != null) {
                        // nextLine[] is an array of values from the line
                        System.out.println(nextLine[0] + nextLine[1] + "etc...");
                        result_data = new Bundle();

                        result_data.putString(Constants.DESTN_CUR, nextLine[0]);
                        result_data.putString(Constants.DESTN_CUR_QUOTE, nextLine[1]);
                        return result_data;

                    }
                }

            } catch (IOException e) {
                return null;
            }
            return result_data;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Bundle result) {
            mNotifierListener.quotesDownloadNotifier(result);
        }
    }




}

