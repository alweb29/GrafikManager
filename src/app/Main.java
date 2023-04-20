package app;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {


        Month month = new Month(0);
        // setting up correct month to make schedule on
        MenuManager.SetMonthNumber(month);


        boolean isRunning = true;
        while (isRunning) {

            //main menu layout
            MenuManager.PrintOutMainMenuLayout();

            int choice = Integer.parseInt(MenuManager.reader.readLine());

            switch (choice) {
                //entering workers
                case 1 -> {
                    MenuManager.SetWorkersList();
                }
                // See free days of a worker
                case 2 -> {
                    MenuManager.SeeFreeDaysForWorkerOrDeleteThem();
                }
                // generate schedule
                case 3 -> {
                    MenuManager.GenerateSchedule();
                }
                // deleting worker from the list
                case 4 ->{
                    MenuManager.DeleteWorkersFromTheList();
                }
                //change month or year u want to make schedule on
                case 5 ->{
                        MenuManager.ChangeMonthAndYear(month);
                }
                //add free days for a worker
                case 6 ->{
                    MenuManager.AddFreeDaysToAWorker();
                }
                // changing number of shifts of a worker
                case 7 ->{
                    MenuManager.ChangeNumberOfShiftsOfAWorker();
                }
                // quiting program
                case 9 -> isRunning = false;
                //default answer if number given doesn't exist
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}