package app;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.*;

public class MenuManager {
    protected static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static final int MAX_NUMBER_OF_SHIFTS = 62;
    private static final Month month = new Month(1);
    private static final List<Worker> workers = new ArrayList<>();

    public static void setMonthNumber() throws IOException {
        System.out.println("Enter month u want to make schedule on (1, 2,..., 11, 12) : ");
        int monthNum = getNumberFromStringInputInRange(1, 12);
        month.setMonthNumber(monthNum);
    }
    public static void changeMonthAndYear() throws IOException {
        setMonthNumber();
        System.out.println("Do you want to change year too ? ");
        System.out.println("1. yes");
        System.out.println("2. no");

        int answer = getNumberFromStringInputInRange(1, 2);
        if (answer == 1){
            System.out.println("Please enter year :");
            int year = getNumberFromStringInputInRange(1900, 3000);
            month.setYear(year);
        }
    }
    public static void setWorkersList() throws IOException {
        System.out.println("Enter number of workers : ");

        int numberOfWorkers = getNumberFromStringInputInRange(1, 100);
        for (int i = 0; i < numberOfWorkers; i++) {
            addNewWorkerToList(workers);
        }
        System.out.println("Your workers : ");
        System.out.println(workers);

    }

    public static void addNewWorkerToList(List<Worker> workers) throws IOException {
        System.out.println("Enter name :");
        String name = reader.readLine();

        System.out.println("Enter number of shifts for this worker per month: ");

        int shifts = getNumberFromStringInputInRange(1, MAX_NUMBER_OF_SHIFTS);

        Worker worker = new Worker(name, shifts);
        workers.add(worker);
        System.out.printf("You added worker : " + name + ", who has " + shifts + " shift to work this month \n");
    }
    public static void generateSchedule() throws IOException {

        if (workers.isEmpty()) {
            System.out.println("Please enter number of workers first.");
            return;
        }
        //set number of people on each shift
        int [][] numberOfPeopleOnShift;
        numberOfPeopleOnShift = setNumberOfPeopleOnShift();
        if (numberOfPeopleOnShift == null){
            return;
        }

        System.out.println("Enter way u want to generate schedule with : ");
        System.out.println("1. Based on free days, rotation");
        System.out.println("2. Based on number of shifts they have to work this month");

        int algorithmChoice = getNumberFromStringInputInRange(1,2);
        if (algorithmChoice == 1){ // schedule based on rotation
            makeScheduleOnRotation(numberOfPeopleOnShift);
        }else {
            makeScheduleOnEachWorkerShiftsNumber(numberOfPeopleOnShift);
        }

        System.out.println();
        //ask if You want to save this to the file
        ////// change it to make it serializable !!!
        System.out.println("Do You want to save this to the file ? ");
        System.out.println("1. Yes");
        System.out.println("2. No");

        int fileSaving = getNumberFromStringInputInRange(1,2);
        if (fileSaving == 1){

            System.out.println("Enter name of a file :");
            String filename = reader.readLine() + ".txt";
            FileWriter fileWriter = new FileWriter(filename);

            for (Day day : month.getDays()){
                fileWriter.write(day.toString());
            }
            fileWriter.close();
            Path path = Path.of(filename);
            System.out.println("Your file has been saved as " + filename + " at path : \n " + path.toAbsolutePath());

        }
        //// serializable up to this point
    }
    /////MAKE IT WORK
    private static void makeScheduleOnEachWorkerShiftsNumber(int[][] numberOfPeopleOnShift) {


        // can't have workers with the same name !
        Map<Worker, Integer> shiftsInThisMonth = new HashMap<>();

        //empty worker is put if no more workers available
        Worker emptyWorker = new Worker("EMPTY", 900);
        shiftsInThisMonth.put(emptyWorker, 100);

        //initialize map with workers and 0 hours yet
        for (Worker worker: workers){
            shiftsInThisMonth.put(worker, 0);
        }

        //initialize temporary workers list
        List<Worker> tempWorkers = workers;

        //iteration on every day in month
        for (int i = 0; i < month.getNumberOfDaysInThisMonth(); i++) {
            Day day = new Day(i +1);

            //filling people required on 1 shift
            for (int j = 0; j < numberOfPeopleOnShift[i][0]; j++) {

                Worker worker;
                if (tempWorkers.isEmpty()){
                    worker = emptyWorker;
                }else {
                    worker = getNextWorkerFromList(tempWorkers, i+1);
                }

                day.addWorkerTo1Shift(worker);
                // save num of shift for this month
                int numOfShifts = shiftsInThisMonth.get(worker);
                shiftsInThisMonth.put(worker, ++numOfShifts);

                //check if norm for this month is reached
                removeWorkerIfHeHasDoneAllHours(numOfShifts, worker, tempWorkers);
            }
            //filling people required on 2 shift
            for (int k = 0; k < numberOfPeopleOnShift[i][1]; k++) {

                Worker worker;
                if (tempWorkers.isEmpty()){
                    worker = emptyWorker;
                }else {
                    worker = getNextWorkerFromList(tempWorkers, i+1);
                }

                day.addWorkerTo2Shift(worker);
                // save num of shift for this month
                int numOfShifts = shiftsInThisMonth.get(worker);
                shiftsInThisMonth.put(worker, ++numOfShifts);

                //check if norm for this month is reached
                removeWorkerIfHeHasDoneAllHours(numOfShifts, worker, tempWorkers);
            }
            //add full day to days list in month
            month.addDay(day);
        }
        System.out.println(month);
    }
    /////MAKE IT WORK
    private static void makeScheduleOnRotation(int[][] numberOfPeopleOnShift) {
        //iterate over days
        for (int i = 0; i < month.getNumberOfDaysInThisMonth(); i++) {
            Day day = new Day(i +1);
            //fill 1 shift
            for (int j = 0; j < numberOfPeopleOnShift[i][0]; j++) {
                day.addWorkerTo1Shift(getNextWorkerFromList(workers, i+1));
            }
            //fill 2 shift
            for (int j = 0; j < numberOfPeopleOnShift[i][1]; j++) {
                day.addWorkerTo2Shift(getNextWorkerFromList(workers, i+1));
            }
            //add day to days list
            month.addDay(day);
        }
    }

