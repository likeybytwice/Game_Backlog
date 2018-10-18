package hva.nl.mira.mayla.Game_Backlog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class UpdateActivity extends AppCompatActivity {

    //Variables
    private EditText mGameTitle;
    private EditText mGamePlatform;
    private EditText mGameNotes;
    private Spinner mGameStatusSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Edit game card");

        //initializing variables
        mGameTitle = findViewById(R.id.editTitle);
        mGamePlatform = findViewById(R.id.editPlatform);
        mGameNotes = findViewById(R.id.editNotes);
        mGameStatusSpinner = findViewById(R.id.gameStatusSpinner);

        //when game card gets clicked, get the info at set it as text in the textfields
        final Game gameUpdate = getIntent().getParcelableExtra(MainActivity.GAME_KEY);
        if(gameUpdate != null) {

            mGameTitle.setText(gameUpdate.getGameTitle());
            mGamePlatform.setText(gameUpdate.getGamePlatform());
            mGameNotes.setText(gameUpdate.getGameNotes());

            String[] stringArray = getResources().getStringArray(R.array.game_options);

            int position = Arrays.asList(stringArray).indexOf(gameUpdate.getGameStatus());
            mGameStatusSpinner.setSelection(position);
        }

        //when save button is clicked
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String gameTitle = mGameTitle.getText().toString();
                String gamePlatform = mGamePlatform.getText().toString();
                String gameNotes = mGameNotes.getText().toString();
                String gameStatus = mGameStatusSpinner.getSelectedItem().toString();

                Game currentGame = gameUpdate;

                if (!mGameTitle.getText().toString().isEmpty() && !mGamePlatform.getText().toString().isEmpty()) {

                    if(currentGame == null){
                        currentGame = new Game(gameTitle, gamePlatform, gameNotes, gameStatus, new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
                    }
                    else {
                        //save the data
                        currentGame.setGameTitle(gameTitle);
                        currentGame.setGamePlatform(gamePlatform);
                        currentGame.setGameNotes(gameNotes);
                        currentGame.setGameStatus(gameStatus);
                        currentGame.setGameDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
                        Log.d("Testing", "what happens here");

                    }

                    //travel back to main
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(MainActivity.GAME_KEY, currentGame);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    Snackbar.make(view, "Please fill in the fields", Snackbar.LENGTH_LONG);
                }
            }
        });
    }

}
