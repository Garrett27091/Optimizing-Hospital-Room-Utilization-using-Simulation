import Entities.*;

public class test {
    public static void main(String[] args) {
        CreateEntitytList makeLists = new CreateEntitytList();
        FirstFit firstFitSchedule = new FirstFit();
        
        String[] specialties = {"Anes", "Card", "OBGYN"};
        // set up parameters for patients
        int patientCount = 100;
        Patient[] patientList = makeLists.generatePatientList(patientCount, specialties);
        // set up parameters for provider numbers
        int providerCount = 20;
        Provider[] providerList = makeLists.generateProviderList(providerCount, specialties);
        // set number and type of rooms
        int roomCount = 10;
        Room[] roomList = makeLists.generateRoomList(roomCount, specialties);

        System.out.println("Patients:");
        for (Patient p : patientList) {
            System.out.println(p);
        }
        System.out.println("Providers:");
        for (Provider p : providerList) {
            System.out.println(p);
        }
        System.out.println("Rooms: ");
        for (Room r : roomList) {
            System.out.println(r);
        }

        System.out  .println("________________________________________");

        Assignment[] schedule = firstFitSchedule.createSchedule(patientList, roomList, providerList, 14);

        for (Assignment a : schedule) {
            System.out.println(a);
        }
    }
}
