import java.util.ArrayList;
import java.util.HashMap;

public class Worker {
    private String name;

    private ArrayList<Integer> freeDays;
    // day and shift
    public HashMap<Integer, Integer> daysOfWork;

    public Worker(String name){
        this.name = name;
        daysOfWork = new HashMap<>();
        freeDays = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public void setFreeDays(ArrayList<Integer> freeDays) {
        this.freeDays = freeDays;
    }
    public ArrayList<Integer> getFreeDays(){
        return freeDays;
    }
    @Override
    public String toString() {
        return name;
    }

    public void addDayOfWork(Integer day, Integer shift) {
        daysOfWork.put(day, shift);
    }

    public ArrayList<Integer> getDaysOffWork(){
        return freeDays;
    }
    public void deleteFreeDays (){
        freeDays.clear();
    }

}
