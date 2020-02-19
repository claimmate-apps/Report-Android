package com.cwclaims.claimapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.models.ReportModel;
import com.cwclaims.claimapp.other.PrefManager;
import com.cwclaims.claimapp.other.Utility;
import com.cwclaims.claimapp.retrofit.APIInterface;
import com.cwclaims.claimapp.retrofit.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportzActivity extends Activity implements View.OnClickListener {

    private final String TAG = "ReportzActivity";
    private Context mContext;
    private ReportModel reportModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportz);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        init();

        if (getIntent().getStringExtra("isUpdate").equals("1")) {
            btnSave.setText("Update");
            reportModel = (ReportModel) getIntent().getSerializableExtra("report");
            edtReportName.setText(reportModel.getUser_name());
            edtReport.setText(reportModel.getReport());
        } else {
            edtReport.setText(getIntent().getStringExtra("report"));
            Calendar calendar = Calendar.getInstance();
            edtReportName.setText("generic_"+calendar.get(Calendar.DAY_OF_MONTH)+"_"+(calendar.get(Calendar.MONTH)+1)+"_"+calendar.get(Calendar.YEAR)+"_"+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND));
        }
    }

    ImageView imgBack;
    EditText edtReport, edtReportName;
    Button btnSave;

    private void init() {
        mContext = ReportzActivity.this;

        imgBack = findViewById(R.id.imgBack);
        edtReportName = findViewById(R.id.edtReportName);
        edtReport = findViewById(R.id.edtReport);
        btnSave = findViewById(R.id.btnSave);

        String report = "Arrived for scheduled appointment at [[INSPECTION_TIME_AND_DATE]] "
                + "\nMr. [[INSURED_NAME]] was present during inspection. An onsite inspection of the loss was performed and the following was found: "
                + "\n\nDWELLING INSPECTION: "
                + "\n[[NUMBER_OF_STORIES]] Story [[TYPE_OF_CONSTRUCTION]]  [[TYPE_OF_USE]] [[TYPE_OF_USE_2]]"
                + "\n[[GARAGE]]"
                + "\n\n-The dwelling appears to be owner occupied on a year round basis. "
                + "\n- The exterior of the dwelling is [[EXTERIOR]]"
                + "\n- The dwelling roof is [[ROOFING]] Drip edge"
                + "\n-Depreciation documentation: Based on the age and condition of the materials, depreciation was set at  years."
                + "\n\n\nROOFING:"
                + "\n[[NUMBER_OF_STORIES]] Story  Roof with a [[PITCH]]/12 Roof Pitch "
                + "\nThere is [[NUMBER_OG_LAYERS]] Layer [[ROOFING]] &  [[DRIP_EDGE]] on the roof of the dwelling. "
                + "\nApproximate age of the roof in years:[[AGE]] years"
                + "\nROOF: [[ROOF_DAMAGE]]"
                + "\n\nFront elevation: [[FRONT_ELEVATION_DAMAGE]]"
                + "\nLeft elevation: [[LEFT_ELEVATION_DAMAGE]]"
                + "\nRear elevation: [[BACK_ELEVATION_DAMAGE]]"
                + "\nRight elevation: [[RIGHT_ELEVATION_DAMAGE]]"
                + "\n\nInterior of Dwelling: [[INTERIOR_DAMAGE]]"
                + "\n\nAPS: [[OTHER_STRUCTURES_DAMAGE]]"
                + "\nCONTENTS:  [[CONTENTS_DAMAGE]]"
                + "\nADDITIONAL: [[NOTES]]"
                + "\n\n\nFIELD REVIEW: The following was reviewed with Mr. [[INSURED_NAME]]"
                + "\n-The scope of the inspection was reviewed with Mr. [[INSURED_NAME]] in person. "
                + "\nMr. [[INSURED_NAME]] acknowledged understanding and is in agreement with the scope of repairs."
                + "\n-The claim process and file being subject to carriers review were addressed. "
                + "\nDiscussed with Mr. [[INSURED_NAME]] that I completed my inspection of damage to their property. I reviewed the scope of damages with them as noted in the above descriptions. I advised that I cannot comment on the payment of coverage but their Inside claim representative will do so upon receipt of my report. I let them know I would be submitting my report and that they should be hearing from their Inside Rep within 6 to 10 business days.  Confirmed that [[MORTGAGE]] as the mortgage company."
                + "\n\nLABOR MINIMUMS:  [[LABOR_MIN_REMOVED]] Labor Minimums were removed and [[LABOR_MIN_ADDED]] labor minimums were added to this estimate."
                + "\nCAUSE OF LOSS: [[CAUSE_OF_LOSS]] - 11/21/2017"
                + "\nSALVAGE: [[SALVAGE]]"
                + "\nSUBROGATION: [[SUBROGATION]]"
                + "\nOVERHEAD & PROFIT: - [[OVERHEAD_AND_PROFIT]]."
                + "\n\nCLAIM COMPLETION:  All documents and estimates are being forwarded via electronic upload to [[COMPANY_NAME]].";

        edtReport.setText(report);
        imgBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == imgBack.getId()) {
            onBackPressed();
        } else if (view.getId() == btnSave.getId()) {
            if (TextUtils.isEmpty(edtReportName.getText())) {
                edtReportName.setError("Please enter report name.");
                edtReportName.requestFocus();
            } else {
                if (btnSave.getText().toString().equals("Update"))
                    updateReport();
                else
                    addReport();
            }
        }
    }

    private void addReport() {
        if (Utility.haveInternet(mContext)) {
            HashMap<String, String> hmParam = new HashMap<>();
            hmParam.put("user_id", PrefManager.getUserId());
            hmParam.put("user_name", edtReportName.getText().toString());
            hmParam.put("report", edtReport.getText().toString());
            hmParam.put("report_type", "2");
            Log.i(TAG, "addReportParam = " + hmParam.toString());

            Utility.showProgress(mContext);
            ApiClient.getClient().create(APIInterface.class).addReport(hmParam).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Utility.dismissProgress();
                    Log.i(TAG, "addReportRes = " + response.body());

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
                    Log.i(TAG, "addReportError = " + t.toString());
                }
            });
        }
    }

    private void updateReport() {
        if (Utility.haveInternet(mContext)) {
            HashMap<String, String> hmParam = new HashMap<>();
            hmParam.put("user_name", edtReportName.getText().toString());
            hmParam.put("report", edtReport.getText().toString());
            hmParam.put("report_type", "2");

            Utility.showProgress(mContext);
            ApiClient.getClient().create(APIInterface.class).updateReport(reportModel.getId(), hmParam).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Utility.dismissProgress();
                    Log.i(TAG, "updateReportRes = " + response.body());

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
                    Log.i(TAG, "updateReportError = " + t.toString());
                }
            });
        }
    }
}
