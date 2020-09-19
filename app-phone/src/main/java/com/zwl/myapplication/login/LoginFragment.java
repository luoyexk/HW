package com.zwl.myapplication.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectAuthCredential;
import com.huawei.agconnect.auth.EmailAuthProvider;
import com.huawei.agconnect.auth.SignInResult;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.zwl.myapplication.R;
import com.zwl.myapplication.base.BaseFragment;

/**
 * 认证服务 -- 登陆
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private EditText mEtEmail;
    private EditText mEtPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        mEtEmail = view.findViewById(R.id.et_email);
        mEtPassword = view.findViewById(R.id.et_password_login);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            clickLogin();
        }
    }

    private void clickLogin() {
        String password = mEtPassword.getText().toString();
        String email = mEtEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            toast(R.string.empty_email);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            toast(R.string.empty_email);
            return;
        }
        startLogin(email, password);
    }

    private void startLogin(@NonNull String email, @NonNull String password) {
        AGConnectAuthCredential credential = EmailAuthProvider.credentialWithPassword(email, password);
        AGConnectAuth.getInstance().signIn(credential)
                .addOnSuccessListener(new OnSuccessListener<SignInResult>() {
                    @Override
                    public void onSuccess(SignInResult signInResult) {
                        //获取登录信息
                        toast(R.string.suc_login);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        toast(R.string.failed_login);
                    }
                });
    }
}
