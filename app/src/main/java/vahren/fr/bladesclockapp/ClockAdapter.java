package vahren.fr.bladesclockapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fdroumaguet on 09/01/18.
 */

class ClockAdapter extends BaseAdapter {

    private final Context context;
    private List<Clock> clocks = new ArrayList<>(0);

    public ClockAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return clocks.size();
    }

    @Override
    public Object getItem(int i) {
        return clocks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = clocks.get(i);
        v.setLayoutParams(new GridView.LayoutParams(400,400));
        return v;
    }

    public void addNewClock(int nbSector, int color, String name) {
        clocks.add(new Clock(context,nbSector,color, name));
    }
}
