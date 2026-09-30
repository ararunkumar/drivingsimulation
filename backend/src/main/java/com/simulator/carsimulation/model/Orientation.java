package com.simulator.carsimulation.model;

public enum Orientation {
    N(0, 1), E(1, 0), S(0, -1), W(-1, 0);

    public final int deltaX;
    public final int deltaY;

    Orientation(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public Orientation turnLeft() {
        return values()[(this.ordinal() + 3) % 4];
    }

    public Orientation turnRight() {
        return values()[(this.ordinal() + 1) % 4];
    }
}
