package first_task_13.service;

import first_task_13.exception.TransportException;
import first_task_13.model.Route;
import first_task_13.model.Stop;
import first_task_13.model.Trip;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TransportService {

    private final List<Route> routes = new ArrayList<>();
    private final List<Trip> trips = new ArrayList<>();

    public void addRoute(Route route) {
        routes.add(route);
    }

    public void addTrip(Trip trip) {
        if (!routes.contains(trip.route())) {
            throw new TransportException(
                    "Route must be registered before adding trip"
            );
        }

        trips.add(trip);
    }

    public List<Arrival> scheduleForStop(Stop stop) {

        List<Arrival> result = new ArrayList<>();

        for (Trip trip : trips) {

            Route route = trip.route();

            int stopIndex = route.indexOf(stop);

            if (stopIndex == -1) {
                continue;
            }

            LocalTime arrival =
                    trip.arrivalTimeAt(stopIndex);

            result.add(
                    new Arrival(
                            route.getNumber(),
                            stop,
                            arrival
                    )
            );
        }

        result.sort(
                Comparator.comparing(Arrival::arrivalTime)
        );

        return List.copyOf(result);
    }

    public LocalTime calculateArrival(
            Trip trip,
            int stopIndex
    ) {
        return trip.arrivalTimeAt(stopIndex);
    }

    public List<Duration> movementIntervals(Route route) {

        List<LocalTime> departures = trips.stream()
                .filter(trip -> trip.route().equals(route))
                .map(Trip::departureTime)
                .sorted()
                .toList();

        List<Duration> intervals = new ArrayList<>();

        for (int i = 1; i < departures.size(); i++) {
            intervals.add(
                    Duration.between(
                            departures.get(i - 1),
                            departures.get(i)
                    )
            );
        }

        return List.copyOf(intervals);
    }

    public List<Route> directRoutes(
            Stop from,
            Stop to
    ) {

        return routes.stream()
                .filter(route -> {
                    int fromIndex = route.indexOf(from);
                    int toIndex = route.indexOf(to);

                    return fromIndex != -1
                            && toIndex != -1
                            && fromIndex < toIndex;
                })
                .toList();
    }

    public Duration totalTravelTime(
            Route route,
            Stop from,
            Stop to
    ) {
        return route.travelTimeBetween(from, to);
    }

    public List<Route> getRoutes() {
        return List.copyOf(routes);
    }

    public List<Trip> getTrips() {
        return List.copyOf(trips);
    }

    public record Arrival(
            String routeNumber,
            Stop stop,
            LocalTime arrivalTime
    ) {
    }
}