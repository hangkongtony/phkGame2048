package phk.phkgame2048;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button start;
    private GameView gameView;
    public TextView score,highscore;
    public int count = 0,theHighest=0;
    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    private static  MainActivity mainActivity = null;
    public MainActivity() {
        mainActivity = this;
    }

    void addScore(int i)
    {
        count += i;
        if(count >=theHighest)
        {
            theHighest = count;
            highscore.setText("  "+theHighest+"  ");
        }
        score.setText("  "+count+"  ");

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameView = (GameView) findViewById(R.id.gameView);

        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameView.startGame();
                score.setText("");
                count = 0;
            }
        });

        score = (TextView) findViewById(R.id.tvscore);
        highscore = (TextView) findViewById(R.id.highscore);
    }


}
