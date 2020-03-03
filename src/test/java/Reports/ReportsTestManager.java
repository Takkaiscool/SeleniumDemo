package Reports;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.util.HashMap;
import java.util.Map;

public class ReportsTestManager {

    static Map reportTestMap = new HashMap();
    static ExtentReports reports = ReportsManager.getReporter();

    public static synchronized ExtentTest getTest() {
        return (ExtentTest) reportTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized void endTest() {
        reports.endTest((ExtentTest) reportTestMap.get((int) (long) (Thread.currentThread().getId())));
    }

    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = reports.startTest(testName, desc);
        reportTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }

}
