package app;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        // setting up correct month to make schedule on
        MenuManager.setMonthNumber();

        boolean isRunning = true;
        while (isRunning) {

            //main menu layout
            MenuManager.printOutMainMenuLayout();

            int choice = Integer.parseInt(MenuManager.reader.readLine());

            switch (choice) {
                //entering workers
                case 1 -> {
                    MenuManager.setWorkersList();
                }
                // See or delete free days of a worker
                case 2 -> {
                    MenuManager.seeOrDeleteFreeDaysOfWorker();
                }
                // generate schedule
                case 3 -> {
                    MenuManager.generateSchedule();
                }
                // deleting worker from the list
                case 4 ->{
                    MenuManager.deleteWorkersFromTheList();
                }
                //change month or year u want to make schedule on
                case 5 ->{
                        MenuManager.changeMonthAndYear();
                }
                //add free days for a worker
                case 6 ->{
                    MenuManager.addFreeDaysToAWorker();
                }
                // changing number of shifts of a worker
                case 7 ->{
                    MenuManager.changeShiftsOfAWorker();
                }
                // quiting program
                case 9 -> isRunning = false;
                //default answer if number given doesn't exist
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}