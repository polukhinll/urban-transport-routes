package first_task_13.model;

public enum TransportType {

    BUS(35, 80),
    TRAM(25, 150),
    TROLLEYBUS(30, 100),
    METRO(45, 300);

    private final int averageSpeed;
    private final int capacity;

    TransportType(int averageSpeed, int capacity) {
        this.averageSpeed = averageSpeed;
        this.capacity = capacity;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public int getCapacity() {
        return capacity;
    }
}