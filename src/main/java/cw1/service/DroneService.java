package cw1.service;


import cw1.model.Drone;
import cw1.model.DroneList;
import cw1.model.Position;
import cw1.model.Region;

import java.util.List;

public interface DroneService {

    public double calculateDistance(Position p1, Position p2);
    boolean isCloseTo(Position p1, Position p2);
    Position nextPosition(Position start, Double angleDegrees);
    boolean isInRegion(Position point, Region region);
    public List<String> dronesWithCooling (boolean state);
    public Drone getDrone (String id);
    public List<String> queryAsPath(String attributeName, String attributeValue);
}