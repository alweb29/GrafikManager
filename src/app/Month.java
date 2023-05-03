package app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Month {
    //{ can be only between 1 and 12 }
    private int monthNumber;
    private final int year;
    private final List<Day> days;
    private LocalDate date = LocalDate.now();
    private int numberOfDaysInThisMonth;
    // Define the desired output format
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMMM/yyyy");

    public Month(int monthNumber) {
        this.monthNumber = monthNumber;
        year = date.getYear();
        days = new ArrayList<>();
    }

    public void addDay(Day day) {
        this.days.add(day);
    }
    public Day getDay(Integer dayNumber){return days.get(dayNumber);}

    public List<Day> getDays() {
        return days;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    //change only monthNumber
    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
        int day = date.getDayOfMonth();
        date = LocalDate.of(year, monthNumber, day);
        updateNumberOfDaysInThisMonth();
    }

    private void updateNumberOfDaysInThisMonth(){
        this.numberOfDaysInThisMonth = date.lengthOfMonth();
    }
    public int getNumberOfDaysInThisMonth() {
        return numberOfDaysInThisMonth;
    }

    //change only year
    public void setYear(int year) {
        int day = date.getDayOfMonth();
        date = LocalDate.of(year, monthNumber, day);
        updateNumberOfDaysInThisMonth();
        System.out.println(getFormattedDate());
    }
    public String getFormattedDate(){
        return date.format(formatter);
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Day day : days){
            stringBuilder.append(day.toString()).append("\n");
        }
        return getFormattedDate() + "\n" + stringBuilder;
    }
}
