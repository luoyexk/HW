package com.zwl.myapplication;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import com.zwl.myapplication.base.BaseActivity;
import com.zwl.myapplication.navigator.AppNavigator;
import com.zwl.myapplication.navigator.AppNavigatorImpl;
import com.zwl.myapplication.navigator.Screens;

import static com.zwl.myapplication.navigator.Screens.*;
import static com.zwl.myapplication.navigator.Screens.Register;

public class CertificationActivity extends BaseActivity {

    private AppNavigator mNavigate = new AppNavigatorImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                clickRegister();
            case R.id.btn_login:
                clickLogin();
            default:
        }
    }

    private void clickLogin() {
        navigate(Login);
    }

    private void clickRegister() {
        navigate(Register);
    }

    private void navigate(Screens screen) {
        mNavigate.navigateTo(screen);
    }

}