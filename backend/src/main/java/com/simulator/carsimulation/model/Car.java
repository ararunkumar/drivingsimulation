package com.simulator.carsimulation.model;

import java.util.ArrayList;
import java.util.List;

public class Car {
    public String name;
    public int initialX;
    public int initialY;
    public Orientation initialOrientation;
    public String commands;

    public int currentX;
    public int currentY;
    public Orientation currentOrientation;
    public boolean collided = false;
    public int collisionStep = -1;
    public List<String> collidedWith = new ArrayList<>();
}
