package phk.phkgame2048;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/1/15.
 */
public class GameView extends GridLayout {
    public GameView(Context context) {
        super(context);
        initGmaeView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGmaeView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGmaeView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (Math.min(w,h)-10)/4;
        addCards(cardWidth, cardWidth);
        startGame();
    }

    private void setColor()
    {
        for(int i = 0;i<4;i++)
        {
            for(int j =  0;j < 4;j++)
            {
                if(cardMap[i][j].getNumber() == 0)
                {
                    cardMap[i][j].setBackgroundColor(0xffbebebe);
                }
                if(cardMap[i][j].getNumber() == 2)
                {
                    cardMap[i][j].setBackgroundColor(0xffaaaaff);
                }
                if(cardMap[i][j].getNumber() == 4)
                {
                    cardMap[i][j].setBackgroundColor(0xff7d7dff);
                }
                if(cardMap[i][j].getNumber() == 8)
                {
                    cardMap[i][j].setBackgroundColor(0xff2828ff);
                }
                if(cardMap[i][j].getNumber() == 16)
                {
                    cardMap[i][j].setBackgroundColor(0xffffffaa);
                }
                if(cardMap[i][j].getNumber() == 32)
                {
                    cardMap[i][j].setBackgroundColor(0xffffff6f);
                }
                if(cardMap[i][j].getNumber() == 64)
                {
                    cardMap[i][j].setBackgroundColor(0xffffff37);
                }
                if(cardMap[i][j].getNumber() == 128)
                {
                    cardMap[i][j].setBackgroundColor(0xffffd3d6);
                }
                if(cardMap[i][j].getNumber() == 256)
                {
                    cardMap[i][j].setBackgroundColor(0xffeac100);
                }
                if(cardMap[i][j].getNumber() == 512)
                {
                    cardMap[i][j].setBackgroundColor(0xff5a5aad);
                }
                if(cardMap[i][j].getNumber() == 1024)
                {
                    cardMap[i][j].setBackgroundColor(0xff484891);
                }
                if(cardMap[i][j].getNumber() == 2048)
                {
                    cardMap[i][j].setBackgroundColor(0xfff9f900);
                }
            }
        }
    }
    private void addCards(int cardWidth, int cardHeight) {
        Card card;
        for(int i = 0;i < 4;i++)
        {
            for(int j = 0; j < 4;j++)
            {
                card = new Card(getContext());
                card.setNumber(0);
                addView(card ,cardWidth,cardHeight);

                cardMap[i][j] = card;
            }
        }
    }

    private List<Point> emptyPoints = new ArrayList<Point>();

    public void startGame()
    {
        for(int i = 0;i < 4;i++)
        {
            for(int j = 0;j < 4;j++)
            {
                cardMap[i][j].setNumber(0);
            }
        }
        addRandomNum();
        addRandomNum();
        setColor();
    }
    private void addRandomNum()
    {
        emptyPoints.clear();
        for(int i = 0;i < 4;i++)
        {
            for(int j = 0;j < 4;j++)
            {
                if(cardMap[i][j].getNumber()<=0) {
                    emptyPoints.add(new Point(i, j));
                }
            }
        }

        Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
        cardMap[p.x][p.y].setNumber(Math.random() > 0.1 ? 2 : 4);
    }

