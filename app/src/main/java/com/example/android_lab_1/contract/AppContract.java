package com.example.android_lab_1.contract;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android_lab_1.fragments.BaseFragment;
import com.example.android_lab_1.model.Game;

public interface AppContract {
    void toOptionsScreen(Fragment target);

    void toTimerScreen(Fragment target);

    void toGameScreen(Fragment target);

    void toHistoryScreen(Fragment target);

    void cancel();
}
