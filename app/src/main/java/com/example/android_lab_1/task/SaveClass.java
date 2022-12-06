package com.example.android_lab_1.task;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.android_lab_1.model.History;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class SaveClass implements Runnable{
    private History history;
    private SharedPreferences sPref;
    private final String SAVED_TEXT = "saved_text";
    public SaveClass(History history) {
        this.history = history;
    }

    @Override
    public void run() {

    }
    private void saveInFile(){
        new Runnable() {
            @Override
            public void run() {
                try {
                    sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
                    String savedHistory=sPref.getString(SAVED_TEXT,"");
                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putString(SAVED_TEXT, history+savedHistory);
                    ed.commit();

                }
                catch (IOException e) {
                    Log.e("Exception", "File write failed: " + e.toString());
                }
            }
        };

    }
}
