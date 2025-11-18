package cw1.service;


import cw1.model.Position;
import cw1.model.Region;
import cw1.util.geometry.DistanceTo;
import cw1.util.geometry.IsCloseTo;
import cw1.util.geometry.IsInRegion;
import cw1.util.geometry.NextPosition;
import org.springframework.stereotype.Service;

@Service
public class DroneServiceImpl implements DroneService {

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
        return NextPosition.NextPosition(start, degrees);
    }

    @Override
    public boolean isInRegion(Position point, Region region) {
        Position.validatePosition(point);
        region.validateRegion();
        return IsInRegion.isInRegion(point, region);
    }
}
