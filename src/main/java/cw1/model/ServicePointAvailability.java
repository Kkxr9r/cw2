package cw1.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ServicePointAvailability {
        private Integer servicePointId;
        private List<AvailableDrone> drones;
}
