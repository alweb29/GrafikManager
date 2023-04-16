package app;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MenuManager {
    private static final int numOfDays = 0;
    protected static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    protected static Calendar calendar = Calendar.getInstance();
    protected static Date date;
    private static Month currentMonth;

    private static ArrayList<Worker> workers = new ArrayList<>();

    public static Month getCurrentMonth() {
        return currentMonth;
    }

    public static void setCurrentMonth(Month currentMOnth) {
        MenuManager.currentMonth = currentMOnth;
    }

    public static int getNumOfDays() {
        return numOfDays;
    }

    public static void SetMonthNumber(Month month) throws IOException {

        boolean rightMonth = false;
        int monthNum;
        while (!rightMonth){

            System.out.println("Enter month u want to make schedule on (1, 2,...11, 12) : ");

            monthNum = Integer.parseInt(reader.readLine()) - 1;

            if (monthNum >= 0 && monthNum <= 11){
                rightMonth = true;
                month.setMonthNumber(monthNum);
            }else {
                System.out.println("You entered wrong number");
            }
        }
    }
    public static void ChangeMonthAndYear(Month month) throws IOException {
        setCurrentMonth(month);
        System.out.println("Do you want to change year too ? ");
        System.out.println("1. yes");
        System.out.println("2. no");

        int answer = Integer.parseInt(reader.readLine());
        if (answer == 1){
            System.out.println("Please enter year :");
            int year = Integer.parseInt(reader.readLine());
            calendar.set(Calendar.YEAR , year);
        }

        date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("MMM yyyy");
        System.out.println("Chosen date is : " + dateFormat.format(date));
    }
    public static void SetWorkersList() throws IOException {
        System.out.println("Enter number of workers : ");

        int numberOfWorkers = GetNumberFromStringInputInRange(0, 100);
        for (int i = 0; i < numberOfWorkers; i++) {
            AddNewWorkerToList(workers);
        }
        System.out.println("Your workers : ");
        System.out.println(workers);

    }

    public static void AddNewWorkerToList(ArrayList<Worker> workers) throws IOException {
        System.out.println("Enter name :");
        String name = reader.readLine();

        System.out.println("Enter number of shifts for this worker per month: ");
        String shifts;

        while (!IsValidNumberOfShifts(shifts = reader.readLine())){
            System.out.println("You must enter number between 1 and 62");
        }

        Worker worker = new Worker(name, Integer.parseInt(shifts));
        workers.add(worker);
        System.out.printf("You added worker : " + name + ", who has " + shifts + " shift to work this month \n");
    }
    public static void GenerateSchedule() throws IOException {
        if (workers.isEmpty()) {
            System.out.println("Please enter number of workers and days first.");
            return;
        }

        int [][] numOfpplOnShift = new int[MenuManager.getNumOfDays()][2];
        ArrayList<Day> days = new ArrayList<>(MenuManager.getNumOfDays());

        //enter number of people on each shift
        System.out.println("What you want to do :");
        System.out.println("1. enter number of people on each shift by hand");
        System.out.println("2. enter number of people on each shift for all");
        System.out.println("3. back to menu");

        int answer = Integer.parseInt(MenuManager.reader.readLine());
        if (answer == 1){
            for (int day = 1; day <= MenuManager.getNumOfDays(); day++){
                System.out.println("app.Day: " + day + " enter number of people on first shift: ");
                int pplOn1shift = Integer.parseInt(MenuManager.reader.readLine());
                System.out.println("app.Day: " + day + " enter number of people on second shift: ");
                int pplOn2shift = Integer.parseInt(MenuManager.reader.readLine());
                numOfpplOnShift[day-1][0] = pplOn1shift;
                numOfpplOnShift[day-1][1] = pplOn2shift;
            }
        } else if (answer == 2) {
            System.out.println("Enter number of workers on each shift");
            int numOfWorkersEachShift = Integer.parseInt(MenuManager.reader.readLine());
            for (int[] day : numOfpplOnShift){
                day[0] = numOfWorkersEachShift;
                day[1] = numOfWorkersEachShift;
            }
        }else {
            return;
        }

        System.out.println("Enter way u want to generate schedule with : ");
        System.out.println("1. Based on free days, rotation");
        System.out.println("2. Based on number of shifts they have to work this month");

        int algorithmChoice = Integer.parseInt(MenuManager.reader.readLine());
        if (algorithmChoice == 1){ // schedule based on rotation

            //add worker to schedule of days
            for (int i = 0; i < MenuManager.getNumOfDays(); i++) {
                Day day = new Day(i +1);
                days.add(day);
                for (int j = 0; j < numOfpplOnShift[i][0]; j++) {
                    day.addWorkerTo1Shift(getNextWorkerFromList(workers, i+1, 1));
                }
                for (int j = 0; j < numOfpplOnShift[i][1]; j++) {
                    day.addWorkerTo2Shift(getNextWorkerFromList(workers, i+1, 2));
                }
            }
        }else {// schedule based on num of shifts

            ArrayList<Worker> tempWorkers = workers;
            // can't have workers with the same name !
            HashMap<Worker, Integer> shiftsInThisMonth = new HashMap<>();
            //empty worker if no more available
            Worker emptyWorker = new Worker("EMPTY", 900);
            shiftsInThisMonth.put(emptyWorker, 100);

            for (Worker worker: workers){
                shiftsInThisMonth.put(worker, 0);
            }

            for (int i = 0; i < MenuManager.getNumOfDays(); i++) {
                Day day = new Day(i +1);
                days.add(day);
                for (int j = 0; j < numOfpplOnShift[i][0]; j++) {

                    Worker worker;
                    if (tempWorkers.isEmpty()){
                        worker = emptyWorker;
                    }else {
                        worker = getNextWorkerFromList(tempWorkers, i+1, 1);
                    }

                    day.addWorkerTo1Shift(worker);
                    // save num of shift for this month
                    int numOfShifts = shiftsInThisMonth.get(worker);

                    shiftsInThisMonth.put(worker, ++numOfShifts);
                    //check if norm for this month is reached
                    checkShifts(numOfShifts, worker, tempWorkers);
                }
                for (int j = 0; j < numOfpplOnShift[i][1]; j++) {

                    Worker worker;
                    if (tempWorkers.isEmpty()){
                        worker = emptyWorker;
                    }else {
                        worker = getNextWorkerFromList(tempWorkers, i+1, 2);
                    }

                    day.addWorkerTo2Shift(worker);
                    int numOfShifts = shiftsInThisMonth.get(worker);
                    shiftsInThisMonth.put(worker, ++numOfShifts);
                    checkShifts(numOfShifts, worker, tempWorkers);
                }
            }
        }
        System.out.println(days);
        //ask if You want to save this to the file
        System.out.println("Do You want to save this to the file ? ");
        System.out.println("1. Yes");
        System.out.println("2. No");

        int fileSaving = Integer.parseInt(MenuManager.reader.readLine());
        if (fileSaving == 1){

            System.out.println("Enter name of a file :");
            String filename = MenuManager.reader.readLine() + ".txt";
            FileWriter fileWriter = new FileWriter(filename);

            for (Day day : days){
                fileWriter.write(day.toString());
            }
            fileWriter.close();
            Path path = Path.of(filename);
            System.out.println("Your file has been saved as " + filename + " at path : \n " + path.toAbsolutePath());

        }
    }

    public static void SetFreeDaysForWorker() throws IOException {

            System.out.println("Which worker You want see free days ?");
            for (int i = 1; i < workers.size()+1; i++) {
                System.out.println(i +". " + workers.get(i-1));
            }
            System.out.println("Enter his number or if You want to go back enter " +  -1 +" : ");
            Worker chosenWorker;

            int workerToDayOff = Integer.parseInt(reader.readLine());
            if (workerToDayOff == -1){
                return;
            } else if (workerToDayOff >= 0 && workerToDayOff <= workers.size()) {

                chosenWorker = workers.get(workerToDayOff - 1);
                if (chosenWorker.getDaysOffWork() == null || chosenWorker.getDaysOffWork().isEmpty()){
                    System.out.println("This worker doesn't have days off!");
                    return;
                }else {
                    System.out.println("You choose worker : " + chosenWorker.getName());
                    System.out.println("Free days of this worker are : ");
                    System.out.println(chosenWorker.getDaysOffWork());
                }
            }else {
                return;
            }
            System.out.println("Do you want to delete them or go back ?");
            System.out.println("1. delete");
            System.out.println("2. go to menu");
            int input = Integer.parseInt(MenuManager.reader.readLine());
            if (input == 1){
                chosenWorker.deleteFreeDays();
                System.out.println("Free days for worker " + chosenWorker.getName() + " deleted");
            }

    }
///////////////USED for alogrithm
    public static Worker getNextWorkerFromList(ArrayList<Worker> workers, int day, int shift){

        // if not free get him to work!
        for (Worker worker:workers) {
            if (!worker.getFreeDays().contains(day) ) {
                 popWorker(workers);
                worker.addDayOfWork(day, shift);
                return worker;
            }
        }
        return new Worker("NO MORE WORKERS", 900);
    }

    public static void DeleteWorkersFromTheList() throws IOException {

        if (workers.isEmpty()){
            System.out.println("You need to add workers first!");
            return;
        }
        System.out.println("Which worker You want to delete ?");
        for (int i = 1; i < workers.size()+1; i++) {
            System.out.println(i +". " + workers.get(i-1));
        }
        System.out.println("Enter his number or if You want to go back enter " +  -1 +" : ");
        int workerToDelete = Integer.parseInt(MenuManager.reader.readLine());
        if (workerToDelete == -1){
            return;
        } else if (workerToDelete >= 0 && workerToDelete <= workers.size()) {
            workers.remove(workerToDelete - 1);

        }else {
            System.out.println("Wrong number entered");
        }
    }
    public static void AddFreeDaysToAWorker() throws IOException {
        System.out.println("Which worker You want to add free days ?");
        for (int i = 1; i < workers.size()+1; i++) {
            System.out.println(i +". " + workers.get(i-1));
        }
        System.out.println("Enter his number or if You want to go back enter [-1] : ");

        int workerToDayOff = Integer.parseInt(MenuManager.reader.readLine());
        if (workerToDayOff == -1){
            return;
        } else if (workerToDayOff > 0 && workerToDayOff <= workers.size()) {
            Worker chosenWorker =  workers.get(workerToDayOff - 1);
            System.out.println("You choose worker : " + chosenWorker.getName());
            System.out.println("Enter free days for him, each on new line:");
            System.out.println("If You want to stop enter any number other than (1-31)");

            boolean entering = true;
            ArrayList<Integer> daysOff = new ArrayList<>();

            while (entering){
                int dayOff = Integer.parseInt(MenuManager.reader.readLine());
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
    public static void ChangeNumberOfShiftsOfAWorker() throws IOException {
        System.out.println("Which worker You want to change number of shifts ?");
        for (int i = 1; i < workers.size()+1; i++) {
            System.out.println(i +". " + workers.get(i-1));
        }
        System.out.println("Enter his number or if You want to go back enter [-1] : ");

        int workerChangeShifts = Integer.parseInt(MenuManager.reader.readLine());
        if (workerChangeShifts == -1){
            return;
        } else if (workerChangeShifts > 0 && workerChangeShifts <= workers.size()) {
            Worker chosenWorker =  workers.get(workerChangeShifts - 1);
            System.out.println("You choose worker : " + chosenWorker.getName());
            System.out.println("Current number of shifts is " + chosenWorker.getShiftsPerMonth());
            System.out.println("Enter new number of shifts :");

            int newShifts;

            while ((newShifts = Integer.parseInt(MenuManager.reader.readLine())) > 62){
                System.out.println("You can't set more than 62 shifts per month, it's not humane!");
            }
            chosenWorker.setShiftsPerMonth(newShifts);

            System.out.println("You set " +  newShifts + " shifts for " + chosenWorker.getName());
        }else {
            System.out.println("Wrong number entered");
        }
    }
    public static void PrintOutMainMenuLayout(){
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

    public static boolean IsValidNumberOfShifts(String numInString){
        int num;
        try {
            num = Integer.parseInt(numInString);
        }catch (NumberFormatException e ){
            System.out.println("Not a number, try again");
            return false;
        }
        return num >0 && num <63;
    }

    public static int GetNumberFromStringInputInRange(int min, int max) throws IOException {
        String tryNumber;
        while (!IsValidNumber(tryNumber = reader.readLine())){
            System.out.println("You must enter number ");

        }
        while (Integer.parseInt(tryNumber) < min && Integer.parseInt(tryNumber) > max){
            System.out.println("You must enter number bigger than " + min + " and smaller than " + max);
            GetNumberFromStringInputInRange(min, max);
        }
        return Integer.parseInt(tryNumber);
    }

    public static void popWorker(ArrayList<Worker> workers){
        Worker temp = workers.remove(0);
        workers.add(temp);
    }
    public static void checkShifts(int days, Worker worker, ArrayList<Worker> tempWorkers){
        if (days >= worker.getShiftsPerMonth()){
            tempWorkers.remove(worker);
        }
    }

    public static boolean IsValidNumber(String numInString){
        int num;
        try {
            num = Integer.parseInt(numInString);
        }catch (NumberFormatException e ){
            System.out.println("Not a number, try again");
            return false;
        }
        return num >=0 ;
    }
}
