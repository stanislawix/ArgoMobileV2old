package pl.argo.argomobile.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RoverDao {

    @Query("SELECT * FROM Rover")
    List<Rover> getAll();

    @Query("SELECT * FROM Rover WHERE id=(:id)")
    Rover getById(int id);

    @Insert
    void insert(Rover rover);

    @Query("DELETE FROM Rover")
    void deleteAll();
}
