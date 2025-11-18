package cw1.util.validation;

import cw1.exception.InvalidDataException;
import cw1.model.Position;
import cw1.model.Region;

import java.util.List;

public final class ValidateRegion {
    private void validateRegion() {}

    public static void validateRegion(Region r){

        if (r == null) {
            throw new InvalidDataException("region must be provided");
        }

        String name = r.getName();
        List<Position> vertices = r.getVertices();

        if (name == null) {
            throw new InvalidDataException("region.name must be provided");
        }
        // size ≥ 4 (closed polygon minimal)
        if (vertices.size() < 4) {
            throw new InvalidDataException("region must have at least 3 sides to be an area");
        }



        // first position must equal last for the region to be a valid region
        Position first = vertices.getFirst();
        Position last  = vertices.getLast();
        boolean closed = first.getLng().equals(last.getLng()) && first.getLat().equals(last.getLat());
        if (!closed) {
            throw new InvalidDataException("region is not closed (first vertex must equal last), not a valid region");
        }
    }
}
