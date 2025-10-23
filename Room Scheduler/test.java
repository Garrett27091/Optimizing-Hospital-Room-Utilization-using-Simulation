import java.io.*;
import java.util.Scanner;

import Entities.*;

public class test {
    static int patientCount;
    static int providerCount;
    static int roomCount;
    static String[] specialties;
    public static void main(String[] args) {
        CreateEntitytList makeLists = new CreateEntitytList();
        FirstFit firstFit = new FirstFit();
        ABC abc = new ABC();
        
        Scanner scan = new Scanner(System.in);

        // prompt user input for specilaties number
        System.out.println("Enter number of specialties: (enter 0 for all default settings)");
        int specNum = scan.nextInt();

        // default parameters
        if (specNum == 0) {
            specialties = new String[]{"Anes", "Card", "OBGYN"};
            patientCount = 100;
            providerCount = 20;
            roomCount = 10;
        } 
        // user input initalization
        else {
            scan.nextLine();
            specialties = new String[specNum];
            for (int i = 1; i < specNum+1; i++) {
                System.out.println("Enter Specialty " + i);
                specialties[i-1] = scan.nextLine();
            }

            System.out.println("Enter number of patients:");
            patientCount = scan.nextInt();

            System.out.println("Enter number of providers:");
            providerCount = scan.nextInt();
            
            System.out.println("Enter number of rooms:");
            roomCount = scan.nextInt();       
        }
        
        Patient[] patientList = makeLists.generatePatientList(patientCount, specialties);
        Provider[] providerList = makeLists.generateProviderList(providerCount, specialties);
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
        System.out.println("First Fit Runtime: " + firstFit.getRunTime());

        for (Assignment a : firstFitSchedule) {
            System.out.println(a);
        }

        Assignment[] artificialBeeColonySchedule = abc.createSchedule(patientList, providerList, roomList, 14);

        System.out.println("Artifical Bee Colony Algorithm");
        System.out.println("________________________________________");

        for (Assignment a : artificialBeeColonySchedule) {
            System.out.println(a);
        }

        System.out.println("ABC Runtime: " + abc.getRunTime());

        try {
            writeCSV(firstFitSchedule, patientList, "firstFitTest.csv");
            writeCSV(artificialBeeColonySchedule, patientList, "abcTest.csv");
        } catch (IOException e) {}
    }
    // csv file export method
    public static void writeCSV(Assignment[] schedule, Patient[] patients, String fileName) throws IOException {
        File csvOutput = new File(fileName);
        try (PrintWriter pw = new PrintWriter(csvOutput)) {
            pw.write("Patient Count: " + patientCount + "," + "Provider Count: " + providerCount + "," + "Room Count: " + roomCount + "," + "Specialty Count: " + specialties.length + "\n");
            pw.write("Patient Scheduling Rate: " + calcPatientRatio(schedule) + "\n");
            pw.write("Patient ID,Provider ID,Room ID,Day Assigned,System Time Created, Simulation Time Created\n");
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
    // calculates ratio of patients successfully scheduled
    public static double calcPatientRatio(Assignment[] schedule) {
        int unscheduled = 0;
        for (Assignment a : schedule) {
            if (a == null) {
                unscheduled++;
            }
        }
        return (double) (patientCount - unscheduled) / patientCount;
    }
}
