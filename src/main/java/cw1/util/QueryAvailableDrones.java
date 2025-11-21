package cw1.util;

import cw1.model.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;


public class QueryAvailableDrones {
    // helper function for queryAvailableDrones
    public static boolean droneMeetsRequirements(Drone drone, MedDispatchRec dispatchRec) {
        // no need to refresh data since it won't change in a request (it may between requests)

        Requirements requirements = dispatchRec.getRequirements();

        if (drone.getCapability().getCapacity() < requirements.getCapacity()) {
            return false;
        }

        if (requirements.isCooling()){
            return drone.getCapability().isCooling();
        } else if (requirements.isHeating()){
            return drone.getCapability().isHeating();
        }
        return true;
    }

    // helper function for queryAvailableDrones
    public static boolean droneIsAvailableForRec(
            Drone drone, MedDispatchRec dispatchRec, ServicePointAvailabilityList servicePointAvailabilityList) {

        if (dispatchRec.getDate() == null || dispatchRec.getTime() == null) {
            return true;
        }

        DayOfWeek day = dispatchRec.getDate().getDayOfWeek();
        LocalTime time = dispatchRec.getTime();

        List<DroneAvailabilitySlot> slots = servicePointAvailabilityList.getSlotsForDroneId(drone.getId());


        if (slots == null || slots.isEmpty()) {
            return false;
        }

        return slots.stream().anyMatch(slot -> {
            if (slot.getDayOfWeek() != day) return false;

            LocalTime from = slot.getFrom();
            LocalTime until = slot.getUntil();

            return isBetween(time, from, until);
        });
    }

    private static boolean isBetween(LocalTime time, LocalTime from, LocalTime until) {
        return !time.isBefore(from) && !time.isAfter(until);
    }

}
