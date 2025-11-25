package cw1.model;


import cw1.client.IlpClient;
import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DroneList {

    private final IlpClient ilpClient;
    private List<Drone> droneList = new ArrayList<>();

    public DroneList(IlpClient ilpClient) {
        this.ilpClient = ilpClient;
    }

    public List<Drone> getAllDrones() {
        return droneList;
    }

    @PostConstruct
    public void setDrones() {
        this.droneList = ilpClient.fetchDrones();
    }
}

