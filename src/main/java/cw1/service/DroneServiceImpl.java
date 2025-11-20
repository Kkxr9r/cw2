package cw1.service;


import cw1.exception.InvalidDataException;
import cw1.exception.NotFoundException;
import cw1.model.Drone;
import cw1.model.DroneList;
import cw1.model.Position;
import cw1.model.Region;
import cw1.util.DistanceTo;
import cw1.util.IsCloseTo;
import cw1.util.IsInRegion;
import cw1.util.NextPosition;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class DroneServiceImpl implements DroneService {

    private final DroneList droneList1;

    public DroneServiceImpl(DroneList droneList1) {
        this.droneList1 = droneList1;
    }

    @Override
    public double calculateDistance(Position p1, Position p2) {
        Position.validatePosition(p1);
        Position.validatePosition(p2);
        return DistanceTo.distanceTo(p1, p2);
    }

    @Override
    public boolean isCloseTo(Position p1, Position p2) {
        Position.validatePosition(p1);
        Position.validatePosition(p2);
        return IsCloseTo.isCloseTo(p1, p2);
    }

    @Override
    public Position nextPosition(Position start, Double degrees) {
        Position.validatePosition(start);
        if (degrees == null) {
            throw new InvalidDataException("angle must be provided");
        }
        return NextPosition.NextPosition(start, degrees);
    }

    @Override
    public boolean isInRegion(Position point, Region region) {
        Position.validatePosition(point);
        Region.validateRegion(region);
        return IsInRegion.isInRegion(point, region);
    }

    @Override
    public List<String> dronesWithCooling(boolean state) {
        droneList1.setDrones();
        return droneList1.getAllDrones().stream()
                .filter(drone -> state
                        ? drone.getCapability().isCooling()
                        : drone.getCapability().isHeating()
                )
                .map(Drone::getId)
                .toList();
    }

    @Override
    public Drone getDrone (String id) {
        droneList1.setDrones();
        return droneList1.getAllDrones().stream()
                .filter(drone -> id.equals(drone.getId())).findFirst()
                .orElseThrow(() -> new NotFoundException("drone with id " + id + " not found"));
    }

    @Override
    public List<String> queryAsPath(String attributeName, String attributeValue) {
        droneList1.setDrones();
        return droneList1.getAllDrones().stream().filter(drone -> {
                    return switch (attributeName) {
                        // added these as well just in case they wanted to do it the complicated way
                        case "id" -> drone.getId().equals(attributeValue);
                        case "cooling" -> Boolean.toString(drone.getCapability().isCooling()).equals(attributeValue);
                        case "heating" -> Boolean.toString(drone.getCapability().isHeating()).equals(attributeValue);

                        case "capacity" -> Double.toString(drone.getCapability().getCapacity()).equals(attributeValue);
                        case "maxMoves" -> Integer.toString(drone.getCapability().getMaxMoves()).equals(attributeValue);
                        case "costPerMove" ->  Double.toString(drone.getCapability().getCostPerMove()).equals(attributeValue);
                        case "costInitial" -> Double.toString(drone.getCapability().getCostInitial()).equals(attributeValue);
                        case "costFinal" -> Double.toString(drone.getCapability().getCostFinal()).equals(attributeValue);

                        default -> false;
                    };
                }).map(Drone::getId).toList();
    }

}
