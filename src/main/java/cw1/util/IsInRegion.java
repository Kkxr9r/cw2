package cw1.util;

import cw1.model.Position;
import cw1.model.Region;

import java.util.List;

import static cw1.service.Constants.step_size;


public class IsInRegion {

    // helper function for isOnLine
    private static Position nextPosition(Position start, Position end) {
        double stepSize = step_size;
        double dx = end.getLng() - start.getLng();
        double dy = end.getLat() - start.getLat();

        double dist = DistanceTo.distanceTo(start, end);

        // avoid division by 0 if dist = 0, and moving beyond the end point
        if (dist < stepSize) {
            return new Position(end.getLng(), end.getLat());
        }

        double ux = dx / dist;
        double uy = dy / dist;

        double newLng = start.getLng() + ux * stepSize;
        double newLat = start.getLat() + uy * stepSize;

        return new Position(newLng, newLat);
    }

    // helper function for isOnBoundry, determines if a particular point is on a side/line
    private static Boolean isOnLine(Position target, Position start, Position end) {
        Position current = start;
        do {
            if (IsCloseTo.isCloseTo(target, current)) { return true; }
            current = nextPosition(current, end);

        } while (!current.equals(end));
        // one last check to see if target isCloseTo end
        if (IsCloseTo.isCloseTo(target, current)) { return true; }

        return false;
    }

    // helper function for isOnRegion, determines if the target is on the sides
    private static Boolean isOnBoundry(Position target, Region region) {
        List<Position> vertices = region.getVertices();
        for (int i = 0; i < vertices.size(); i++) {
            Position start = vertices.get(i);
            Position end = vertices.get((i + 1) % vertices.size());
            if (isOnLine(target, start, end)) {
                return true;
            }
        }
        return false;
    }

    private static boolean rayCasting(Position target, Region region) {
        // Assumes boundary check is done elsewhere; this handles interior test only.
        List<Position> v = region.getVertices();
        double px = target.getLng();
        double py = target.getLat();

        boolean inside = false;
        int n = v.size();

        for (int i = 0, j = n - 1; i < n; j = i++) {
            double xi = v.get(i).getLng(), yi = v.get(i).getLat();
            double xj = v.get(j).getLng(), yj = v.get(j).getLat();

            // Edge straddles the horizontal line y = py? (strict test avoids double counting)
            boolean straddles = ((yi > py) != (yj > py));
            if (straddles) {
                double xCross = xi + (py - yi) * (xj - xi) / (yj - yi);
                if (px < xCross) {
                    inside = !inside; // toggle parity
                }
            }
        }
        return inside;
    }



    public static Boolean isInRegion(Position target, Region region) {
        // checks if the target is on the edges
        if (isOnBoundry(target, region)) { return true; }

        // runs rayCasting algorithm to find whether the target is on the inside of the region
        return rayCasting(target, region);
    }
}
