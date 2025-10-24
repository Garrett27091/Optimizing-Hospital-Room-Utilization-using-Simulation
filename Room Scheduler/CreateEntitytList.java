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
            Provider newDoc = new Provider(rand.nextInt(9)+1,specialties[rand.nextInt(specialties.length)], i);
            providerList[i] = newDoc;
        }
        return providerList;
    }
    public Room[] generateRoomList(int size, String[] specialties) {
        Random rand = new Random();
        Room[] roomList = new Room[size];
        for (int i = 0; i < size; i++) {
            String[] randSps = new String[rand.nextInt(specialties.length-1)+1];
            for (int j = 0; j < randSps.length; j++) {
                randSps[j] = specialties[rand.nextInt(specialties.length)];
            }
            Room newRoom = new Room(randSps, i);
            roomList[i] = newRoom;
        }
        return roomList;
    }
}