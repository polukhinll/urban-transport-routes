package first_task_13.model;

import java.util.Objects;

public record Stop(String id, String name) {

    public Stop {
        Objects.requireNonNull(id, "id cannot be null");
        Objects.requireNonNull(name, "name cannot be null");

        if (id.isBlank()) {
            throw new IllegalArgumentException("Stop id cannot be blank");
        }

        if (name.isBlank()) {
            throw new IllegalArgumentException("Stop name cannot be blank");
        }
    }