    private static int [][] setNumberOfPeopleOnShift() throws IOException {

        int [][] numberOfPeopleOnShift = new int[month.getNumberOfDaysInThisMonth()][2];
        System.out.println("What you want to do :");
        System.out.println("1. enter number of people on each shift by hand");
        System.out.println("2. enter number of people on each shift for all days the same");
        System.out.println("3. back to menu");

        int answer = getNumberFromStringInputInRange(1,3);
        if (answer == 1){

            for (int day = 1; day <= month.getNumberOfDaysInThisMonth(); day++){
                System.out.println("Day: " + day + " enter number of people on first shift: ");
                int pplOn1shift = getNumberFromStringInputInRange(0, workers.size());
                numberOfPeopleOnShift[day-1][0] = pplOn1shift;

                System.out.println("Day: " + day + " enter number of people on second shift: ");
                int pplOn2shift = getNumberFromStringInputInRange(0, workers.size());
                numberOfPeopleOnShift[day-1][1] = pplOn2shift;
            }
        } else if (answer == 2) {
            System.out.println("Enter number of workers on each shift everyday");
            int numOfWorkersEachShift = getNumberFromStringInputInRange(0, workers.size());
            for (int[] day : numberOfPeopleOnShift){
                day[0] = numOfWorkersEachShift;
                day[1] = numOfWorkersEachShift;
            }
        }else {
            return null;
        }
        return numberOfPeopleOnShift;
    }

    public static void seeOrDeleteFreeDaysOfWorker() throws IOException {
        //choose worker
        String message = "Which worker You want see free days ?";
        printWorkersWithNumbersMessage(message);

        Worker chosenWorker;
        int workerToDayOff = getNumberFromStringInputInRange(-1, workers.size());

        if (workerToDayOff > 0 && workerToDayOff <= workers.size()) {

            //check if he has any days off
            chosenWorker = workers.get(workerToDayOff - 1);
            if (chosenWorker.getFreeDays().isEmpty()){
                System.out.println("This worker doesn't have days off!");
                return;
            }else { //print out his days off
                System.out.println("You choose worker : " + chosenWorker.getName());
                System.out.println("Free days of this worker are : ");
                System.out.println(chosenWorker.getFreeDays());
            }

        } else {return;}

        // delete free days if chosen
        System.out.println("Do you want to delete them or go back ?");
        System.out.println("1. delete free days");
        System.out.println("2. go to menu");

        int input = getNumberFromStringInputInRange(1, 2);
        if (input == 1){
            chosenWorker.deleteFreeDays();
            System.out.println("Free days for worker " + chosenWorker.getName() + " deleted");
        }
    }

    public static Worker getNextWorkerFromList(List<Worker> workers, int day){

        // if worker is doesn't have free day put him on schedule
        for (Worker worker:workers) {
            if (!worker.getFreeDays().contains(day) ) {
                putWorkerOnTheEndOfList(workers);
                return worker;
            }
        }
        return new Worker("NO MORE WORKERS", 900);
    }

