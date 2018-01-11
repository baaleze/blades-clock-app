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
    public long id;
    public float value;
    private ClockView c;

    public Clock(Context context){
        this(context,4,"default name",true,true,true,true,0);
    }

    public Clock(Context context, int nbSector, String name, boolean score, boolean pc, boolean general, boolean hidden, long id){
        super(context);
        LayoutInflater.from(context).inflate(R.layout.clock_layout,this);
        c = findViewById(R.id.clockView);
        c.setClock(this);
        this.score = score;
        this.pc = pc;
        if(!pc && !score && !general && !hidden){
            // AT LEAST ONE!
            this.general = true;
        }else{
            this.general = general;
        }
        this.hidden = hidden;
        this.nbSector = nbSector;
        this.name = name;
        this.id = id;


    }

    public Clock(Context context, ClockEntity ce) {
        this(context,ce.nbSector,ce.name,ce.score,ce.pc,ce.general,ce.hidden,ce.id);
        this.value = ce.value;
    }

    public void increaseValue() {
        if(value == nbSector){
            // max was attained reset to 0
            value = 0;
        }else{
            value++;
        }
        c.invalidate();
    }

    public boolean decreaseValue() {
        if(value == 0){
            // delete clock
            return true;
        } else {
            value = value-1;
            c.invalidate();
            return false;
        }

    }

    public ClockEntity toEntity() {
        ClockEntity e = new ClockEntity();
        e.id = id;
        e.general = general;
        e.hidden = hidden;
        e.name = name;
        e.pc = pc;
        e.score = score;
        e.value = value;
        e.nbSector = nbSector;
        return e;
    }
}
