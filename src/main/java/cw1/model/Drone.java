package cw1.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Drone {
    private String name;
    private String id;
    private DroneCapability capability;
}
