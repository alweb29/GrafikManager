package test;

import app.MenuManager;
import org.junit.Assert;

import static app.MenuManager.MAX_NUMBER_OF_SHIFTS;
import static org.junit.Assert.assertTrue;

import org.junit.Test;




public class ValidNumbersTest {


    @Test
    public void testIsValidNumberOfShifts_ValidNumbers() {
        assertTrue(MenuManager.isStringANumber("1"));
        assertTrue(MenuManager.isStringANumber(String.valueOf(MAX_NUMBER_OF_SHIFTS -1)));
    }

    @Test
    public void testIsValidNumberOfShifts_InvalidNumbers() {
        Assert.assertFalse(MenuManager.isStringANumber("-1"));
        Assert.assertFalse(MenuManager.isStringANumber("0"));
        Assert.assertFalse(MenuManager.isStringANumber("63"));
        Assert.assertFalse(MenuManager.isStringANumber("abc"));
        Assert.assertFalse(MenuManager.isStringANumber(""));
    }

    @Test
    public void testIsValidNumber_ValidNumbers() {
        assertTrue(MenuManager.isStringANumber("123"));
        assertTrue(MenuManager.isStringANumber("1"));
        assertTrue(MenuManager.isStringANumber("0"));
        assertTrue(MenuManager.isStringANumber("-1"));
    }

    @Test
    public void testIsValidNumber_InvalidNumbers() {
        Assert.assertFalse(MenuManager.isStringANumber("abc"));
        Assert.assertFalse(MenuManager.isStringANumber("12a"));
        Assert.assertFalse(MenuManager.isStringANumber(""));
    }
}
