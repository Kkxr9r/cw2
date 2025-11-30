package cw1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DronePath {
    private Integer droneId;
    private List<Delivery> deliveryList;
}
