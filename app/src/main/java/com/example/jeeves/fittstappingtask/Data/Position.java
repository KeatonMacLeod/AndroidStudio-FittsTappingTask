package com.example.jeeves.fittstappingtask.Data;

public class Position {
    private float x;
    private float y;

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y + 63;
    }

    public void setY(int y) {
        this.y = y;
    }
}
