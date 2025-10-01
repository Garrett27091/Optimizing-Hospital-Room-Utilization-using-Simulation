package Entities;

public class Room {
    public boolean isScheduledAM = false;
    public boolean isScheduledPM = false;
    public boolean hasPatient = false;
    public String specialty;

    public void AssignProvider(Provider doc) {
        return;
    }

    public void assignPatient(Patient pat) {
        return;
    }

    public void emptyRoom() {
        hasPatient = false;
    }

    public Room(String specialty) {
        this.specialty = specialty;
    }
}
