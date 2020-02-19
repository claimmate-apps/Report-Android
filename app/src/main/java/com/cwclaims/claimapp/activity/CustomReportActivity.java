package com.cwclaims.claimapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.db.ClaimSqlLiteDbHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class CustomReportActivity extends Activity implements View.OnClickListener {

    private final String TAG = "CustomReportActivity";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_report);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//		hideSoftKeyboard();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        init();
    }

    ImageView imgBack;
    EditText edtReportName, edtReport;
    Button btnTagList, btnSave;

    private void init() {
        mContext = CustomReportActivity.this;

        imgBack = findViewById(R.id.imgBack);

        edtReportName = findViewById(R.id.edtReportName);
        edtReport = findViewById(R.id.edtReport);

        btnTagList = findViewById(R.id.btnTagList);
        btnSave = findViewById(R.id.btnSave);

        imgBack.setOnClickListener(this);
        btnTagList.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        edtReportName.setText("generic_"+calendar.get(Calendar.DAY_OF_MONTH)+"_"+(calendar.get(Calendar.MONTH)+1)+"_"+calendar.get(Calendar.YEAR)+"_"+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnTagList.getId()) {
            tagList();
        } else if (view.getId() == imgBack.getId()) {
            onBackPressed();
        } else if (view.getId() == btnSave.getId()) {
            saveReport();
        }
    }

    private void tagList() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Tag List");

        ListView listView = new ListView(mContext);
        builder.setView(listView);

        final ArrayList<String> arrayListTags = new ArrayList<>();
        arrayListTags.add("#INSPECTION_TIME_AND_DATE#");
        arrayListTags.add("#INSURED_NAME#");
        arrayListTags.add("#NUMBER_OF_STORIES#");
        arrayListTags.add("#TYPE_OF_CONSTRUCTION#");
        arrayListTags.add("#TYPE_OF_USE#");
        arrayListTags.add("#TYPE_OF_USE_2#");
        arrayListTags.add("#GARAGE#");
        arrayListTags.add("#EXTERIOR#");
        arrayListTags.add("#ROOFING#");
        arrayListTags.add("#PITCH#");
        arrayListTags.add("#NUMBER_OF_LAYERS#");
        arrayListTags.add("#DRIP_EDGE#");
        arrayListTags.add("#AGE#");
        arrayListTags.add("#ROOF_DAMAGE#");
        arrayListTags.add("#FRONT_ELEVATION_DAMAGE#");
        arrayListTags.add("#LEFT_ELEVATION_DAMAGE#");
        arrayListTags.add("#BACK_ELEVATION_DAMAGE#");
        arrayListTags.add("#RIGHT_ELEVATION_DAMAGE#");
        arrayListTags.add("#INTERIOR_DAMAGE#");
        arrayListTags.add("#OTHER_STRUCTURES_DAMAGE#");
        arrayListTags.add("#CONTENTS_DAMAGE#");
        arrayListTags.add("#NOTES#");
        arrayListTags.add("#MORTGAGE#");
        arrayListTags.add("#LABOR_MIN_REMOVED#");
        arrayListTags.add("#LABOR_MIN_ADDED#");
        arrayListTags.add("#CAUSE_OF_LOSS#");
        arrayListTags.add("#SALVAGE#");
        arrayListTags.add("#SUBROGATION#");
        arrayListTags.add("#OVERHEAD_AND_PROFIT#");
        arrayListTags.add("#COMPANY_NAME#");
        arrayListTags.add("#CONTRACTOR_NAME#");

        ArrayAdapter arrayAdapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, arrayListTags);
        listView.setAdapter(arrayAdapter);

        Button btnBack = new Button(mContext);
        btnBack.setText("Back");
//        builder.setView(btnBack);

        final AlertDialog alertDialog = builder.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                char[] tag = arrayListTags.get(i).toCharArray();
                char[] rep = edtReport.getText().toString().toCharArray();

                char[] reports = new char[rep.length+tag.length];

                int t=0, r=0, curPos = edtReport.getSelectionStart();
                for (int j=0; j<reports.length; j++) {
                    if (j>=edtReport.getSelectionStart() && t<tag.length) {
                        reports[j] = tag[t++];
                    } else {
                        reports[j] = rep[r++];
                    }
                    Log.i(TAG, "tag_ "+reports[j]);
                }
                Log.i(TAG, "reports_len = "+reports.length+" reports = "+String.copyValueOf(reports));
                edtReport.setText(String.copyValueOf(reports));
                edtReport.setSelection(curPos+t);
                edtReport.requestFocus();
                InputMethodManager inputMethodManager =
                        (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInputFromWindow(
                        edtReport.getApplicationWindowToken(),
                        InputMethodManager.SHOW_FORCED, 0);
                alertDialog.dismiss();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    private void saveReport() {
        if (TextUtils.isEmpty(edtReportName.getText())) {
            edtReportName.setError("Please enter report name.");
            edtReportName.requestFocus();
        } else if (TextUtils.isEmpty(edtReport.getText())){
            edtReport.setError("Please enter custom report.");
            edtReport.requestFocus();
        } else {
            ClaimSqlLiteDbHelper sqlLiteDbHelper = new ClaimSqlLiteDbHelper(mContext);
            sqlLiteDbHelper.addCustomReport(edtReportName.getText().toString(), edtReport.getText().toString());
            Toast.makeText(mContext, "Report Save Success.", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }
}
