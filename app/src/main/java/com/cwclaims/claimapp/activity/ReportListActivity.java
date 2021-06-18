package com.cwclaims.claimapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.adpts.ReportAdpt;
import com.cwclaims.claimapp.adpts.SpinnerAdpt;
import com.cwclaims.claimapp.common.Commons;
import com.cwclaims.claimapp.models.ReportModel;
import com.cwclaims.claimapp.other.PrefManager;
import com.cwclaims.claimapp.other.Utility;
import com.cwclaims.claimapp.retrofit.APIInterface;
import com.cwclaims.claimapp.retrofit.ApiClient;
import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportListActivity extends Activity implements View.OnClickListener {

    private final String TAG = "ReportListActivity";
    private Context mContext;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

        init();
    }

    private ImageView imgAdd, imgLogout;
    private Button btnSetting;
    private ListView listReports;

    private ArrayList<ReportModel> arrayListReport;

    private void init() {
        mContext = ReportListActivity.this;
        new PrefManager(mContext);

        imgAdd = findViewById(R.id.imgAdd);
        imgLogout = findViewById(R.id.imgLogout);
        btnSetting = findViewById(R.id.btnSetting);
        listReports = findViewById(R.id.listReports);

        imgAdd.setOnClickListener(this);
        imgLogout.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getReport();
        //checkAuth();
    }

    private void getReport() {
        if (Utility.haveInternet(mContext)) {
            Utility.showProgress(mContext);
            ApiClient.getClient().create(APIInterface.class).getReport(PrefManager.getUserId()).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Utility.dismissProgress();
                    Log.i(TAG, "getReportRes = " + response.body());

                    if (response.body() == null) {
                        Utility.errorDialog(mContext, getString(R.string.error_data_not_found));
                        return;
                    }

                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        if (jsonObject.getString("success").equals("success")) {
                            arrayListReport = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            int cnt=1;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Gson gson = new Gson();
                                ReportModel reportModel = gson.fromJson(jsonArray.getJSONObject(i).toString(), ReportModel.class);
//                                if (reportModel.getReport_type().equals("2"))
//                                    reportModel.setUser_name("Report "+cnt++);
                                arrayListReport.add(reportModel);
                            }
                            listReports.setAdapter(new ReportAdpt(mContext, arrayListReport, ReportListActivity.this));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Utility.dismissProgress();
                    Log.i(TAG, "getReportError = " + t.toString());
                }
            });
        }
    }

    public void deleteReport(final String reportId, final String from) {
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle("Delete?");
        alert.setMessage("Are you sure want to delete?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Utility.showProgress(mContext);
                ApiClient.getClient().create(APIInterface.class).deleteReport(reportId, from).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Utility.dismissProgress();
                        Log.i(TAG, "deleteReportRes = " + response.body());

                        if (response.body() == null) {
                            Utility.errorDialog(mContext, getString(R.string.error_data_not_found));
                            return;
                        }

                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            if (jsonObject.getString("success").equals("success")) {
                                getReport();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Utility.dismissProgress();
                        Log.i(TAG, "deleteReportError = " + t.toString());
                    }
                });
            }
        });
        alert.setNegativeButton("No", null);

        if (Utility.haveInternet(mContext))
            alert.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == imgAdd.getId())
        {

            Intent addreportactivity = new Intent(ReportListActivity.this, AddReportActivity.class);
            addreportactivity.putExtra("flag","add");
            startActivity(addreportactivity);

//            startActivity(new Intent(mContext, AddReportActivity.class));
        } else if (view.getId() == imgLogout.getId()) {
            logout();
        } else if (view.getId() == btnSetting.getId()) {
            startActivity(new Intent(mContext, SettingActivity.class));
        }
    }

    private void logout() {

        PrefManager.logout();

        Prefs.remove(Commons.PREFKEY_USEREMAIL);
        Prefs.remove(Commons.PREFKEY_USERPWD);

        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();

        /*if (Utility.haveInternet(mContext)) {
            Utility.showProgress(mContext);
            ApiClient.getClient().create(APIInterface.class).logout(PrefManager.getUserId(), "inspect").enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    Utility.dismissProgress();
                    Log.i(TAG, "logoutRes = " + response.body());

                    if (response.body() == null) {
                        Utility.errorDialog(mContext, getString(R.string.error_data_not_found));
                        return;
                    }

                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        if (jsonObject.getString("success").equals("success")) {
                            PrefManager.logout();
                            startActivity(new Intent(mContext, LoginActivity.class));
                            ReportListActivity.this.finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Utility.dismissProgress();
                    Log.i(TAG, "logoutError = " + t.toString());
                }
            });
        }*/
    }

    SharedPreferences lastpathpf;
    SharedPreferences.Editor lastimageeditor;

    public void showSettingDialog() {

        lastpathpf = getSharedPreferences("claimmateform", Context.MODE_PRIVATE);
        lastimageeditor = lastpathpf.edit();

        String stremail = lastpathpf.getString("toemail", "claimzapp@gmail.com");
        String strdate = lastpathpf.getString("dateofloss", "11/21/2017");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setCancelable(false);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.settingalert, null);
        dialogBuilder.setView(dialogView);

        Button btnaddvalue = dialogView.findViewById(R.id.btnaddvalue);
        final Button btndefaultdateofloss = dialogView.findViewById(R.id.btndefaultdateofloss);
        btndefaultdateofloss.setText(strdate);

        final EditText txtemail = dialogView.findViewById(R.id.txtemail);
        txtemail.setText(stremail);

        dialogBuilder.setTitle("Setting");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                lastimageeditor.putString("toemail", txtemail.getText().toString().trim());
                lastimageeditor.commit();
                //do something with edt.getText().toString();
                dialog.dismiss();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        final AlertDialog b = dialogBuilder.create();
        b.show();

        btnaddvalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent tablelist = new Intent(getApplicationContext(), TableNameListActivity.class);
//                startActivity(tablelist);
                startActivity(new Intent(mContext, SettingActivity.class));
                b.dismiss();
            }
        });

        btndefaultdateofloss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, AlertDialog.THEME_DEVICE_DEFAULT_DARK, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        btndefaultdateofloss.setText((month + 1) + "/" + day + "/" + year);

                        lastimageeditor.putString("dateofloss", "" + (month + 1) + "/" + day + "/" + year);
                        lastimageeditor.commit();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
    }

    private void checkAuth() {

        if (Utility.haveInternet(mContext)) {
            ApiClient.getClient().create(APIInterface.class).checkStatus(PrefManager.getUserId()).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    Log.i(TAG, "checkStatusRes = " + response.body());
                    if (response.body() == null) {
                        return;
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        if (jsonObject.getString("message").equalsIgnoreCase("User blocked!")) {
                            logout();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.i(TAG, "checkStatusError = " + t.toString());
                }
            });
        }
    }
}
