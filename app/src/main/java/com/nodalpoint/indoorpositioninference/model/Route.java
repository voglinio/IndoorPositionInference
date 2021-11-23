package com.nodalpoint.indoorpositioninference.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class Route {

    private List<Checkpoint> checkpoints;
    private String name;

    public Route(List<Checkpoint> checkpoints, String name) {
        this.checkpoints = checkpoints;
        this.name = name;
    }

    public void addItem(Checkpoint item) {
        checkpoints.add(item);
    }

    public void addCheckpoints(List<Checkpoint> items) {
        checkpoints.addAll(items);
    }
}
