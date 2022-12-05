package com.example.android_lab_1.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android_lab_1.R;


public class OptionsFragment extends BaseFragment{
    private static final String TIME = "time";
    private static final String ROWS = "rows";
    private static final String COLUMNS = "columns";
    private static final String INTERVAL_START = "intervalStart";
    private static final String INTERVAL_FINISH = "intervalFinish";

    private Spinner speedSpinner;
    private Spinner gridSpinner;
    private Spinner timeSpinner;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_options,
                container,
                false
        );
    }
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.startButton)
                .setOnClickListener(v -> {
                    setOptions();
                    getAppContract().toTimerScreen(this);
                });
        gridSpinner=view.findViewById(R.id.gridSpinner);
        timeSpinner=view.findViewById(R.id.timeSpinner);
        speedSpinner=view.findViewById(R.id.speedSpinner);
    }


    private void setOptions(){
        Bundle b=new Bundle();
        int row,column;

        String[] gridCount =gridSpinner.getSelectedItem().toString().split("x");
        row=Integer.parseInt(gridCount[0]);
        column=Integer.parseInt(gridCount[0]);
        b.putInt(ROWS,row);
        b.putInt(COLUMNS,column);


        String[] time = timeSpinner.getSelectedItem().toString().split(" ");
        b.putInt(TIME,Integer.parseInt(time[0]));

        String[] interval = speedSpinner.getSelectedItem().toString().split("[- ]");
        b.putFloat(INTERVAL_START,Float.parseFloat(interval[0]));
        b.putFloat(INTERVAL_FINISH,Float.parseFloat(interval[1]));

        setArguments(b);
    }
}
