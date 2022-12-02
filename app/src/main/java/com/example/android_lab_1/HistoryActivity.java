package com.example.android_lab_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.android_lab_1.model.History;
import com.example.android_lab_1.service.LoadSaveClass;
import com.example.android_lab_1.service.Observable;
import com.example.android_lab_1.service.Observer;

import java.util.List;

public class HistoryActivity extends AppCompatActivity implements Observable {
    private Observer service;
    private List<History> history;

    private Button buttonClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        registerObserver(new LoadSaveClass("LoadSave"));
        notifyObservers();
        buttonClose=findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
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
            history=service.loadHistory();
    }
}