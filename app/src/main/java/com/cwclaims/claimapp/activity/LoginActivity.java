package com.cwclaims.claimapp.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.common.BaseActivity;
import com.cwclaims.claimapp.common.Commons;
import com.cwclaims.claimapp.other.PrefManager;
import com.cwclaims.claimapp.other.Utility;
import com.cwclaims.claimapp.retrofit.APIInterface;
import com.cwclaims.claimapp.retrofit.ApiClient;
import com.cwclaims.claimapp.retrofit.ApiManager;
import com.cwclaims.claimapp.retrofit.ICallback;
import com.google.gson.JsonObject;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private final String TAG = "LoginActivity";
    private Context mContext;


    int requestSignUp = 502;
    String _email = "", _password = "";
    boolean _isFromLogout = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnRegister;
    private TextView txtForgotPassword;

    private void init() {
        mContext = LoginActivity.this;
        new PrefManager(mContext);

        if (!PrefManager.getUserId().equals("")) {
            Intent intent = new Intent(LoginActivity.this, ReportListActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        txtForgotPassword = findViewById(R.id.txtForgotPassword);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        txtForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnLogin.getId()) {
            if (Utility.haveInternet(mContext)) {
                if (validate())
                    login();
            }
        } else if (view.getId() == btnRegister.getId()) {
            Intent register_act = new Intent(getApplicationContext(), SignUpActivity1.class);
            startActivityForResult(register_act, requestSignUp);
            //startActivity(new Intent(mContext, SignupActivity.class));
        } else if (view.getId() == txtForgotPassword.getId()) {
            Intent register_act = new Intent(getApplicationContext(), ForgotActivity.class);
            startActivity(register_act);
            //forgotPassword();
        }
    }

    private void login() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
            return;
        }
        //final String IMEI = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        final String IMEI = "ReportAppDeviceTokenStatic";
                Utility.showProgress(mContext);

        ApiManager.login(edtEmail.getText().toString(), edtPassword.getText().toString(), new ICallback() {
            @Override
            public void onCompletion(ICallback.RESULT result, Object resultParam) {
                Utility.dismissProgress();
                switch (result){

                    case FAILURE:

                        if (resultParam == null)
                            showToast1(getString(R.string.error));
                        else {
                            int resultCode = (Integer)resultParam;
                            if (resultCode == 201)
                                showToast1(getString(R.string.invalid_login));
                        }
                        break;


                    case SUCCESS:

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                JsonObject jsonUser = ((JsonObject)resultParam).get(ApiManager.PARAMS.USER_DATA).getAsJsonObject();

                                Log.d("user_id====>", jsonUser.get(ApiManager.PARAMS.USER_ID).getAsString());

                                PrefManager.setUserId(jsonUser.get(ApiManager.PARAMS.USER_ID).getAsString());


                                Prefs.putString(Commons.PREFKEY_USEREMAIL, _email);
                                Prefs.putString(Commons.PREFKEY_USERPWD, _password);

                                startActivity(new Intent(LoginActivity.this, ReportListActivity.class));
                                LoginActivity.this.finish();
                            }
                        });

                        break;
                }
            }
        });
        /*ApiClient.getClient().create(APIInterface.class).login(edtEmail.getText().toString(), edtPassword.getText().toString(), IMEI, "inspect").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Utility.dismissProgress();
                Log.i(TAG, "loginRes = " + response.body());

                if (response.body() == null) {
                    Utility.errorDialog(mContext, getString(R.string.error_data_not_found));
                    return;
                }

                try {
                    JSONObject jsonObject = new JSONObject(response.body());

                    if (jsonObject.getString("success").equals("success")) {
                        JSONObject user_data = jsonObject.getJSONObject("user_data");

                        PrefManager.setUserId(user_data.getString("id"));

                        Intent intent = new Intent(LoginActivity.this, ReportListActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    } else {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Utility.dismissProgress();
                Log.i(TAG, "loginError = " + t.toString());
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestSignUp){
            if (resultCode == Activity.RESULT_OK){

                _email = (String)data.getStringExtra(Commons.PREFKEY_USEREMAIL);
                _password = (String) data.getStringExtra(Commons.PREFKEY_USERPWD);

                progressLogin();

            }
        }
    }

    private void progressLogin(){

        Utility.showProgress(mContext);

        ApiManager.login(_email, _password, new ICallback() {
            @Override
            public void onCompletion(ICallback.RESULT result, Object resultParam) {
                Utility.dismissProgress();
                switch (result){

                    case FAILURE:

                        if (resultParam == null)
                            showToast1(getString(R.string.error));

                        else {

                            int resultCode = (Integer)resultParam;

                            if (resultCode == 201)
                                showToast1(getString(R.string.invalid_login));
                        }
                        break;

                    case SUCCESS:

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                JsonObject jsonUser = ((JsonObject)resultParam).get(ApiManager.PARAMS.USER_DATA).getAsJsonObject();

                                PrefManager.setUserId(jsonUser.get(ApiManager.PARAMS.USER_ID).getAsString());

                                Prefs.putString(Commons.PREFKEY_USEREMAIL, _email);
                                Prefs.putString(Commons.PREFKEY_USERPWD, _password);

                                startActivity(new Intent(LoginActivity.this, ReportListActivity.class));
                                LoginActivity.this.finish();
                            }
                        });

                        break;
                }
            }
        });
    }

    private void forgotPassword() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.popup_forgot_password);

        final EditText edtEmail = dialog.findViewById(R.id.edtEmail);

        dialog.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.hideKeyboard(mContext, edtEmail);
                if (Utility.haveInternet(mContext)) {
                    if (TextUtils.isEmpty(edtEmail.getText())) {
                        edtEmail.setError("Enter Email.");
                        edtEmail.requestFocus();
                    } else {
                        Utility.showProgress(mContext);
                        ApiClient.getClient().create(APIInterface.class).forgotPassword(edtEmail.getText().toString()).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Utility.dismissProgress();
                                Log.i(TAG, "forgotPasswordRes = "+response.body());
                                if (response.body() == null) {
                                    Utility.errorDialog(mContext, getString(R.string.error_data_not_found));
                                    return;
                                }

                                try {
                                    JSONObject jsonObject = new JSONObject(response.body());

                                    if (jsonObject.getString("success").equalsIgnoreCase("success")) {
                                        Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        Utility.errorDialog(mContext, jsonObject.getString("message"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Utility.dismissProgress();
                                Log.i(TAG, "forgotPasswordError = "+t.toString());
                            }
                        });
                    }
                }
            }
        });

        dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private boolean validate() {
        boolean isValid = true;

        if (TextUtils.isEmpty(edtEmail.getText())) {
            edtEmail.setError(getString(R.string.error_field_required));
            edtEmail.requestFocus();
            isValid = false;
        } else if (TextUtils.isEmpty(edtPassword.getText())) {
            edtPassword.setError(getString(R.string.error_field_required));
            isValid = false;
        }
        return isValid;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (checkPermission()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, 101);
        }
    }

    public boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101 && (grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//            startHome();
        } else {
            showPermissionDialog();
        }
    }

    public void showPermissionDialog() {

        new AlertDialog.Builder(LoginActivity.this)
                .setMessage(
                        getString(R.string.error_permission))
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        goToSetting();
                    }
                }).setCancelable(false).show();
    }

    void goToSetting() {
        Intent loAppSettings = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + getPackageName()));
        loAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
        loAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(loAppSettings, 102);
    }
}
