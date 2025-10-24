package Entities;

public class Room {
    public boolean isScheduledAM = false;
    public boolean isScheduledPM = false;
    public String[] specialties;
    public int roomID;

    public void AssignProvider(Provider doc) {
        return;
    }

    public void assignPatient(Patient pat) {
        return;
    }

    public Room(String specialty, int roomID) {
        this.specialties = new String[]{specialty};
        this.roomID = roomID;
    }

    public Room(String[] specialties, int roomID) {
        this.specialties = specialties;
        this.roomID = roomID;
    }

    @Override
    public String toString() {
        return "ID: " + roomID + " " + specialties;
    }
}
