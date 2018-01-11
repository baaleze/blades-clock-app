package vahren.fr.bladesclockapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class Clock extends LinearLayout {

    public final boolean score;
    public final boolean pc;
    public final boolean general;
    public final boolean hidden;
    public final int nbSector;
    public final String name;
    private ClockView c;

    public Clock(Context context, int nbSector, String name, boolean score, boolean pc, boolean general, boolean hidden){
        super(context);
        LayoutInflater.from(context).inflate(R.layout.clock_layout,this);
        c = findViewById(R.id.clockView);
        c.setClock(this);
        this.score = score;
        this.pc = pc;
        this.general = general;
        this.hidden = hidden;
        this.nbSector = nbSector;
        this.name = name;
    }

    public void click() {
        c.increaseValue();
    }

    public void longClick() {
        c.decreaseValue();
    }

}
