import java.io.*;
import Entities.*;

public class test {
    public static void main(String[] args) {
        CreateEntitytList makeLists = new CreateEntitytList();
        FirstFit firstFit = new FirstFit();
        ABC abc = new ABC();
        
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

        System.out.println("First Fit Schedule");
        System.out.println("________________________________________");

        Assignment[] firstFitSchedule = firstFit.createSchedule(patientList, roomList, providerList, 14);

        Assignment[] artificialBeeColonySchedule = abc.createSchedule(patientList, providerList, roomList, 14);

        for (Assignment a : firstFitSchedule) {
            System.out.println(a);
        }

        System.out.println("Artifical Bee Colony Algorithm");
        System.out.println("________________________________________");

        for (Assignment a : artificialBeeColonySchedule) {
            System.out.println(a);
        }
        try {
            writeCSV(firstFitSchedule, "firstFitTest.csv");
            writeCSV(artificialBeeColonySchedule, "abcTest.csv");
        } catch (IOException e) {}

    }
    // csv file export method
    public static void writeCSV(Assignment[] schedule, String fileName) throws IOException {
        File csvOutput = new File(fileName);
        try (PrintWriter pw = new PrintWriter(csvOutput)) {
            for (Assignment a : schedule) {
                if (a == null) {
                    continue;
                }
                pw.write(a.toCSVString());
            }
        } catch (IOException e) {
            System.out.println("Error writing file");
        }
    }
}
