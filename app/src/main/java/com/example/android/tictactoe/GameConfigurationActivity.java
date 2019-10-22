package com.example.android.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.tictactoe.config.Computer;
import com.example.android.tictactoe.config.DifficultyLevel;
import com.example.android.tictactoe.config.Mode;
import com.example.android.tictactoe.config.Player;
import com.example.android.tictactoe.config.PlayerType;
import com.example.android.tictactoe.config.Setting;
import com.example.android.tictactoe.config.TwoPlayer;
import com.google.gson.Gson;

public class GameConfigurationActivity extends AppCompatActivity {
    private Mode mEnabledMode = null;
    private Player mEnabledPlayer = null;
    private DifficultyLevel mEnabledDifficultyLevel = null;
    private PlayerType mEnabledPlayerType = null;

    private SharedPreferences mSharedPreferences;

    private static final String TAG = "GameConfigurationActivi";

    //tag to store player i.e, either NOUGHT or CROSS
    private static final String SHARED_PREFERENCE_PLAYER_TAG = "player";
    //tag to store game mode
    private static String SHARED_PREFERENCE_MODE_TAG = "mode";
    private static String SHARED_PREFERENCE_DIFFICULTYLEVEL_TAG = "difficultyLevel";

    private TextView mTvEasy;
    private TextView mTvMedium;
    private TextView mTvHard;
    private TextView mTvNought;
    private TextView mTvCross;
    private TextView mTvTwoPlayerMode;
    private TextView mTvComputerMode;
    private CoordinatorLayout mCoordinatorLayout;
    private Snackbar mEasySnackbar;
    private Snackbar mMediumSnackbar;

