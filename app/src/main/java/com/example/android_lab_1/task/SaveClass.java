package com.example.android_lab_1.task;

import android.content.Context;
import android.util.Log;

import com.example.android_lab_1.model.History;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class SaveClass implements Runnable{
    private History history;

    public SaveClass(History history) {
        this.history = history;
    }

    @Override
    public void run() {

    }
    private void saveInFile(Context context){
        new Runnable() {
            @Override
            public void run() {
                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("save_game.txt", Context.MODE_PRIVATE));
                   // outputStreamWriter.write();
                    outputStreamWriter.close();
                }
                catch (IOException e) {
                    Log.e("Exception", "File write failed: " + e.toString());
                }
            }
        };

    }
}
