package com.cwclaims.claimapp.asynckTask;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cwclaims.claimapp.other.Utility;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class AsyncTaskJSON extends AsyncTask<Void, Void, String> {

    private Context context;
    private OnAsyncTaskJSON listener;
    private ProgressDialog progressDialog;
    private String requestUrl;
    private HashMap<String, String> hashMap;

    public AsyncTaskJSON(Context context, String url, HashMap<String, String> hashMap, OnAsyncTaskJSON listener) {
        this.context = context;
        this.listener = listener;
        this.requestUrl = url;
        this.hashMap = hashMap;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait . . .");
//        progressDialog.show();
        Utility.showProgress(context);
    }

    @Override
    protected String doInBackground(Void... params) {

        URL url;
        String response = "";

        try {
            url = new URL(requestUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (hashMap != null) {
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(hashMap));

                writer.flush();
                writer.close();
                os.close();
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response = "error";

            }
        } catch (Exception e) {
            response = "error";
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
//        progressDialog.dismiss();
        Utility.dismissProgress();
        if(!result.equals("error") && !result.contains("errors"))
        {
            if(listener != null){
                listener.onResult(result);
            }
        }
        else {
            if(listener != null){
                listener.onResult("Error");
            }
        }
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {

        String result = "";
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result +="&";
            result += entry.getKey() +"="+ entry.getValue();
        }
        Log.e("state", result + " url");
        return result;
    }

    public interface OnAsyncTaskJSON {
        void onResult(String result);
    }
}