    //shared preference tags
    //tag to store the difficulty level of the computer
    //private static final String SHARED_PREFERENCE_DIFFICULTY_LEVEL_TAG = "difficulty level";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_configuration);
        init();
    }

    private void restoreSharedPreferences() {
        mEnabledMode = new Gson().fromJson(this.mSharedPreferences.getString(SHARED_PREFERENCE_MODE_TAG, new Gson().toJson(Setting.getDefaultMode())), Computer.class);
        mEnabledPlayer = new Gson().fromJson(this.mSharedPreferences.getString(SHARED_PREFERENCE_PLAYER_TAG, new Gson().toJson(Setting.getDefaultPlayer())), Player.class);
        Log.d(TAG, "restoreSharedPreferences: ");
        Log.d(TAG, "restoreSharedPreferences: mode " + mEnabledMode);
        Log.d(TAG, "restoreSharedPreferences: mode " + mEnabledPlayer);
        initViews(mEnabledMode, mEnabledPlayer);
    }

    private void saveSharedPreferences() {
        SharedPreferences.Editor editor = this.mSharedPreferences.edit();
        editor.putString(SHARED_PREFERENCE_PLAYER_TAG, new Gson().toJson(mEnabledPlayer));
        editor.putString(SHARED_PREFERENCE_MODE_TAG, new Gson().toJson(mEnabledMode));
        editor.apply();
    }

    private void savePlayer() {
        SharedPreferences.Editor editor = this.mSharedPreferences.edit();
        editor.putString(SHARED_PREFERENCE_PLAYER_TAG, new Gson().toJson(mEnabledPlayer));
        editor.apply();
    }

    private void saveDifficultyLevel(DifficultyLevel difficultyLevel){
        SharedPreferences.Editor editor = this.mSharedPreferences.edit();
        editor.putString(SHARED_PREFERENCE_DIFFICULTYLEVEL_TAG, new Gson().toJson(difficultyLevel));
        editor.apply();
        mEnabledDifficultyLevel = difficultyLevel;
    }

    private void saveMode() {
        SharedPreferences.Editor editor = this.mSharedPreferences.edit();
        editor.putString(SHARED_PREFERENCE_MODE_TAG, new Gson().toJson(mEnabledMode));
        editor.apply();
    }

    /**
     * This method checks the value of the given string and compares to the text view data of difficulty and renders it properly
     *
     * @param difficultyLevel the value of difficulty i.e., EASY, MEDIUM or HARD
     */
    private void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        if (difficultyLevel.equals(DifficultyLevel.EASY)) {
            setDifficultyLevelToEasy();
        } else if (difficultyLevel.equals(DifficultyLevel.MEDIUM)) {
            setDifficultyLevelToMedium();
        } else if (difficultyLevel.equals(DifficultyLevel.HARD)) {
            setDifficultyLevelToHard();
        }
    }

    /**
     * Setting player to either Cross("X)" or Nought("O")
     *
     * @param str value to set human to either "X"(Cross) or "O"(Nought)
     */
    private void setPlayer(String str) {
        if (mTvCross.getText().equals("X")) {
            setPlayerToCross();
        } else {
            setPlayerToNought();
        }
    }

    private void resetSettings() {


    }

    /**
     * Initializes the view and other values
     */
    private void init() {
        mTvEasy = (TextView) findViewById(R.id.tv_easy);
        mTvMedium = (TextView) findViewById(R.id.tv_medium);
        mTvHard = (TextView) findViewById(R.id.tv_hard);
        mTvCross = (TextView) findViewById(R.id.tv_cross);
        mTvNought = (TextView) findViewById(R.id.tv_nought);
        mTvTwoPlayerMode = (TextView) findViewById(R.id.tv_computer_mode);
        mTvComputerMode = (TextView) findViewById(R.id.tv_two_player_mode);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        mEasySnackbar = null;
        if (this.getSharedPreferences(getResources().getString(R.string.game_configuration_activity_shared_preferences), Context.MODE_PRIVATE) != null) {
            this.mSharedPreferences = this.getSharedPreferences(getResources().getString(R.string.game_configuration_activity_shared_preferences), Context.MODE_PRIVATE);
            //Restoring game configuration
            restoreSharedPreferences();
        } else {
            defaultSetup();
        }
    }

    private void defaultSetup() {
        Log.d(TAG, "defaultSetup: ");
        mEnabledMode = Setting.getDefaultMode();
        mEnabledPlayer = Setting.getDefaultPlayer();
        initViews(mEnabledMode, mEnabledPlayer);
    }

    private void initViews(Mode m, Player p) {
        if (m instanceof Computer) {
            enableComputerMode();
        } else if (m instanceof TwoPlayer) {
            enableTwoPlayerMode();
        }
        //setting player(The one who is playing)
        if (p.getPlayerType().equals(PlayerType.CROSS)) {
            setPlayerToCross();
        } else {
            setPlayerToNought();
        }
    }


    /**
     * Call this method when you want to enable computer defaultMode. You do not need to disable two player defaultMode as it is
     * done automatically here.
     * You can disable computer defaultMode but the work done will be redundant
     * <p>
     * This method disables two player defaultMode and enables computer defaultMode
     */
    private void enableComputerMode() {
        //disabling two player defaultMode, before enabling computer defaultMode
        disableTwoPlayerMode();
        try {
            enableComputerModeViews();
            setDifficultyLevel(((Computer) mEnabledMode).getDifficultyLevel());

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void enableComputerModeViews() {

    }

    /**
     * This method takes care of disabling the view related to computer default Setting.
     */
    //TODO Disable the computer mode here
    private void disableComputerMode() {

    }

    /**
     * Call this method when you want to enable two player defaultMode. You do not need to disable computer defaultMode as it is
     * done automatically here.
     * You can disable computer defaultMode but the work done will be redundant
     * <p>
     * This methods disables computer defaultMode and enables two player defaultMode
     */

    //TODO Enable two player mode here
    private void enableTwoPlayerMode() {
        //disabling computer defaultMode
        disableComputerMode();
        enableTwoPlayerModeViews();
    }

    private void enableTwoPlayerModeViews() {
    }

    /**
     * This method takes care of disabling the view related to two player defaultSettings.
     */
    private void disableTwoPlayerMode() {
        //TODO handle view of two player here
    }

    /**
     * An on click listener for "Easy" TextView
     * //TODO add easy difficulty level feature
     * Currently shows a snack bar message informing the user about the unavailability of this feature.
     *
     * @param view TextView
     */
    public void setDifficultyLevelToEasy(View view) {
        saveDifficultyLevel(DifficultyLevel.EASY);
        setDifficultyLevelToEasy();
    }

    /**
     * Helper method to handle setDifficultyLevelToMedium(View v)
     * This method is used to render views to provide visibility of users action when clicked on the above mentioned view.
     */
    private void setDifficultyLevelToEasy() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mTvEasy.setBackground(getDrawable(R.drawable.vertical_selected_easy_button));
            mTvMedium.setBackground(getDrawable(R.drawable.vertical_unselected_medium_button));
            mTvHard.setBackground(getDrawable(R.drawable.vertical_unselected_hard_button));
        } else {
            mTvEasy.setBackground(getDrawable(R.drawable.selected_easy_button));
            mTvMedium.setBackground(getDrawable(R.drawable.unselected_medium_button));
            mTvHard.setBackground(getDrawable(R.drawable.unselected_hard_button));
        }

        mTvEasy.setTypeface(null, Typeface.BOLD);
        mTvEasy.setTextColor(getResources().getColor(R.color.colorOnSelected));

        mTvMedium.setTypeface(null, Typeface.NORMAL);
        mTvMedium.setTextColor(getResources().getColor(R.color.colorOnUnSelected));

        mTvHard.setTypeface(null, Typeface.NORMAL);
        mTvHard.setTextColor(getResources().getColor(R.color.colorOnUnSelected));
    }

    /**
     * An on click listener for "Medium" TextView
     * Currently shows a snack bar message informing the user about the unavailability of this feature.
     *
     * @param view TextView
     */
    public void setDifficultyLevelToMedium(View view) {
        saveDifficultyLevel(DifficultyLevel.MEDIUM);
        setDifficultyLevelToMedium();
    }

    /**
     * Helper method to handle setDifficultyLevelToMedium(View v)
     * This method is used to render views to provide visibility of users action when clicked on the above mentioned view.
     */
    private void setDifficultyLevelToMedium() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mTvMedium.setBackground(getDrawable(R.drawable.vertical_selected_medium_button));
            mTvEasy.setBackground(getDrawable(R.drawable.vertical_unselected_easy_button));
            mTvHard.setBackground(getDrawable(R.drawable.vertical_unselected_hard_button));
        } else {
            mTvMedium.setBackground(getDrawable(R.drawable.selected_medium_button));
            mTvEasy.setBackground(getDrawable(R.drawable.unselected_easy_button));
            mTvHard.setBackground(getDrawable(R.drawable.unselected_hard_button));
        }
        mTvMedium.setTypeface(null, Typeface.BOLD);
        mTvMedium.setTextColor(getResources().getColor(R.color.colorOnSelected));

        mTvEasy.setTypeface(null, Typeface.NORMAL);
        mTvEasy.setTextColor(getResources().getColor(R.color.colorOnUnSelected));

        mTvHard.setTypeface(null, Typeface.NORMAL);
        mTvHard.setTextColor(getResources().getColor(R.color.colorOnUnSelected));
    }

    /**
     * An on click event handler for "Hard" TextView
     *
     * @param view TextView
     */
    public void setDifficultyLevelToHard(View view) {
        saveDifficultyLevel(DifficultyLevel.HARD);
        setDifficultyLevelToHard();
    }

    /**
     * Helper method to handle setDifficultyLevelToHard(View v)
     * This method is used to render views to provide visibility of users action when clicked on the above mentioned view.
     */
    private void setDifficultyLevelToHard() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mTvHard.setBackground(getDrawable(R.drawable.vertical_selected_hard_button));
            mTvEasy.setBackground(getDrawable(R.drawable.vertical_unselected_easy_button));
            mTvMedium.setBackground(getDrawable(R.drawable.vertical_unselected_medium_button));
        } else {
            mTvHard.setBackground(getDrawable(R.drawable.selected_hard_button));
            mTvEasy.setBackground(getDrawable(R.drawable.unselected_easy_button));
            mTvMedium.setBackground(getDrawable(R.drawable.unselected_medium_button));
        }
        mTvHard.setTypeface(null, Typeface.BOLD);
        mTvHard.setTextColor(getResources().getColor(R.color.colorOnSelected));

        mTvEasy.setTypeface(null, Typeface.NORMAL);
        mTvEasy.setTextColor(getResources().getColor(R.color.colorOnUnSelected));

        mTvMedium.setTypeface(null, Typeface.NORMAL);
        mTvMedium.setTextColor(getResources().getColor(R.color.colorOnUnSelected));
    }

    /**
     * An on click event handler for "X" TextView
     *
     * @param view TextView
     */
    public void setPlayerToCross(View view) {
        Snackbar.make(mCoordinatorLayout, "Player set to " + PlayerType.CROSS, Snackbar.LENGTH_SHORT)
                .show();
        setPlayerToCross();
    }

    /**
     * Helper method to handle setPlayerToCross(View v)
     * This method is used to render views to provide visibility of users action when clicked on the above mentioned view.
     */
    private void setPlayerToCross() {
        mEnabledPlayer.setPlayerType(PlayerType.CROSS);
        savePlayer();
        mTvCross.setTypeface(null, Typeface.BOLD);
        mTvCross.setBackground(getDrawable(R.drawable.selected_x_button));
        mTvCross.setTextColor(getResources().getColor(R.color.colorOnSelected));

        mTvNought.setTypeface(null, Typeface.NORMAL);
        mTvNought.setBackground(getDrawable(R.drawable.unselected_o_button));
        mTvNought.setTextColor(getResources().getColor(R.color.colorOnUnSelected));
    }

    /**
     * An on click event handler for "O" TextView
     *
     * @param view TextView
     */
    public void setPlayerToNought(View view) {
        Snackbar.make(mCoordinatorLayout, "Player set to " + PlayerType.NOUGHT, Snackbar.LENGTH_SHORT)
                .show();
        setPlayerToNought();
    }

    /**
     * Helper method to handle setPlayerToNought(View v)
     * This method is used to render views to provide visibility of users action when clicked on the above mentioned view.
     */
    private void setPlayerToNought() {
        mEnabledPlayer.setPlayerType(PlayerType.NOUGHT);
        mTvNought.setTypeface(null, Typeface.BOLD);
        mTvNought.setBackground(getDrawable(R.drawable.selected_o_button));
        mTvNought.setTextColor(getResources().getColor(R.color.colorOnSelected));

        mTvCross.setTypeface(null, Typeface.NORMAL);
        mTvCross.setBackground(getDrawable(R.drawable.unselected_x_button));
        mTvCross.setTextColor(getResources().getColor(R.color.colorOnUnSelected));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Starts the game with the set game configuration
     * This method sends game configuration data in a key value pair.
     * The activity that will be opened (for now, MainActivity) can get the data using getIntent().getExtras() and must specify the exact key to get the corresponding value.
     *
     * @param view Button
     */
    public void startGame(View view) {
        Intent intent = new Intent(GameConfigurationActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        //bundle.putString("difficulty", mDifficultLevel);
        //bundle.putString("human", mEnabledPlayerType);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (mEnabledMode instanceof Computer) {
                switch (((Computer) mEnabledMode).getDifficultyLevel()) {
                    case EASY:
                        mTvEasy.setBackground(getDrawable(R.drawable.vertical_selected_easy_button));
                        mTvMedium.setBackground(getDrawable(R.drawable.vertical_unselected_medium_button));
                        mTvHard.setBackground(getDrawable(R.drawable.vertical_unselected_hard_button));
                        break;
                    case MEDIUM:
                        mTvMedium.setBackground(getDrawable(R.drawable.vertical_selected_medium_button));
                        mTvEasy.setBackground(getDrawable(R.drawable.vertical_unselected_easy_button));
                        mTvHard.setBackground(getDrawable(R.drawable.vertical_unselected_hard_button));
                        break;
                    case HARD:
                        mTvHard.setBackground(getDrawable(R.drawable.vertical_selected_hard_button));
                        mTvEasy.setBackground(getDrawable(R.drawable.vertical_unselected_easy_button));
                        mTvMedium.setBackground(getDrawable(R.drawable.vertical_unselected_medium_button));
                        break;
                    default:
                }
            } else {
                Computer m = new Gson().fromJson(this.mSharedPreferences.getString(SHARED_PREFERENCE_MODE_TAG, new Gson().toJson(new Computer())), Computer.class);
                switch (m.getDifficultyLevel()) {
                    case EASY:
                        mTvEasy.setBackground(getDrawable(R.drawable.selected_easy_button));
                        mTvMedium.setBackground(getDrawable(R.drawable.unselected_medium_button));
                        mTvHard.setBackground(getDrawable(R.drawable.unselected_hard_button));
                        break;
                    case MEDIUM:
                        mTvMedium.setBackground(getDrawable(R.drawable.selected_medium_button));
                        mTvEasy.setBackground(getDrawable(R.drawable.unselected_easy_button));
                        mTvHard.setBackground(getDrawable(R.drawable.unselected_hard_button));
                        break;
                    case HARD:
                        mTvHard.setBackground(getDrawable(R.drawable.selected_hard_button));
                        mTvEasy.setBackground(getDrawable(R.drawable.unselected_easy_button));
                        mTvMedium.setBackground(getDrawable(R.drawable.unselected_medium_button));
                        break;
                    default:
                }
            }
        }
    }

    public void changeToTwoPlayerMode(View view) {
        enableTwoPlayerMode();
    }

    public void changeToComputerMode(View view) {
        enableComputerMode();
    }
}
