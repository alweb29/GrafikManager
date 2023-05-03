package app;

import java.util.ArrayList;
import java.util.List;

public class Shift {
    //Identifier between morning and afternoon shift
    private final boolean MORNING_SHIFT;
    List<Worker> workers = new ArrayList<>();

    public Shift(boolean morningShift) {
        MORNING_SHIFT = morningShift;
    }

    public boolean isMorningShift() {
        return MORNING_SHIFT;
    }

    public void addWorker(Worker worker) {
        workers.add(worker);
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    @Override
    public String toString() {
        //Prints out which shift it is and workers on this shift
        String whichShift;
        if (isMorningShift()){
            whichShift = "Morning Shift";
        }else whichShift = "Afternoon Shift";
        return whichShift + "\n " + workers;
    }
}
