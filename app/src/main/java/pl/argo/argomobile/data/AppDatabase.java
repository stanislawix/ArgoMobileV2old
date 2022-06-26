package pl.argo.argomobile.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Rover.class, Joint.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RoverDao roverDao();

    public abstract JointDao jointDao();
}
