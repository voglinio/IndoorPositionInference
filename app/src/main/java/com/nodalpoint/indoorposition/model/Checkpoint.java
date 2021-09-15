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

    public Checkpoint(String name, int id, List<Checkpoint> neighbours) {
        this.name = name;
        this.id = id;
        this.neighbours = neighbours;
    }

    public Checkpoint(String name, int id, float longitude, float latitude) {
        this.name = name;
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static Map<Integer,Checkpoint> generatePoints() {
        Map<Integer,Checkpoint> checkpoints = new HashMap<>();
        checkpoints.put(1, new Checkpoint("1", 1, new ArrayList<Checkpoint>(){{add(new Checkpoint("20", 20)); add(new Checkpoint("2", 2));}}));
        checkpoints.put(2, new Checkpoint("2", 2, new ArrayList<Checkpoint>(){{add(new Checkpoint("1", 1)); add(new Checkpoint("3", 3));}}));
        checkpoints.put(3, new Checkpoint("3", 3, new ArrayList<Checkpoint>(){{add(new Checkpoint("2", 2)); add(new Checkpoint("4", 4));}}));
        checkpoints.put(4, new Checkpoint("4", 4, new ArrayList<Checkpoint>(){{add(new Checkpoint("3", 3)); add(new Checkpoint("5", 5));}}));
        checkpoints.put(5, new Checkpoint("5", 5, new ArrayList<Checkpoint>(){{add(new Checkpoint("4", 4)); add(new Checkpoint("6", 6));}}));
        checkpoints.put(6, new Checkpoint("6", 6, new ArrayList<Checkpoint>(){{add(new Checkpoint("5", 5)); add(new Checkpoint("7", 7));}}));
        checkpoints.put(7, new Checkpoint("7", 7, new ArrayList<Checkpoint>(){{add(new Checkpoint("6", 6)); add(new Checkpoint("8", 8));}}));
        checkpoints.put(8, new Checkpoint("8", 8, new ArrayList<Checkpoint>(){{add(new Checkpoint("7", 7)); add(new Checkpoint("9", 9));}}));
        checkpoints.put(9, new Checkpoint("9", 9, new ArrayList<Checkpoint>(){{add(new Checkpoint("8", 8)); add(new Checkpoint("10", 10)); add(new Checkpoint("15", 15));}}));
        checkpoints.put(10, new Checkpoint("10", 10, new ArrayList<Checkpoint>(){{add(new Checkpoint("9", 9)); add(new Checkpoint("11", 11));}}));
        checkpoints.put(11, new Checkpoint("11", 11, new ArrayList<Checkpoint>(){{add(new Checkpoint("10", 10)); add(new Checkpoint("12", 12)); add(new Checkpoint("17", 17));}}));
        checkpoints.put(12, new Checkpoint("12", 12, new ArrayList<Checkpoint>(){{add(new Checkpoint("11", 11)); add(new Checkpoint("13", 13)); add(new Checkpoint("17", 17));}}));
        checkpoints.put(13, new Checkpoint("13", 13, new ArrayList<Checkpoint>(){{add(new Checkpoint("12", 12)); add(new Checkpoint("14", 14));}}));
        checkpoints.put(14, new Checkpoint("14", 14, new ArrayList<Checkpoint>(){{add(new Checkpoint("13", 13));}}));
        checkpoints.put(15, new Checkpoint("15", 15, new ArrayList<Checkpoint>(){{add(new Checkpoint("9", 9)); add(new Checkpoint("16", 16));}}));
        checkpoints.put(16, new Checkpoint("16", 16, new ArrayList<Checkpoint>(){{add(new Checkpoint("21", 21)); add(new Checkpoint("15", 15));}}));
        checkpoints.put(17, new Checkpoint("17", 17, new ArrayList<Checkpoint>(){{add(new Checkpoint("11", 11)); add(new Checkpoint("12", 12)); add(new Checkpoint("18", 18));}}));
        checkpoints.put(18, new Checkpoint("18", 18, new ArrayList<Checkpoint>(){{add(new Checkpoint("17", 17)); add(new Checkpoint("19", 19));}}));
        checkpoints.put(19, new Checkpoint("19", 19, new ArrayList<Checkpoint>(){{add(new Checkpoint("18", 18));}}));
        checkpoints.put(20, new Checkpoint("20", 20, new ArrayList<Checkpoint>(){{add(new Checkpoint("1", 1));}}));

        checkpoints.put(26, new Checkpoint("26", 26, new ArrayList<Checkpoint>(){{add(new Checkpoint("25", 25)); add(new Checkpoint("27", 27));}}));
        checkpoints.put(27, new Checkpoint("27", 27, new ArrayList<Checkpoint>(){{add(new Checkpoint("26", 26)); add(new Checkpoint("28", 28)); add(new Checkpoint("29", 29));}}));
        checkpoints.put(28, new Checkpoint("28", 28, new ArrayList<Checkpoint>(){{add(new Checkpoint("27", 27)); add(new Checkpoint("29", 29)); add(new Checkpoint("30", 30)); add(new Checkpoint("33", 33));}}));
        checkpoints.put(29, new Checkpoint("29", 29, new ArrayList<Checkpoint>(){{add(new Checkpoint("27", 27)); add(new Checkpoint("28", 28)); add(new Checkpoint("30", 30));}}));
        checkpoints.put(30, new Checkpoint("30", 30, new ArrayList<Checkpoint>(){{add(new Checkpoint("28", 28)); add(new Checkpoint("29", 29)); add(new Checkpoint("31", 31));}}));
        checkpoints.put(31, new Checkpoint("31", 31, new ArrayList<Checkpoint>(){{add(new Checkpoint("30", 30)); add(new Checkpoint("32", 32));}}));
        checkpoints.put(32, new Checkpoint("32", 32, new ArrayList<Checkpoint>(){{add(new Checkpoint("31", 31));}}));

        checkpoints.put(33, new Checkpoint("33", 33, new ArrayList<Checkpoint>(){{add(new Checkpoint("28", 28)); add(new Checkpoint("34", 34)); add(new Checkpoint("35", 35));}}));
        checkpoints.put(34, new Checkpoint("34", 34, new ArrayList<Checkpoint>(){{add(new Checkpoint("33", 33));}}));
        checkpoints.put(35, new Checkpoint("35", 35, new ArrayList<Checkpoint>(){{add(new Checkpoint("33", 33)); add(new Checkpoint("36", 36));}}));
        checkpoints.put(36, new Checkpoint("36", 36, new ArrayList<Checkpoint>(){{add(new Checkpoint("37", 37)); add(new Checkpoint("35", 35));}}));
        checkpoints.put(37, new Checkpoint("37", 37, new ArrayList<Checkpoint>(){{add(new Checkpoint("38", 38)); add(new Checkpoint("36", 36));}}));
        checkpoints.put(38, new Checkpoint("38", 38, new ArrayList<Checkpoint>(){{add(new Checkpoint("37", 37)); add(new Checkpoint("39", 39));}}));
        checkpoints.put(39, new Checkpoint("39", 39, new ArrayList<Checkpoint>(){{add(new Checkpoint("38", 38)); add(new Checkpoint("40", 40));}}));
        checkpoints.put(40, new Checkpoint("40", 40, new ArrayList<Checkpoint>(){{add(new Checkpoint("39", 39)); add(new Checkpoint("41", 41));}}));
        checkpoints.put(41, new Checkpoint("41", 41, new ArrayList<Checkpoint>(){{add(new Checkpoint("40", 40)); add(new Checkpoint("42", 42));}}));
        checkpoints.put(42, new Checkpoint("42", 42, new ArrayList<Checkpoint>(){{add(new Checkpoint("41", 41));}}));



//        checkpoints.put(7, new Checkpoint("7", 7));
//        checkpoints.put(8, new Checkpoint("8", 8));
//        checkpoints.put(9, new Checkpoint("9", 9));
//        checkpoints.put(10, new Checkpoint("10", 10));
        return checkpoints;
    }
}
