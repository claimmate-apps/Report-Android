package com.cwclaims.claimapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.asynckTask.AsyncTaskJSON;
import com.cwclaims.claimapp.other.Constants;
import com.cwclaims.claimapp.other.PrefManager;
import com.cwclaims.claimapp.other.Utility;
import com.cwclaims.claimapp.retrofit.APIInterface;
import com.cwclaims.claimapp.retrofit.ApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyReportActivity extends Activity implements OnClickListener {

    private final String TAG = "VerifyReportActivity";
    private Context mContext;

    EditText etVerifyReport;
    Button btnVerifyEmailSend, btnSave;

    String uname = "", report = "";

    SharedPreferences lastpathpf;
    SharedPreferences.Editor lastimageeditor;
    String strtoemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_report);

        reportId = getIntent().getStringExtra("reportId");
        Log.e("reportid","=>"+reportId);

        btnSave = findViewById(R.id.btnSave);

        if(reportId.equalsIgnoreCase(""))
        {
            btnSave.setText("Save");

        }
        else
        {
            btnSave.setText("Update");

        }

        init();
    }

    String reportId = "";

    @Override
    protected void onStart() {
      /*  try {
            reportId = getIntent().getStringExtra("reportId");
            if (!reportId.equals(""))
                btnSave.setText("Update");
        } catch (Exception e) {
            reportId = "";
            btnSave.setText("Save");
        }*/
        if (getIntent().getIntExtra("exit", 0) == 1)
            onClick(btnSave);
        super.onStart();
    }

    HashMap<String, String> hmParam, hmParam2;

    private void init() {
        mContext = VerifyReportActivity.this;

        lastpathpf = getSharedPreferences("claimmateform", Context.MODE_PRIVATE);
        lastimageeditor = lastpathpf.edit();
        strtoemail = lastpathpf.getString("toemail", "");

        etVerifyReport = findViewById(R.id.etVerifyReport);

        btnVerifyEmailSend = findViewById(R.id.btnVerifyEmailSend);

        report = getIntent().getStringExtra("report");
        uname = getIntent().getStringExtra("username");

        etVerifyReport.setText(report);

        btnSave.setOnClickListener(this);
        btnVerifyEmailSend.setOnClickListener(this);

        hmParam = (HashMap<String, String>) getIntent().getSerializableExtra("hm");
        hmParam2 = (HashMap<String, String>) getIntent().getSerializableExtra("hm2");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnSave.getId()) {


            if (reportId.equals(""))
            {
                Toast.makeText(getApplicationContext(),"addReport",Toast.LENGTH_LONG).show();
                addReport();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"updateReport",Toast.LENGTH_LONG).show();
                updateReport();
            }
        } else if (view.getId() == btnVerifyEmailSend.getId()) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{strtoemail});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, uname + " Inspection Reportz");
            emailIntent.putExtra(Intent.EXTRA_TEXT, etVerifyReport.getEditableText().toString());
            startActivity(Intent.createChooser(emailIntent, "Send report email..."));
        }
    }

    private void addReport() {
        if (Utility.haveInternet(mContext)) {
            hmParam.put("user_id", PrefManager.getUserId());
            hmParam.put("user_name", uname);
            hmParam.put("report", etVerifyReport.getText().toString());
            Log.i(TAG, "addReportParam = " + hmParam.toString());

            Utility.showProgress(mContext);
            ApiClient.getClient().create(APIInterface.class).addReport(hmParam).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.i(TAG, "addReportRes = " + response.body());

                    if (response.body() == null) {
                        Utility.errorDialog(mContext, getString(R.string.error_data_not_found));
                        return;
                    }

                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        if (jsonObject.getString("success").equals("success")) {
                            hmParam2.put("report_id", jsonObject.getString("data"));
                            updateReport2();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Utility.dismissProgress();
                    Log.i(TAG, "addReportError = " + t.toString());
                }
            });
        }
    }

    private void updateReport2() {
        if (Utility.haveInternet(mContext)) {
            Log.i(TAG, "updateReport2Param = " + hmParam2.toString());

            ApiClient.getClient().create(APIInterface.class).updateReport2(hmParam2).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Utility.dismissProgress();
                    Log.i(TAG, "updateReport2Res = " + response.body());

                    if (response.body() == null) {
                        Utility.errorDialog(mContext, getString(R.string.error_data_not_found));
                        return;
                    }

                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        if (jsonObject.getString("success").equals("success")) {
                            Intent intent = new Intent(mContext, ReportListActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Utility.dismissProgress();
                    Log.i(TAG, "updateReport2Error = " + t.toString());
                }
            });
        }
    }

    private void updateReport() {
        if (Utility.haveInternet(mContext)) {
            hmParam.put("user_id", PrefManager.getUserId());
            hmParam.put("user_name", uname);
            hmParam.put("report", etVerifyReport.getText().toString());
            Log.i(TAG, "updateReportParam = " + hmParam.toString());

            Utility.showProgress(mContext);
            ApiClient.getClient().create(APIInterface.class).updateReport(reportId, hmParam).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
//                    Utility.dismissProgress();
                    Log.i(TAG, "updateReportRes = " + response.body());

                    if (response.body() == null) {
                        Utility.errorDialog(mContext, getString(R.string.error_data_not_found));
                        return;
                    }

                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        if (jsonObject.getString("success").equals("success")) {
                            if (jsonObject.isNull("data"))
                                hmParam2.put("report_id", reportId);
                            else
                                hmParam2.put("report_id", jsonObject.getString("data"));
                            updateReport2();
                            /*Intent intent = new Intent(mContext, ReportListActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);*/
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
//                    Utility.dismissProgress();
                    Log.i(TAG, "updateReportError = " + t.toString());
                }
            });
        }
    }
}
