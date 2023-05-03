package app;

public class Day {
    private final int number;
    private final Shift morningShift;
    private final Shift afternoonShift;

    public Day(int number ){
        this.number = number;
        morningShift = new Shift(true);
        afternoonShift = new Shift(false);
    }
    public void addWorkerTo1Shift(Worker worker){
        morningShift.addWorker(worker);
    }
    public void addWorkerTo2Shift(Worker worker){
        afternoonShift.addWorker(worker);
    }

    public Shift getMorningShift() {
        return morningShift;
    }

    public Shift getAfternoonShift() {
        return afternoonShift;
    }

    //Prints out number of the day and each shift
    @Override
    public String toString() {
        return "\n [Day:" + number  + "] \n" + morningShift + "\n" + afternoonShift;
    }
}
