package common;


public enum ConnectionStatus {
    PENDING,
    ACCEPTED,
    DECLINED;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
