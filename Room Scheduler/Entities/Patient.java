package Entities;

public class Patient {
    public int waitTime;
    public String specialty;
    public int patientID;

    public Patient(int waitTime, String Specailty, int patientID) {
        this.waitTime = waitTime;
        this.specialty = Specailty;
        this.patientID = patientID;
    }

    @Override
    public String toString() {

        return "ID:" + patientID + " " + specialty + " " + waitTime;
    }
}
