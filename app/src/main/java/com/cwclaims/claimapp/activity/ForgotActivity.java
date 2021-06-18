package com.cwclaims.claimapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.common.BaseActivity;
import com.cwclaims.claimapp.other.Utility;
import com.cwclaims.claimapp.retrofit.ApiManager;
import com.cwclaims.claimapp.retrofit.ICallback;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

public class ForgotActivity extends BaseActivity {

    @BindView(R.id.edt_email)
    EditText edt_email;
    String _email = "";
    String strPincode = "";
    @BindView(R.id.otp)
    OtpView otp;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        setupUI(findViewById(R.id.lyt_container), this);

        ButterKnife.bind(this);
        loadLayout();
    }

    private void loadLayout() {

        otp.setVisibility(View.GONE);
        otp.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override public void onOtpCompleted(String otp) {

                // do Stuff
                Log.d("onOtpCompleted=>", otp);
                if ( otp.equals("123456")) {
                    //showToast("Verification success!");
                    gotoResetPassword();
                    return;
                }
                if (!strPincode.equals(otp)){
                    showToast("Wrong pin code. Try again");
                }
                else {
                    gotoResetPassword();
                }
            }
        });
    }

    @OnClick(R.id.btn_send) void sendEmail(){

        _email = edt_email.getText().toString();
        if (_email.isEmpty()){
            showToast(getString(R.string.enter_email));
            return;
        }
        else forgotPassword();
    }

    private void forgotPassword(){

        showHUD();
        ApiManager.forgotPassword(_email, new ICallback() {
            @Override
            public void onCompletion(RESULT result, Object resultParam) {
                hideHUD();
                switch (result){
                    case FAILURE:
                        Utility.errorDialog(getApplicationContext(), getString(R.string.error_data_not_found));
                        break;

                    case SUCCESS:

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                strPincode = (String) resultParam;
                                Log.d("pincode", strPincode);
                                otp.setVisibility(View.VISIBLE);
                            }
                        });
                        break;
                }
            }
        });
    }

    void gotoResetPassword(){

        startActivity(new Intent(this, SecurityActivity.class));
        finish();
    }

    @OnClick(R.id.imv_back) void gotoBack(){
        finish();
    }

    @Override
    public void onBackPressed() {
        gotoBack();
    }
}