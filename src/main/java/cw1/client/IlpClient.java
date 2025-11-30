package cw1.client;
import cw1.model.Drone;
import cw1.model.DroneServicePoint;
import cw1.model.RestrictedRegion;
import cw1.model.ServicePointAvailability;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Component
public class IlpClient {

    private final RestTemplate restTemplate;
    private final String ilpBaseUrl;

    public IlpClient(RestTemplate restTemplate, String ilpBaseUrl) {
        this.restTemplate = restTemplate;
        this.ilpBaseUrl = ilpBaseUrl;
    }

    public List<Drone> fetchDrones() {
        String url = ilpBaseUrl + "/drones";
        Drone[] drones = restTemplate.getForObject(url, Drone[].class);
        return drones == null ? List.of() : Arrays.asList(drones);
    }

    public List<ServicePointAvailability> fetchServicePointAvailability() {
        String url = ilpBaseUrl + "/drones-for-service-points";
        ServicePointAvailability[] availabilities = restTemplate.getForObject(url, ServicePointAvailability[].class);
        return availabilities == null ? List.of() : Arrays.asList(availabilities);
    }


    public List<DroneServicePoint> fetchDroneServicePoint() {
        String url = ilpBaseUrl + "/service-points";
        DroneServicePoint[] servicePoints = restTemplate.getForObject(url, DroneServicePoint[].class);
        return servicePoints == null ? List.of() : Arrays.asList(servicePoints);
    }

    public List<RestrictedRegion> fetchRestrictedRegions() {
        String url = ilpBaseUrl + "/restricted-areas";
        RestrictedRegion[] regions = restTemplate.getForObject(url, RestrictedRegion[].class);
        return regions == null ? List.of() : Arrays.asList(regions);
    }
}
