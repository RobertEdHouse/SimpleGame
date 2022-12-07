package com.example.android_lab_1.task;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.android_lab_1.HistoryActivity;
import com.example.android_lab_1.fragments.GameDialogFragment;

import java.io.IOException;
import java.util.Objects;

public class LoadSaveThread extends Thread implements Observable{

    private Observer observer;
    private Runnable runnable=null;
    SharedPreferences sPref;
    final String SAVED_TEXT = "saved_text";
    @Override
    public void run() {
        if(runnable!=null)
            runnable.run();
    }
    public void saveHistory(String history){
        runnable = () -> {
            try {
                writeFile(history);
            } catch (IOException e) {
                e.printStackTrace();
            }

        };
    }

    public void loadHistory(){
        runnable = () -> {
            try {
                readFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    private void writeFile(String history) throws IOException {

        sPref = Objects.requireNonNull(getActivity()).getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedHistory=sPref.getString(SAVED_TEXT,"");
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, history+savedHistory);
        if(ed.commit()){
            notifyObservers("Write in File");
        }
    }

    private void readFile() throws IOException {
        sPref = Objects.requireNonNull(getActivity()).getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");
        notifyObservers(savedText);
    }



    @Override
    public void registerObserver(Observer o) {
        observer=o;
    }

    @Override
    public void notifyObservers(String message) {
        if(observer!=null)
            observer.update(message);
    }



    private Activity getActivity(){
        if(observer instanceof HistoryActivity)
            return (HistoryActivity)observer;
        else if(observer instanceof GameDialogFragment)
            return ((GameDialogFragment)observer).getActivity();

        return null;
    }
}
