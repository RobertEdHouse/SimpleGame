package com.example.android_lab_1.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_lab_1.R;
import com.example.android_lab_1.service.LoadSaveClass;
import com.example.android_lab_1.service.Observer;

import java.io.Serializable;


public class GameDialogFragment extends DialogFragment implements Observer, Serializable {
    private static final String TAG =
            GameDialogFragment.class.getSimpleName();

    private static final String TIME = "time";
    private static final String SPEED = "speed";
    private static final String SCORE = "score";

    private int time;
    private String speed;
    private int score;
    public GameDialogFragment() {
    }
    public static GameDialogFragment newInstance(Bundle bundle) {
        GameDialogFragment fragment = new GameDialogFragment();
        Bundle args = new Bundle();
        args.putInt(TIME, bundle.getInt(TIME));
        args.putString(SPEED, bundle.getString(SPEED));
        args.putInt(SCORE, bundle.getInt(SCORE));
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null) {
            time = getArguments().getInt(TIME);
            speed = getArguments().getString(SPEED);
            score = getArguments().getInt(SCORE);
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder
                .setTitle("Загальний рахунок "+ score)
                .setView(R.layout.fragment_game_dialog)
                .setMessage("Час: "+ time +'\n'+
                        "Швидкість: "+speed+'\n'+
                        "Рахунок: "+ score)
                .setPositiveButton("Добре!", null)
                .create();
        if (getArguments() != null)
            launchService(LoadSaveClass.ACTION_SAVE_HISTORY, getArguments());
        return builder.create();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_game_dialog, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void update(String message) {
        Log.println(Log.INFO,TAG,message);
    }

    private void launchService(String action, Bundle data) {

        Intent intent = new Intent(this.getActivity(), LoadSaveClass.class);
        intent.setAction(action);
        intent.putExtras(data);
        intent.putExtra("observer",  this);
        requireActivity().startService(intent);
    }
}