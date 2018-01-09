package vahren.fr.bladesclockapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by fdroumaguet on 09/01/18.
 */

public class Clock extends LinearLayout {

    ClockView c;
    View v;
    public Clock(Context context, int nbSector, int color, String name){
        super(context);
        LayoutInflater.from(context).inflate(R.layout.clock_layout,this);
        c = findViewById(R.id.clockView);
        c.setNbSector(nbSector);
        c.setColor(color);
        ((TextView)findViewById(R.id.name)).setText(name);
    }

    public void click() {
        c.increaseValue();
        Log.d("BCA","VALUE = "+c.getValue());
    }

    public void longClick() {
        c.decreaseValue();
        Log.d("BCA","VALUE = "+c.getValue());
    }
}
