package vahren.fr.bladesclockapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "clock")
public class ClockEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public boolean score;
    public boolean pc;
    public boolean general;
    public boolean hidden;
    public int nbSector;
    public String name;
    public float value;

    @Override
    public String toString() {
        return "ClockEntity{" +
                "id=" + id +
                ", score=" + score +
                ", pc=" + pc +
                ", general=" + general +
                ", hidden=" + hidden +
                ", nbSector=" + nbSector +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
