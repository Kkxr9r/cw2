package cw1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cw1.model.Position;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DistanceToRequest {

    private Position position1;
    private Position position2;

    public DistanceToRequest() {
        // needed by Jackson for deserialization
    }

    public DistanceToRequest(Position position1, Position position2) {
        this.position1 = position1;
        this.position2 = position2;
    }

}