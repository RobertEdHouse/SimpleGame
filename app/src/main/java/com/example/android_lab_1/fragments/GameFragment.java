package com.example.android_lab_1.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android_lab_1.R;
import com.example.android_lab_1.model.Game;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameFragment extends BaseFragment{
    private static final String TIME = "time";
    private static final String ROWS = "rows";
    private static final String COLUMNS = "columns";
    private static final String INTERVAL_START = "intervalStart";
    private static final String INTERVAL_FINISH = "intervalFinish";
    private static final String SPEED = "speed";
    private static final String SCORE = "score";

    private int time;
    private int rows;
    private int columns;
    private float intervalStart;
    private float intervalFinish;


    Handler updateHandler = new Handler();
    private TextView textTime;
    private TextView textScore;
    CountDownTimer countDownTimer;
    Timer myTimer;
    private Game game;

    public static GameFragment newInstance(Bundle bundle) {

        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putInt(TIME, bundle.getInt(TIME));
        args.putInt(ROWS, bundle.getInt(ROWS));
        args.putInt(COLUMNS, bundle.getInt(COLUMNS));
        args.putFloat(INTERVAL_START, bundle.getFloat(INTERVAL_START));
        args.putFloat(INTERVAL_FINISH, bundle.getFloat(INTERVAL_FINISH));

        args.putString(SPEED, bundle.getFloat(INTERVAL_START) +"-"+
                bundle.getFloat(INTERVAL_FINISH));
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        if(savedInstanceState!=null)
            bundle=savedInstanceState;
        time= bundle!= null ? bundle.getInt(TIME) : 0;
        assert bundle != null;
        rows=bundle.getInt(ROWS);
        columns=bundle.getInt(COLUMNS);
        intervalStart=bundle.getFloat(INTERVAL_START);
        intervalFinish=bundle.getFloat(INTERVAL_FINISH);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_game,
                container,
                false
        );
    }
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textTime=view.findViewById(R.id.textTimer);
        textScore=view.findViewById(R.id.textScore);
        createGame(view);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    private void createGame(View view){
        game=new Game();
        Random random = new Random();
        myTimer = new Timer();
        updateHandler = new Handler();
        myTimer.schedule(new MyTimerTask(myTimer, random,
                (int)(intervalStart*1000), (int)(intervalFinish*1000),
                view, this, updateHandler), 0);

        countDownTimer = new CountDownTimer(time* 1000L, 1000) {
            public void onTick(long millisUntilFinished) {
                textTime.setText(String.valueOf(millisUntilFinished / 1000 +1));
            }
            public void onFinish() {
                endGame();
            }
        }.start();

    }
    private void endGame(){
        myTimer.cancel();
        assert getArguments() != null;
        getArguments().putInt(SCORE,game.getScore());
        GameDialogFragment dialog = GameDialogFragment.newInstance(getArguments());
        dialog.show(this.requireActivity().getSupportFragmentManager(), "custom");
        getAppContract().toOptionsScreen(this);
    }

    private void createGrid(View view){

        TableLayout tl= view.findViewById(R.id.tableGame);
        Random random = new Random();
        int column= random.nextInt(columns);
        int row= random.nextInt(rows);
        for(int i=0;i<rows;i++){
            TableRow tableRow = new TableRow(this.getActivity());

            for(int j=0;j<columns;j++){
                ImageButton simpleImageButton = new ImageButton(this.getActivity());
                if(i==row&&j==column){
                    simpleImageButton.setImageResource(R.drawable.smile);
                    simpleImageButton.setBackgroundResource(R.drawable.color_buttom_selector);
                    simpleImageButton.setOnClickListener(v -> {
                        game.scorePoints();
                        textScore.setText(String.valueOf(game.getScore()));
                    });
                }
                else{
                    simpleImageButton.setImageResource(R.drawable.resource_void);
                    simpleImageButton.setBackgroundResource(R.drawable.gray_button_selector);
                    simpleImageButton.setOnClickListener(v -> {
                        game.takePoints();
                        textScore.setText(String.valueOf(game.getScore()));
                    });
                }
                simpleImageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                tableRow.addView(simpleImageButton,j);
            }
            tl.addView(tableRow,i);
        }

    }
    private void deleteGrid(View view){
        TableLayout tl= view.findViewById(R.id.tableGame);
        tl.removeAllViews();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(countDownTimer!=null)
            countDownTimer.cancel();
        if(myTimer!=null)
            myTimer.cancel();
    }

    private static class MyTimerTask extends TimerTask {
        private final Timer timer;
        private final Random random;
        private final int startInterval;
        private final int endInterval;
        private final View view;
        private final GameFragment fragment;
        private final Handler updateHandler;

        public MyTimerTask(Timer timer, Random random, int startInterval, int endInterval, View view, GameFragment fragment, Handler updateHandler) {
            this.timer = timer;
            this.random = random;
            this.startInterval = startInterval;
            this.endInterval = endInterval;
            this.view = view;
            this.fragment = fragment;
            this.updateHandler = updateHandler;
        }

        @Override
        public void run() {
            updateHandler.post(() -> {
                fragment.deleteGrid(view);
                fragment.createGrid(view);

            });

            timer.schedule(new MyTimerTask(timer, random, startInterval, endInterval, view, fragment, updateHandler),
                    random.nextInt(endInterval - startInterval) + startInterval);
        }
    }
}
