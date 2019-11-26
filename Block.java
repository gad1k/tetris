package com.javarush.games.tetris;

import com.javarush.engine.cell.*;

public class Block {
    private Color color;

    public Block(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}