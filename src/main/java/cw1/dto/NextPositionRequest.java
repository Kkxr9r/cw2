package cw1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cw1.model.Position;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NextPositionRequest {


    private Position start;
    private Double angle;

    public NextPositionRequest(Position start, Double angle) {
        this.start = start;
        this.angle = angle;
    }
}