package com.zwl.myapplication.base;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    protected void toast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
