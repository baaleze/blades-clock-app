package vahren.fr.bladesclockapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ClockView extends View {


    private static final int padding = 10;
    private static final float TEXT_PAD = 100;

    private float diameter;

    private Paint circlePaint;
    private Paint sectorPaint;
    private Paint textPaint;
    private Clock clock;
    private int w;
    private int h;

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // INIT
        this.circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.circlePaint.setStyle(Paint.Style.STROKE);

        this.sectorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.sectorPaint.setStyle(Paint.Style.FILL);

        this.textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.textPaint.setColor(Color.BLACK);
        this.textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.textPaint.setTextSize(TEXT_PAD/2);
        this.textPaint.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float ww = (float)w ;
        float hh = (float)h - TEXT_PAD;

        // Figure out how big we can make the pie.
        this.diameter = Math.min(ww, hh);
        this.w = (int) ww;
        this.h = (int) hh;
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
        sectorPaint.setColor(getColorFromPercent(1-clock.value/clock.nbSector));
        int angleStep = 360 / clock.nbSector;
        canvas.drawArc(w/2-diameter/2+padding,
                h/2-diameter/2+padding,
                w/2+diameter/2-padding,
                h/2+diameter/2-padding,
                -90, angleStep *clock.value,true,sectorPaint);

        circlePaint.setStrokeWidth(5);
        circlePaint.setColor(Color.BLACK);
        // draw as many arc as the sector number
        for(int a = -90; a < 270; a = a + angleStep) {
            canvas.drawArc(w/2-diameter/2+padding,
                    h/2-diameter/2+padding,
                    w/2+diameter/2-padding,
                    h/2+diameter/2-padding,
                    a, angleStep,true,circlePaint);
        }

        circlePaint.setStrokeWidth(10);
        // TAGS
        if(clock.score){
            circlePaint.setColor(getResources().getColor(android.R.color.holo_red_dark));
            canvas.drawArc(w/2-diameter/2+padding/2,
                    h/2-diameter/2+padding/2,
                    w/2+diameter/2-padding/2,
                    h/2+diameter/2-padding/2, -90, 90,false,circlePaint);
        }
        if(clock.pc){
            circlePaint.setColor(getResources().getColor(android.R.color.holo_blue_dark));
            canvas.drawArc(w/2-diameter/2+padding/2,
                    h/2-diameter/2+padding/2,
                    w/2+diameter/2-padding/2,
                    h/2+diameter/2-padding/2, 0, 90,false,circlePaint);
        }
        if(clock.general){
            circlePaint.setColor(getResources().getColor(android.R.color.holo_green_dark));
            canvas.drawArc(w/2-diameter/2+padding/2,
                    h/2-diameter/2+padding/2,
                    w/2+diameter/2-padding/2,
                    h/2+diameter/2-padding/2, 90, 90,false,circlePaint);
        }
        if(clock.hidden){
            circlePaint.setColor(getResources().getColor(android.R.color.darker_gray));
            canvas.drawArc(w/2-diameter/2+padding/2,
                    h/2-diameter/2+padding/2,
                    w/2+diameter/2-padding/2,
                    h/2+diameter/2-padding/2, 180, 90,false,circlePaint);
        }


        // Text
        canvas.drawText(clock.name,w/2,diameter+TEXT_PAD/2,textPaint);
    }



    public void setClock(Clock clock) {
        this.clock = clock;
    }
}
