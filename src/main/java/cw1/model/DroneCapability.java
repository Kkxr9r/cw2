package cw1.model;

import lombok.Data;

@Data
public class DroneCapability {
    private boolean cooling = false;
    private boolean heating = false;

    private double capacity;
    private int maxMoves;

    private double costPerMove;
    private double costInitial;
    private double costFinal;
}
