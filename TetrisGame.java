package com.javarush.games.tetris;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TetrisGame extends Game {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 22;
    private static final int GOAL_COUNT = 30;

    private int score;
    private boolean isGameStopped;

    private List<int[][]> tetraminoTypeList;
    private Tower tower;
    private Tetramino tetramino;

    private void createGame() {
        setTurnTimer(30);
        score = 0;
        isGameStopped = false;
        tetraminoTypeList = new ArrayList<int[][]>(Arrays.asList(
                ShapeMatrix.I,
                ShapeMatrix.J,
                ShapeMatrix.L,
                ShapeMatrix.O,
                ShapeMatrix.S,
                ShapeMatrix.T,
                ShapeMatrix.Z
        ));
        tower = new Tower(WIDTH, HEIGHT);
        createNewTetramino();
        drawScene();
    }

    private void drawScene() {
        tower.draw(this);
        tetramino.draw(this);
    }

    private void createNewTetramino() {
        int index = getRandomNumber(tetraminoTypeList.size());
        int[][] matrix = tetraminoTypeList.get(index);
        int x = getRandomNumber(WIDTH - matrix[0].length + 1);
        int y = -1 * matrix.length;
        tetramino = new Tetramino(x, y, matrix);
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.LIGHTPINK, "Congratulation, You are win!", Color.BLACK, 24);
    }

    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.LIGHTPINK, "Game is over!", Color.BLACK, 24);
    }

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void onTurn(int step) {
        tetramino.move(tower);
        if (tetramino.checkDeadStep())
            gameOver();
        else if (tower.addNewBlocks(tetramino))
            createNewTetramino();
        score += tower.removeRow();
        setScore(score);
        if (score == GOAL_COUNT)
            win();
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case SPACE:
                if (isGameStopped)
                    createGame();
                if (!isGameStopped && tetramino.checkRotate(tower))
                    tetramino.rotate();
                break;
            case LEFT:
                tetramino.setDirection(Direction.LEFT);
                break;
            case RIGHT:
                tetramino.setDirection(Direction.RIGHT);
                break;
            case DOWN:
                tetramino.setTurnTimerDelay(1);
                break;
        }
    }

    @Override
    public void onKeyReleased(Key key) {
        switch (key) {
            case DOWN:
                tetramino.setTurnTimerDelay(10);
        }
    }
}