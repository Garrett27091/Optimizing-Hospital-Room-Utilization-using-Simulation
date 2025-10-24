import java.util.*;
import Entities.*;

public class ABC {

    // initiatize bee algorithm parameters
    public final int populationSize = 40;
    public final int maxIterations = 1000;
    public final int limit = 100; // improvement counter
    public final int employedBees = 20;
    public final int onlookerBees = 15;
    public final static int conflictPenalty = 100000; // high penalty to prevent double scheduling
    public final static long sysStartTime = System.currentTimeMillis();
    public long sysEndTime;

    static final Random rand = new Random();

    public Assignment[] createSchedule(Patient[] patients, Provider[] providers, Room[] rooms, int days) {
        if (patients == null || patients.length == 0) return new Assignment[0];

        // initialize population of solutions
        List<Solution> population = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            Solution s = randomSolution(patients, providers, rooms, days);
            evaluateSolution(s);
            population.add(s);
        }

        // initalize empty best schedule
        Solution best = null;
        for (Solution s : population) {
            if (best == null || compareSolutions(s, best) > 0) best = copy(s);
        }

        // ABC main loop
        for (int iter = 0; iter < maxIterations; iter++) {
            // employed bee phase
            for (int i = 0; i < employedBees && i < population.size(); i++) {
                Solution current = population.get(i);
                Solution neighbor = neighborSolution(current, patients, providers, rooms, days);
                evaluateSolution(neighbor);
                if (compareSolutions(neighbor, current) > 0) {
                    population.set(i, neighbor);
                    neighbor.trials = 0;
                    if (compareSolutions(neighbor, best) > 0) best = copy(neighbor);
                } else {
                    current.trials++;
                }
            }

            // onlooker bee phase
            double[] probabilities = calculateSelectionProbabilities(population);
            for (int i = 0; i < onlookerBees; i++) {
                int chosenIndex = rouletteSelect(probabilities);
                Solution chosen = population.get(chosenIndex);
                Solution neighbor = neighborSolution(chosen, patients, providers, rooms, days);
                evaluateSolution(neighbor);
                if (compareSolutions(neighbor, chosen) > 0) {
                    population.set(chosenIndex, neighbor);
                    neighbor.trials = 0;
                    if (compareSolutions(neighbor, best) > 0) best = copy(neighbor);
                } else {
                    chosen.trials++;
                }
            }

            // scout bee phase
            for (int i = 0; i < population.size(); i++) {
                Solution s = population.get(i);
                if (s.trials >= limit) {
                    Solution scout = randomSolution(patients, providers, rooms, days);
                    evaluateSolution(scout);
                    population.set(i, scout);
                    if (compareSolutions(scout, best) > 0) best = copy(scout);
                }
            }
        }
        sysEndTime = System.currentTimeMillis();
        return best.assignments;
    }

    public double getRunTime() {
        return (sysEndTime - sysStartTime)/1000.0;
    }

    private static class Solution {
        Assignment[] assignments;
        int fitness;
        int conflicts;
        int scheduledCount;
        int trials;
    }

    // random solution generation method 

    private static Solution randomSolution(Patient[] patients, Provider[] providers, Room[] rooms, int days) {
        Solution s = new Solution();
        s.assignments = new Assignment[patients.length];
        s.trials = 0;

        // track how many shifts each provider has been assigned
        Map<Provider, Integer> providerShiftCount = new HashMap<>();

        for (int i = 0; i < patients.length; i++) {
            if (rand.nextDouble() < 0.5) { // 50% chance to assign
                Provider doc = getAvailableProvider(providers, providerShiftCount);
                if (doc == null) {
                    s.assignments[i] = null;
                    continue;
                }
                Room room = rooms[rand.nextInt(rooms.length)];
                int day = rand.nextInt(days);
                s.assignments[i] = new Assignment(patients[i], doc, room, day, System.currentTimeMillis(), System.currentTimeMillis()-sysStartTime);
                providerShiftCount.put(doc, providerShiftCount.getOrDefault(doc, 0) + 1);
            } else {
                s.assignments[i] = null;
            }
        }
        return s;
    }

    // neighbor selection
    private static Solution neighborSolution(Solution base, Patient[] patients, Provider[] providers, Room[] rooms, int days) {
        Solution copy = copy(base);

        // Recalculate provider usage before modification
        Map<Provider, Integer> providerShiftCount = new HashMap<>();
        for (Assignment a : copy.assignments) {
            if (a != null) {
                providerShiftCount.put(a.doc, providerShiftCount.getOrDefault(a.doc, 0) + 1);
            }
        }

        int modifications = 1 + rand.nextInt(3);
        for (int m = 0; m < modifications; m++) {
            int idx = rand.nextInt(copy.assignments.length);
            double r = rand.nextDouble();

            if (copy.assignments[idx] == null) {
                // try scheduling an unscheduled patient
                if (r < 0.5) {
                    Provider prov = getAvailableProvider(providers, providerShiftCount);
                    if (prov == null) {
                        continue;
                    }
                    Room room = rooms[rand.nextInt(rooms.length)];
                    int day = rand.nextInt(days);
                    copy.assignments[idx] = new Assignment(patients[idx], prov, room, day, System.currentTimeMillis(), System.currentTimeMillis()-sysStartTime);
                    providerShiftCount.put(prov, providerShiftCount.getOrDefault(prov, 0) + 1);
                }
            } else {
                // unschedule or modify
                if (r < 0.5) {
                    Provider oldProv = copy.assignments[idx].doc;
                    providerShiftCount.put(oldProv, providerShiftCount.getOrDefault(oldProv, 1) - 1);
                    copy.assignments[idx] = null;
                } else {
                    Provider newProv = getAvailableProvider(providers, providerShiftCount);
                    if (newProv == null) continue;
                    Room room = rooms[rand.nextInt(rooms.length)];
                    int day = rand.nextInt(days);
                    Provider oldProv = copy.assignments[idx].doc;
                    providerShiftCount.put(oldProv, providerShiftCount.getOrDefault(oldProv, 1) - 1);
                    copy.assignments[idx] = new Assignment(patients[idx], newProv, room, day, System.currentTimeMillis(), System.currentTimeMillis()-sysStartTime);
                    providerShiftCount.put(newProv, providerShiftCount.getOrDefault(newProv, 0) + 1);
                }
            }
        }
        return copy;
    }

    // get a provider w/ available shift capacity
    private static Provider getAvailableProvider(Provider[] providers, Map<Provider, Integer> shiftCount) {
        List<Provider> available = new ArrayList<>();
        for (Provider p : providers) {
            int used = shiftCount.getOrDefault(p, 0);
            if (used < p.maxShifts) {
                available.add(p);
            }
        }
        if (available.isEmpty()) return null;
        return available.get(rand.nextInt(available.size()));
    }

    // fitness evaulation
    private static void evaluateSolution(Solution s) {
        int conflicts = 0;
        int scheduled = 0;

        // track usage per day
        Map<Integer, Set<Provider>> providersPerDay = new HashMap<>();
        Map<Integer, Set<Room>> roomsPerDay = new HashMap<>();
        Map<Provider, Integer> providerShiftCount = new HashMap<>();

        for (Assignment a : s.assignments) {
            if (a == null) continue;
            scheduled++;
            int day = a.assignDay;

            providersPerDay.putIfAbsent(day, new HashSet<>());
            roomsPerDay.putIfAbsent(day, new HashSet<>());

            if (!providersPerDay.get(day).add(a.doc)) conflicts++;
            if (!roomsPerDay.get(day).add(a.room)) conflicts++;

            providerShiftCount.put(a.doc, providerShiftCount.getOrDefault(a.doc, 0) + 1);
            if (providerShiftCount.get(a.doc) > a.doc.maxShifts) conflicts++;
        }

        s.conflicts = conflicts;
        s.scheduledCount = scheduled;

        if (conflicts > 0) {
            s.fitness = -conflicts * conflictPenalty + scheduled;
        } else {
            s.fitness = scheduled;
        }
    }

    private static int compareSolutions(Solution a, Solution b) {
        if (a.fitness != b.fitness) {
            return Integer.compare(a.fitness, b.fitness);
        }
        if (a.conflicts != b.conflicts) {
            return Integer.compare(b.conflicts, a.conflicts);
        }
        return Integer.compare(a.scheduledCount, b.scheduledCount);
    }

    private static Solution copy(Solution s) {
        Solution c = new Solution();
        c.assignments = new Assignment[s.assignments.length];
        for (int i = 0; i < s.assignments.length; i++) {
            Assignment a = s.assignments[i];
            if (a == null) {
                c.assignments[i] = null;
            } else {
                c.assignments[i] = new Assignment(a.patient, a.doc, a.room, a.assignDay, a.timeCreated, a.simTimeCreated);
            }
        }
        c.fitness = s.fitness;
        c.conflicts = s.conflicts;
        c.scheduledCount = s.scheduledCount;
        c.trials = s.trials;
        return c;
    }

    private static double[] calculateSelectionProbabilities(List<Solution> population) {
        double[] raw = new double[population.size()];
        double sum = 0.0;
        for (int i = 0; i < population.size(); i++) {
            double val = population.get(i).fitness;
            if (val < 0) val = 1.0 / (1 - val);
            raw[i] = val;
            sum += raw[i];
        }
        double[] probs = new double[population.size()];
        if (sum <= 0) {
            Arrays.fill(probs, 1.0 / probs.length);
        } else {
            for (int i = 0; i < probs.length; i++) probs[i] = raw[i] / sum;
        }
        return probs;
    }

    private static int rouletteSelect(double[] probs) {
        double r = rand.nextDouble();
        double acc = 0.0;
        for (int i = 0; i < probs.length; i++) {
            acc += probs[i];
            if (r <= acc) return i;
        }
        return probs.length - 1;
    }
}