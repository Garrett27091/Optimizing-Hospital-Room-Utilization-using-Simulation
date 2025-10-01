package Entities;

public class Provider {
    public int maxShifts;
    public int curShifts = 0;
    public String specialty;

    public void addShift() {
        if (curShifts < maxShifts) {
            curShifts++;
        }
    }

    public Provider(int maxShifts, String specialty) {
        this.specialty = specialty;
        this.maxShifts = maxShifts;
    }
}
