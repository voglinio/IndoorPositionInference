package com.nodalpoint.indoorposition.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Checkpoint {

    private String name;
    private int id;
    private List<Checkpoint> neighbours;
    private float longitude;
    private float latitude;
    private int mapX;
    private int mapY;

    public Checkpoint(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public static Map<Integer,Checkpoint> generatePoints() {
        Map<Integer,Checkpoint> checkpoints = new HashMap<>();
        checkpoints.put(0, new Checkpoint("Start", 0));
        checkpoints.put(1, new Checkpoint("1", 1));
        checkpoints.put(2, new Checkpoint("2", 2));
        checkpoints.put(3, new Checkpoint("3", 3));
        checkpoints.put(4, new Checkpoint("4", 4));
        checkpoints.put(5, new Checkpoint("5", 5));
        checkpoints.put(6, new Checkpoint("6", 6));
        checkpoints.put(7, new Checkpoint("7", 7));
        checkpoints.put(8, new Checkpoint("8", 8));
        checkpoints.put(9, new Checkpoint("9", 9));
        checkpoints.put(10, new Checkpoint("10", 10));
        return checkpoints;
    }
}
