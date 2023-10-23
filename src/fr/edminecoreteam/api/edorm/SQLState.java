package fr.edminecoreteam.api.edorm;

public enum SQLState {

    DISCONNECTED("DISCONNECTED", 0),
    CONNECTED("CONNECTED", 1);

    private SQLState(String name, int ordinal) {}

}
