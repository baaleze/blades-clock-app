package vahren.fr.bladesclockapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

class ClockAdapter extends BaseAdapter {

    private final Context context;

    private boolean score = true;
    private boolean pc = true;
    private boolean general = true;
    private boolean hidden = true;

    private List<Clock> clocks = new ArrayList<>(0);
    private List<Clock> filtered = new ArrayList<>(0);

    public ClockAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return filtered.size();
    }

    @Override
    public Object getItem(int i) {
        return filtered.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = filtered.get(i);
        v.setLayoutParams(new GridView.LayoutParams(400,400));
        return v;
    }

    public void addNewClock(int nbSector, String name, boolean score, boolean pc, boolean general, boolean hidden) {
        clocks.add(new Clock(context,nbSector, name, score, pc, general, hidden));
        updateFilteredList();
    }

    private void updateFilteredList(){
        filtered.clear();
        for(Clock c:clocks){
            if(score && c.score){
                filtered.add(c);
                continue;
            }
            if(pc && c.pc){
                filtered.add(c);
                continue;
            }
            if(general && c.general){
                filtered.add(c);
                continue;
            }
            if(hidden && c.hidden){
                filtered.add(c);
                continue;
            }
        }
        notifyDataSetInvalidated();
    }

    public void toggleScore() {
        score = !score;
        updateFilteredList();
    }

    public void togglePc() {
        pc = !pc;
        updateFilteredList();
    }

    public void toggleGeneral() {
        general = !general;
        updateFilteredList();
    }

    public void toggleHidden() {
        hidden = !hidden;
        updateFilteredList();
    }

    public void setScore(boolean score) {
        this.score = score;
        updateFilteredList();
    }

    public void setPc(boolean pc) {
        this.pc = pc;
        updateFilteredList();
    }

    public void setGeneral(boolean general) {
        this.general = general;
        updateFilteredList();
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
        updateFilteredList();
    }
}
