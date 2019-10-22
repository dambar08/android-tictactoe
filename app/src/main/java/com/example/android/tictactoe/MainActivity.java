package com.example.android.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private TextView mTv00;
    private TextView mTv01;
    private TextView mTv02;
    private TextView mTv10;
    private TextView mTv11;
    private TextView mTv12;
    private TextView mTv20;
    private TextView mTv21;
    private TextView mTv22;
    private TextView mTvMsg;
    private TextView mTvTurnMsg;
    private TextView mTvRestart;

    //Instance var
    private String mMarker;

    private String mDifficulty;//this feature is not for use in this version. Add it when the algorithm for easy or medium difficulty is added
    private String mHuman;//this feature is not for use in this version. Add it when you implement computer to be nought
    private boolean mFoundWinner;

    public static final Random rand = new Random();
    private static Board board;

    private boolean FIRST_MOVE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//setting the design
        init();//initializing the view
        setup();//setting the board
        getBundleData();//getting game configuration data from previous activity
        initBoard();
    }

    /**
     * Getting game configuration data from the previous activity.
     * Previous activity = GameConfigurationActivity
     */
    private void getBundleData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mDifficulty = bundle.getString("difficulty");
            mHuman = bundle.getString("human");
        }
    }

    /**
     * Initialize the Board. This method is called to initialize the board instance and must be called first before work on a new board.
     * The first move of the computer is random
     */
    private void initBoard() {
        Log.d(TAG, "initBoard: ");
        board = new Board();
        Point p = new Point(rand.nextInt(3), rand.nextInt(3));
        renderMove(p.x, p.y);
    }


    /**
     * Helper method for rendering the move of the computer. This method calls onClick(View v) and sets the rendering properties
     * @param x
     * @param y
     */
    private void renderMove(int x, int y) {
        if (x == 0 && y == 0) {
            onClick(mTv00);
        } else if (x == 0 && y == 1) {
            onClick(mTv01);
        } else if (x == 0 && y == 2) {
            onClick(mTv02);
        } else if (x == 1 && y == 0) {
            onClick(mTv10);
        } else if (x == 1 && y == 1) {
            onClick(mTv11);
        } else if (x == 1 && y == 2) {
            onClick(mTv12);
        } else if (x == 2 && y == 0) {
            onClick(mTv20);
        } else if (x == 2 && y == 1) {
            onClick(mTv21);
        } else if (x == 2 && y == 2) {
            onClick(mTv22);
        }
    }

    /**
     * Setting up the board
     */
    private void setup() {
        setupBackground();
        clearBoard();
        hideWinnerMessage();
        setTurnMsg();
        mMarker = "X";//setting the marker to "cross"
        mFoundWinner = false;
        FIRST_MOVE = false;
    }

    /**
     * Initializing turn message
     * Setting the turn message. This view displays a text to indicate  whose turn is to play currently.
     */

    private void setTurnMsg() {
        mTvTurnMsg.setText(getText(R.string.x_turn_msg));
    }

    /**
     * Setting the background of the board.
     */
    private void setupBackground() {
        mTv00.setBackground(getDrawable(R.drawable.top_start_bg));
        mTv01.setBackground(getDrawable(R.drawable.top_center_bg));
        mTv02.setBackground(getDrawable(R.drawable.top_end_bg));

        mTv10.setBackground(getDrawable(R.drawable.center_start_bg));
        mTv11.setBackground(getDrawable(R.drawable.center_center_bg));
        mTv12.setBackground(getDrawable(R.drawable.center_end_bg));

        mTv20.setBackground(getDrawable(R.drawable.bottom_start_bg));
        mTv21.setBackground(getDrawable(R.drawable.bottom_center_bg));
        mTv22.setBackground(getDrawable(R.drawable.bottom_end_bg));
    }


    /**
     * Initializing the view and adding on click event handler to the views needed
     */
    private void init() {
        mTv00 = findViewById(R.id.tv_00);
        mTv00.setOnClickListener(this);
        mTv01 = findViewById(R.id.tv_01);
        mTv01.setOnClickListener(this);
        mTv02 = findViewById(R.id.tv_02);
        mTv02.setOnClickListener(this);
        mTv10 = findViewById(R.id.tv_10);
        mTv10.setOnClickListener(this);
        mTv11 = findViewById(R.id.tv_11);
        mTv11.setOnClickListener(this);
        mTv12 = findViewById(R.id.tv_12);
        mTv12.setOnClickListener(this);
        mTv20 = findViewById(R.id.tv_20);
        mTv20.setOnClickListener(this);
        mTv21 = findViewById(R.id.tv_21);
        mTv21.setOnClickListener(this);
        mTv22 = findViewById(R.id.tv_22);
        mTv22.setOnClickListener(this);
        mTvMsg = findViewById(R.id.tv_msg);
        mTvTurnMsg = findViewById(R.id.tv_turn_msg);
        mTvRestart = findViewById(R.id.tv_restart);
        mTvRestart.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //removing this activity from the back stack when the back button is pressed
        finish();
    }

    /**
     * Clearing the board values.
     */
    private void clearBoard() {
        mTv00.setText("");
        mTv01.setText("");
        mTv02.setText("");
        mTv10.setText("");
        mTv11.setText("");
        mTv12.setText("");
        mTv20.setText("");
        mTv21.setText("");
        mTv22.setText("");
    }

    /**
     * Hiding the winner message but not removing the space of the view
     */
    private void hideWinnerMessage() {
        mTvMsg.setVisibility(View.INVISIBLE);
    }

    /**
     * Shows the winner of the game
     *
     * @param str the marker(cross or nought or both cross and nought if draw) who won the game
     */
    private void showWinnerMessage(String str) {

        if (str.equals(getString(R.string.nought))) {//if O is the winner
            mTvMsg.setText(getText(R.string.winner_o_msg));
        } else if (str.equals(getString(R.string.cross))) {//if X is the winner
            mTvMsg.setText(getText(R.string.winner_x_msg));
        } else {
            mTvMsg.setText(getText(R.string.winner_xo_msg));//if draw
        }
        mTvMsg.setVisibility(View.VISIBLE);
    }


    /**
     * Handling on click for the all the view who have registered to listen
     * This applies to all the view id except for R.id.tv_restart
     * Process in details
     * This is for the TextView of the board
     * 1. Check which view was clicked(1)
     * 2. Check is winner is found. If winner is found, there is no need to handle the events and ignores all the steps below.(2)
     * 3. Check if the view is set to either "O" or "X". If it is already set then ignore the on click event and ignore all the steps below else set the view with the marker value ("O" or "X")
     * 4. If the marker is "X". Change the color of the text i.e., for X (get the color from the resource file)
     * 5. If the marker is "O". Change the color of the text i.e., for O (get the color from the resource file)
     * 6. Change marker. See changeMarker() for details
     * 7. Change turn message. See changeTurnMessage() for details
     * 8. Check if winner was found after this move. See winCheck() for more details
     * 9. Check if this is the first move. If it is the first move then start the board with PLAYER_X
     * This method also renders computer moves
     *
     * @param v view of item
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_00://(1)
                if (!mFoundWinner) {//(2)
                    if (mTv00.getText().equals("")) {//3
                        mTv00.setText(mMarker);

                        //setting the color of marker
                        if (mMarker.equals("X")) {
                            mTv00.setTextColor(getResources().getColor(R.color.colorCross));//(4)
                        } else {
                            mTv00.setTextColor(getResources().getColor(R.color.colorNought));//(5)
                        }
                        //changing the text of marker
                        changeMarker();
                        changeTurnMessage();
                        winCheck();

                        //checking if this is the first move i.e., computers move. indicating that this is a computer move
                        if (!FIRST_MOVE) {
                            Log.d(TAG, "onClick: 00" + board.placeAMove(new Point(0, 0), Board.PLAYER_X));
                            FIRST_MOVE = true;
                        } else {
                            Log.d(TAG, "onClick: 00" + board.placeAMove(new Point(0, 0), Board.PLAYER_O));
                        }
                        if (mMarker.equals("X")) {
                            aiMove();
                        }
                    }
                }
                break;
            case R.id.tv_01:
                if (!mFoundWinner) {
                    if (mTv01.getText().equals("")) {
                        mTv01.setText(mMarker);
                        if (mMarker.equals("X")) {
                            mTv01.setTextColor(getResources().getColor(R.color.colorCross));
                        } else {
                            mTv01.setTextColor(getResources().getColor(R.color.colorNought));
                        }
                        changeMarker();
                        changeTurnMessage();
                        winCheck();
                        if (!FIRST_MOVE) {
                            Log.d(TAG, "onClick: 01" + board.placeAMove(new Point(0, 1), Board.PLAYER_X));
                            FIRST_MOVE = true;
                        } else {
                            Log.d(TAG, "onClick: 01"+board.placeAMove(new Point(0, 1), Board.PLAYER_O));
                        }
                        if (mMarker.equals("X")) {
                            aiMove();
                        }
                    }
                }
                break;
            case R.id.tv_02:
                if (!mFoundWinner) {
                    if (mTv02.getText().equals("")) {
                        mTv02.setText(mMarker);
                        if (mMarker.equals("X")) {
                            mTv02.setTextColor(getResources().getColor(R.color.colorCross));
                        } else {
                            mTv02.setTextColor(getResources().getColor(R.color.colorNought));
                        }
                        changeMarker();
                        changeTurnMessage();
                        winCheck();
                        if (!FIRST_MOVE) {
                            Log.d(TAG, "onClick: 02"+board.placeAMove(new Point(0, 2), Board.PLAYER_X));
                            FIRST_MOVE = true;
                        } else {
                            Log.d(TAG, "onClick: 02"+board.placeAMove(new Point(0, 2), Board.PLAYER_O));
                        }
                        if (mMarker.equals("X")) {
                            aiMove();
                        }
                    }
                }
                break;
            case R.id.tv_10:
                if (!mFoundWinner) {
                    if (mTv10.getText().equals("")) {
                        mTv10.setText(mMarker);
                        if (mMarker.equals("X")) {
                            mTv10.setTextColor(getResources().getColor(R.color.colorCross));
                        } else {
                            mTv10.setTextColor(getResources().getColor(R.color.colorNought));
                        }
                        changeMarker();
                        changeTurnMessage();
                        winCheck();
                        if (!FIRST_MOVE) {
                            Log.d(TAG, "onClick: 10"+board.placeAMove(new Point(1, 0), Board.PLAYER_X));
                            FIRST_MOVE = true;
                        } else {
                            Log.d(TAG, "onClick: 10"+board.placeAMove(new Point(1, 0), Board.PLAYER_O));
                        }
                        if (mMarker.equals("X")) {
                            aiMove();
                        }
                    }
                }
                break;
            case R.id.tv_11:
                if (!mFoundWinner) {
                    if (mTv11.getText().equals("")) {
                        mTv11.setText(mMarker);
                        if (mMarker.equals("X")) {
                            mTv11.setTextColor(getResources().getColor(R.color.colorCross));
                        } else {
                            mTv11.setTextColor(getResources().getColor(R.color.colorNought));
                        }
                        changeMarker();
                        changeTurnMessage();
                        winCheck();
                        if (!FIRST_MOVE) {
                            Log.d(TAG, "onClick: 11"+board.placeAMove(new Point(1, 1), Board.PLAYER_X));
                            FIRST_MOVE = true;
                        } else {
                            Log.d(TAG, "onClick: 11"+board.placeAMove(new Point(1, 1), Board.PLAYER_O));
                        }
                        if (mMarker.equals("X")) {
                            aiMove();
                        }
                    }
                }
                break;
            case R.id.tv_12:
                if (!mFoundWinner) {
                    if (mTv12.getText().equals("")) {
                        mTv12.setText(mMarker);
                        if (mMarker.equals("X")) {
                            mTv12.setTextColor(getResources().getColor(R.color.colorCross));
                        } else {
                            mTv12.setTextColor(getResources().getColor(R.color.colorNought));
                        }
                        changeMarker();
                        changeTurnMessage();
                        winCheck();
                        if (!FIRST_MOVE) {
                            Log.d(TAG, "onClick: 12"+board.placeAMove(new Point(1, 2), Board.PLAYER_X));
                            FIRST_MOVE = true;
                        } else {
                            Log.d(TAG, "onClick: 12"+board.placeAMove(new Point(1, 2), Board.PLAYER_O));
                        }
                        if (mMarker.equals("X")) {
                            aiMove();
                        }
                    }
                }
                break;
            case R.id.tv_20:
                if (!mFoundWinner) {
                    if (mTv20.getText().equals("")) {
                        mTv20.setText(mMarker);
                        if (mMarker.equals("X")) {
                            mTv20.setTextColor(getResources().getColor(R.color.colorCross));
                        } else {
                            mTv20.setTextColor(getResources().getColor(R.color.colorNought));
                        }
                        changeMarker();
                        changeTurnMessage();
                        winCheck();
                        if (!FIRST_MOVE) {
                            Log.d(TAG, "onClick: 20"+board.placeAMove(new Point(2, 0), Board.PLAYER_X));
                            FIRST_MOVE = true;
                        } else {
                            Log.d(TAG, "onClick: 20"+board.placeAMove(new Point(2, 0), Board.PLAYER_O));
                        }
                        if (mMarker.equals("X")) {
                            aiMove();
                        }
                    }
                }
                break;
            case R.id.tv_21:
                if (!mFoundWinner) {
                    if (mTv21.getText().equals("")) {
                        mTv21.setText(mMarker);
                        if (mMarker.equals("X")) {
                            mTv21.setTextColor(getResources().getColor(R.color.colorCross));
                        } else {
                            mTv21.setTextColor(getResources().getColor(R.color.colorNought));
                        }
                        changeMarker();
                        changeTurnMessage();
                        winCheck();
                        if (!FIRST_MOVE) {
                            Log.d(TAG, "onClick: 21"+board.placeAMove(new Point(2, 1), Board.PLAYER_X));
                            FIRST_MOVE = true;
                        } else {
                            Log.d(TAG, "onClick: 21"+board.placeAMove(new Point(2, 1), Board.PLAYER_O));
                        }

                        if (mMarker.equals("X")) {
                            aiMove();
                        }
                    }
                }
                break;
            case R.id.tv_22:
                if (!mFoundWinner) {
                    if (mTv22.getText().equals("")) {
                        mTv22.setText(mMarker);
                        if (mMarker.equals("X")) {
                            mTv22.setTextColor(getResources().getColor(R.color.colorCross));
                        } else {
                            mTv22.setTextColor(getResources().getColor(R.color.colorNought));
                        }
                        changeMarker();
                        changeTurnMessage();
                        winCheck();
                        if (!FIRST_MOVE) {
                            Log.d(TAG, "onClick: 22"+board.placeAMove(new Point(2, 2), Board.PLAYER_X));
                            FIRST_MOVE = true;
                        } else {
                            Log.d(TAG, "onClick: 22"+board.placeAMove(new Point(2, 2), Board.PLAYER_O));
                        }
                        if (mMarker.equals("X")) {
                            aiMove();
                        }
                    }
                }
                break;

            case R.id.tv_restart://restart button on click listener
                reset();
                Log.d(TAG, "onClick: reset");
                break;
        }
    }

    /**
     * This method is to be called when its AI's turn to move. It uses minimax algorithm to find its move.
     */
    private void aiMove() {
        board.minimax(0, Board.PLAYER_X);
        board.placeAMove(board.computerMove, Board.PLAYER_X);
        renderMove(board.computerMove.x, board.computerMove.y);//calling helper method to render the AI's move
        winCheck();
    }

    /**
     * Change the turn message. Shows the current player turn
     */
    private void changeTurnMessage() {
        if (mTvTurnMsg.getText().equals(getText(R.string.x_turn_msg))) {
            mTvTurnMsg.setText(getText(R.string.o_turn_msg));
        } else {
            mTvTurnMsg.setText(getText(R.string.x_turn_msg));
        }
    }

    /**
     * Change the marker (X to O) or (O to X)
     */
    private void changeMarker() {
        if (mMarker.equals("O")) {
            mMarker = "X";
        } else {
            mMarker = "O";
        }
    }

    /**
     * Checks if there is a winner. This method sets mFoundWinner to true if a winner is found, which can block on click events for some of the view. Check onClick(View v) for more details.
     */
    private void winCheck() {
        //checking if X is the winner
        if ((mTv00.getText().toString().equals("X") & mTv01.getText().toString().equals("X") & mTv02.getText().toString().equals("X")) ||//top horizontal check
                (mTv10.getText().toString().equals("X") & mTv11.getText().toString().equals("X") & mTv12.getText().toString().equals("X")) ||//middle horizontal check
                (mTv20.getText().toString().equals("X") & mTv21.getText().toString().equals("X") & mTv22.getText().toString().equals("X")) ||//bottom horizontal check
                (mTv00.getText().toString().equals("X") & mTv10.getText().toString().equals("X") & mTv20.getText().toString().equals("X")) ||//left vertical check
                (mTv01.getText().toString().equals("X") & mTv11.getText().toString().equals("X") & mTv21.getText().toString().equals("X")) ||//middle vertical check
                (mTv02.getText().toString().equals("X") & mTv12.getText().toString().equals("X") & mTv22.getText().toString().equals("X")) ||//right vertical check
                (mTv20.getText().toString().equals("X") & mTv11.getText().toString().equals("X") & mTv02.getText().toString().equals("X")) ||//top right to bottom left diagonal check
                (mTv00.getText().toString().equals("X") & mTv11.getText().toString().equals("X") & mTv22.getText().toString().equals("X"))) {//top left to bottom right diagonal check
            mFoundWinner = true;//indicating that we have found the winner
            showWinnerMessage("X");//showing X is the winner
            hideTurnMsg();//hiding the turn msg
        }
        //checking if O is the winner
        else if ((mTv00.getText().toString().equals("O") & mTv01.getText().toString().equals("O") & mTv02.getText().toString().equals("O")) ||//top horizontal check
                (mTv10.getText().toString().equals("O") & mTv11.getText().toString().equals("O") & mTv12.getText().toString().equals("O")) ||//middle horizontal check
                (mTv20.getText().toString().equals("O") & mTv21.getText().toString().equals("O") & mTv22.getText().toString().equals("O")) ||//bottom horizontal check
                (mTv00.getText().toString().equals("O") & mTv10.getText().toString().equals("O") & mTv20.getText().toString().equals("O")) ||//left vertical check
                (mTv01.getText().toString().equals("O") & mTv11.getText().toString().equals("O") & mTv21.getText().toString().equals("O")) ||//middle vertical check
                (mTv02.getText().toString().equals("O") & mTv12.getText().toString().equals("O") & mTv22.getText().toString().equals("O")) ||//right vertical check
                (mTv20.getText().toString().equals("O") & mTv11.getText().toString().equals("O") & mTv02.getText().toString().equals("O")) ||//top right to bottom left diagonal check
                (mTv00.getText().toString().equals("O") & mTv11.getText().toString().equals("O") & mTv22.getText().toString().equals("O"))) {//top left to bottom right diagonal check
            mFoundWinner = true;//indicating that we have found the winner
            showWinnerMessage("O");//showing O is the winner
            hideTurnMsg();//hiding the turn message
        } else if (!mTv00.getText().toString().equals("") && !mTv01.getText().toString().equals("") && !mTv02.getText().toString().equals("") && !mTv10.getText().toString().equals("") && !mTv11.getText().toString().equals("") && !mTv12.getText().toString().equals("") && !mTv20.getText().toString().equals("") && !mTv21.getText().toString().equals("") && !mTv22.getText().toString().equals("")) {
            showWinnerMessage("");//indicating it is a draw
            hideTurnMsg();//hiding the turn message

        }
    }

    /**
     * Hides the turn msg view.
     */
    private void hideTurnMsg() {
        mTvTurnMsg.setVisibility(View.INVISIBLE);
    }

    /**
     * Shows the turn msg view, which shows whose turn is to play currently.
     */
    private void showTurnMsg() {
        mTvTurnMsg.setVisibility(View.VISIBLE);
    }

    /**
     * Resets the game
     */
    private void reset() {
        clearBoard();//clearing the board
        hideWinnerMessage();//hiding the winner msg
        setTurnMsg();//setting the turn msg
        showTurnMsg();//showing the turn msg
        mMarker = "X";//setting the marker to "cross"
        mFoundWinner = false;//indicates no winner
        FIRST_MOVE = false;//indication that the first move is not done
        initBoard();//intializing the board
    }

    /**
     * Call back method to handle screen orientation change
     *
     * @param savedInstanceState bundle data to set the activity state from the onSaveInstanceState() bundle
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //restoring the data and text color of view
        mTv00.setText(savedInstanceState.getString("00"));
        mTv00.setTextColor(savedInstanceState.getInt("00_text_color"));

        mTv01.setText(savedInstanceState.getString("01"));
        mTv01.setTextColor(savedInstanceState.getInt("01_text_color"));

        mTv02.setText(savedInstanceState.getString("02"));
        mTv02.setTextColor(savedInstanceState.getInt("02_text_color"));

        mTv10.setText(savedInstanceState.getString("10"));
        mTv10.setTextColor(savedInstanceState.getInt("10_text_color"));

        mTv11.setText(savedInstanceState.getString("11"));
        mTv11.setTextColor(savedInstanceState.getInt("11_text_color"));

        mTv12.setText(savedInstanceState.getString("12"));
        mTv12.setTextColor(savedInstanceState.getInt("12_text_color"));

        mTv20.setText(savedInstanceState.getString("20"));
        mTv20.setTextColor(savedInstanceState.getInt("20_text_color"));

        mTv21.setText(savedInstanceState.getString("21"));
        mTv21.setTextColor(savedInstanceState.getInt("21_text_color"));

        mTv22.setText(savedInstanceState.getString("22"));
        mTv22.setTextColor(savedInstanceState.getInt("22_text_color"));

        //restoring the visibility state of turn message and winner message view
        mTvMsg.setVisibility(savedInstanceState.getInt("msg_visibility"));
        mTvTurnMsg.setVisibility(savedInstanceState.getInt("turn_msg_visibility"));
        mTvTurnMsg.setText(savedInstanceState.getString("turn_msg"));
        mMarker = savedInstanceState.getString("marker");
        mTvMsg.setText(savedInstanceState.getString("msg"));
    }

    /**
     * Call back method to save the state of the the activity
     *
     * @param outState bundle data to help preserve activity state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //saving the data and text color of view
        outState.putString("00", mTv00.getText().toString());
        outState.putInt("00_text_color", mTv00.getCurrentTextColor());

        outState.putString("01", mTv01.getText().toString());
        outState.putInt("01_text_color", mTv01.getCurrentTextColor());

        outState.putString("02", mTv02.getText().toString());
        outState.putInt("02_text_color", mTv02.getCurrentTextColor());

        outState.putString("10", mTv10.getText().toString());
        outState.putInt("10_text_color", mTv10.getCurrentTextColor());

        outState.putString("11", mTv11.getText().toString());
        outState.putInt("11_text_color", mTv11.getCurrentTextColor());

        outState.putString("12", mTv12.getText().toString());
        outState.putInt("12_text_color", mTv12.getCurrentTextColor());

        outState.putString("20", mTv20.getText().toString());
        outState.putInt("20_text_color", mTv20.getCurrentTextColor());

        outState.putString("21", mTv21.getText().toString());
        outState.putInt("21_text_color", mTv21.getCurrentTextColor());

        outState.putString("22", mTv22.getText().toString());
        outState.putInt("22_text_color", mTv22.getCurrentTextColor());

        //saving the value of turn message
        outState.putString("turn_msg", mTvTurnMsg.getText().toString());
        //saving the marker value
        outState.putString("marker", mMarker);
        //saving the visibility of turn message and winner message view
        outState.putInt("turn_msg_visibility", mTvTurnMsg.getVisibility());
        outState.putInt("msg_visibility", mTvMsg.getVisibility());
        //saving winner message
        outState.putString("msg", TextUtils.htmlEncode(mTvMsg.getText().toString()));
    }
}
