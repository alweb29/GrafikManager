package Test;

import app.Month;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MonthOperationsTest {

    @Test
    public void SetMonthNumber_Valid(){
        Month month = new Month(1);
        month.setMonthNumber(3);
        assertEquals(3, month.getMonthNumber());
        month.setMonthNumber(12);
        assertEquals(12, month.getMonthNumber());
    }

    @Test
    public void GetMonthNumber_Valid(){
        Month month = new Month(2);
        assertEquals(2, month.getMonthNumber());
        month.setMonthNumber(12);
        assertEquals(12, month.getMonthNumber());
    }

}
