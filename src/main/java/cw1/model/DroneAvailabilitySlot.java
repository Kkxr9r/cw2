package cw1.model;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class DroneAvailabilitySlot {
    private DayOfWeek DayOfWeek;
    private LocalTime from;
    private LocalTime until;
}
