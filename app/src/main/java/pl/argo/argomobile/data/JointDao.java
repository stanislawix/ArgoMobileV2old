package pl.argo.argomobile.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import pl.argo.argomobile.data.Joint;

import java.util.List;

@Dao
public interface JointDao {

    @Query("SELECT * FROM Joint")
    List<Joint> getAll();

    @Query("SELECT * FROM Joint WHERE rover_id=(:roverId)")
    List<Joint> getAllByRoverId(int roverId);

    @Insert
    void insert(Joint joint);

    @Query("DELETE FROM Joint")
    void deleteAll();
}
