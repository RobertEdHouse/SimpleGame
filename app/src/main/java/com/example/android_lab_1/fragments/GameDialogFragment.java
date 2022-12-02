package com.example.android_lab_1.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_lab_1.R;
import com.example.android_lab_1.model.History;
import com.example.android_lab_1.service.LoadSaveClass;
import com.example.android_lab_1.service.Observable;
import com.example.android_lab_1.service.Observer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class GameDialogFragment extends DialogFragment implements Observable {

    private static final String TIME = "time";
    private static final String SPEED = "speed";
    private static final String SCORE = "score";

    private int time;
    private String speed;
    private int score;

    private History history;

    private Observer service;
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
        notifyObservers();
        return builder.create();
    }

//    private History makeHistory(){
//        @SuppressLint("SimpleDateFormat")
//        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy '@' HH:mm:ss");
//        String date = dateFormat.format(Calendar.getInstance().getTime());
//        return  new History(score,time,speed,date);
//    }
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
    public void registerObserver(Observer o) {
        service=o;
    }

    @Override
    public void removeObserver(Observer o) {
        service=null;
    }

    @Override
    public void notifyObservers() {
        if(service!=null)
            service.saveHistory(history);
    }
}