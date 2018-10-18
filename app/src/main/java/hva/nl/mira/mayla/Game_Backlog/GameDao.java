package hva.nl.mira.mayla.Game_Backlog;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

//Data access object
@Dao
public interface GameDao {

    @Query("SELECT * FROM games")
    public List<Game> getAllGames();

    @Insert
    public void insertGame(Game game);

    @Delete
    public void deleteGame(Game game);

    @Update
    public void updateGame(Game game);

}