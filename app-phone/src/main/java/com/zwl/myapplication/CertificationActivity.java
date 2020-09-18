package com.zwl.myapplication;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectUser;
import com.huawei.agconnect.auth.EmailAuthProvider;
import com.huawei.agconnect.auth.EmailUser;
import com.huawei.agconnect.auth.SignInResult;
import com.huawei.agconnect.auth.VerifyCodeResult;
import com.huawei.agconnect.auth.VerifyCodeSettings;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskExecutors;

import java.util.Locale;

import static com.huawei.agconnect.auth.VerifyCodeSettings.ACTION_REGISTER_LOGIN;

/**
 * 认证服务
 */
public class CertificationActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etCode;
    private EditText etPassword;
    private ViewGroup mAuthContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        etEmail = findViewById(R.id.et_email);
        etCode = findViewById(R.id.et_code);
        etPassword = findViewById(R.id.et_password);
        mAuthContainer = findViewById(R.id.container_register);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_auth:
                clickEmailAuth();
            case R.id.btn_register:
                clickRegister();
            default:
        }
    }

    private void clickRegister() {
        String email = etEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            toast("邮箱为空");
            return;
        }

        String code = etCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            toast("验证码为空");
            return;
        }

        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            toast("密码为空");
            return;
        }
        startRegister(email, password, code);
    }

    private void startRegister(@NonNull String email, @NonNull String password, @NonNull String code) {
        EmailUser emailUser = new EmailUser.Builder()
                .setEmail(email)
                .setVerifyCode(code)
                .setPassword(password) //非必选，如果设置了，表示为当前用户默认创建了密码，后续可以通过密码登录
                //否则，只能通过验证码登录
                .build();
        AGConnectAuth.getInstance().createUser(emailUser)
                .addOnSuccessListener(new OnSuccessListener<SignInResult>() {
                    @Override
                    public void onSuccess(SignInResult signInResult) {
                        //创建帐号成功后，默认已登录
                        String email = signInResult.getUser().getEmail();
                        new AlertDialog.Builder(CertificationActivity.this)
                                .setTitle("创建成功")
                                .setMessage("邮箱: " + email)
                                .setPositiveButton(android.R.string.ok, null)
                                .show();
                        mAuthContainer.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        toast("创建失败:" + e.getMessage());
                    }
                });
    }

    private void clickEmailAuth() {
        AGConnectUser user = AGConnectAuth.getInstance().getCurrentUser();
        if (user == null) {
            String email = ((EditText) findViewById(R.id.et_email)).getText().toString();
            if (TextUtils.isEmpty(email)) {
                toast("输入邮箱");
                return;
            }
            register(email);
        } else {
            toast("已经注册过");
        }
    }

    private void register(String email) {
        VerifyCodeSettings settings = VerifyCodeSettings.newBuilder()
                .action(ACTION_REGISTER_LOGIN)   //ACTION_REGISTER_LOGIN/ACTION_RESET_PASSWORD
                .sendInterval(30) //最小发送间隔，30-120s
                .locale(Locale.CHINA) //非必选，发送验证码的语言，locale必须包含Langrage和Country,默认为Locale.getDefault
                .build();

        Task<VerifyCodeResult> task = EmailAuthProvider.requestVerifyCode(email, settings);
        task.addOnSuccessListener(TaskExecutors.uiThread(), new OnSuccessListener<VerifyCodeResult>() {
            @Override
            public void onSuccess(VerifyCodeResult verifyCodeResult) {
                //验证码申请成功
                toast("验证码发送成功");
                mAuthContainer.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(TaskExecutors.uiThread(), new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                toast("验证码发送失败");
                mAuthContainer.setVisibility(View.GONE);
            }
        });
    }

    private void toast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}