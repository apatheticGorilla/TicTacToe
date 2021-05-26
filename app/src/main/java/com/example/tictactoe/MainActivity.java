package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

/*
 ***IMPORTANT!***
 * Requires the custom assets I made which can be found here: https://drive.google.com/file/d/1h1SAMj0VJ6csJU5ypGxiWOV3or9sY_T7/view?usp=sharing
 
 */
public class MainActivity extends AppCompatActivity {
	ImageView grid;
	ImageView b1, b2, b3, b4, b5, b6, b7, b8, b9;
	Button clear;
	Button quit;
	TextView textview;
	TextView playerOneScore;
	TextView playerTwoScore;
	int[] board = new int[9];
	int blanks = 9;
	
	//an index of button ids in order to eliminate lengthy switch statements
	ArrayList<Integer> buttonIndex = new ArrayList<>();
	ArrayList<String> winConditions = new ArrayList<>();
	
	int[] gamePieces = new int[3];
	int player = 1;
	int p1Score = 0, p2Score = 0;
	boolean gameOver = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		for (int i = 0; i < 9; i++) board[i] = 0;
		
		b1 = findViewById(R.id.b1);
		b2 = findViewById(R.id.b2);
		b3 = findViewById(R.id.b3);
		b4 = findViewById(R.id.b4);
		b5 = findViewById(R.id.b5);
		b6 = findViewById(R.id.b6);
		b7 = findViewById(R.id.b7);
		b8 = findViewById(R.id.b8);
		b9 = findViewById(R.id.b9);
		
		clear = findViewById(R.id.clear);
		quit = findViewById(R.id.quit);
		grid = findViewById(R.id.grid);
		textview = findViewById(R.id.textView);
		playerOneScore = findViewById(R.id.playerOneScore);
		playerTwoScore = findViewById(R.id.playerTwoScore);
		
		buttonIndex.addAll(Arrays.asList(R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8, R.id.b9));
		winConditions.addAll(Arrays.asList("012", "345", "678", "036", "147", "258", "048", "246"));
		
		//set light or dark theme based on System setting
		Configuration config = getResources().getConfiguration();
		int currentNightMode = config.uiMode & Configuration.UI_MODE_NIGHT_MASK;
		gamePieces[0] = R.drawable.blankgamepiece;
		if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
			gamePieces[1] = R.drawable.xgamepiece_dark;
			gamePieces[2] = R.drawable.ogamepiece_dark;
			grid.setImageResource(R.drawable.board_dark);
		} else {
			gamePieces[1] = R.drawable.xgamepiece_light;
			gamePieces[2] = R.drawable.ogamepiece_light;
			grid.setImageResource(R.drawable.board);
		}
		drawBoard();
		Log.i("info", "app started");
	}
	
	@SuppressLint("SetTextI18n")
	public void placePiece(View view) {
		int index = buttonIndex.indexOf(view.getId());
		if (board[index] == 0 && !gameOver) {
			board[index] = player;
			blanks--;
			if (player == 1) player = 2;
			else player = 1;
			drawBoard();
			int winner = checkWinCondition();
			
			//somebody has won or board is full
			if (winner != 0 || blanks == 0) {
				if (winner == 0) textview.setText("Its a Tie.");
				else {
					textview.setText("Player " + winner + " Wins!");
					if (winner == 1) {
						p1Score++;
						playerOneScore.setText(String.valueOf(p1Score));
					} else {
						p2Score++;
						playerTwoScore.setText(String.valueOf(p2Score));
					}
				}
				gameOver = true;
			} else textview.setText("Player " + player + "'s turn");
			
		}
	}
	
	private void drawBoard() {
		for (int i = 0; i < 9; i++) {
			ImageView imageView = findViewById(buttonIndex.get(i));
			imageView.setImageResource(gamePieces[board[i]]);
		}
	}
	
	private int checkWinCondition() {
		for (int i = 0; i < winConditions.size(); i++) {
			StringBuilder row = new StringBuilder();
			for (int j = 0; j < 3; j++) {
				String string = winConditions.get(i).substring(j, j + 1);
				int index = Integer.parseInt(string);
				row.append(board[index]);
			}
			if (row.toString().equals("111")) return 1;
			if (row.toString().equals("222")) return 2;
		}
		return 0;
	}
	
	@SuppressLint("SetTextI18n")
	public void clear(View view) {
		for (int i = 0; i < 9; i++) board[i] = 0;
		player = 1;
		drawBoard();
		gameOver = false;
		blanks = 9;
		textview.setText("Player 1's turn");
	}
	
	public void quit(View view) {
		finishAndRemoveTask();
	}
	
}