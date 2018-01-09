package vahren.fr.bladesclockapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class ClockView extends View {


    private static final int padding = 5;

    private int n;
    private float diameter;
    private float value;

    private Paint circlePaint;
    private Paint sectorPaint;

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.ClockView,0,0);
        try {
            this.n = a.getInt(R.styleable.ClockView_nbSector, 4);
        } finally {
            a.recycle();
        }

        this.value = 0;

        // INIT
        this.circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.circlePaint.setStrokeWidth(5);
        this.circlePaint.setColor(Color.BLACK);
        this.circlePaint.setStyle(Paint.Style.STROKE);

        this.sectorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.sectorPaint.setStyle(Paint.Style.FILL);

    }

    public int getNbSector() {
        return this.n;
    }

    public void setNbSector(int nbSector) {
        this.n = nbSector;
        invalidate();
        requestLayout();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Account for padding
        float xpad = (float)(getPaddingLeft() + getPaddingRight());
        float ypad = (float)(getPaddingTop() + getPaddingBottom());

        float ww = (float)w - xpad;
        float hh = (float)h - ypad;

        // Figure out how big we can make the pie.
        this.diameter = Math.min(ww, hh);
        Log.d("BCA","Diameter is "+diameter+" for dimensions "+w+"/"+h);
    }

    public static int getColorFromPercent(float percent) {
        float hue = percent*120;
        return Color.HSVToColor(new float[]{hue, 0.8f, 0.8f});
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // set sector color
        sectorPaint.setColor(getColorFromPercent(1-value/n));
        int angleStep = 360 / n;
        canvas.drawArc(padding,padding,diameter-2*padding,diameter-2*padding,0, angleStep *value,true,sectorPaint);

        // draw as many arc as the sector number
        for(int a = 0; a < 360; a = a + angleStep) {
            canvas.drawArc(padding,padding,diameter-2*padding,diameter-2*padding, a,a+ angleStep,true,circlePaint);
        }
    }

    public void increaseValue() {
        if(value == n){
            // max was attained reset to 0
            value = 0;
        }else{
            value++;
        }
        invalidate();
    }

    public void decreaseValue() {
        value = Math.max(0,value-1);
        invalidate();
    }

    public float getValue() {
        return value;
    }
}
