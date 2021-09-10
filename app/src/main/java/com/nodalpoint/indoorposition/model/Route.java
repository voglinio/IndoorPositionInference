package com.nodalpoint.indoorposition.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class Route {

    private List<Checkpoint> checkpoints;

    public Route(List<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public void addItem(Checkpoint item) {
        checkpoints.add(item);
    }

    public void addCheckpoints(List<Checkpoint> items) {
        checkpoints.addAll(items);
    }
}
