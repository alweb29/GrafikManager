import java.util.ArrayList;

public class Day {
    private int number;
    private ArrayList<Worker> workersOf1Shift;
    private ArrayList<Worker> workersOf2Shift;

    public Day(int number ){
        this.number = number;
        workersOf1Shift = new ArrayList<>();
        workersOf2Shift = new ArrayList<>();
    }
    public void addWorkerTo1Shift(Worker worker){
        this.workersOf1Shift.add(worker);
    }
    public void addWorkerTo2Shift(Worker worker){
        this.workersOf2Shift.add(worker);
    }

    @Override
    public String toString() {
        return "\n[Day: " + number  + "]\n" + "[Shift 1 workers : " + workersOf1Shift + "]  [Shift 2 workers : " + workersOf2Shift + "] \n";
    }
}
