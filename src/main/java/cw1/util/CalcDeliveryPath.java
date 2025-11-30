package cw1.util;

import cw1.model.*;

import java.util.*;

import static cw1.service.Constants.step_size;

public class CalcDeliveryPath {

    private static class Node {
        Position pos;
        double fScore;

        Node(Position pos, double fScore) {
            this.pos = pos;
            this.fScore = fScore;
        }
    }

    public static class PathResult {
        public List<Position> path;
        public int moves;
        public double cost;

        public PathResult(List<Position> path, int moves, double cost) {
            this.path = path;
            this.moves = moves;
            this.cost = cost;
        }
    }

    private static double heuristic(Position p, Position goal) {
        return DistanceTo.distanceTo(p, goal) / step_size;
    }

    private static int countMoves(List<Position> path) {
        int moves = 0;
        for (int i = 1; i < path.size(); i++) {
            if (!path.get(i).equals(path.get(i - 1))) {
                moves++;
            }
        }
        return moves;
    }

    private static List<Position> reconstructPath(Map<Position, Position> cameFrom, Position current
    ) {
        List<Position> path = new ArrayList<>();

        Position node = current;
        path.add(node);

        while (cameFrom.containsKey(node)) {
            node = cameFrom.get(node);
            path.add(node);
        }

        Collections.reverse(path);

        return path;
    }

    private static String key(Position p) {
        long lng = Math.round(p.getLng() * 1_000_000);
        long lat = Math.round(p.getLat() * 1_000_000);
        return lng + "," + lat;
    }

    private static boolean entersNoFly(Position p, List<Region> regions) {
        if (regions == null) return false;
        for (Region r : regions) {
            if (IsInRegion.isInRegion(p, r)) return true;
        }
        return false;
    }

    private static int computeMovesBudget(Drone drone, Double maxCost) {

        int maxMovesByDrone = drone.getCapability().getMaxMoves();

        if (maxCost == null) {
            return maxMovesByDrone;
        }

        double costInitial = drone.getCapability().getCostInitial();
        double costFinal   = drone.getCapability().getCostFinal();
        double costPerMove = drone.getCapability().getCostPerMove();

        double budgetForMoves = maxCost - costInitial - costFinal;
        if (budgetForMoves <= 0) {
            return 0;
        }

        int maxMovesByCost = (int) Math.floor(budgetForMoves / costPerMove);

        return Math.min(maxMovesByDrone, maxMovesByCost);
    }

