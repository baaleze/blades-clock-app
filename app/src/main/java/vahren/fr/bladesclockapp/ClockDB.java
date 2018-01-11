package vahren.fr.bladesclockapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {ClockEntity.class}, version = 2)
public abstract class ClockDB extends RoomDatabase {
    public abstract ClockDao clockDao();
}
