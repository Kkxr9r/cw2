package cw1.model;

import lombok.Data;

import java.util.List;

@Data
public class AvailableDrone {
    private String id;
    private List<DroneAvailabilitySlot> availability;
}
