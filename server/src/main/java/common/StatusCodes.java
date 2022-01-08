package common;

public enum StatusCodes {
    SUCCESS(1),
    NOT_FOUND(2);

    private final int value;

    StatusCodes(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }

}
