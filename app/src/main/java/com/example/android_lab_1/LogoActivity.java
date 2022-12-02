package com.example.android_lab_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.example.android_lab_1.contract.AppContract;
import com.example.android_lab_1.fragments.LogoFragment;

import java.util.UUID;

public class LogoActivity extends AppCompatActivity implements AppContract {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);
        if (savedInstanceState == null) {
            launchFragment(null, new LogoFragment());
        }
    }
    private void launchFragment(@Nullable Fragment target,
                                Fragment fragment) {

        String tag = UUID.randomUUID().toString();
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.logoContainer, fragment, tag)
                .commit();
    }

    public void toMainActivity(){
        Intent intent = new Intent(LogoActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void toOptionsScreen(Fragment target) {

    }

    @Override
    public void toTimerScreen(Fragment target) {

    }

    @Override
    public void toGameScreen(Fragment target) {

    }

    @Override
    public void toHistoryScreen(Fragment target) {

    }

    @Override
    public void cancel() {

    }
}