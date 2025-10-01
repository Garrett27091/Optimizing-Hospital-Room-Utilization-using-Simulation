import java.util.Random;

import Entities.*;

public class CreateEntitytList {

    public Patient[] generatePatientList(int size, String[] specialties) {
        Random rand = new Random();
        Patient[] patientList = new Patient[size];
        for (int i = 0; i < size; i++) {
            Patient newPatient = new Patient(rand.nextInt(30),specialties[rand.nextInt(specialties.length)], i);
            patientList[i] = newPatient;
        }
        return patientList;
    }
    public Provider[] generateProviderList(int size, String[] specialties) {
        Random rand = new Random();
        Provider[] providerList = new Provider[size];
        for (int i = 0; i < size; i++) {
            Provider newDoc = new Provider(rand.nextInt(10),specialties[rand.nextInt(specialties.length)], i);
            providerList[i] = newDoc;
        }
        return providerList;
    }
    public Room[] generateRoomList(int size, String[] specialties) {
        Random rand = new Random();
        Room[] roomList = new Room[size];
        for (int i = 0; i < size; i++) {
            Room newRoom = new Room(specialties[rand.nextInt(specialties.length)], i);
            roomList[i] = newRoom;
        }
        return roomList;
    }
}