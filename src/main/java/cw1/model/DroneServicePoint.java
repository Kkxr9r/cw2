package cw1.model;

import lombok.Data;

import java.awt.*;

@Data
public class DroneServicePoint {
    private String name;
    private Integer servicePointId;
    private Position location;

}
