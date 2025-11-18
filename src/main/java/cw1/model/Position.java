package cw1.model;

import cw1.exception.InvalidDataException;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Position {
    private Double lng;
    private Double lat;

    public Position() {}

    public Position(Double lng, Double lat) {
        this.lng = lng;
        this.lat = lat;
        validatePosition(this);
    }


    public static void validatePosition(Position position) {
        if (Objects.isNull(position)) {
            throw new InvalidDataException("position must be provided.");
        }
        if (position.lng == null) {
            throw new InvalidDataException("position.lng must be provided");
        }
        if (position.lat == null) {
            throw new InvalidDataException("position.lat must be provided");
        }
        if (Double.isNaN(position.lng) || Double.isInfinite(position.lng)) {
            throw new InvalidDataException("position.lng must be a finite number");
        }
        if (Double.isNaN(position.lat) || Double.isInfinite(position.lat)) {
            throw new InvalidDataException("position.lat must be a finite number");
        }
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position that = (Position) o;
        return Objects.equals(that.lng, lng) &&
                Objects.equals(that.lat, lat);
    }

    @Override public int hashCode() { return Objects.hash(lng, lat); }

    @Override public String toString() { return "Position{lng=" + lng + ", lat=" + lat + '}'; }
}
