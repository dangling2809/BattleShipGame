package game.events;

/**
 * Denoting result of {@link FireEvent}
 */
public enum FireEventResultType {
    TARGET_HIT("hit"),
    TARGET_MISS("miss");

    private String value;

    FireEventResultType(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
