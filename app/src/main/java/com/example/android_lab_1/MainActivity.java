package com.example.android_lab_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Window;

import com.example.android_lab_1.contract.AppContract;
import com.example.android_lab_1.fragments.GameFragment;
import com.example.android_lab_1.fragments.LogoFragment;
import com.example.android_lab_1.fragments.OptionsFragment;
import com.example.android_lab_1.fragments.TimerFragment;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements AppContract {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            launchFragment(null, new LogoFragment());
        }
    }

    @Override
    public void toOptionsScreen(Fragment target) {
        launchFragment(null, new OptionsFragment());
    }
    @Override
    public void toTimerScreen(Fragment target) {

        launchFragment(target, TimerFragment.newInstance(target.getArguments()));

    }
    @Override
    public void toGameScreen(Fragment target) {

        launchFragment(target,GameFragment.newInstance(target.requireArguments()));
    }

    @Override
    public void cancel() {
        int count = getSupportFragmentManager()
                .getBackStackEntryCount();
        if (count <= 1) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    private void launchFragment(@Nullable Fragment target,
                                Fragment fragment) {
        if (target != null) {
            fragment.setTargetFragment(target, 0);
        }
        String tag = UUID.randomUUID().toString();
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, fragment, tag)
                .commit();
    }
}