package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ValidNumbersTest.class,
        MonthOperationsTest.class,
        ShiftTest.class
        // Add more test classes here
})

public class TestSuite {
    // This class is just a container for the test suite
}
