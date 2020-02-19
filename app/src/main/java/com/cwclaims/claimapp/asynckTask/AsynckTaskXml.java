package com.cwclaims.claimapp.asynckTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.w3c.dom.Document;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Hitesh on 02 Mar. 2017.
 */

public class AsynckTaskXml extends AsyncTask<Void, Void, Document> {

    Context context;
    ProgressDialog progressDialog;
    String dataUrl;
    OnAsynckTaskXml onAsynckTaskXml;

    public AsynckTaskXml(Context context, String dataUrl, OnAsynckTaskXml onAsynckTaskXml) {
        this.context = context;
        this.onAsynckTaskXml = onAsynckTaskXml;
        this.dataUrl = dataUrl;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait Data is Loading...");
//        progressDialog.show();
//        Helper.showLoader(context);
    }

    @Override
    protected Document doInBackground(Void... voids) {
        Document document = null;
        try {
            URL url = new URL(dataUrl);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            document = db.parse(url.openStream());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return document;
    }

    @Override
    protected void onPostExecute(Document document) {
        super.onPostExecute(document);
//        Helper.stopLoader();
//        progressDialog.dismiss();
        onAsynckTaskXml.onResult(document);
    }

    public interface OnAsynckTaskXml {
        void onResult(Document document);
    }
}
