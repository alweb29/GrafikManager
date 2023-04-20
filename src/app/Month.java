package app;

import app.MenuManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Month {
    private int MonthNumber;
    private int numberOfDaysInMonth;
    private List<Day> days;

    public Month(int monthNumber) {
        MonthNumber = monthNumber;
        days = new ArrayList<>();
    }

    public int getNumberOfDaysInMonth() {
        return numberOfDaysInMonth;
    }

    public int getMonthNumber() {
        return MonthNumber;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public void setMonthNumber(int monthNumber) {
        MonthNumber = monthNumber;
        SetNumberOfDaysInMonth(monthNumber);
    }
    private void SetNumberOfDaysInMonth(int monthNumber ){
        MenuManager.calendar.set(Calendar.MONTH, monthNumber);
        MenuManager.date = MenuManager.calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("MMM yyyy");
        System.out.println("Chosen date is : " + dateFormat.format(MenuManager.date));
        numberOfDaysInMonth = MenuManager.calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
