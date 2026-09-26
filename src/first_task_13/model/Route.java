package first_task_13.model;

import first_task_13.exception.TransportException;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class Route {

    private final String number;
    private final TransportType transportType;
    private final List<Stop> stops;
    private final List<Duration> travelTimes;

    public Route(
            String number,
            TransportType transportType,
            List<Stop> stops,
            List<Duration> travelTimes
    ) {
        Objects.requireNonNull(number);
        Objects.requireNonNull(transportType);
        Objects.requireNonNull(stops);
        Objects.requireNonNull(travelTimes);

        if (number.isBlank()) {
            throw new IllegalArgumentException("Route number cannot be blank");
        }

        if (stops.size() < 2) {
            throw new IllegalArgumentException(
                    "Route must contain at least two stops"
            );
        }

        if (travelTimes.size() != stops.size() - 1) {
            throw new IllegalArgumentException(
                    "Number of travel times must be stops count - 1"
            );
        }

        if (travelTimes.stream().anyMatch(time ->
                time == null || time.isZero() || time.isNegative())) {
            throw new IllegalArgumentException(
                    "Travel time must be positive"
            );
        }

        this.number = number;
        this.transportType = transportType;
        this.stops = List.copyOf(stops);
        this.travelTimes = List.copyOf(travelTimes);
    }

    public String getNumber() {
        return number;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public List<Stop> getStops() {
        return List.copyOf(stops);
    }

    public List<Duration> getTravelTimes() {
        return List.copyOf(travelTimes);
    }

    public boolean contains(Stop stop) {
        return stops.contains(stop);
    }

    public int indexOf(Stop stop) {
        return stops.indexOf(stop);
    }

    /**
     * Возвращает время движения между двумя остановками
     * данного маршрута.
     */
    public Duration travelTimeBetween(Stop from, Stop to) {
        int fromIndex = stops.indexOf(from);
        int toIndex = stops.indexOf(to);

        if (fromIndex == -1 || toIndex == -1) {
            throw new TransportException(
                    "Both stops must belong to the route"
            );
        }

        if (fromIndex >= toIndex) {
            throw new TransportException(
                    "Destination must be after starting stop"
            );
        }

        Duration result = Duration.ZERO;

        for (int i = fromIndex; i < toIndex; i++) {
            result = result.plus(travelTimes.get(i));
        }

        return result;
    }

    /**
     * Возвращает время прибытия на указанную остановку,
     * если отправление было в departure.
     */
    public Duration timeFromStartToStop(int stopIndex) {
        if (stopIndex < 0 || stopIndex >= stops.size()) {
            throw new IndexOutOfBoundsException(
                    "Invalid stop index: " + stopIndex
            );
        }

        Duration result = Duration.ZERO;

        for (int i = 0; i < stopIndex; i++) {
            result = result.plus(travelTimes.get(i));
        }

        return result;
    }

    @Override
    public String toString() {
        return "Route{" +
                "number='" + number + '\'' +
                ", transportType=" + transportType +
                ", stops=" + stops +
                '}';
    }
}