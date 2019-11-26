package com.javarush.games.tetris;

import com.javarush.engine.cell.*;

public class Tetramino {
    public int x;
    public int y;
    public int width;
    public int height;
    public int[][] matrix;

    private int stepDelay;
    private int turnTimerDelay;
    private Direction direction;

    public boolean isAlive;

    public Tetramino(int x, int y, int[][] matrix) {
        this.x = x;
        this.y = y;
        this.width = matrix[0].length;
        this.height = matrix.length;
        this.matrix = matrix;
        this.turnTimerDelay = 10;
        this.direction = Direction.NONE;
        this.isAlive = true;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setTurnTimerDelay(int turnTimerDelay) {
        this.turnTimerDelay = turnTimerDelay;
    }

    private int getStepDelay() {
        if (++stepDelay >= turnTimerDelay) {
            stepDelay = 0;
            return 1;
        } else
            return 0;
    }

    private void checkBorders(int width, int height) {
        if (x < 0)
            x = 0;
        if (x + this.width > width)
            x = width - this.width;
        if (y + this.height > height) {
            y = height - this.height;
            isAlive = false;
        }
    }

    private boolean isCollision(Tower tower) {
        if (!isAlive)
            return false;
        for (int j = 0; j < height; j++) {
            if (y + j >= 0)
                for (int i = 0; i < width; i++)
                    if (matrix[j][i] != 0 && tower.getBlockColor(y + j, x + i) != Color.ALICEBLUE)
                        return true;
        }
        return false;
    }

    public void draw(Game game) {
        for (int j = 0; j < height; j++)
            for (int i = 0; i < width; i++)
                if (y + j >= 0)
                    game.setCellColor(x + i, y + j, Color.values()[matrix[j][i]]);
    }

    public void move(Tower tower) {
        switch (direction) {
            case LEFT:
                x--;
                checkBorders(tower.getWidth(), tower.getHeight());
                if (isCollision(tower))
                    x++;
                break;
            case RIGHT:
                x++;
                checkBorders(tower.getWidth(), tower.getHeight());
                if (isCollision(tower))
                    x--;
                break;
        }
        direction = Direction.NONE;
        y += getStepDelay();
        checkBorders(tower.getWidth(), tower.getHeight());
        if (isCollision(tower)) {
            y--;
            isAlive = false;
        }
    }

    public void rotate() {
        int width = this.matrix.length;
        int height = this.matrix[0].length;
        int[][] matrix = new int[height][width];
        for (int x = 0; x < this.height; x++)
            for (int y = 0; y < this.width; y++)
                matrix[y][width - (x + 1)] = this.matrix[x][y];
        this.width = width;
        this.height = height;
        this.matrix = matrix;
    }

    public boolean checkRotate(Tower tower) {
        if (y < 0)
            return false;
        Tetramino testTetramino = new Tetramino(x, y, matrix);
        testTetramino.rotate();
        testTetramino.checkBorders(tower.getWidth(), tower.getHeight());
        return !testTetramino.isCollision(tower);
    }

    public boolean checkDeadStep() {
        return !isAlive && y <= 0;
    }
}