package cw1.model;


import java.util.List;

public class PathResult {
    public List<Position> path;
    public int moves;
    public double distance;
    public double cost;

    public PathResult(List<Position> path, int moves, double distance, double cost) {
        this.path = path;
        this.moves = moves;
        this.distance = distance;
        this.cost = cost;
    }
}

