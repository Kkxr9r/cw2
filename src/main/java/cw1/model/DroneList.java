package cw1.model;


import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DroneList {

    private List<Drone> droneList = new ArrayList<>();

    public List<Drone> getAllDrones() {
        return droneList;
    }

    public void setDrones(List<Drone> droneList) {
        this.droneList = droneList;
    }
}

