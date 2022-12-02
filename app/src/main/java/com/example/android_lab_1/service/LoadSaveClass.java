package com.example.android_lab_1.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.android_lab_1.BuildConfig;
import com.example.android_lab_1.model.History;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class LoadSaveClass extends IntentService implements Observable{

    public static final String TAG =
            LoadSaveClass.class.getSimpleName();
    public static final String ACTION_SAVE_HISTORY =
            BuildConfig.APPLICATION_ID + ".ACTION_SAVE_HISTORY";
    public static final String ACTION_LOAD_HISTORY =
            BuildConfig.APPLICATION_ID + ".ACTION_LOAD_HISTORY";

    private static final String TIME = "time";
    private static final String SPEED = "speed";
    private static final String SCORE = "score";
    final String SAVED_TEXT = "saved_text";

    private static final String FILENAME = "fileHistory.txt";
    private Observer observer;
    SharedPreferences sPref;

    private IBinder binder = new ServiceBinder();


    public LoadSaveClass(String name) {
        super(name);
    }
    public LoadSaveClass() {
        super("LoadSaveService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags,
                              int startId) {
        Serializable observer=intent.getSerializableExtra("observer");
        registerObserver((Observer) observer);
        return super.onStartCommand(intent, flags, startId);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent arg0) {
        return binder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }
    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) return;
        String action = intent.getAction();
        if (action == null) return;
        try {
            handleAction(action, intent);
        } catch (InterruptedException e) {
            Log.e(TAG, "Job has been interrupted");
        }
    }
    private void handleAction(String action, Intent intent)
            throws InterruptedException {
        switch (action) {
            case ACTION_SAVE_HISTORY:
                    saveHistory(intent);
                        Log.d(TAG, "Save History!");
                break;
            case ACTION_LOAD_HISTORY:
                        readFile();
                break;
            default:
                throw new RuntimeException("Unknown action!");
        }
    }


    protected void saveHistory(Intent intent){
        Bundle arg=intent.getExtras();
        String newHistory=createHistory(arg);
        writeFile(newHistory);

    }

    void writeFile(String history) {

        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedHistory=sPref.getString(SAVED_TEXT,"");
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, savedHistory+history);
        if(ed.commit()){
            notifyObservers("Write in File");
        }

    }

    void readFile() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");
        notifyObservers(savedText);
    }

    private String createHistory(Bundle arg){
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm:ss");
        String date = dateFormat.format(Calendar.getInstance().getTime());
        int time=arg.getInt(TIME);
        String speed = arg.getString(SPEED);
        int score = arg.getInt(SCORE);

        return "\t" + date +"\n"+
                "Час: " + time +"\n"+
                "Швидкість: " + speed +"\n"+
                "Загальний рахунок: " + score +"\n";
    }

    @Override
    public void registerObserver(Observer o) {
        observer=o;
    }

    @Override
    public void removeObserver() {
        observer=null;
    }

    @Override
    public void notifyObservers(String message) {
        if(observer!=null){
            observer.update(message);
        }
    }

    public class ServiceBinder extends Binder {
        public LoadSaveClass getService() {
            return LoadSaveClass.this;
        }
    }

}
