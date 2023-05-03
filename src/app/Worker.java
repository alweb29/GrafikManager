package app;

import java.util.ArrayList;
import java.util.List;

public class Worker {
    private final String name;
    // Contains number of shifts that this worker has to make this month
    private int shiftsPerMonth;
    private List<Integer> freeDays;

    public Worker(String name, int shiftsPerMonth){
        this.name = name;
        this.shiftsPerMonth = shiftsPerMonth;
        freeDays = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public void setFreeDays(List<Integer> freeDays) {
        this.freeDays = freeDays;
    }
    public List<Integer> getFreeDays(){
        return freeDays;
    }
    public void deleteFreeDays (){
        freeDays.clear();
    }
    public void setShiftsPerMonth(int shiftsPerMonth) {
        this.shiftsPerMonth = shiftsPerMonth;
    }
    public int getShiftsPerMonth() {
        return shiftsPerMonth;
    }

    @Override
    public String toString() {
        return name;
    }
}
