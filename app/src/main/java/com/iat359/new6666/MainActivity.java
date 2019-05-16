package com.iat359.new6666;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iat359.new6666.view.GameCompleteDialogFragment;
import com.iat359.new6666.view.GameOverDialogFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GameOverDialogFragment.GameOverDialogListener, GameCompleteDialogFragment.GameCompleteDialogListener {
    private TextView r0n0, r0n1, r0n2, r0n3;
    private TextView r1n0, r1n1, r1n2, r1n3;
    private TextView r2n0, r2n1, r2n2, r2n3;
    private TextView r3n0, r3n1, r3n2, r3n3;

    private TextView r4n0, r4n1, r4n2, r4n3;

    private TextView[] textViewArray;

    private TextView[] invisibleTextViewArray;

    private int[] grid[];

    private Button newGameButton;

    private TableLayout gameGrid;

    private int score = 0;

    private TextView scoreTextView;

    private TextView bestScoreTextView;

    private boolean gameOver = false;

    private boolean gameComplete = false;


    private int addNumberSate = 0;

    // default value for best score in SharedPreference
    private final static int NOT_EXIST = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        r0n0 = findViewById(R.id.r0n0);
        r0n1 = findViewById(R.id.r0n1);
        r0n2 = findViewById(R.id.r0n2);
        r0n3 = findViewById(R.id.r0n3);

        r1n0 = findViewById(R.id.r1n0);
        r1n1 = findViewById(R.id.r1n1);
        r1n2 = findViewById(R.id.r1n2);
        r1n3 = findViewById(R.id.r1n3);

        r2n0 = findViewById(R.id.r2n0);
        r2n1 = findViewById(R.id.r2n1);
        r2n2 = findViewById(R.id.r2n2);
        r2n3 = findViewById(R.id.r2n3);

        r3n0 = findViewById(R.id.r3n0);
        r3n1 = findViewById(R.id.r3n1);
        r3n2 = findViewById(R.id.r3n2);
        r3n3 = findViewById(R.id.r3n3);

        r4n0 = findViewById(R.id.r4n0);
        r4n1 = findViewById(R.id.r4n1);
        r4n2 = findViewById(R.id.r4n2);
        r4n3 = findViewById(R.id.r4n3);

        textViewArray = new TextView[]{r0n0, r0n1, r0n2, r0n3, r1n0, r1n1, r1n2, r1n3, r2n0, r2n1, r2n2, r2n3, r3n0, r3n1, r3n2, r3n3};

        invisibleTextViewArray = new TextView[]{r4n0, r4n1, r4n2, r4n3};

        grid = new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};

        addNumber();
        addNumber();

        scoreTextView = findViewById(R.id.score_textView);

        // retrieve the best score
        bestScoreTextView = findViewById(R.id.best_score_textView);
        getBestScore();

        newGameButton = findViewById(R.id.button);

        // swipe listener
        gameGrid = findViewById(R.id.game_grid);

        gameGrid.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                gameOverDialog();
                gameCompleteDialog();
                getBestScore();

                cleanTextViewBeforeChanges();

                for (int i = 0; i < 4; i++) {
                    grid[i] = slideLeft(grid[i]);
                    grid[i] = combineLeft(grid[i]);
                    for (int j = 0; j < 4; j++) {
                        if (grid[i][j] != 0) {
                            String value = Integer.toString(grid[i][j]);
                            setTextViewAfterChanges(i, j, value);
                        }
                    }
                }

                setInvisibleTextViewArray();
                addNumber();
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                gameOverDialog();
                gameCompleteDialog();
                getBestScore();

                cleanTextViewBeforeChanges();

                for (int i = 0; i < 4; i++) {
                    grid[i] = slide(grid[i]);
                    grid[i] = combine(grid[i]);
                    for (int j = 0; j < 4; j++) {
                        if (grid[i][j] != 0) {
                            String value = Integer.toString(grid[i][j]);
                            setTextViewAfterChanges(i, j, value);
                        }
                    }
                }

                setInvisibleTextViewArray();
                addNumber();
            }

            // Insane operation, rotate the grid, and then rotate back
            @Override
            public void onSwipeTop() {
                super.onSwipeTop();
                gameOverDialog();
                gameCompleteDialog();
                getBestScore();

                cleanTextViewBeforeChanges();

                grid = rotateGrid(grid);

                for (int i = 0; i < 4; i++) {
                    grid[i] = slideLeft(grid[i]);
                    grid[i] = combineLeft(grid[i]);
                }

                grid = rotateGrid(grid);

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (grid[i][j] != 0) {
                            String value = Integer.toString(grid[i][j]);
                            setTextViewAfterChanges(i, j, value);
                        }
                    }
                }

                setInvisibleTextViewArray();
                addNumber();
            }

            // Insane operation, rotate the grid, and then rotate back
            @Override
            public void onSwipeBottom() {
                super.onSwipeBottom();
                gameOverDialog();
                gameCompleteDialog();
                getBestScore();

                cleanTextViewBeforeChanges();

                grid = rotateGrid(grid);

                for (int i = 0; i < 4; i++) {
                    grid[i] = slide(grid[i]);
                    grid[i] = combine(grid[i]);
                }

                grid = rotateGrid(grid);

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (grid[i][j] != 0) {
                            String value = Integer.toString(grid[i][j]);
                            setTextViewAfterChanges(i, j, value);
                        }
                    }
                }

                setInvisibleTextViewArray();
                addNumber();
            }

        });

        // restart button
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshGame();
            }
        });

    }

    private void addNumber() {
        ArrayList<Point> options = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j] == 0) {
                    options.add(new Point(i, j));
                }
            }
        }


        if (options.size() > 0) {
            int spotIndex = (int) (Math.random() * (options.size() - 1));

            double r = Math.random();

            Point spot = options.get(spotIndex);
            grid[spot.x][spot.y] = r < 0.8 ? 6 : 12;

            String value = Integer.toString(grid[spot.x][spot.y]);

            setTextViewAfterChanges(spot.x, spot.y, value);
        }
    }

    private int[] slide(int[] row) {
        int[] arr = new int[4];
        int index = 3;
        for (int i = 3; i >= 0; i--) {
            if (row[i] != 0) {
                arr[index] = row[i];
                index--;
            }
        }

        return arr;
    }

    private int[] slideLeft(int[] row) {
        int[] arr = new int[4];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            if (row[i] != 0) {
                arr[index] = row[i];
                index++;
            }
        }

        return arr;
    }

    private int[] combine(int[] row) {
        for (int i = 3; i >= 1; i--) {
            int a = row[i];
            int b = row[i - 1];
            if (a == b) {
                row[i] = a + b;
                score += row[i];
                setScore();
                row[i - 1] = 0;
            }
        }
        return row;
    }

    private int[] combineLeft(int[] row) {
        for (int i = 1; i <= 3; i++) {
            int a = row[i];
            int b = row[i - 1];
            if (a == b) {
                row[i - 1] = a + b;
                score += row[i - 1];
                setScore();
                row[i] = 0;
            }
        }
        return row;
    }

    private int[][] rotateGrid(int grid[][]) {
        int[][] newGrid = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newGrid[i][j] = grid[j][i];
            }
        }
        return newGrid;
    }

    private void cleanTextViewBeforeChanges() {
        for (int i = 0; i < textViewArray.length; i++) {
            if (textViewArray[i].getText() != null) {
                textViewArray[i].setText("");
            }
        }
    }

    private void setTextViewAfterChanges(int i, int j, String value) {
        if (i == 0) {
            if (j == 0) {
                r0n0.setText(value);
            }
            if (j == 1) {
                r0n1.setText(value);
            }
            if (j == 2) {
                r0n2.setText(value);
            }
            if (j == 3) {
                r0n3.setText(value);
            }
        }

        if (i == 1) {
            if (j == 0) {
                r1n0.setText(value);
            }
            if (j == 1) {
                r1n1.setText(value);
            }
            if (j == 2) {
                r1n2.setText(value);
            }
            if (j == 3) {
                r1n3.setText(value);
            }
        }

        if (i == 2) {
            if (j == 0) {
                r2n0.setText(value);
            }
            if (j == 1) {
                r2n1.setText(value);
            }
            if (j == 2) {
                r2n2.setText(value);
            }
            if (j == 3) {
                r2n3.setText(value);
            }
        }

        if (i == 3) {
            if (j == 0) {
                r3n0.setText(value);
            }
            if (j == 1) {
                r3n1.setText(value);
            }
            if (j == 2) {
                r3n2.setText(value);
            }
            if (j == 3) {
                r3n3.setText(value);
            }
        }
    }

    private void setScore() {
        scoreTextView.setText(Integer.toString(score));
    }

    private boolean checkGameOver() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // check every cell
                if (grid[i][j] == 0) {
                    return false;
                }
                // check each column
                if (i != 3 && grid[i][j] == grid[i + 1][j]) {
                    return false;
                }
                // check each row
                if (j != 3 && grid[i][j] == grid[i][j + 1]) {
                    return false;
                }
            }
        }
        return true;
    }

    // save the best score
    // open the gameOverDialog box
    private void gameOverDialog() {
        gameOver = checkGameOver();
        if (gameOver) {
            getBestScore();
            GameOverDialogFragment gameOverDialogFragment = new GameOverDialogFragment();
            gameOverDialogFragment.show(getSupportFragmentManager(), "Game Over");
            gameOverDialogFragment.setCancelable(false);
        }
    }

    // check any tile is equal to 666
    private boolean checkGameComplete() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j] >= 666) {
                    return true;
                }
            }
        }
        return false;
    }

    // save the best score
    // show the gameComplete box
    private void gameCompleteDialog() {
        gameComplete = checkGameComplete();
        if (gameComplete) {
            getBestScore();
            GameCompleteDialogFragment gameCompleteDialogFragment = new GameCompleteDialogFragment();
            gameCompleteDialogFragment.show(getSupportFragmentManager(), "Game Complete");
            gameCompleteDialogFragment.setCancelable(false);

        }
    }

    @Override
    public void onFragmentInteraction(boolean result) {
        gameOver = result;
        refreshGame();
    }

    @Override
    public void onCompleteFragmentInteraction(boolean result) {
        gameComplete = result;
    }

    private void refreshGame() {
        for (int i = 0; i < textViewArray.length; i++) {
            textViewArray[i].setText("");
        }

        for (int i = 0; i < invisibleTextViewArray.length; i++) {
            invisibleTextViewArray[i].setText("00");
            invisibleTextViewArray[i].setVisibility(View.INVISIBLE);
        }

        score = 0;
        setScore();

        grid = new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        addNumber();
        addNumber();
    }

    private void getBestScore() {
        SharedPreferences prefs = this.getSharedPreferences("USER INFO", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        int previousBestScore = prefs.getInt("best_score_key", NOT_EXIST);

        // clean the old score
        if (previousBestScore < score) {
            bestScoreTextView.setText(Integer.toString(score));
            editor.clear().commit();
            editor.putInt("best_score_key", score);
            editor.apply();
        } else {
            bestScoreTextView.setText(Integer.toString(previousBestScore));
        }
    }

    // crazy idea
    // loop through the grid
    // get the biggest number
    // then set the correspond digits to the invisible text
    private void setInvisibleTextViewArray() {
        int biggestValue = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int currentValue = grid[i][j];
                if (currentValue > biggestValue) {
                    biggestValue = currentValue;
                }
            }
        }

        for (int i = 0; i < invisibleTextViewArray.length; i++) {
            invisibleTextViewArray[i].setText(Integer.toString(biggestValue));
            invisibleTextViewArray[i].setVisibility(View.INVISIBLE);
        }
    }
}
