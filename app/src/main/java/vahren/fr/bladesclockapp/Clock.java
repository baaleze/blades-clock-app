package vahren.fr.bladesclockapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Clock extends LinearLayout {

    ClockView c;
    View v;
    public Clock(Context context, int nbSector, String name){
        super(context);
        LayoutInflater.from(context).inflate(R.layout.clock_layout,this);
        c = findViewById(R.id.clockView);
        c.setNbSector(nbSector);
        ((TextView)findViewById(R.id.name)).setText(name);
    }

    public void click() {
        c.increaseValue();
    }

    public void longClick() {
        c.decreaseValue();
    }

}
