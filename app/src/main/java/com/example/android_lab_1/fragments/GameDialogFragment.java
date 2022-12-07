package com.example.android_lab_1.fragments;

import android.annotation.SuppressLint;
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
import com.example.android_lab_1.task.LoadSaveThread;
import com.example.android_lab_1.task.Observer;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


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

        if (getArguments() != null){
            saveHistory();
        }
        return builder.create();
    }
    private void saveHistory(){
        LoadSaveThread thread= new LoadSaveThread();
        thread.registerObserver(this);
        String history=createHistory(getArguments());
        thread.saveHistory(history);
        thread.start();
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

    private String createHistory(Bundle arg){
        @SuppressLint("SimpleDateFormat")
        String date = makeDate();
        int time=arg.getInt(TIME);
        String speed = arg.getString(SPEED);
        int score = arg.getInt(SCORE);

        return "\t"+date +"\n"+
                "Час: " + time +" с\n"+
                "Швидкість: " + speed +" с\n"+
                "Загальний рахунок: " + score +"\n"+"\n";
    }

    @SuppressLint("SimpleDateFormat")
    private String makeDate(){
        Date date = Calendar.getInstance().getTime();
        String Date=date.getDay()+" ";
        switch (date.getMonth()){
            case 1:
                Date+="січня";
                break;
            case 2:
                Date+="лютого";
                break;
            case 3:
                Date+="березня";
                break;
            case 4:
                Date+="квітня";
                break;
            case 5:
                Date+="травня";
                break;
            case 6:
                Date+="червня";
                break;
            case 7:
                Date+="липня";
                break;
            case 8:
                Date+="серпня";
                break;
            case 9:
                Date+="вересня";
                break;
            case 10:
                Date+="жовтня";
                break;
            case 11:
                Date+="листопада";
                break;
            case 12:
                Date+="грудня";
                break;
        }
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat(" yyy");
        Date += dateFormat.format(Calendar.getInstance().getTime());
        dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date +="\n\t"+dateFormat.format(Calendar.getInstance().getTime());
        return Date;
    }
}