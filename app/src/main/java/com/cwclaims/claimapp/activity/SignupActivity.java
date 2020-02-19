package com.cwclaims.claimapp.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.other.Utility;
import com.cwclaims.claimapp.retrofit.APIInterface;
import com.cwclaims.claimapp.retrofit.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;

public class SignupActivity extends Activity implements View.OnClickListener {

    private final String TAG = "SignupActivity";
    private Context mContext;
    ProgressDialog progressDialog;
    WebView wv_register;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        init();
    }

    private EditText edtName, edtMobile, edtEmail, edtPassword, edtConfirmPassword;
    private Button btnRegister;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        mContext = SignupActivity.this;


        this.wv_register = (WebView) findViewById(R.id.wv_register);

        wv_register.setWebViewClient(new myWebClient());
        WebSettings webSettings = wv_register.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv_register.getSettings().setBuiltInZoomControls(false);
        wv_register.getSettings().setSupportZoom(true);
        wv_register.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv_register.getSettings().setBuiltInZoomControls(true);
//      webview.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
        wv_register.getSettings().setDisplayZoomControls(false);

        startProDialog();

        wv_register.loadUrl("https://www.claimmate.com/signup");



        edtName = findViewById(R.id.edtName);
        edtMobile = findViewById(R.id.edtMobile);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);

        img_back = (ImageView)findViewById(R.id.img_back);

        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);

        img_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnRegister.getId()) {
            if (Utility.haveInternet(mContext)) {
                if (validate()) {
                    register();
                }
            }
        }
        else  if(view == img_back)
        {
            finish();
        }
    }

    private void register() {
        Utility.showProgress(mContext);
        ApiClient.getClient().create(APIInterface.class).registerUser(edtName.getText().toString(), edtEmail.getText().toString(), edtMobile.getText().toString(), edtPassword.getText().toString()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Utility.dismissProgress();
                Log.i(TAG, "registerUserRes = "+response.body());

                if (response.body() == null) {
                    Utility.errorDialog(mContext, getString(R.string.error_data_not_found));
                    return;
                }

                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    if (jsonObject.getString("success").equals("success")) {
                        onBackPressed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Utility.dismissProgress();
                Log.i(TAG, "registerUserError = "+t.toString());
            }
        });
    }

    private boolean validate() {
        boolean isValid = true;

        if (TextUtils.isEmpty(edtName.getText())) {
            edtName.setError(getString(R.string.error_field_required));
            edtName.requestFocus();
            isValid = false;
        } else if (TextUtils.isEmpty(edtMobile.getText())) {
            edtMobile.setError(getString(R.string.error_field_required));
            edtMobile.requestFocus();
            isValid = false;
        } else if (TextUtils.isEmpty(edtEmail.getText())) {
            edtEmail.setError(getString(R.string.error_field_required));
            edtEmail.requestFocus();
            isValid = false;
        } else if (!edtEmail.getText().toString().matches(emailPattern)) {
            edtEmail.setError("Enter Valid Email Address.");
            edtEmail.requestFocus();
            isValid = false;
        } else if (TextUtils.isEmpty(edtPassword.getText())) {
            edtPassword.setError(getString(R.string.error_field_required));
            edtPassword.requestFocus();
            isValid = false;
        } else if (TextUtils.isEmpty(edtConfirmPassword.getText())) {
            edtConfirmPassword.setError(getString(R.string.error_field_required));
            edtConfirmPassword.requestFocus();
            isValid = false;
        } else if (!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
            Toast.makeText(getApplicationContext(),"Both password must be same.",Toast.LENGTH_LONG).show();
            isValid = false;
        }
        return isValid;
    }



    public void startProDialog()
    {

        if(progressDialog == null)
        {
            progressDialog = new ProgressDialog(SignupActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading... please wait");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }
        else
        {
            progressDialog.show();
        }

    }

    public void stopProDialog()
    {
        if(progressDialog != null )
        {
            if(progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
        }
    }




    public  class myWebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            startProDialog();
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);
            stopProDialog();
        }



    }


}
