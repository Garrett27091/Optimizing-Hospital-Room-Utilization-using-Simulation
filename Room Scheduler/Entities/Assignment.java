package Entities;

public class Assignment {
    public Patient patient;
    public Provider doc;
    public Room room;
    public String assignTime;

    public Assignment(Patient patient, Provider doc, Room room) {
        this.patient = patient;
        this.doc = doc;
        this.room = room;
    }
}
