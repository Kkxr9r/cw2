package cw1.model;

import cw1.exception.InvalidDataException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class Region {
    private String name;
    private List<Position> vertices = new ArrayList<>();

    public Region(String name, List<Position> vertices) {
        this.name = name;
        this.vertices = vertices;
        validateRegion(this);
    }

    public static void validateRegion(Region region){

        if (Objects.isNull(region)){
            throw new InvalidDataException("region must be provided");
        }

        if (region.name == null) {
            throw new InvalidDataException("region.name must be provided");
        }
        // size ≥ 4 (closed polygon minimal)
        if (region.vertices.size() < 4) {
            throw new InvalidDataException("region must have at least 3 sides to be an area");
        }

        // first position must equal last for the region to be a valid region
        Position first = region.vertices.getFirst();
        Position last  = region.vertices.getLast();
        boolean closed = first.getLng().equals(last.getLng()) && first.getLat().equals(last.getLat());
        if (!closed) {
            throw new InvalidDataException("region is not closed (first vertex must equal last), not a valid region");
        }

        // validates each of the vertices
        for (Position p : region.vertices) {
            Position.validatePosition(p);
        }
    }

    @Override public String toString() {
        return "Region{name='" + name + "', vertices=" + vertices + '}';
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Region)) return false;
        Region region = (Region) o;
        return Objects.equals(name, region.name) &&
                Objects.equals(vertices, region.vertices);
    }

    @Override public int hashCode() { return Objects.hash(name, vertices); }
}