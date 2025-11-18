package cw1.util.geometry;

import cw1.exception.InvalidDataException;
import cw1.model.Position;

import static cw1.service.Constants.step_size;

public class NextPosition {

    private static double normalizeAngle(double angle) {
        double a = angle % 360.0;
        return a < 0 ? a + 360.0 : a;
    }

    private static void validateAngle(Double angle) {

        if (angle == null) {
            throw new InvalidDataException("angle must be provided");
        }
        if (Double.isNaN(angle) || Double.isInfinite(angle)) {
            throw new InvalidDataException("angle must be a finite number");
        }
    }

    public static Position NextPosition(Position start, double angle) {
        double stepSize = step_size;
        validateAngle(angle); // validation
        double rad = Math.toRadians(normalizeAngle(angle));
        double dx = Math.cos(rad) * stepSize; // lng
        double dy = Math.sin(rad) * stepSize; // lat
        return new Position(start.getLng() + dx, start.getLat() + dy);
    }

    // this one is for isInRegion, but can be used instead as well if you don't have an angle
    static Position NextPosition(Position start, Position end) {
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
}