    public static PathResult calcDistanceAndCost(
            Position start,
            Drone drone,
            Position end,
            RestrictedRegionList restrictedRegionList,
            Double maxCost
    ) {

        List<Region> cachedRegions =
                (restrictedRegionList == null || restrictedRegionList.getRestrictedRegionList() == null)
                        ? List.of()
                        : restrictedRegionList.getRestrictedRegionList().stream()
                        .filter(Objects::nonNull)
                        .map(RestrictedRegion::toRegion)
                        .filter(Objects::nonNull)
                        .toList();

        PriorityQueue<Node> openSet =
                new PriorityQueue<>(Comparator.comparingDouble(n -> n.fScore));

        Map<Position, Position> cameFrom = new HashMap<>();
        Map<String, Integer> gScore = new HashMap<>();
        Set<String> closed = new HashSet<>();

        String startKey = key(start);
        gScore.put(startKey, 0);
        openSet.add(new Node(start, heuristic(start, end)));


        final long MAX_DURATION_NANOS = 2_000_000_000L;
        final long startTime = System.nanoTime();

        int maximumMoves = computeMovesBudget(drone, maxCost);

        while (!openSet.isEmpty()) {
            long elapsed = System.nanoTime() - startTime;
            if (elapsed > MAX_DURATION_NANOS) {
                return null;
            }

            Node node = openSet.poll();
            Position current = node.pos;
            String ck = key(current);

            if (closed.contains(ck)) continue;
            closed.add(ck);


            int gCurrent = gScore.getOrDefault(ck, Integer.MAX_VALUE);
            if (gCurrent > maximumMoves) {
                continue;
            }

            if (IsCloseTo.isCloseTo(current, end)) {
                List<Position> path = reconstructPath(cameFrom, current);
                int moves = countMoves(path);

                double cost = drone.getCapability().getCostInitial()
                        + drone.getCapability().getCostFinal()
                        + moves * drone.getCapability().getCostPerMove();

                return new PathResult(path, moves, cost);
            }

            for (int angle = 0; angle < 360; angle += 45) {
                Position neighbor = NextPosition.NextPosition(current, angle);
                String nk = key(neighbor);

                if (closed.contains(nk)) continue;
                if (entersNoFly(neighbor, cachedRegions)) continue;

                int tentativeG = gCurrent + 1;

                if (tentativeG > maximumMoves) continue;

                if (tentativeG < gScore.getOrDefault(nk, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(nk, tentativeG);

                    double f = tentativeG + heuristic(neighbor, end);
                    openSet.add(new Node(neighbor, f));
                }
            }
        }

        return null;
    }



    public static DroneDeliveryPath singleTrip(
            DroneServicePoint sp,
            Drone drone,
            List<MedDispatchRec> medDispatchRecs,
            RestrictedRegionList restrictedRegionList
    ) {

        Position spPos = sp.getLocation();
        Position currentPos = spPos;
        double remainingCapacity = drone.getCapability().getCapacity();
        int remainingMoves = drone.getCapability().getMaxMoves();

        List<MedDispatchRec> remaining = new ArrayList<>(medDispatchRecs);
        List<Delivery> deliveries = new ArrayList<>();
        int tripMoves = 0;

        while (!remaining.isEmpty()) {

            int finalRemainingMoves = remainingMoves;
            Position finalCurrentPos = currentPos;
            double finalRemainingCapacity = remainingCapacity;

            MedDispatchRec next = remaining.stream()
                    .filter(rec -> rec.getRequirements().getCapacity() <= finalRemainingCapacity)

                    .map(rec -> new AbstractMap.SimpleEntry<>(
                            rec,
                            CalcDeliveryPath.calcDistanceAndCost(
                                    finalCurrentPos, drone, rec.getDelivery(), restrictedRegionList, rec.getRequirements().getMaxCost())
                    ))
                    .filter(e -> e.getValue() != null)
                    .filter(e -> {
                        PathResult out = e.getValue();
                        PathResult back =
                                CalcDeliveryPath.calcDistanceAndCost(
                                        e.getKey().getDelivery(), drone, spPos, restrictedRegionList, null);

                        return back != null && (out.moves + back.moves) <= finalRemainingMoves;
                    })
                    .min(Comparator.comparingInt(e -> e.getValue().moves))
                    .map(AbstractMap.SimpleEntry::getKey)
                    .orElse(null);

            if (next == null) return null;

            PathResult outLeg =
                    CalcDeliveryPath.calcDistanceAndCost(
                            currentPos, drone, next.getDelivery(), restrictedRegionList, next.getRequirements().getMaxCost());

            if (outLeg == null) return null;

            List<Position> flightPath = new ArrayList<>(outLeg.path);

            if (!flightPath.isEmpty()) {
                flightPath.add(flightPath.getLast());
            }

            deliveries.add(new Delivery(next.getId(), flightPath));

            remainingMoves -= outLeg.moves;
            tripMoves += outLeg.moves;
            currentPos = next.getDelivery();

            remaining.remove(next);
        }

        PathResult backLeg =
                CalcDeliveryPath.calcDistanceAndCost(
                        currentPos, drone, spPos, restrictedRegionList, null);

        if (backLeg == null || backLeg.moves > remainingMoves) return null;

        Delivery last = deliveries.getLast();
        last.getFlightPath().addAll(backLeg.path);
        tripMoves += backLeg.moves;

        double tripCost =
                drone.getCapability().getCostInitial()
                        + drone.getCapability().getCostFinal()
                        + tripMoves * drone.getCapability().getCostPerMove();

        DronePath dronePath = new DronePath(Integer.parseInt(drone.getId()), deliveries);

        ArrayList<DronePath> dronePaths = new ArrayList<>();
        dronePaths.add(dronePath);

        return new DroneDeliveryPath(tripCost, tripMoves, dronePaths);
    }

    public static DroneDeliveryPath multiTrip(
            DroneServicePoint sp,
            Drone drone,
            List<MedDispatchRec> medDispatchRecs,
            RestrictedRegionList restrictedRegionList
    ) {
        Position spPos = sp.getLocation();

        List<MedDispatchRec> remaining = new ArrayList<>(medDispatchRecs);

        ArrayList<DronePath> dronePaths = new ArrayList<>();
        double totalCost = 0.0;
        int totalMoves = 0;

        while (!remaining.isEmpty()) {

            Position currentPos = spPos;
            int remainingMoves = drone.getCapability().getMaxMoves();
            double remainingCapacity = drone.getCapability().getCapacity();

            List<Delivery> tripDeliveries = new ArrayList<>();
            List<MedDispatchRec> deliveredThisTrip = new ArrayList<>();
            int tripMoves = 0;

            while (true) {
                int finalRemainingMoves = remainingMoves;
                Position finalCurrentPos = currentPos;
                double finalRemainingCapacity = remainingCapacity;


                MedDispatchRec next = remaining.stream()
                        .filter(rec -> rec.getRequirements().getCapacity() <= finalRemainingCapacity)

                        .map(rec -> new AbstractMap.SimpleEntry<>(
                                rec,
                                CalcDeliveryPath.calcDistanceAndCost(
                                        finalCurrentPos, drone, rec.getDelivery(), restrictedRegionList, rec.getRequirements().getMaxCost())
                        ))
                        .filter(e -> e.getValue() != null)
                        .filter(e -> {
                            PathResult out = e.getValue();
                            PathResult back =
                                    CalcDeliveryPath.calcDistanceAndCost(
                                            e.getKey().getDelivery(), drone, spPos, restrictedRegionList, null);

                            return back != null && (out.moves + back.moves) <= finalRemainingMoves;
                        })
                        .min(Comparator.comparingInt(e -> e.getValue().moves))
                        .map(AbstractMap.SimpleEntry::getKey)
                        .orElse(null);

                if (next == null) break;

                PathResult outLeg =
                        CalcDeliveryPath.calcDistanceAndCost(
                                currentPos, drone, next.getDelivery(), restrictedRegionList, next.getRequirements().getMaxCost());

                if (outLeg == null) break;

                List<Position> flightPath = new ArrayList<>(outLeg.path);

                if (!flightPath.isEmpty()) {
                    flightPath.add(flightPath.getLast());
                }

                tripDeliveries.add(new Delivery(next.getId(), flightPath));
                deliveredThisTrip.add(next);

                remainingMoves -= outLeg.moves;
                tripMoves += outLeg.moves;
                currentPos = next.getDelivery();
            }

            if (tripDeliveries.isEmpty()) {
                return null;
            }

            PathResult backLeg =
                    CalcDeliveryPath.calcDistanceAndCost(
                            currentPos, drone, spPos, restrictedRegionList, null);

            if (backLeg == null || backLeg.moves > remainingMoves) {
                return null;
            }

            Delivery last = tripDeliveries.getLast();
            last.getFlightPath().addAll(backLeg.path);
            tripMoves += backLeg.moves;

            double tripCost =
                    drone.getCapability().getCostInitial()
                            + drone.getCapability().getCostFinal()
                            + tripMoves * drone.getCapability().getCostPerMove();

            DronePath tripPath = new DronePath(Integer.parseInt(drone.getId()), tripDeliveries);

            dronePaths.add(tripPath);
            totalMoves += tripMoves;
            totalCost += tripCost;

            remaining.removeAll(deliveredThisTrip);
        }

        return new DroneDeliveryPath(totalCost, totalMoves, dronePaths);
    }
}
