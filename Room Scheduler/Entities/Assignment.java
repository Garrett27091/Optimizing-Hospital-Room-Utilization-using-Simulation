package Entities;

public class Assignment {
    public Patient patient;
    public Provider doc;
    public Room room;
    public int assignDay;
    public long timeCreated;
    public long simTimeCreated;

    public Assignment(Patient patient, Provider doc, Room room, int day, long timeCreated, long simTimeCreated) {
        this.patient = patient;
        this.doc = doc;
        this.room = room;
        assignDay = day;
        this.timeCreated = timeCreated;
        this.simTimeCreated = simTimeCreated;
    }

    @Override
    public String toString() {
        return "Patient:" + patient.patientID + " Provider:" + doc.docID + " Room:" + room.roomID + " Day:" + assignDay;
    }

    public String toCSVString() {
        return patient.patientID + "," + doc.docID + "," + room.roomID + "," + assignDay + "," + timeCreated + "," + simTimeCreated + "\n";
    }
}
