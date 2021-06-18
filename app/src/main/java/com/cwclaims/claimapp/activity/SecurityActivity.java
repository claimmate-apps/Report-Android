package com.cwclaims.claimapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.EditText;

import com.cwclaims.claimapp.R;
import com.cwclaims.claimapp.common.BaseActivity;
import com.cwclaims.claimapp.common.Commons;
import com.cwclaims.claimapp.retrofit.ApiManager;
import com.cwclaims.claimapp.retrofit.ICallback;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecurityActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.edt_email)
    EditText edt_email;

    @BindView(R.id.edt_new_password)
    EditText edt_new_password;

    @BindView(R.id.edt_confirm)
    EditText edt_confirm;

    String _edt_email = "", _new_password = "", _confirm_password = "";
    String password = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        setupUI(findViewById(R.id.lyt_container), this);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadLayout();
    }

    private void loadLayout() {

    }

    @OnClick(R.id.btn_save) void save(){

        _edt_email = edt_email.getText().toString();
        _new_password = edt_new_password.getText().toString();
        _confirm_password = edt_confirm.getText().toString();

        if (checkedValid()){
            password = _new_password;
            processReset();
        }

    }

    private void processReset(){
        showHUD();
        ApiManager.resetPassword(Commons.thisUser.getId(), password, new ICallback() {
            @Override
            public void onCompletion(RESULT result, Object resultParam) {
                hideHUD();
                switch (result){
                    case SUCCESS:
                        showToast1("Success");
                        finish();
                        break;

                    case FAILURE:
                        showToast1(getString(R.string.error));
                        break;
                }
            }
        });
    }

    private boolean checkedValid(){

        if (_edt_email.isEmpty()){
            showToast(getString(R.string.enter_email));
            return false;
        }
        else if (_new_password.isEmpty()){
            showToast("Enter new password");
            return false;
        }
        else if (!_new_password.equals(_confirm_password)){
            showToast("Password doesn't match");
            return false;
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}