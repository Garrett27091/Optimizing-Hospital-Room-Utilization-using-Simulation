package Entities;

public class Provider {
    public boolean scheudledDay = false;
    public boolean scheudledNight = false;
    public int maxShifts = 5;
    public String specialty;

    Provider(boolean scheudledDay, boolean scheudledNight, String specialty) {
        this.scheudledDay = scheudledDay;
        this.scheudledNight = scheudledNight;
        this.specialty = specialty;
    }
}
