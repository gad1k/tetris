package com.javarush.games.tetris;

import com.javarush.engine.cell.*;

public class Tower {
    private int width;
    private int height;

    private Block[][] blocks;

    public Tower(int width, int height) {
        this.width = width;
        this.height = height;
        this.blocks = new Block[height][width];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                this.blocks[y][x] = new Block(Color.ALICEBLUE);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Color getBlockColor(int x, int y) {
        return this.blocks[x][y].getColor();
    }

    private int checkRow(Block[] row) {
        int count = 0;
        for (int i = 0; i < row.length; i++)
            if (row[i].getColor() != Color.ALICEBLUE)
                count++;
        return count;
    }

    private void moveRow(int baseRow) {
        for (int i = 0; i < blocks[baseRow].length; i++)
            blocks[baseRow][i].setColor(blocks[baseRow - 1][i].getColor());
    }

    public int removeRow() {
        int score = 0;
        for (int i = blocks.length - 1; i >= 0; i--) {
            if (checkRow(blocks[i]) == 0)
                return score;
            if (checkRow(blocks[i]) == width) {
                score++;
                for (int j = i; j > 0; j--) {
                    moveRow(j);
                    if (checkRow(blocks[j - 1]) == 0)
                        break;
                }
            }
        }
        return score;
    }

    public boolean addNewBlocks(Tetramino tetramino) {
        if (tetramino.isAlive)
            return false;
        for (int j = 0; j < tetramino.height; j++)
            for (int i = 0; i < tetramino.width; i++)
                if (tetramino.matrix[j][i] != 0)
                    blocks[tetramino.y + j][tetramino.x + i].setColor(Color.values()[tetramino.matrix[j][i]]);
        return true;
    }

    public void draw(Game game) {
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                game.setCellColor(x, y, blocks[y][x].getColor());
    }
}