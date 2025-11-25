package cw1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Data
@AllArgsConstructor
public class DroneDeliveryPath {
    private Double totalCost;
    private Integer totalMoves;
    private List<DronePath> dronePaths;

    public static DroneDeliveryPath empty() {
        return new DroneDeliveryPath(0.0, 0, new ArrayList<>());
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String toGeoJson() {
        Map<String, Object> featureCollection = new LinkedHashMap<>();
        featureCollection.put("type", "FeatureCollection");

        List<Map<String, Object>> features = new ArrayList<>();

        for (DronePath dp : this.getDronePaths()) {
            Integer droneId = dp.getDroneId();

            for (Delivery del : dp.getDeliveryList()) {
                Map<String, Object> feature = new LinkedHashMap<>();
                feature.put("type", "Feature");

                // properties
                Map<String, Object> props = new LinkedHashMap<>();
                props.put("droneId", droneId);
                props.put("deliveryId", del.getDeliveryId());
                feature.put("properties", props);

                // geometry
                Map<String, Object> geom = new LinkedHashMap<>();
                geom.put("type", "LineString");

                List<List<Double>> coords = del.getFlightPath().stream()
                        .map(p -> List.of(p.getLng(), p.getLat())) // [lng, lat]
                        .toList();

                geom.put("coordinates", coords);
                feature.put("geometry", geom);

                features.add(feature);
            }
        }

        featureCollection.put("features", features);

        try {
            return objectMapper.writeValueAsString(featureCollection);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialise GeoJSON", e);
        }
    }


}
