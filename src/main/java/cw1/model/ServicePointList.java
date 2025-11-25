package cw1.model;

import cw1.client.IlpClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ServicePointList {
    private final IlpClient ilpClient;
    private List<DroneServicePoint> servicePointList = new ArrayList<>();

    public ServicePointList(IlpClient ilpClient) {
        this.ilpClient = ilpClient;
    }

    public List<DroneServicePoint> getServicePointList() {
        return servicePointList;
    }

    public void setServicePoints(){
        this.servicePointList = ilpClient.fetchDroneServicePoint();
    }
}
