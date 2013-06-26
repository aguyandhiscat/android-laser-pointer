package com.game.AndroidLaserPointer;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final String TAG = "AndroidLaserPointerGame";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.menu);

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
        gameView.setDependentViews((TextView) findViewById(R.id.points_counter));

        gameView.initNewGame();
    }
}
