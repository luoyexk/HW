package com.zwl.myapplication.navigator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.zwl.myapplication.login.LoginFragment;
import com.zwl.myapplication.R;
import com.zwl.myapplication.register.RegisterFragment;

public class AppNavigatorImpl implements AppNavigator {

    private final AppCompatActivity mActivity;

    public AppNavigatorImpl(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Override
    public void navigateTo(Screens screen) {
        Fragment fragment = null;
        switch (screen) {
            case Register:
                fragment = new RegisterFragment();
                break;
            case Login:
                fragment = new LoginFragment();
                break;
        }

        mActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }
}
