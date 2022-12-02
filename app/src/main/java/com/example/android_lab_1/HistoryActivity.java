package com.example.android_lab_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_lab_1.service.LoadSaveClass;
import com.example.android_lab_1.service.Observer;

import java.io.Serializable;

public class HistoryActivity extends AppCompatActivity implements Observer, Serializable {
    private static transient TextView textHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ImageView buttonClose = findViewById(R.id.buttonClose_history);
        buttonClose.setOnClickListener(view -> {
            Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
            startActivity(intent);
        });
        textHistory=findViewById(R.id.textHistory);
        launchService(LoadSaveClass.ACTION_LOAD_HISTORY);
    }


    @Override
    public void update(String message) {
       textHistory.setText(message);
    }

    private void launchService(String action) {
        Intent intent = new Intent(this, LoadSaveClass.class);
        intent.setAction(action);
        intent.putExtra("observer",  this);
        startService(intent);
    }
}