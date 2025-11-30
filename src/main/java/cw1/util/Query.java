package cw1.util;

import cw1.model.Drone;
import cw1.model.Request;

public class Query {
    public static boolean droneHasCapability(Request request, Drone drone) {
        String attribute = request.getAttribute();
        String operator = request.getOperator();
        String value = request.getValue();
        try{
        return switch (operator) {
            case "=" -> switch (attribute) {
                case "name" -> drone.getName().equals(value);
                case "id" -> drone.getId().equals(value);
                case "cooling" -> Boolean.toString(drone.getCapability().isCooling()).equals(value);
                case "heating" -> Boolean.toString(drone.getCapability().isHeating()).equals(value);

                case "capacity"  -> drone.getCapability().getCapacity() == Double.parseDouble(value);
                case "maxMoves"  -> drone.getCapability().getMaxMoves() == Integer.parseInt(value);
                case "costPerMove" -> drone.getCapability().getCostPerMove() == Double.parseDouble(value);
                case "costInitial" -> drone.getCapability().getCostInitial() == Double.parseDouble(value);
                case "costFinal"   -> drone.getCapability().getCostFinal() == Double.parseDouble(value);
                default -> false;
            };
            case "!=" -> switch (attribute) {
                case "capacity"   -> drone.getCapability().getCapacity() != Double.parseDouble(value);
                case "maxMoves"   -> drone.getCapability().getMaxMoves() != Integer.parseInt(value);
                case "costPerMove"-> drone.getCapability().getCostPerMove() != Double.parseDouble(value);
                case "costInitial"-> drone.getCapability().getCostInitial() != Double.parseDouble(value);
                case "costFinal"  -> drone.getCapability().getCostFinal() != Double.parseDouble(value);
                default -> false;
            };
            case "<" -> switch (attribute) {
                case "capacity" -> Double.parseDouble(value) > drone.getCapability().getCapacity();
                case "maxMoves" -> Integer.parseInt(value) > (drone.getCapability().getMaxMoves());
                case "costPerMove" -> Double.parseDouble(value) > drone.getCapability().getCostPerMove();
                case "costInitial" -> Double.parseDouble(value) > drone.getCapability().getCostInitial();
                case "costFinal" -> Double.parseDouble(value) > drone.getCapability().getCostFinal();

                default -> false;
            };
            case ">" -> switch (attribute) {
                case "capacity" -> Double.parseDouble(value) < drone.getCapability().getCapacity();
                case "maxMoves" -> Integer.parseInt(value) < (drone.getCapability().getMaxMoves());
                case "costPerMove" -> Double.parseDouble(value) < drone.getCapability().getCostPerMove();
                case "costInitial" -> Double.parseDouble(value) < drone.getCapability().getCostInitial();
                case "costFinal" -> Double.parseDouble(value) < drone.getCapability().getCostFinal();

                default -> false;
            };
            default -> false;
        };
        } catch(NumberFormatException e){
            return false;
        }

    }
}
