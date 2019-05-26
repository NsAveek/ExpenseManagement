package aveek.com.management;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import aveek.com.management.R;
import dagger.android.AndroidInjection;


public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    public void onError(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
