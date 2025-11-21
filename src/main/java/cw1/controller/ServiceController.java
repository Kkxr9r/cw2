package cw1.controller;

import cw1.dto.*;
import cw1.model.Drone;
import cw1.model.Position;
import cw1.model.Request;
import cw1.service.DroneService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.List;

/**
 * Controller class that handles various HTTP endpoints for the application.
 * Provides functionality for serving the index page, retrieving a static UUID,
 * and managing key-value pairs through POST requests.
 */

@RestController()
@RequestMapping("/api/v1")
public class ServiceController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @Value("${ilp.service.url}")
    public URL serviceUrl;

    private final DroneService droneService;

    public ServiceController(DroneService droneService) {
        this.droneService = droneService;
    }

    @GetMapping("/")
    public String index() {
        return "<html><body>" +
                "<h1>Welcome from ILP</h1>" +
                "<h4>ILP-REST-Service-URL:</h4> <a href=\"" + serviceUrl + "\" target=\"_blank\"> " + serviceUrl+ " </a>" +
                "</body></html>";
    }

    @GetMapping("/uid")
    public String uid() {
        return "s2532847";
    }


    @PostMapping("/distanceTo")
    public Double distanceTo(@RequestBody DistanceToRequest distanceToRequest) {

        return droneService.calculateDistance(distanceToRequest.getPosition1(), distanceToRequest.getPosition2());
    }

    @PostMapping("/isCloseTo")
    public boolean isCloseTo(@RequestBody IsCloseToRequest isCloseToRequest) {
        return droneService.isCloseTo(isCloseToRequest.getPosition1(), isCloseToRequest.getPosition2());
    }

    @PostMapping("/nextPosition")
    public Position nextPosition(@RequestBody NextPositionRequest nextPositionRequest) {
        return droneService.nextPosition(nextPositionRequest.getStart(), nextPositionRequest.getAngle());
    }

    @PostMapping("/isInRegion")
    public boolean isInRegion(@RequestBody IsInRegionRequest isInRegionRequest) {
        return droneService.isInRegion(isInRegionRequest.getPosition(), isInRegionRequest.getRegion());
    }

    // cw 2

    @GetMapping("/dronesWithCooling/{state}")
    public List<String> dronesWithCooling(@PathVariable boolean state) {
        return droneService.dronesWithCooling(state);
    }

    @GetMapping("/droneDetails/{id}")
    public Drone droneDetails(@PathVariable String id) {
        return droneService.getDrone(id);
    }

    @GetMapping("/queryAsPath/{attributeName}/{attributeValue}")
    public List<String> queryAsPath(@PathVariable String attributeName, @PathVariable String attributeValue) {
        return droneService.queryAsPath(attributeName, attributeValue);
    }

    @PostMapping("/query")
    public List<String> query(@RequestBody List<Request> requestList) {
        return droneService.query(requestList);
    }

    @PostMapping("/queryAvailableDrones")
    public List<String> queryAvailableDrones(@RequestBody QueryAvailableDronesRequest QueryAvailableDronesRequest) {
        return droneService.queryAvailableDrones(QueryAvailableDronesRequest.getMedDispatchRecs());
    }

}
