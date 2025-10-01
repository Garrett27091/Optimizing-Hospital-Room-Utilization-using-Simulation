import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import Entities.Patient;

public class CreatePatientQueue {
    public Queue<Patient> patientQueue = new LinkedList<>();

    public void generatePatientQueue(int size, String[] specialties) {
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            Patient newPatient = new Patient(rand.nextInt(30),specialties[rand.nextInt(specialties.length)]);
            patientQueue.add(newPatient);
        }
    }
}
