package vahren.fr.bladesclockapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Clock extends LinearLayout {

    public final boolean score;
    public final boolean pc;
    public final boolean general;
    public final boolean hidden;
    private ClockView c;

    public Clock(Context context, int nbSector, String name, boolean score, boolean pc, boolean general, boolean hidden){
        super(context);
        LayoutInflater.from(context).inflate(R.layout.clock_layout,this);
        c = findViewById(R.id.clockView);
        c.setNbSector(nbSector);
        ((TextView)findViewById(R.id.name)).setText(name);
        this.score = score;
        this.pc = pc;
        this.general = general;
        this.hidden = hidden;
    }

    public void click() {
        c.increaseValue();
    }

    public void longClick() {
        c.decreaseValue();
    }

}
