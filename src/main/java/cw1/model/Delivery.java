package cw1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Delivery {
    private Integer deliveryId;
    private List<Position> flightPath;
}
