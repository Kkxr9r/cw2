package cw1.model;

import cw1.client.IlpClient;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ServicePointAvailabilityList {

    private final IlpClient ilpClient;
    public List<ServicePointAvailability> ServicePointAvailabilities = new ArrayList<>();

    public ServicePointAvailabilityList(IlpClient ilpClient) {
        this.ilpClient = ilpClient;
    }

    @PostConstruct
    public void setServicePointAvailabilities() {
        this.ServicePointAvailabilities = ilpClient.fetchServicePointAvailability();
    }

    public List<DroneAvailabilitySlot> getSlotsForDroneId(String id) {
        for (ServicePointAvailability spa : ServicePointAvailabilities) {
            for (AvailableDrone drone : spa.getDrones()) {
                if (drone.getId().equals(id)) {
                    return drone.getAvailability();
                }
            }
        }
        return List.of();
    }
}
