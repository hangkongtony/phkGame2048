package phk.phkgame2048;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by lenovo on 2016/1/15.
 */
public class Card extends FrameLayout {
    public Card(Context context) {
        super(context);

        text = new TextView(context);
        text.setTextSize(32);
        text.setBackgroundColor(0x33ffffff);
        text.setGravity(Gravity.CENTER);

        LayoutParams lp = new LayoutParams(-1,-1);
        lp.setMargins(10,10,0,0);
        addView(text, lp);

        setNumber(0);
    }

    public Card(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Card(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int number = 0;
    private TextView text;

    public void setNumber(int number) {
        this.number = number;

        if(number == 0)
        {
            text.setText("");
        }else{
            text.setText(number+"");
        }

    }

    public int getNumber() {
        return number;
    }

    public boolean equals(Card c)
    {
        return number == c.getNumber();
    }

}