    private void initGmaeView()
    {
        setColumnCount(4);
        setBackgroundColor(0xffbbada0);

        setOnTouchListener(new OnTouchListener() {

            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = motionEvent.getX();
                        startY = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = motionEvent.getX() - startX;
                        offsetY = motionEvent.getY() - startY;

                        if (Math.abs(offsetX) >= Math.abs(offsetY))//X方向
                        {
                            if (offsetX < -5)
                                swipeLeft();
                            if (offsetX >= 5)
                                swipeRight();
                        } else//Y方向
                        {
                            if (offsetY <= -5)
                                swipeUp();
                            if (offsetY >= 5)
                                swipeDown();
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void swipeDown() {
        for(int j = 0 ;j < 4;j++)
        {
            for(int i = 3; i >= 0;i--)
            {
                for(int k = i-1;k>=0;k--)
                {
                    if(cardMap[k][j].getNumber()>0)
                    {
                        if(cardMap[i][j].getNumber()<=0)
                        {
                            cardMap[i][j].setNumber(cardMap[k][j].getNumber());
                            cardMap[k][j].setNumber(0);
                            i++;
                        }
                        else if (cardMap[k][j].equals(cardMap[i][j]))
                        {
                            cardMap[i][j].setNumber(cardMap[i][j].getNumber() * 2);

                            MainActivity.getMainActivity().addScore( cardMap[i][j].getNumber());
                            cardMap[k][j].setNumber(0);
                        }
                        break;
                    }
                }
            }
        }
        addRandomNum();
        setColor();
        checkComplete();
    }

    private void swipeUp() {
        for(int j = 0 ;j < 4;j++)
        {
            for(int i = 0; i < 4;i++)
            {
                for(int k = i+1;k<4;k++)
                {
                    if(cardMap[k][j].getNumber()>0)
                    {
                        if(cardMap[i][j].getNumber()<=0)
                        {
                            cardMap[i][j].setNumber(cardMap[k][j].getNumber());
                            cardMap[k][j].setNumber(0);
                            i--;
                        }
                        else if(cardMap[k][j].equals(cardMap[i][j]))
                        {
                            cardMap[i][j].setNumber(cardMap[i][j].getNumber()*2);
                            MainActivity.getMainActivity().addScore(cardMap[i][j].getNumber());
                            cardMap[k][j].setNumber(0);
                        }
                        break;
                    }
                }
            }
        }
        addRandomNum();
        setColor();
        checkComplete();
    }

    private void swipeRight() {
        for(int i = 0 ;i <4 ;i++)
        {
            for(int j = 3; j >= 0;j--)
            {
                for(int k = j-1;k>=0;k--)
                {
                    if(cardMap[i][k].getNumber()>0)
                    {
                        if(cardMap[i][j].getNumber()<=0)
                        {
                            cardMap[i][j].setNumber(cardMap[i][k].getNumber());
                            cardMap[i][k].setNumber(0);
                            j++;
                        }
                        else if(cardMap[i][k].equals(cardMap[i][j]))
                        {
                            cardMap[i][j].setNumber(cardMap[i][j].getNumber()*2);
                            MainActivity.getMainActivity().addScore(cardMap[i][j].getNumber());
                            cardMap[i][k].setNumber(0);
                        }
                        break;
                    }
                }
            }
        }
        addRandomNum();
        setColor();
        checkComplete();
    }

    private void swipeLeft() {
        for(int i = 0 ;i < 4;i++)
        {
            for(int j = 0; j < 4;j++)
            {
                for(int k = j+1;k<4;k++)
                {
                    if(cardMap[i][k].getNumber()>0)
                    {
                        if(cardMap[i][j].getNumber()<=0)
                        {
                            cardMap[i][j].setNumber(cardMap[i][k].getNumber());
                            cardMap[i][k].setNumber(0);
                            j--;
                        }
                        else if(cardMap[i][k].equals(cardMap[i][j]))
                        {
                            cardMap[i][j].setNumber(cardMap[i][j].getNumber()*2);
                            MainActivity.getMainActivity().addScore(cardMap[i][j].getNumber());
                            cardMap[i][k].setNumber(0);
                        }
                        break;
                    }
                }
            }
        }
        addRandomNum();
        setColor();
        checkComplete();
    }

    private void checkComplete()
    {
        boolean complete = true;

        all:
        for(int i = 0;i < 4;i++)
        {
            for(int j = 0;j < 4;j++)
            {
                if(cardMap[i][j].getNumber()==0||
                        i>0&&cardMap[i][j].equals(cardMap[i-1][j])||
                        i<3&&cardMap[i][j].equals(cardMap[i+1][j])||
                        j>0&&cardMap[i][j].equals(cardMap[i][j-1])||
                        j<3&&cardMap[i][j].equals(cardMap[i][j+1]))
                {
                    complete = false;
                    break all;
                }
            }
        }

        if(complete)
        {
            new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("Game Over!").setPositiveButton("Again",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startGame();
                            MainActivity.getMainActivity().score.setText("");
                            MainActivity.getMainActivity().count = 0;

                        }
                    }).show();
        }
    }
    private Card[][] cardMap = new Card[4][4];
}
