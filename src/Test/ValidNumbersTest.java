package Test;

import app.MenuManager;
import org.junit.Assert;

import static app.MenuManager.MAX_NUMBER_OF_SHIFTS;
import static org.junit.Assert.assertTrue;

import org.junit.Test;




public class ValidNumbersTest {


    @Test
    public void testIsValidNumberOfShifts_ValidNumbers() {
        assertTrue(MenuManager.IsValidNumberOfShifts("1"));
        assertTrue(MenuManager.IsValidNumberOfShifts(String.valueOf(MAX_NUMBER_OF_SHIFTS -1)));
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
        assertTrue(MenuManager.IsValidNumber("123"));
        assertTrue(MenuManager.IsValidNumber("1"));
        assertTrue(MenuManager.IsValidNumber("0"));
        assertTrue(MenuManager.IsValidNumber("-1"));
    }

    @Test
    public void testIsValidNumber_InvalidNumbers() {
        Assert.assertFalse(MenuManager.IsValidNumber("abc"));
        Assert.assertFalse(MenuManager.IsValidNumber("12a"));
        Assert.assertFalse(MenuManager.IsValidNumber(""));
    }

    /*@Test(timeout = 1000)
    public void testGetNumberFromStringInputInRange_ValidInput() throws IOException {
        String input = "5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        int result = MenuManager.GetNumberFromStringInputInRange(1, 10);
        Assert.assertEquals(5, result);
    }*/




}
