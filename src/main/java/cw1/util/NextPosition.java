package cw1.util;

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
}
