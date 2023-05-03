package test;

import app.Shift;
import app.Worker;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShiftTest {

    @Test
    public void isMorningShift_TRUE() {
        Shift shift1 = new Shift(true);
        assertTrue(shift1.isMorningShift());

        Shift shift2 = new Shift(false);
        assertFalse(shift2.isMorningShift());
    }

    @Test
    public void isMorningShift_FALSE() {
        Shift shift1 = new Shift(false);
        assertFalse(shift1.isMorningShift());

        Shift shift2 = new Shift(true);
        assertTrue(shift2.isMorningShift());
    }
    @Test
    public void addWorker_getWorkers_Valid() {
        Shift shift = new Shift(true);
        Worker worker = new Worker("XYZ", 20);
        shift.addWorker(worker);
        assertEquals(worker.getName(), shift.getWorkers().get(0).getName());
        assertEquals(worker.getShiftsPerMonth(), shift.getWorkers().get(0).getShiftsPerMonth());

        Worker newWorker = new Worker("ABC", 60);
        shift.addWorker(newWorker);
        //1 worker
        assertEquals(worker.getName(), shift.getWorkers().get(0).getName());
        assertEquals(worker.getShiftsPerMonth(), shift.getWorkers().get(0).getShiftsPerMonth());
        //2 worker
        assertEquals(newWorker.getName(), shift.getWorkers().get(1).getName());
        assertEquals(newWorker.getShiftsPerMonth(), shift.getWorkers().get(1).getShiftsPerMonth());
    }
}