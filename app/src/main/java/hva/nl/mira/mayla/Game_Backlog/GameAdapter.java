package hva.nl.mira.mayla.Game_Backlog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private List<Game> mGames;
    final private GameClickListener mGameClickListener;

    public GameAdapter(List<Game> mGames, GameClickListener mGameClickListener) {
        this.mGames = mGames;
        this.mGameClickListener = mGameClickListener;
    }

    //inflate layout to display the row items in the RecylcerView
    @Override
    public GameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.game_backlog_item, null);

        // Return a new holder instance
        GameAdapter.ViewHolder viewHolder = new GameAdapter.ViewHolder(view);
        return viewHolder;
    }

    //Bind  data to the viewHolder
    @Override
    public void onBindViewHolder(GameAdapter.ViewHolder holder, int position) {
        Game game = mGames.get(position);
        holder.gameTitle.setText(game.getGameTitle());
        holder.gamePlatform.setText(game.getGamePlatform());
        holder.gameStatus.setText(game.getGameStatus());
        holder.gameDate.setText(game.getGameDate());
    }

    @Override
    public int getItemCount() {
        return mGames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //ViewHolder displays all the row items that are in the RecyclerView

        public TextView gameTitle;
        public TextView gamePlatform;
        public TextView gameStatus;
        public TextView gameDate;

        public ViewHolder(View itemView) {

            super(itemView);
            gameTitle = itemView.findViewById(R.id.gameTitle);
            gamePlatform = itemView.findViewById(R.id.gamePlatform);
            gameStatus = itemView.findViewById(R.id.gameStatus);
            gameDate = itemView.findViewById(R.id.gameDate);
            itemView.setOnClickListener(this);
        }

        //When clicked on game card
        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mGameClickListener.gameOnClick(clickedPosition);
        }

    }

    public interface GameClickListener{
        void gameOnClick (int i);
    }


    public void swapList (List<Game> newList) {

        mGames = newList;

        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

}
