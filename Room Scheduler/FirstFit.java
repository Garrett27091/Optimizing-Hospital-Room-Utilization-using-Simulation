
import Entities.*;

public class FirstFit {
    public Assignment[] createSchedule(Patient[] patients, Room[] rooms, Provider[] docs, int days){
        Assignment[] schedule = new Assignment[days*rooms.length*2];

        // loop through each assignment for the number of days to schedule
        for (int i = 0; i < schedule.length; i++) {
            int curPatient = 0;
            int curRoom = 0;
            int curDoc = 0;
            int day = 1;

            boolean foundAssign = false;

            while (day < days+1 && !foundAssign) {
                while (curPatient < patients.length && !foundAssign) {
                    // first search for doctor to schedule to patient
                    while (curDoc < docs.length && !foundAssign) {
                        if (patients[curPatient].specialty.equals(docs[curDoc].specialty) && (docs[curDoc].curShifts < docs[curDoc].maxShifts)) {
                            while (curRoom < rooms.length && !foundAssign) {
                                if (patients[curPatient].specialty.equals(rooms[curRoom].specialty) && (!rooms[curRoom].hasPatient)) {
                                    Assignment newSchedule = new Assignment(patients[curPatient], docs[curDoc], rooms[curRoom], day);
                                    schedule[i] = newSchedule;
                                    foundAssign = true;
                                } else {
                                    curRoom++;
                                }
                            }
                        } else {
                            curDoc++;
                        }
                    }   
                }
                day++;
            }
            
        }
        return schedule;
    }
}