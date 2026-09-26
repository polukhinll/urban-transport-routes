package first_task_13.model;

import java.time.LocalTime;
import java.util.Objects;

public record Trip(Route route, LocalTime departureTime) {

    public Trip {
        Objects.requireNonNull(route);
        Objects.requireNonNull(departureTime);
    }

    public LocalTime arrivalTimeAt(int stopIndex) {
        return departureTime.plus(
                route.timeFromStartToStop(stopIndex)
        );
    }
}
