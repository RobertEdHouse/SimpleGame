package com.example.android_lab_1.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android_lab_1.R;

public class TimerFragment extends BaseFragment{
    CountDownTimer countDownTimer;

    public static TimerFragment newInstance(Bundle args) {
        TimerFragment fragment = new TimerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_timer,
                container,
                false
        );
    }
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.quitButton)
                .setOnClickListener(v -> {
                    if(countDownTimer!=null)
                        countDownTimer.cancel();
                    getAppContract().cancel();

                });
        TextView timer = view.findViewById(R.id.timerText);
        countDownTimer = new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000));
            }
            public void onFinish() {
                startGame();
            }
        }.start();
    }
    private void startGame(){
        getAppContract().toGameScreen(this);
    }
    @Override
    public void onStop() {
        super.onStop();
        if(countDownTimer!=null)
            countDownTimer.cancel();
    }
}
