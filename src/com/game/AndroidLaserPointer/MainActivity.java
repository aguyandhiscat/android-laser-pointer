package com.game.AndroidLaserPointer;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private static final String TAG = "AndroidLaserPointerGame";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        reset();
    }

    public void reset() {
        setContentView(R.layout.menu);

        ArrayList<LeaderboardItem> list = new ArrayList<LeaderboardItem>();
        list.add(new LeaderboardItem("Alex", "80"));
        list.add(new LeaderboardItem("Danielle", "100"));
        list.add(new LeaderboardItem("Steve", "120"));

        LeaderboardListAdapter adapter = new LeaderboardListAdapter(this, list);

        ListView leaderBoardView = (ListView) findViewById(R.id.leader_board);
        leaderBoardView.setAdapter(adapter);

        Button btn = (Button) findViewById(R.id.start_button);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                beginGame();
            }
        });
    }

    public void beginGame() {
        setContentView(R.layout.game);

        GameView gameView = (GameView) findViewById(R.id.game);
        gameView.setDependentViews((TextView) findViewById(R.id.points_counter),
                (TextView) findViewById(R.id.time_counter));

        gameView.initNewGame();
    }

    public void endGame(int finalScore) {
        setScoreInLeaderboard(finalScore);
        reset();
    }

    public void setScoreInLeaderboard(int score) {
        Toast.makeText(this, "Score: "+score, 2000).show();
    }
}
