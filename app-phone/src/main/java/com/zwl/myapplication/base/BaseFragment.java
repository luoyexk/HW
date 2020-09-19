package com.zwl.myapplication.base;

import android.widget.Toast;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    protected void toast(@StringRes int id) {
        if (getActivity() == null) {
            return;
        }
        toast(getString(id));
    }

    protected void toast(String content) {
        if (getActivity() == null) {
            return;
        }
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }
}
