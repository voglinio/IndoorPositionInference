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
        checkpoints.put(20, new Checkpoint("20", 20, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("1", 1));
            }}));
        checkpoints.put(1, new Checkpoint("1", 1, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("2", 2));
                add(new Checkpoint("20", 20));
            }}));
        checkpoints.put(2, new Checkpoint("2", 2, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("1", 1));
                add(new Checkpoint("3", 3));
            }}));
        checkpoints.put(3, new Checkpoint("3", 3, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("2", 2));
                add(new Checkpoint("4", 4));
            }}));
        checkpoints.put(4, new Checkpoint("4", 4, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("3", 3));
                add(new Checkpoint("5", 5));
            }}));
        checkpoints.put(5, new Checkpoint("5", 5, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("4", 4));
                add(new Checkpoint("6", 6));
            }}));
        checkpoints.put(6, new Checkpoint("6", 6, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("5", 5));
                add(new Checkpoint("7", 7));
            }}));
        checkpoints.put(7, new Checkpoint("7", 7, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("6", 6));
                add(new Checkpoint("8", 8));
            }}));
        checkpoints.put(8, new Checkpoint("8", 8, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("7", 7));
                add(new Checkpoint("9", 9));
            }}));
        checkpoints.put(9, new Checkpoint("9", 9, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("8", 8));
                add(new Checkpoint("10", 10));
                add(new Checkpoint("15", 15));
            }}));
        checkpoints.put(10, new Checkpoint("10", 10, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("9", 9));
                add(new Checkpoint("11", 11));
            }}));
        checkpoints.put(11, new Checkpoint("11", 11, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("10", 10));
                add(new Checkpoint("12", 12));
            }}));
        checkpoints.put(12, new Checkpoint("12", 12, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("11", 11));
                add(new Checkpoint("13", 13));
            }}));
        checkpoints.put(13, new Checkpoint("13", 13, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("12", 12));
                add(new Checkpoint("14", 14));
            }}));
        checkpoints.put(14, new Checkpoint("14", 14, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("13", 13));
            }}));
        checkpoints.put(15, new Checkpoint("15", 15, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("9", 9));
                add(new Checkpoint("16", 16));
            }}));
        checkpoints.put(16, new Checkpoint("16", 16, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("15", 15));
                add(new Checkpoint("21", 21));
            }}));
        checkpoints.put(17, new Checkpoint("17", 17, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("12", 12));
                add(new Checkpoint("18", 18));
            }}));
        checkpoints.put(18, new Checkpoint("18", 18, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("17", 17));
                add(new Checkpoint("19", 19));
            }}));
        checkpoints.put(19, new Checkpoint("19", 19, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("18", 18));
            }}));
        checkpoints.put(21, new Checkpoint("21", 21, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("22", 22));
                add(new Checkpoint("43", 43));
            }}));
        checkpoints.put(22, new Checkpoint("22", 22, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("21", 21));
                add(new Checkpoint("23", 23));
                add(new Checkpoint("44", 44));
            }}));
        checkpoints.put(23, new Checkpoint("23", 23, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("22", 22));
                add(new Checkpoint("24", 24));
                add(new Checkpoint("45", 45));
            }}));
        checkpoints.put(24, new Checkpoint("24", 24, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("23", 23));
            }}));
        checkpoints.put(25, new Checkpoint("25", 25, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("26", 26));
                add(new Checkpoint("50", 50));
            }}));
        checkpoints.put(26, new Checkpoint("26", 26, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("25", 25));
                add(new Checkpoint("27", 27));
            }}));
        checkpoints.put(27, new Checkpoint("27", 27, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("26", 26));
                add(new Checkpoint("28", 28));
            }}));
        checkpoints.put(28, new Checkpoint("28", 28, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("27", 27));
                add(new Checkpoint("29", 29));
                add(new Checkpoint("30", 30));
                add(new Checkpoint("33", 33));
            }}));
        checkpoints.put(29, new Checkpoint("29", 29, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("28", 28));
            }}));
        checkpoints.put(30, new Checkpoint("30", 30, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("28", 28));
                add(new Checkpoint("31", 31));
                add(new Checkpoint("39", 39));
            }}));
        checkpoints.put(31, new Checkpoint("31", 31, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("30", 30));
                add(new Checkpoint("32", 32));
            }}));
        checkpoints.put(32, new Checkpoint("32", 32, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("31", 31));
            }}));
        checkpoints.put(33, new Checkpoint("33", 33, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("28", 28));
                add(new Checkpoint("30", 30));
                add(new Checkpoint("34", 34));
                add(new Checkpoint("35", 35));
            }}));
        checkpoints.put(34, new Checkpoint("34", 34, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("30", 30));
                add(new Checkpoint("33", 33));
            }}));
        checkpoints.put(35, new Checkpoint("35", 35, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("33", 33));
                add(new Checkpoint("36", 36));
            }}));
        checkpoints.put(36, new Checkpoint("36", 36, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("35", 35));
                add(new Checkpoint("37", 37));
            }}));
        checkpoints.put(37, new Checkpoint("37", 37, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("36", 36));
                add(new Checkpoint("38", 38));
            }}));
        checkpoints.put(38, new Checkpoint("38", 38, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("37", 37));
                add(new Checkpoint("39", 39));
            }}));
        checkpoints.put(39, new Checkpoint("39", 39, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("38", 38));
                add(new Checkpoint("40", 40));
            }}));
        checkpoints.put(40, new Checkpoint("40", 40, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("39", 39));
                add(new Checkpoint("41", 41));
            }}));
        checkpoints.put(41, new Checkpoint("41", 41, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("40", 40));
                add(new Checkpoint("42", 42));
            }}));
        checkpoints.put(42, new Checkpoint("42", 42, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("41", 41));
                add(new Checkpoint("64", 64));
            }}));
        checkpoints.put(43, new Checkpoint("43", 43, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("21", 21));
                add(new Checkpoint("44", 44));
                add(new Checkpoint("47", 47));
                add(new Checkpoint("48", 48));
                add(new Checkpoint("49", 49));
            }}));
        checkpoints.put(44, new Checkpoint("44", 44, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("43", 43));
                add(new Checkpoint("45", 45));
                add(new Checkpoint("22", 22));
                add(new Checkpoint("47", 47));
                add(new Checkpoint("46", 46));
                add(new Checkpoint("48", 48));
            }}));
        checkpoints.put(45, new Checkpoint("45", 45, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("23", 23));
                add(new Checkpoint("44", 44));
                add(new Checkpoint("46", 46));
                add(new Checkpoint("47", 47));
                add(new Checkpoint("81", 81));
            }}));
        checkpoints.put(46, new Checkpoint("46", 46, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("45", 45));
                add(new Checkpoint("44", 44));
                add(new Checkpoint("47", 47));
                add(new Checkpoint("52", 52));
                add(new Checkpoint("53", 53));
            }}));
        checkpoints.put(47, new Checkpoint("47", 47, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("43", 43));
                add(new Checkpoint("44", 44));
                add(new Checkpoint("45", 45));
                add(new Checkpoint("46", 46));
                add(new Checkpoint("48", 48));
                add(new Checkpoint("51", 51));
                add(new Checkpoint("52", 52));
                add(new Checkpoint("53", 53));
            }}));
        checkpoints.put(48, new Checkpoint("48", 48, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("43", 43));
                add(new Checkpoint("44", 44));
                add(new Checkpoint("47", 47));
                add(new Checkpoint("51", 51));
                add(new Checkpoint("52", 52));
            }}));
        checkpoints.put(49, new Checkpoint("49", 49, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("43", 43));
                add(new Checkpoint("50", 50));
            }}));
        checkpoints.put(50, new Checkpoint("50", 50, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("25", 25));
                add(new Checkpoint("49", 49));
            }}));
        checkpoints.put(51, new Checkpoint("51", 51, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("47", 47));
                add(new Checkpoint("48", 48));
                add(new Checkpoint("52", 52));
                add(new Checkpoint("56", 56));
                add(new Checkpoint("57", 57));
            }}));
        checkpoints.put(52, new Checkpoint("52", 52, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("46", 46));
                add(new Checkpoint("47", 47));
                add(new Checkpoint("48", 48));
                add(new Checkpoint("51", 51));
                add(new Checkpoint("53", 53));
                add(new Checkpoint("54", 54));
                add(new Checkpoint("56", 56));
                add(new Checkpoint("57", 57));
            }}));
        checkpoints.put(53, new Checkpoint("53", 53, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("46", 46));
                add(new Checkpoint("47", 47));
                add(new Checkpoint("52", 52));
                add(new Checkpoint("54", 54));
                add(new Checkpoint("55", 55));
                add(new Checkpoint("56", 56));
            }}));
        checkpoints.put(54, new Checkpoint("54", 54, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("52", 52));
                add(new Checkpoint("53", 53));
                add(new Checkpoint("55", 55));
                add(new Checkpoint("56", 56));
                add(new Checkpoint("59", 59));
                add(new Checkpoint("60", 60));
                add(new Checkpoint("61", 61));
            }}));
        checkpoints.put(55, new Checkpoint("55", 55, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("53", 53));
                add(new Checkpoint("54", 54));
                add(new Checkpoint("60", 60));
                add(new Checkpoint("61", 61));
            }}));
        checkpoints.put(56, new Checkpoint("56", 56, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("51", 51));
                add(new Checkpoint("52", 52));
                add(new Checkpoint("53", 53));
                add(new Checkpoint("54", 54));
                add(new Checkpoint("57", 57));
                add(new Checkpoint("58", 58));
                add(new Checkpoint("59", 59));
                add(new Checkpoint("60", 60));
            }}));
        checkpoints.put(57, new Checkpoint("57", 57, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("51", 51));
                add(new Checkpoint("52", 52));
                add(new Checkpoint("56", 56));
                add(new Checkpoint("58", 58));
                add(new Checkpoint("59", 59));
            }}));
        checkpoints.put(58, new Checkpoint("58", 58, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("56", 56));
                add(new Checkpoint("57", 57));
                add(new Checkpoint("59", 59));
            }}));
        checkpoints.put(59, new Checkpoint("59", 59, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("54", 54));
                add(new Checkpoint("56", 56));
                add(new Checkpoint("57", 57));
                add(new Checkpoint("58", 58));
                add(new Checkpoint("60", 60));
                add(new Checkpoint("62", 62));
                add(new Checkpoint("63", 63));
                add(new Checkpoint("64", 64));
            }}));
        checkpoints.put(60, new Checkpoint("60", 60, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("54", 54));
                add(new Checkpoint("55", 55));
                add(new Checkpoint("56", 56));
                add(new Checkpoint("59", 59));
                add(new Checkpoint("61", 61));
                add(new Checkpoint("62", 62));
                add(new Checkpoint("63", 63));
            }}));
        checkpoints.put(61, new Checkpoint("61", 61, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("54", 54));
                add(new Checkpoint("55", 55));
                add(new Checkpoint("60", 60));
                add(new Checkpoint("62", 62));
            }}));
        checkpoints.put(62, new Checkpoint("62", 62, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("59", 59));
                add(new Checkpoint("60", 60));
                add(new Checkpoint("61", 61));
                add(new Checkpoint("63", 63));
                add(new Checkpoint("65", 65));
            }}));
        checkpoints.put(63, new Checkpoint("63", 63, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("58", 58));
                add(new Checkpoint("59", 59));
                add(new Checkpoint("60", 60));
                add(new Checkpoint("62", 62));
                add(new Checkpoint("64", 64));
                add(new Checkpoint("71", 71));
            }}));
        checkpoints.put(64, new Checkpoint("64", 64, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("58", 58));
                add(new Checkpoint("63", 63));
                add(new Checkpoint("42", 42));
            }}));
        checkpoints.put(65, new Checkpoint("65", 65, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("62", 62));
                add(new Checkpoint("66", 66));
                add(new Checkpoint("70", 70));
                add(new Checkpoint("71", 71));
            }}));
        checkpoints.put(66, new Checkpoint("66", 66, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("65", 65));
                add(new Checkpoint("67", 67));
                add(new Checkpoint("68", 68));
                add(new Checkpoint("69", 69));
                add(new Checkpoint("70", 70));
            }}));
        checkpoints.put(67, new Checkpoint("67", 67, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("66", 66));
                add(new Checkpoint("68", 68));
                add(new Checkpoint("83", 83));
            }}));
        checkpoints.put(68, new Checkpoint("68", 68, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("67", 67));
                add(new Checkpoint("69", 69));
                add(new Checkpoint("84", 84));
            }}));
        checkpoints.put(69, new Checkpoint("69", 69, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("65", 65));
                add(new Checkpoint("66", 66));
                add(new Checkpoint("67", 67));
                add(new Checkpoint("68", 68));
                add(new Checkpoint("70", 70));
                add(new Checkpoint("73", 73));
            }}));
        checkpoints.put(70, new Checkpoint("70", 70, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("66", 66));
                add(new Checkpoint("66", 66));
                add(new Checkpoint("69", 69));
                add(new Checkpoint("71", 71));
                add(new Checkpoint("73", 73));
            }}));
        checkpoints.put(71, new Checkpoint("71", 71, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("65", 65));
                add(new Checkpoint("70", 70));
            }}));
        checkpoints.put(72, new Checkpoint("72", 72, new ArrayList<Checkpoint>() {
            {
            }}));
        checkpoints.put(73, new Checkpoint("73", 73, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("70", 70));
                add(new Checkpoint("74", 74));
                add(new Checkpoint("75", 75));
            }}));
        checkpoints.put(74, new Checkpoint("74", 74, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("74", 74));
                add(new Checkpoint("75", 75));
                add(new Checkpoint("78", 78));
                add(new Checkpoint("79", 79));
                add(new Checkpoint("80", 80));
            }}));
        checkpoints.put(75, new Checkpoint("75", 75, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("74", 74));
                add(new Checkpoint("75", 75));
                add(new Checkpoint("77", 77));
                add(new Checkpoint("78", 78));
                add(new Checkpoint("79", 79));
            }}));
        checkpoints.put(76, new Checkpoint("76", 76, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("75", 75));
                add(new Checkpoint("77", 77));
                add(new Checkpoint("78", 78));
            }}));
        checkpoints.put(77, new Checkpoint("77", 77, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("76", 76));
                add(new Checkpoint("78", 78));
            }}));
        checkpoints.put(78, new Checkpoint("78", 78, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("74", 74));
                add(new Checkpoint("75", 75));
                add(new Checkpoint("76", 76));
                add(new Checkpoint("77", 77));
                add(new Checkpoint("79", 79));
            }}));
        checkpoints.put(79, new Checkpoint("79", 79, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("74", 74));
                add(new Checkpoint("75", 75));
                add(new Checkpoint("78", 78));
                add(new Checkpoint("80", 80));
            }}));
        checkpoints.put(80, new Checkpoint("80", 80, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("74", 74));
                add(new Checkpoint("79", 79));
            }}));
        checkpoints.put(81, new Checkpoint("81", 81, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("45", 45));
                add(new Checkpoint("82", 82));
            }}));
        checkpoints.put(82, new Checkpoint("82", 82, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("81", 81));
                add(new Checkpoint("89", 89));
            }}));
        checkpoints.put(83, new Checkpoint("83", 83, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("67", 67));
                add(new Checkpoint("68", 68));
                add(new Checkpoint("84", 84));
                add(new Checkpoint("85", 85));
                add(new Checkpoint("86", 86));
            }}));
        checkpoints.put(84, new Checkpoint("84", 84, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("67", 67));
                add(new Checkpoint("68", 68));
                add(new Checkpoint("83", 83));
                add(new Checkpoint("85", 85));
                add(new Checkpoint("86", 86));
            }}));
        checkpoints.put(85, new Checkpoint("85", 85, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("83", 83));
                add(new Checkpoint("84", 84));
                add(new Checkpoint("86", 86));
                add(new Checkpoint("87", 87));
                add(new Checkpoint("88", 88));
            }}));
        checkpoints.put(86, new Checkpoint("86", 86, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("83", 83));
                add(new Checkpoint("84", 84));
                add(new Checkpoint("85", 85));
                add(new Checkpoint("87", 87));
                add(new Checkpoint("88", 88));
            }}));
        checkpoints.put(87, new Checkpoint("87", 87, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("85", 85));
                add(new Checkpoint("86", 86));
                add(new Checkpoint("88", 88));
            }}));
        checkpoints.put(88, new Checkpoint("88", 88, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("85", 85));
                add(new Checkpoint("86", 86));
                add(new Checkpoint("87", 87));
            }}));
        checkpoints.put(89, new Checkpoint("89", 89, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("82", 82));
                add(new Checkpoint("90", 90));
            }}));
        checkpoints.put(90, new Checkpoint("90", 90, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("89", 89));
                add(new Checkpoint("91", 91));
            }}));
        checkpoints.put(91, new Checkpoint("91", 91, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("90", 90));
                add(new Checkpoint("92", 92));
            }}));
        checkpoints.put(92, new Checkpoint("92", 92, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("91", 91));
                add(new Checkpoint("93", 93));
            }}));
        checkpoints.put(93, new Checkpoint("93", 93, new ArrayList<Checkpoint>() {
            {
                add(new Checkpoint("92", 92));
            }}));


//        checkpoints.put(7, new Checkpoint("7", 7));
//        checkpoints.put(8, new Checkpoint("8", 8));
//        checkpoints.put(9, new Checkpoint("9", 9));
//        checkpoints.put(10, new Checkpoint("10", 10));
        return checkpoints;
    }
}
