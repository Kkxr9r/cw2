package cw1.client;
import cw1.model.Drone;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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


}
