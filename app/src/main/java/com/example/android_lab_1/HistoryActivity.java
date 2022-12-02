package com.example.android_lab_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.android_lab_1.model.History;
import com.example.android_lab_1.service.LoadSaveClass;
import com.example.android_lab_1.service.Observable;
import com.example.android_lab_1.service.Observer;

import java.io.Serializable;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements Observer, Serializable {
    private static transient TextView textHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Button buttonClose = findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
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