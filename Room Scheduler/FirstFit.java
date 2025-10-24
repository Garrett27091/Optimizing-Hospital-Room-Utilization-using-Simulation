
import Entities.*;

public class FirstFit {
    long sysStartTime = System.currentTimeMillis();
    long sysEndTime;

    public Assignment[] createSchedule(Patient[] patients, Room[] rooms, Provider[] docs, int days){
        Assignment[] schedule = new Assignment[patients.length];

        // loop through each assignment for the number of days to schedule
        for (int curPatient = 0; curPatient < patients.length; curPatient++) {
            int curRoom = 0;
            int curDoc = 0;

            boolean foundAssign = false;

            // first search for doctor to schedule to patient
            while (curDoc < docs.length && !foundAssign) {
                
                // check for specialty match and if doctor has an available shift
                if (patients[curPatient].specialty.equals(docs[curDoc].specialty) && (docs[curDoc].curShifts < docs[curDoc].maxShifts)) {
                    // after finding doctor search for room
                    while (curRoom < rooms.length && !foundAssign) {
                        if (patients[curPatient].specialty.equals(rooms[curRoom].specialty)) {
                            // with doctor and room pairing search for available day starting w/ day 1
                            int day = 1;
                            while (day < days+1 && !foundAssign) {
                                //check to see if assignment for room or doctor is already created on that day
                                boolean isConflict = false;
                                for (int i = 0; i < curPatient; i++) {
                                    if (schedule[i] == null) {
                                        continue;
                                    }
                                    if ((schedule[i].room.roomID == rooms[curRoom].roomID && schedule[i].assignDay == day) || (schedule[i].doc.docID == docs[curDoc].docID && schedule[i].assignDay == day)) {
                                        isConflict = true;
                                    }
                                }
                                if (!isConflict) {
                                    //create new assignment for successful match
                                    Assignment newSchedule = new Assignment(patients[curPatient], docs[curDoc], rooms[curRoom], day, System.currentTimeMillis(), System.currentTimeMillis()-sysStartTime);
                                    //update room and provider to be scheduled
                                    docs[curDoc].addShift();

                                    schedule[curPatient] = newSchedule;
                                    foundAssign = true;
                                }
                                day++;
                            }
                        }
                        curRoom++;
                    }
                }
                curDoc++;
            }  
        } 
        sysEndTime = System.currentTimeMillis();
        return schedule;           
    }
    public double getRunTime() {
        return (sysEndTime - sysStartTime)/1000.0;
    }
}