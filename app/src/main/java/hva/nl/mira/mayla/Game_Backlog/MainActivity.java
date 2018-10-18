package hva.nl.mira.mayla.Game_Backlog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GameAdapter.GameClickListener {

    //Create an instance of the AppDatabase class
    static AppDatabase db;

    //Local variables
    private List<Game> mGames;
    private GameAdapter mAdapter;
    private RecyclerView mRecyclerView;

    //Constants
    public static final int REQUESTCODE_INSERT = 1717;
    public static final int REQUESTCODE_UPDATE = 0707;
    public final static int TASK_GET_ALL_GAMES = 0;
    public final static int TASK_DELETE_GAME = 1;
    public final static int TASK_UPDATE_GAME = 2;
    public final static int TASK_INSERT_GAME = 3;
    public final static String GAME_KEY = "Game";

    public static int mModifyPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("My game backlog");

        db = AppDatabase.getInstance(this);
        new GameAsyncTask(TASK_GET_ALL_GAMES).execute();

        mRecyclerView = findViewById(R.id.gameRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mGames = new ArrayList<>();
//        mGames.add(new Game("Fifa 19", "PS4", "To buy", "Want to play", "15-10-2018"));
//        updateUI();


        /* Add a touch helper to the RecyclerView to recognize when a user swipes to delete a list entry.
           An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
           and uses callbacks to signal when a user is performing these actions.*/

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    //Called when a user swipes left or right on a ViewHolder
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                        //Get the index corresponding to the selected position
                        int position = (viewHolder.getAdapterPosition());
                        new GameAsyncTask(TASK_DELETE_GAME).execute(mGames.get(position));
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //An intent is an abstract description of an operation to be performed
                //Initialize an Intent to navigate to the update activity
                Intent create = new Intent(MainActivity.this, UpdateActivity.class);
                startActivityForResult(create, REQUESTCODE_INSERT);
            }
        });


    }

    //Update
    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new GameAdapter(mGames, this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.swapList(mGames);
        }
    }

    //When an existing game gets clicked, go to updating activity
    @Override
    public void gameOnClick(int i) {
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        mModifyPosition = i;
        intent.putExtra(GAME_KEY, mGames.get(i));
        startActivityForResult(intent, REQUESTCODE_UPDATE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUESTCODE_UPDATE) {
            if (resultCode == RESULT_OK) {

                //Because the updated object is a new object with a different id,
                //change the text of the current one,
                //So the database can recognize the game and update it
                Game updatedGame = data.getParcelableExtra(MainActivity.GAME_KEY);
                new GameAsyncTask(TASK_UPDATE_GAME).execute(updatedGame);
                updateUI();
            }
        }

        //Same as above, but this time its a new game
        if(requestCode == REQUESTCODE_INSERT){
            if(resultCode == RESULT_OK){
                Game newGame = data.getParcelableExtra(MainActivity.GAME_KEY);
                new GameAsyncTask(TASK_INSERT_GAME).execute(newGame);
                updateUI();
            }
        }
    }

    //allows you to perform background operations and publish results on the UI thread
    public class GameAsyncTask extends AsyncTask<Game, Void, List> {

        private int taskCode;

        public GameAsyncTask(int taskCode) {
            this.taskCode = taskCode;
        }

        //This step is used to perform background computation that can take a long time
        @Override
        protected List doInBackground(Game... games) {
            switch (taskCode) {
                case TASK_DELETE_GAME:
                    db.gameDao().deleteGame(games[0]);
                    break;

                case TASK_UPDATE_GAME:
                    db.gameDao().updateGame(games[0]);
                    break;

                case TASK_INSERT_GAME:
                    db.gameDao().insertGame(games[0]);
                    break;
            }

            //To return a new list with the updated data, we get all the data from the database again.
            return db.gameDao().getAllGames();
        }

        //This method is used to display any form of progress in the user interface while the
        //background computation is still executing
        public void onGameDbUpdated(List list) {
            mGames = list;
            updateUI();
        }

        //invoked on the UI thread after the background computation finishes
        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            onGameDbUpdated(list);
        }

    }
}
