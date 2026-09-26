package first_task_13;

import first_task_13.model.Route;
import first_task_13.model.Stop;
import first_task_13.model.TransportType;
import first_task_13.model.Trip;
import first_task_13.service.TransportService;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        TransportService service = new TransportService();

        Stop center = new Stop("S1", "Центр");
        Stop station = new Stop("S2", "Вокзал");
        Stop university = new Stop("S3", "Университет");
        Stop park = new Stop("S4", "Парк");
        Stop hospital = new Stop("S5", "Больница");

        Stop airport = new Stop("S6", "Аэропорт");
        Stop market = new Stop("S7", "Рынок");
        Stop stadium = new Stop("S8", "Стадион");
        Stop school = new Stop("S9", "Школа");
        Stop mall = new Stop("S10", "Торговый центр");

        Route route10 = new Route(
                "10",
                TransportType.BUS,
                List.of(center, station, university, park),
                List.of(
                        Duration.ofMinutes(10),
                        Duration.ofMinutes(7),
                        Duration.ofMinutes(12)
                )
        );

        Route route20 = new Route(
                "20",
                TransportType.TRAM,
                List.of(station, center, hospital, stadium),
                List.of(
                        Duration.ofMinutes(8),
                        Duration.ofMinutes(10),
                        Duration.ofMinutes(9)
                )
        );

        Route route30 = new Route(
                "30",
                TransportType.TROLLEYBUS,
                List.of(airport, center, market, mall),
                List.of(
                        Duration.ofMinutes(20),
                        Duration.ofMinutes(6),
                        Duration.ofMinutes(8)
                )
        );

        Route route40 = new Route(
                "40",
                TransportType.BUS,
                List.of(school, university, center, stadium),
                List.of(
                        Duration.ofMinutes(6),
                        Duration.ofMinutes(11),
                        Duration.ofMinutes(13)
                )
        );

        Route route50 = new Route(
                "50",
                TransportType.METRO,
                List.of(airport, station, center, hospital),
                List.of(
                        Duration.ofMinutes(15),
                        Duration.ofMinutes(5),
                        Duration.ofMinutes(7)
                )
        );

        service.addRoute(route10);
        service.addRoute(route20);
        service.addRoute(route30);
        service.addRoute(route40);
        service.addRoute(route50);

        service.addTrip(new Trip(route10, LocalTime.of(8, 0)));
        service.addTrip(new Trip(route10, LocalTime.of(8, 30)));
        service.addTrip(new Trip(route10, LocalTime.of(9, 0)));

        service.addTrip(new Trip(route20, LocalTime.of(8, 10)));
        service.addTrip(new Trip(route20, LocalTime.of(8, 40)));
        service.addTrip(new Trip(route20, LocalTime.of(9, 10)));

        service.addTrip(new Trip(route30, LocalTime.of(8, 15)));
        service.addTrip(new Trip(route30, LocalTime.of(8, 45)));
        service.addTrip(new Trip(route30, LocalTime.of(9, 15)));

        service.addTrip(new Trip(route40, LocalTime.of(8, 20)));
        service.addTrip(new Trip(route40, LocalTime.of(8, 50)));
        service.addTrip(new Trip(route40, LocalTime.of(9, 20)));

        service.addTrip(new Trip(route50, LocalTime.of(8, 5)));
        service.addTrip(new Trip(route50, LocalTime.of(8, 35)));
        service.addTrip(new Trip(route50, LocalTime.of(9, 5)));

        System.out.println("=== РАСПИСАНИЕ ПО ОСТАНОВКЕ ===");

        service.scheduleForStop(center)
                .forEach(arrival ->
                        System.out.println(
                                "Маршрут "
                                        + arrival.routeNumber()
                                        + " -> "
                                        + arrival.stop().name()
                                        + " в "
                                        + arrival.arrivalTime()
                        )
                );

        System.out.println("\n=== РАСЧЁТ ПРИБЫТИЯ ===");

        Trip trip = new Trip(
                route10,
                LocalTime.of(8, 0)
        );

        LocalTime arrival =
                service.calculateArrival(trip, 2);

        System.out.println(
                "Маршрут 10, отправление в 08:00"
        );

        System.out.println(
                "Прибытие на остановку №3: "
                        + arrival
        );

        System.out.println("\n=== ИНТЕРВАЛЫ МАРШРУТА 10 ===");

        service.movementIntervals(route10)
                .forEach(interval ->
                        System.out.println(
                                interval.toMinutes()
                                        + " минут"
                        )
                );

        System.out.println(
                "\n=== ПРЯМЫЕ МАРШРУТЫ ==="
        );

        service.directRoutes(
                        station,
                        park
                )
                .forEach(route ->
                        System.out.println(
                                "Маршрут №"
                                        + route.getNumber()
                        )
                );

        System.out.println(
                "\n=== ОБЩЕЕ ВРЕМЯ В ПУТИ ==="
        );

        Duration travelTime =
                service.totalTravelTime(
                        route10,
                        center,
                        park
                );

        System.out.println(
                "Центр -> Парк: "
                        + travelTime.toMinutes()
                        + " минут"
        );
    }
}