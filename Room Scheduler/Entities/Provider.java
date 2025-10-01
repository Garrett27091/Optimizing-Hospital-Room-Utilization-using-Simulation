package Entities;

public class Provider {
    public int maxShifts;
    public int curShifts = 0;
    public String specialty;
    public int docID;

    public void addShift() {
        if (curShifts < maxShifts) {
            curShifts++;
        }
    }

    public Provider(int maxShifts, String specialty, int docID) {
        this.specialty = specialty;
        this.maxShifts = maxShifts;
        this.docID = docID;
    }

    @Override
    public String toString() {
        return "ID: " + docID + " " + specialty + " Max Shifts: " + maxShifts + " Current Shifts: " + curShifts;
    }
}
