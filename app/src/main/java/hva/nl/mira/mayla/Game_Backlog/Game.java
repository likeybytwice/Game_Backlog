package hva.nl.mira.mayla.Game_Backlog;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;


@Entity(tableName = "games")
public class Game implements Parcelable {

    //to allow a custom object to be parsed to another component
    //the parcelable interface needs to b implement

    //constructor
    public Game(String gameTitle, String gamePlatform, String gameNotes, String gameStatus, String gameDate) {
        this.gameTitle = gameTitle;
        this.gamePlatform = gamePlatform;
        this.gameNotes = gameNotes;
        this.gameStatus = gameStatus;
        this.gameDate = gameDate;
    }

    //@ SQL stuff
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "gametitle")
    private String gameTitle;

    @ColumnInfo(name = "gameplatform")
    private String gamePlatform;

    @ColumnInfo(name = "gamenotes")
    private String gameNotes;

    @ColumnInfo(name = "gamestatus")
    private String gameStatus;

    //Just some getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getGamePlatform() {
        return gamePlatform;
    }

    public void setGamePlatform(String gamePlatform) {
        this.gamePlatform = gamePlatform;
    }

    public String getGameNotes() {
        return gameNotes;
    }

    public void setGameNotes(String gameNotes) {
        this.gameNotes = gameNotes;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public static Creator<Game> getCREATOR() {
        return CREATOR;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    //Parcelling part
    protected Game(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        gameTitle = in.readString();
        gamePlatform = in.readString();
        gameNotes = in.readString();
        gameStatus = in.readString();
        gameDate = in.readString();

    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(gameTitle);
        dest.writeString(gamePlatform);
        dest.writeString(gameNotes);
        dest.writeString(gameStatus);
        dest.writeString(gameDate);
    }

    @ColumnInfo(name = "gamedate")
    private String gameDate;


}