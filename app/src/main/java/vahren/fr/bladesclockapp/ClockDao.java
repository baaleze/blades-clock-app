package vahren.fr.bladesclockapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ClockDao {

    @Query("SELECT * FROM clock WHERE id = :id")
    ClockEntity get(int id);

    @Query("SELECT * FROM clock")
    List<ClockEntity> getAll();

    @Update
    void update(ClockEntity clock);

    @Insert
    long insert(ClockEntity clock);

    @Delete
    void delete(ClockEntity clock);

}
