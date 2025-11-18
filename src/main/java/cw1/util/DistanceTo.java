package cw1.util;

import cw1.model.Position;

public class DistanceTo {
    public static Double distanceTo (Position position1, Position position2){
            Double dx = position2.getLng() - position1.getLng();
            Double dy = position2.getLat() - position1.getLat();
            return Math.sqrt(dx * dx + dy * dy);
    }
}

