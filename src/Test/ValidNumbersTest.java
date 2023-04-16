package Test;

import app.MenuManager;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;



public class ValidNumbersTest {
    @Test
    public void testIsValidNumberOfShifts_ValidNumbers() {
        Assert.assertTrue(MenuManager.IsValidNumberOfShifts("1"));
        Assert.assertTrue(MenuManager.IsValidNumberOfShifts("62"));
    }

    @Test
    public void testIsValidNumberOfShifts_InvalidNumbers() {
        Assert.assertFalse(MenuManager.IsValidNumberOfShifts("-1"));
        Assert.assertFalse(MenuManager.IsValidNumberOfShifts("0"));
        Assert.assertFalse(MenuManager.IsValidNumberOfShifts("63"));
        Assert.assertFalse(MenuManager.IsValidNumberOfShifts("abc"));
        Assert.assertFalse(MenuManager.IsValidNumberOfShifts(""));
    }

    @Test
    public void testIsValidNumber_ValidNumbers() {
        Assert.assertTrue(MenuManager.IsValidNumber("123"));
        Assert.assertTrue(MenuManager.IsValidNumber("1"));
        Assert.assertTrue(MenuManager.IsValidNumber("0"));
    }

    @Test
    public void testIsValidNumber_InvalidNumbers() {
        Assert.assertFalse(MenuManager.IsValidNumber("abc"));
        Assert.assertFalse(MenuManager.IsValidNumber("-1"));
        Assert.assertFalse(MenuManager.IsValidNumber("12a"));
        Assert.assertFalse(MenuManager.IsValidNumber(""));
    }

    @Test
    public void testGetNumberFromStringInputInRange() throws IOException {
        // Simulate user input of "5"
        String input = "5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Call the function with a range of 1 to 10
        int result = MenuManager.GetNumberFromStringInputInRange(1, 10);

        // Assert that the result is 5
        assertEquals(5, result);
    }

}
