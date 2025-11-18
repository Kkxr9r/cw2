package cw1.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Requirements {
    private double capacity;
    private boolean cooling = false;
    private boolean heating = false;
    private Double maxCost = null;

}
