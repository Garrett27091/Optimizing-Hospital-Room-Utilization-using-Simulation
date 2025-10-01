package Entities;

public class Assignment {
    public Patient patient;
    public Provider doc;
    public Room room;
    public int assignDay;

    public Assignment(Patient patient, Provider doc, Room room, int day) {
        this.patient = patient;
        this.doc = doc;
        this.room = room;
        assignDay = day;
    }

    @Override
    public String toString() {
        return "Patient:" + patient.patientID + " Provider:" + doc.docID + " Room:" + room.roomID + " Day:" + assignDay;
    }
}
