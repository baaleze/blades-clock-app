package vahren.fr.bladesclockapp;

import android.app.DialogFragment;
import android.arch.persistence.room.Room;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;

import java.util.List;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class MainActivity extends AppCompatActivity implements CreateClockMenu.CreateClockMenuListener {


    public static final int CLOCK_ICON_SIZE = 50;
    private GridView grid;
    private ClockAdapter clocks;
    private boolean showingMenu = true;
    private Animation show_fab_1;
    private Animation hide_fab_1;
    private Animation show_fab_2;
    private Animation hide_fab_2;
    private Animation show_fab_3;
    private Animation hide_fab_3;


    private final Bitmap fab4 = bitmapClock(4, CLOCK_ICON_SIZE, Color.WHITE);
    private final Bitmap fab6 = bitmapClock(6, CLOCK_ICON_SIZE, Color.WHITE);
    private final Bitmap fab8 = bitmapClock(8, CLOCK_ICON_SIZE, Color.WHITE);
    private ClockDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ANIMATIONS
        show_fab_1 = AnimationUtils.loadAnimation(this, R.anim.fab1_show);
        hide_fab_1 = AnimationUtils.loadAnimation(this, R.anim.fab1_hide);
        show_fab_2 = AnimationUtils.loadAnimation(this, R.anim.fab2_show);
        hide_fab_2 = AnimationUtils.loadAnimation(this, R.anim.fab2_hide);
        show_fab_3 = AnimationUtils.loadAnimation(this, R.anim.fab3_show);
        hide_fab_3 = AnimationUtils.loadAnimation(this, R.anim.fab3_hide);

        // FABS
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFabMenu();
            }
        });
        FloatingActionButton fab1 = findViewById(R.id.fab_4);
        fab1.setImageBitmap(fab4);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddClockMenu(4);
            }
        });
        FloatingActionButton fab2 = findViewById(R.id.fab_6);
        fab2.setImageBitmap(fab6);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddClockMenu(6);
            }
        });
        FloatingActionButton fab3 = findViewById(R.id.fab_8);
        fab3.setImageBitmap(fab8);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddClockMenu(8);
            }
        });

        // TAGS
        CheckBox score = findViewById(R.id.scoreToggle);
        score.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                clocks.setScore(b);
            }
        });
        CheckBox pc = findViewById(R.id.pcToggle);
        pc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                clocks.setPc(b);
            }
        });
        CheckBox general = findViewById(R.id.generalToggle);
        general.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                clocks.setGeneral(b);
            }
        });
        CheckBox hidden = findViewById(R.id.hiddenToggle);
        hidden.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                clocks.setHidden(b);
            }
        });

        // GRID
        this.grid = findViewById(R.id.grid);
        this.grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((Clock)view).increaseValue();
                // save
                save((Clock)view);
                printClocks();
            }
        });
        this.grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                boolean delete = ((Clock)view).decreaseValue();
                if(delete) {
                    delete((Clock)view);
                }else{
                    // save
                    save((Clock)view);
                }
                printClocks();
                return true;
            }
        });

        this.clocks = new ClockAdapter(this);
        grid.setAdapter(this.clocks);

        // DATA
        db = Room.databaseBuilder(this, ClockDB.class, "clock-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        // LOAD
        List<ClockEntity> clocks = db.clockDao().getAll();
        for(ClockEntity ce: clocks){
            load(ce);
        }
    }

    private void printClocks() {
        for(ClockEntity ce :db.clockDao().getAll()){
            Log.d("BCA",ce.toString());
        }
    }

    private void load(ClockEntity ce) {
        this.clocks.loadClock(new Clock(this, ce));
    }

    private void delete(Clock view) {
        db.clockDao().delete(view.toEntity());
        this.clocks.delete(view);
    }

    private void save(Clock view) {
        db.clockDao().update(view.toEntity());
    }


    private long create(Clock newClock) {
        return db.clockDao().insert(newClock.toEntity());
    }


    private void showAddClockMenu(int nbSector) {
        // show popup with one field for name
        // and tags checkboxes
        DialogFragment menu = new CreateClockMenu();
        Bundle args = new Bundle();
        args.putInt("nbSector",nbSector);
        menu.setArguments(args);
        menu.show(getFragmentManager(), "createClock");
    }

    private void showFabMenu() {
        FloatingActionButton fab1 = findViewById(R.id.fab_4);
        FloatingActionButton fab2 = findViewById(R.id.fab_6);
        FloatingActionButton fab3 = findViewById(R.id.fab_8);
        if(this.showingMenu){
            // First button
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
            layoutParams.rightMargin += (int) (fab1.getWidth() * 1.7);
            layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.25);
            fab1.setLayoutParams(layoutParams);
            fab1.startAnimation(show_fab_1);
            fab1.setClickable(true);
            fab1.setVisibility(View.VISIBLE);

            layoutParams = (FrameLayout.LayoutParams) fab2.getLayoutParams();
            layoutParams.rightMargin += (int) (fab2.getWidth() * 1.5);
            layoutParams.bottomMargin += (int) (fab2.getHeight() * 1.5);
            fab2.setLayoutParams(layoutParams);
            fab2.startAnimation(show_fab_2);
            fab2.setClickable(true);
            fab2.setVisibility(View.VISIBLE);

            layoutParams = (FrameLayout.LayoutParams) fab3.getLayoutParams();
            layoutParams.rightMargin += (int) (fab3.getWidth() * 0.25);
            layoutParams.bottomMargin += (int) (fab3.getHeight() * 1.7);
            fab3.setLayoutParams(layoutParams);
            fab3.startAnimation(show_fab_3);
            fab3.setClickable(true);
            fab3.setVisibility(View.VISIBLE);
        }else{
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
            layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
            layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
            fab1.setLayoutParams(layoutParams);
            fab1.startAnimation(hide_fab_1);
            fab1.setClickable(false);
            fab1.setVisibility(View.INVISIBLE);

            layoutParams = (FrameLayout.LayoutParams) fab2.getLayoutParams();
            layoutParams.rightMargin -= (int) (fab2.getWidth() * 1.5);
            layoutParams.bottomMargin -= (int) (fab2.getHeight() * 1.5);
            fab2.setLayoutParams(layoutParams);
            fab2.startAnimation(hide_fab_2);
            fab2.setClickable(false);
            fab2.setVisibility(View.INVISIBLE);

            layoutParams = (FrameLayout.LayoutParams) fab3.getLayoutParams();
            layoutParams.rightMargin -= (int) (fab3.getWidth() * 0.25);
            layoutParams.bottomMargin -= (int) (fab3.getHeight() * 1.7);
            fab3.setLayoutParams(layoutParams);
            fab3.startAnimation(hide_fab_3);
            fab3.setClickable(false);
            fab3.setVisibility(View.INVISIBLE);
        }

        this.showingMenu = !this.showingMenu;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(CreateClockMenu dialog) {
        // create a new clock
        Clock newClock = clocks.addNewClock(dialog.nbSector,
                ((EditText)dialog.getDialog().findViewById(R.id.nameInput)).getText().toString(),
                ((CheckBox)dialog.getDialog().findViewById(R.id.score)).isChecked(),
                ((CheckBox)dialog.getDialog().findViewById(R.id.PC)).isChecked(),
                ((CheckBox)dialog.getDialog().findViewById(R.id.general)).isChecked(),
                ((CheckBox)dialog.getDialog().findViewById(R.id.hidden)).isChecked(), 0);
        // insert it in DB
        newClock.id = create(newClock);
        // hide FABs
        showFabMenu();
    }

    @Override
    public void onDialogNegativeClick(CreateClockMenu dialog) {
        showFabMenu();
    }


    public static Bitmap bitmapClock(int n, int textSize, int textColor) {
        Paint circlePaint = new Paint(ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(1);
        circlePaint.setColor(textColor);
        int padding = 2;
        int width = 2*padding + textSize; // round
        int height = 2*padding + textSize;
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        int angleStep = 360 / n;
        // draw as many arc as the sector number
        for(int a = -90; a < 270; a = a + angleStep) {
            canvas.drawArc(padding,padding,textSize-2*padding,textSize-2*padding, a, angleStep,true,circlePaint);
        }
        return image;
    }
}
