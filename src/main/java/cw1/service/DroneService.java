package cw1.service;


import cw1.model.*;

import java.util.List;

public interface DroneService {

    double calculateDistance(Position p1, Position p2);
    boolean isCloseTo(Position p1, Position p2);
    Position nextPosition(Position start, Double angleDegrees);
    boolean isInRegion(Position point, Region region);
    List<String> dronesWithCooling(boolean state);
    Drone getDrone(String id);
    List<String> queryAsPath(String attributeName, String attributeValue);
    List<String> queryAvailableDrones(List<MedDispatchRec> medDispatchRecs);
}