    public static void deleteWorkersFromTheList() throws IOException {

        //if no workers return
        if (workers.isEmpty()){
            System.out.println("You need to add workers first!");
            return;
        }

        String message = "Which worker You want to delete ?";
        printWorkersWithNumbersMessage(message);

        int workerToDelete = getNumberFromStringInputInRange(-1, workers.size());
        if (workerToDelete == -1){
            return;
        } else if (workerToDelete >= 0 && workerToDelete <= workers.size()) {
            workers.remove(workerToDelete - 1);

        }else {
            System.out.println("Wrong number entered");
        }
    }
    private static void printWorkersWithNumbersMessage(String message){
        System.out.println(message);
        for (int i = 1; i < workers.size()+1; i++) {
            System.out.println(i +". " + workers.get(i-1));
        }
        System.out.println("Enter his number or if You want to go back enter '-1' : ");
    }
    public static void addFreeDaysToAWorker() throws IOException {
        String message = "Which worker You want to add free days ?";
        printWorkersWithNumbersMessage(message);

        int workerToDayOff = Integer.parseInt(reader.readLine());
        if (workerToDayOff == -1){
            return;
        } else if (workerToDayOff > 0 && workerToDayOff <= workers.size()) {

            Worker chosenWorker =  workers.get(workerToDayOff - 1);
            System.out.println("You choose worker : " + chosenWorker.getName());
            System.out.println("Enter free days for him, each on new line:");
            System.out.println("If You want to stop enter any number other than (1-31)");

            boolean entering = true;
            List<Integer> daysOff = new ArrayList<>();

            while (entering){
                int dayOff = Integer.parseInt(reader.readLine());
                if (dayOff > 0 && dayOff <= 31){
                    daysOff.add(dayOff);
                }else {
                    entering = false;
                }
            }
            chosenWorker.setFreeDays(daysOff);

            System.out.println("Free days for worker " + chosenWorker + " are : " + daysOff);
        }else {
            System.out.println("Wrong number entered");
        }
    }
    public static void changeShiftsOfAWorker() throws IOException {

        String message = "Which worker You want to change number of shifts ?";

        printWorkersWithNumbersMessage(message);

        int workerChangeShifts = getNumberFromStringInputInRange(-1, workers.size());
        if (workerChangeShifts == -1){
            return;
        } else if (workerChangeShifts > 0 && workerChangeShifts <= workers.size()) {
            Worker chosenWorker =  workers.get(workerChangeShifts - 1);
            System.out.println("You choose worker : " + chosenWorker.getName());
            System.out.println("Current number of shifts is " + chosenWorker.getShiftsPerMonth());
            System.out.println("Enter new number of shifts :");

            int newShifts = getNumberFromStringInputInRange(0, MAX_NUMBER_OF_SHIFTS);
            chosenWorker.setShiftsPerMonth(newShifts);

            System.out.println("You set " +  newShifts + " shifts for " + chosenWorker.getName());
        }else {
            System.out.println("Wrong number entered");
        }
    }
    public static void printOutMainMenuLayout(){
        System.out.println();
        System.out.println(month.getFormattedDate());
        System.out.println();
        System.out.println("Menu:");
        System.out.println();
        System.out.println("1. Add workers");
        System.out.println("2. See free days of a worker or delete them");
        System.out.println("3. Generate schedule for the month");
        System.out.println("4. Delete worker from list");
        System.out.println("5. Change month or a year u want to make schedule on");
        System.out.println("6. Add free days for worker");
        System.out.println("7. Change number of shifts per month");
        System.out.println("9. Quit");
    }

    public static int getNumberFromStringInputInRange(int min, int max) throws IOException {
        String tryNumber;
        boolean validNumber = false;
        int num;
        do {
            while (!isStringANumber(tryNumber = reader.readLine())){
                System.out.println("You must enter a number");
            }
            num = Integer.parseInt(tryNumber);
            if (num < min || num > max){
                System.out.println("You must enter a number between " + min + " and " + max);
            } else {
                validNumber = true;
            }
        } while (!validNumber);
        return num;
    }

    public static void putWorkerOnTheEndOfList(List<Worker> workers){
        Worker temp = workers.remove(0);
        workers.add(temp);
    }

    public static void removeWorkerIfHeHasDoneAllHours(int days, Worker worker, List<Worker> tempWorkers){
        if (days >= worker.getShiftsPerMonth()){
            tempWorkers.remove(worker);
        }
    }

    public static boolean isStringANumber(String numInString){
        try {
            Integer.parseInt(numInString);
        }catch (NumberFormatException e ){
            System.out.println("Not a number, try again");
            return false;
        }
        return true;
    }
}
